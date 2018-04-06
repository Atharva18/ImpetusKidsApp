package com.example.parij.myschoolcomm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parij.myschoolcomm.Models.Memory;
import com.example.parij.myschoolcomm.Models.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class childdetailswindow extends AppCompatActivity {


    EditText editTextSearch;
    Spinner spinnerFilter;
    ListView listView;
    ArrayList<Student> arrayListFull, arrayListFiltered;
    ArrayList<String> arrayListKeysFiltered, arrayListKeysFull;
    ArrayList<String> arrayListDisplay, arrayListSpinner;
    DatabaseReference databaseReference;
    ArrayAdapter arrayAdapter, arrayAdapterSpinner;
    Dialog dialog;
    StorageReference storageReference;
    int selectedPosition = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_childdetailswindow);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Child Profile");
        //toolbar.setNavigationIcon(R.drawable.childprofbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        initiate();
        getDataFromDB();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void initiate() {
        editTextSearch = findViewById(R.id.editTextRoaster);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                filterList(s.toString(), spinnerFilter.getSelectedItemPosition());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterList(s.toString(), spinnerFilter.getSelectedItemPosition());
            }

            @Override
            public void afterTextChanged(Editable s) {
                filterList(s.toString(), spinnerFilter.getSelectedItemPosition());
            }
        });

        spinnerFilter = findViewById(R.id.spinnerRoaster);
        listView = findViewById(R.id.listViewRoaster);
        arrayListFull = new ArrayList<>();
        arrayListDisplay = new ArrayList<>();
        arrayListKeysFull = new ArrayList<>();
        arrayListFiltered = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference(Constants.FBDB).child("students");
        dialog = new Dialog(childdetailswindow.this, android.R.style.Theme_DeviceDefault_Light_Dialog);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDialogAndStartActivity(position);
            }
        });

        arrayListSpinner = new ArrayList<>();
        arrayListSpinner.add("All");
        arrayListSpinner.add("Day-Care");
        arrayListSpinner.add("Seeding");
        arrayListSpinner.add("Budding");
        arrayListSpinner.add("Blossoming");
        arrayListSpinner.add("Flourishing");
        arrayAdapterSpinner = new ArrayAdapter<>(childdetailswindow.this, android.R.layout.simple_spinner_dropdown_item, arrayListSpinner);
        spinnerFilter.setAdapter(arrayAdapterSpinner);

        spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterList(editTextSearch.getText().toString(), position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    void getDataFromDB() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren())//Roll
                        {
                            Student student;
                            student = ds.getValue(Student.class);
                            arrayListFull.add(student);
                            arrayListDisplay.add("Name: " + student.getName() + "\nRoll no: " + student.getRollNo() + "\nProgram: " + Constants.getProgramName(student.getProgram()));
                            Log.d("StudentObj : ", student.getName());
                            arrayListKeysFull.add(ds.getKey());
                        }
                arrayListFiltered = arrayListFull;
                arrayListKeysFiltered = arrayListKeysFull;

                arrayAdapter = new ArrayAdapter(childdetailswindow.this, android.R.layout.simple_list_item_1, arrayListDisplay) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        /// Get the Item from ListView
                        View view = super.getView(position, convertView, parent);

                        TextView tv = view.findViewById(android.R.id.text1);

                        // Set the text size 25 dip for ListView each item
                        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

                        // Return the view
                        return view;
                    }
                };
                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    void showDialogAndStartActivity(final int position) {
        dialog.setContentView(R.layout.dialog_choose_detail_type);
        dialog.setTitle("Choose action: ");
        final RadioButton radioButtonChildProfile, radioButtonParentProfile, radioButtonEmergency, radioButtonAuthorised, radioButtonMemoryCapture, radioButtonArchiveProfile, radioButtonViewMemories;
        Button button;
        radioButtonAuthorised = dialog.findViewById(R.id.radioButtonDetailTypeAuthorisedPerson);
        radioButtonChildProfile = dialog.findViewById(R.id.radioButtonDetailTypeChildProfile);
        radioButtonEmergency = dialog.findViewById(R.id.radioButtonDetailTypeEmergency);
        radioButtonParentProfile = dialog.findViewById(R.id.radioButtonDetailTypeParentProfile);
        // radioButtonMemoryCapture = dialog.findViewById(R.id.radioButtonDetailTypeCaptureMemory);
        radioButtonArchiveProfile = dialog.findViewById(R.id.radioButtonDetailTypeArchiveProfile);
        radioButtonViewMemories = dialog.findViewById(R.id.radioButtonDetailTypeViewMemory);
        button = dialog.findViewById(R.id.buttonDetailTypeOk);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Bundle bundle = new Bundle();
                bundle.putString("username", arrayListFiltered.get(position).getUsername());

                SessionManagement.username = arrayListFiltered.get(position).getUsername();
                SessionManagement.lastLoginTimestamp = System.currentTimeMillis();
                SessionManagement.updateSharedPreferences();

                if (radioButtonAuthorised.isChecked()) {
                    Intent intent = new Intent(childdetailswindow.this, adminauthorized.class);
                    // intent.putExtras(bundle);
                    startActivity(intent);

                }
                if (radioButtonChildProfile.isChecked()) {
                    Intent intent = new Intent(childdetailswindow.this, adminchild.class);
                    // intent.putExtras(bundle);
                    startActivity(intent);
                }
                if (radioButtonEmergency.isChecked()) {
                    Intent intent = new Intent(childdetailswindow.this, adminemergency.class);
                    // intent.putExtras(bundle);
                    startActivity(intent);
                }
                if (radioButtonParentProfile.isChecked()) {
                    Intent intent = new Intent(childdetailswindow.this, adminparent.class);
                    //intent.putExtras(bundle);
                    startActivity(intent);
                }
              /*  if(radioButtonMemoryCapture.isChecked())
                {
                    selectedPosition = position;
                    EasyImage.openChooserWithGallery(childdetailswindow.this, "Capture/Select image", 0);
                }*/
                if (radioButtonArchiveProfile.isChecked()) {
                    selectedPosition = position;
                    archiveProfile(position);
                }
                if (radioButtonViewMemories.isChecked()) {
                    Intent intent = new Intent(childdetailswindow.this, MemoriesUser.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();

                }
                // finish();
            }
        });
        dialog.show();
    }


    void filterList(String input, int position) {
        int programFilter = (int) arrayAdapterSpinner.getItemId(position) + 3;
        Log.d("program filter ", programFilter + "");
        arrayListFiltered = new ArrayList<>();
        arrayListDisplay = new ArrayList<>();
        arrayListKeysFiltered = new ArrayList<>();
        arrayListDisplay = new ArrayList<>();
        ArrayList<Student> arrayListFilteredNew = new ArrayList<>();
        ArrayList<String> arrayListKeysFilteredNew = new ArrayList<>();
        for (int i = 0; i < arrayListFull.size(); i++) {
            if (arrayListFull.get(i).getName().toLowerCase().contains(input.toLowerCase()) && (position == 0 || arrayListFull.get(i).getProgram() == programFilter)) {
                arrayListDisplay.add("Name: " + arrayListFull.get(i).getName() + "\nRoll no: " + arrayListFull.get(i).getRollNo() + "\nProgram: " + Constants.getProgramName(arrayListFull.get(i).getProgram()));
                arrayListFilteredNew.add(arrayListFull.get(i));
                arrayListKeysFilteredNew.add(arrayListKeysFull.get(i));
            }
        }

        arrayListFiltered = arrayListFilteredNew;
        arrayListKeysFiltered = arrayListKeysFilteredNew;

        arrayAdapter = new ArrayAdapter(childdetailswindow.this, android.R.layout.simple_list_item_1, arrayListDisplay) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                /// Get the Item from ListView
                View view = super.getView(position, convertView, parent);

                TextView tv = view.findViewById(android.R.id.text1);

                // Set the text size 25 dip for ListView each item
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

                // Return the view
                return view;
            }
        };
        listView.setAdapter(arrayAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {

                final Student student = arrayListFiltered.get(selectedPosition);
                final ArrayList<Memory> arrayListMemories = student.getMemoryImageLinks();
                final int lowestTimestampIndex = lowestTimestamp(arrayListMemories);
                storageReference = FirebaseStorage.getInstance().getReference().child("Memories").child(arrayListFiltered.get(selectedPosition).getUsername()).child(lowestTimestampIndex + "");

                final ProgressDialog progressDialog = new ProgressDialog(childdetailswindow.this);
                progressDialog.setIndeterminate(false);
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Uploading image...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.show();

                UploadTask uploadTask = storageReference.putFile(android.net.Uri.parse(imageFile.toURI().toString()));
                uploadTask.addOnFailureListener(new OnFailureListener() {

                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(childdetailswindow.this, "Image upload failed!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        progressDialog.dismiss();
                        databaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.FBDB).child("students").child(arrayListKeysFiltered.get(selectedPosition));
                        if (arrayListMemories.size() < 9)
                            arrayListMemories.add(new Memory(System.currentTimeMillis(), task.getResult().getDownloadUrl().toString()));
                        else
                            arrayListMemories.set(lowestTimestampIndex, new Memory(System.currentTimeMillis(), task.getResult().getDownloadUrl().toString()));


                        student.setMemoryImageLinks(arrayListMemories);

                        databaseReference.setValue(student);

                        Toast.makeText(childdetailswindow.this, "Upload successful", Toast.LENGTH_LONG).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.setMax((int)taskSnapshot.getTotalByteCount());
                        progressDialog.setProgress((int)taskSnapshot.getBytesTransferred());
                        progressDialog.setSecondaryProgress((int)taskSnapshot.getBytesTransferred());
                        Log.e("Task Progress", "bytesT: " + taskSnapshot.getBytesTransferred() + "bytesTot: " + taskSnapshot.getTotalByteCount());
                    }
                });

            }
        });

    }

    int lowestTimestamp(ArrayList<Memory> arrayList)
    {
        long minTS = Long.MAX_VALUE;
        int index = 0;
        if (arrayList.size() >= 9) {
            for (int i = 0; i < arrayList.size(); i++) {
                Log.e("TS: ", "ts: " + minTS + " index: " + i + " size: " + arrayList.size());
                if (arrayList.get(i).getTimestamp() < minTS) {
                    minTS = arrayList.get(i).getTimestamp();
                    index = i;
                }
            }
        } else {
            index = arrayList.size();
        }
        return index;
    }

    void archiveProfile(final int position) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int button) {
                switch (button) {
                    case DialogInterface.BUTTON_POSITIVE:
                        databaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.FBDB).child("students").child(arrayListKeysFiltered.get(position));
                        databaseReference.removeValue();
                        databaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.FBDB).child("archives");
                        databaseReference.push().setValue(arrayListFiltered.get(position));
                        arrayListKeysFull.remove(arrayListKeysFiltered.get(position));
                        arrayListFull.remove(arrayListFiltered.get(position));
                        arrayListKeysFiltered.remove(position);
                        arrayListFiltered.remove(position);
                        arrayListDisplay.remove(position);
                        Toast.makeText(childdetailswindow.this, "Done.", Toast.LENGTH_SHORT).show();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
                arrayAdapter = new ArrayAdapter(childdetailswindow.this, android.R.layout.simple_list_item_1, arrayListDisplay) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        /// Get the Item from ListView
                        View view = super.getView(position, convertView, parent);

                        TextView tv = view.findViewById(android.R.id.text1);

                        // Set the text size 25 dip for ListView each item
                        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

                        // Return the view
                        return view;
                    }
                };
                listView.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();
            }
        };


        AlertDialog.Builder builder = new AlertDialog.Builder(childdetailswindow.this);
        builder.setMessage("Confirm archiving selected profile?").setPositiveButton("Yes, archive.", dialogClickListener).setNegativeButton("No.", dialogClickListener).show();


    }
}




/*
 if (flag == 1) {
                               Toast.makeText(getApplicationContext(), "Entry found!", Toast.LENGTH_LONG).show();
                               Intent intent = new Intent(childdetailswindow.this,adminchild.class);
                                Bundle bundle=new Bundle();
                                bundle.putString("username", user[0]);
                                intent.putExtras(bundle);
                                startActivity(intent);


                           }


 */