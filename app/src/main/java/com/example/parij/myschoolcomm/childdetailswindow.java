package com.example.parij.myschoolcomm;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class childdetailswindow extends AppCompatActivity {


    EditText editTextSearch;
    Spinner spinnerFilter;
    ListView listView;
    ArrayList<Student> arrayList;
    ArrayList<String> arrayListDisplay;
    DatabaseReference databaseReference;
    ArrayAdapter arrayAdapter;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_childdetailswindow);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        editTextSearch = (EditText) findViewById(R.id.editTextRoaster);
        spinnerFilter = (Spinner) findViewById(R.id.spinnerRoaster);
        listView = (ListView) findViewById(R.id.listViewRoaster);
        arrayList = new ArrayList<>();
        arrayListDisplay = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("studinfo");
        dialog = new Dialog(childdetailswindow.this, android.R.style.Theme_DeviceDefault_Light_Dialog);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDialogAndStartActivity(position);
            }
        });
    }

    void getDataFromDB() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String program, batch;
                int rollno;
                String username, name;
                for (DataSnapshot ds : dataSnapshot.getChildren())//Program
                    for (DataSnapshot ds1 : ds.getChildren())//Batch
                        for (DataSnapshot ds2 : ds1.getChildren())//Roll
                        {
                            program = ds.getKey();
                            batch = ds1.getKey();
                            username = ds2.child("username").getValue(String.class);
                            name = ds2.child("name").getValue(String.class);
                            rollno = Integer.parseInt(ds2.getKey());
                            Student student = new Student(program, batch, rollno, name, username);
                            arrayList.add(student);
                            arrayListDisplay.add(student.getRollNo() + ". " + student.getName() + "\n" + student.getProgram());
                            Log.d("StudentObj : ", student.toString());
                        }
                arrayAdapter = new ArrayAdapter(childdetailswindow.this, android.R.layout.simple_list_item_1, arrayListDisplay);
                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    void showDialogAndStartActivity(final int position) {
        dialog.setContentView(R.layout.dialog_choose_detail_type);
        final RadioButton radioButtonChildProfile, radioButtonParentProfile, radioButtonEmergency, radioButtonAuthorised;
        Button button;
        radioButtonAuthorised = (RadioButton) dialog.findViewById(R.id.radioButtonDetailTypeAuthorisedPerson);
        radioButtonChildProfile = (RadioButton) dialog.findViewById(R.id.radioButtonDetailTypeChildProfile);
        radioButtonEmergency = (RadioButton) dialog.findViewById(R.id.radioButtonDetailTypeEmergency);
        radioButtonParentProfile = (RadioButton) dialog.findViewById(R.id.radioButtonDetailTypeParentProfile);
        button = (Button) dialog.findViewById(R.id.buttonDetailTypeOk);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setTitle("Choose action: ");
                dialog.dismiss();
                Bundle bundle = new Bundle();
                bundle.putString("username", arrayList.get(position).getUsername());
                if (radioButtonAuthorised.isChecked()) {
                    Intent intent = new Intent(childdetailswindow.this, adminauthorized.class);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }
                if (radioButtonChildProfile.isChecked()) {
                    Intent intent = new Intent(childdetailswindow.this, adminchild.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                if (radioButtonEmergency.isChecked()) {
                    Intent intent = new Intent(childdetailswindow.this, adminemergency.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                if (radioButtonParentProfile.isChecked()) {
                    Intent intent = new Intent(childdetailswindow.this, adminparent.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                finish();
            }
        });
        dialog.show();
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