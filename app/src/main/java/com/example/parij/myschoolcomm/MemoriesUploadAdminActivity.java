package com.example.parij.myschoolcomm;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.darsh.multipleimageselect.activities.AlbumSelectActivity;
import com.darsh.multipleimageselect.models.Image;
import com.example.parij.myschoolcomm.Models.Memory;
import com.example.parij.myschoolcomm.Models.Programs;
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


public class MemoriesUploadAdminActivity extends AppCompatActivity {


    RadioButton radioButtonAll, radioButtonSeeding, radioButtonBudding, radioButtonBlossoming, radioButtonFlourishing, radioButtonDayCare;
    Button button;
    AlertDialog dialog = null;
    View view;
    String myProgram = "";

    //  ArrayList<Programs> programsArrayList = new ArrayList<>();
    StorageReference storageReference;
    Bundle bundle = new Bundle();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memories_admin);
        AlertDialog.Builder build = new AlertDialog.Builder(MemoriesUploadAdminActivity.this);
        view = getLayoutInflater().inflate(R.layout.dialog_choose_program, null);
        initialise();
/*
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("newDb").child("Programs");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Programs programs;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    programs = ds.getValue(Programs.class);
                    programsArrayList.add(programs);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
*/
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (radioButtonAll.isChecked()) {
                    select_photo((Constants.getProgramName(4)));
                    select_photo((Constants.getProgramName(5)));
                    select_photo((Constants.getProgramName(6)));
                    select_photo((Constants.getProgramName(7)));
                    select_photo((Constants.getProgramName(8)));
                    dialog.cancel();
                } else if (radioButtonSeeding.isChecked()) {
                    select_photo((Constants.getProgramName(5)));
                    dialog.cancel();
                } else if (radioButtonBudding.isChecked()) {
                    select_photo((Constants.getProgramName(6)));
                    dialog.cancel();
                } else if (radioButtonBlossoming.isChecked()) {
                    select_photo((Constants.getProgramName(7)));
                    dialog.cancel();
                } else if (radioButtonFlourishing.isChecked()) {
                    select_photo((Constants.getProgramName(8)));
                    dialog.cancel();
                } else if (radioButtonDayCare.isChecked()) {
                    select_photo((Constants.getProgramName(4)));
                    dialog.cancel();
                }

            }
        });
        build.setView(view);
        dialog = build.create();
        dialog.show();
    }


    void select_photo(String program) {

        myProgram = program;

      /* bundle.putString("Program", program);
         bundle.putString(com.darsh.multipleimageselect.helpers.Constants.INTENT_EXTRA_LIMIT, String.valueOf(10));

        for (String key : bundle.keySet()) {
            Log.d("myApplication", key + " is a key in the bundle");
        }
        */
        Intent intent = new Intent(MemoriesUploadAdminActivity.this, AlbumSelectActivity.class);
        // intent.putExtras(bundle);
        startActivityForResult(intent, com.darsh.multipleimageselect.helpers.Constants.REQUEST_CODE);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == com.darsh.multipleimageselect.helpers.Constants.REQUEST_CODE && resultCode == RESULT_OK && data != null) {

            ArrayList<Image> images = new ArrayList<>();
            images = data.getParcelableArrayListExtra(com.darsh.multipleimageselect.helpers.Constants.INTENT_EXTRA_IMAGES);
            Log.e("Images", images.get(0).path);
            String program = myProgram;
            uploadPhoto(images, program);
        }
    }

    void uploadPhoto(final ArrayList<Image> images, final String program) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("newDb").child("Programs").child(program);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Memory> arrayListMemories = new ArrayList<>();

                arrayListMemories = (ArrayList<Memory>) dataSnapshot.child("memoryImageLinks").getValue();

                   /* Toast.makeText(MemoriesUploadAdminActivity.this,"Here1",Toast.LENGTH_SHORT).show();
                    Log.d("value",dataSnapshot.getValue().toString());
                    Log.d("value",ds.getValue().toString());
                    if(dataSnapshot.getValue().equals(program))
                    {
                        Toast.makeText(MemoriesUploadAdminActivity.this,"Here",Toast.LENGTH_SHORT).show();
                        programs = ds.getValue(Programs.class);
                        break;

                    }*/


                // Log.d("progobject",programs.toString());
                // ArrayList<Memory> arrayListMemories = new ArrayList<>();
                //  arrayListMemories=programs.getMemoryImageLinks();
                final int lowestTimestampIndex = lowestTimestamp(arrayListMemories);
                // final Programs progobj = programs;
                storageReference = FirebaseStorage.getInstance().getReference().child("Memories").child(program).child(lowestTimestampIndex + "");

                firebaseUploadTask(0, images, arrayListMemories, lowestTimestampIndex, program);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


       /* if (programsArrayList.size() != 0) {
            for (Programs prog : programsArrayList) {
                if (prog.getName().equals(program)) {
                    programs = prog;
                }
            }
        } else {
            programs.setName(program);
            programs.setMemoryImageLinks(null);
        }*/



    }

    void firebaseUploadTask(final int i, final ArrayList<Image> images, final ArrayList<Memory> arrayListMemories, final int lowestTimestampIndex, final String program) {
        final ProgressDialog progressDialog = new ProgressDialog(MemoriesUploadAdminActivity.this);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Uploading image... " + (i + 1) + " of " + images.size());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
        File file = new File(String.valueOf(images.get(i).path));
        Uri uri = Uri.fromFile(file);
        UploadTask uploadTask = storageReference.putFile(Uri.parse(uri.toString()));
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MemoriesUploadAdminActivity.this, "Image upload failed!", Toast.LENGTH_SHORT).show();
                if (i == images.size() - 1) {
                    startActivity(new Intent(MemoriesUploadAdminActivity.this, admindashboard.class));
                    finish();
                } else {
                    firebaseUploadTask(i + 1, images, arrayListMemories, lowestTimestampIndex + 1, program);
                }
            }
        }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                progressDialog.dismiss();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.FBDB).child("Programs");
                if (arrayListMemories.size() < 9)
                    arrayListMemories.add(new Memory(System.currentTimeMillis(), task.getResult().getDownloadUrl().toString()));
                else
                    arrayListMemories.set(lowestTimestampIndex, new Memory(System.currentTimeMillis(), task.getResult().getDownloadUrl().toString()));

                Programs progobj = new Programs();
                progobj.setMemoryImageLinks(arrayListMemories);
                databaseReference.child(program).setValue(progobj);

                Toast.makeText(MemoriesUploadAdminActivity.this, "Upload successful", Toast.LENGTH_LONG).show();
                if (i == images.size() - 1) {
                    startActivity(new Intent(MemoriesUploadAdminActivity.this, admindashboard.class));
                    finish();
                } else {
                    firebaseUploadTask(i + 1, images, arrayListMemories, lowestTimestampIndex + 1, program);
                }

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                progressDialog.setMax((int) taskSnapshot.getTotalByteCount());
                progressDialog.setProgress((int) taskSnapshot.getBytesTransferred());
                progressDialog.setSecondaryProgress((int) taskSnapshot.getBytesTransferred());
                Log.e("Task Progress", "bytesT: " + taskSnapshot.getBytesTransferred() + "bytesTot: " + taskSnapshot.getTotalByteCount());
            }
        });


    }

    int lowestTimestamp(ArrayList<Memory> arrayList) {
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

    void initialise() {
        radioButtonAll = view.findViewById(R.id.radioButtonAllPrograms);
        radioButtonSeeding = view.findViewById(R.id.radioButtonSeeding);
        radioButtonBudding = view.findViewById(R.id.radioButtonBudding);
        radioButtonBlossoming = view.findViewById(R.id.radioButtonBlossoming);
        radioButtonFlourishing = view.findViewById(R.id.radioButtonFlourishing);
        radioButtonDayCare = view.findViewById(R.id.radioButtonDayCare);
        button = view.findViewById(R.id.buttonTypeOk);
    }

}
