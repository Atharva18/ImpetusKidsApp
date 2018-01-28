package com.example.parij.myschoolcomm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.parij.myschoolcomm.Models.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class tempParentRegistration extends AppCompatActivity {



    EditText username, password, rollNo, name;

    Spinner spinner2, spinnerProgram;
    Button add;
    ArrayAdapter<CharSequence> arrayAdapter, arrayAdapterProgram;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    List<String> userNames, rollNos, arrayListPrograms;
    List<Integer> programs;
    DatabaseReference databaseReference;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_registration);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Parent Registration");
        //  toolbar.setNavigationIcon(R.drawable.childprofbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        rollNo = (EditText) findViewById(R.id.rollNo);
        name = (EditText) findViewById(R.id.name);


        spinnerProgram = (Spinner) findViewById(R.id.spinnerProgram);
        arrayListPrograms = new ArrayList<>();
        arrayListPrograms.add("Day-Care");
        arrayListPrograms.add("Blossoming");
        arrayListPrograms.add("Budding");
        arrayListPrograms.add("Flourishing");
        arrayListPrograms.add("Seeding");
        //arrayAdapterProgram = new ArrayAdapter<CharSequence>(tempParentRegistration.this, android.R.layout.simple_list_item_1, arrayListPrograms);

        spinner2 = (Spinner) findViewById(R.id.spinnerBatch);
        add = (Button) findViewById(R.id.adduser);


        arrayAdapter = ArrayAdapter.createFromResource(this, R.array.Batch, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(arrayAdapter);


        arrayAdapterProgram = ArrayAdapter.createFromResource(this, R.array.Type, android.R.layout.simple_spinner_item);
        arrayAdapterProgram.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProgram.setAdapter(arrayAdapterProgram);


        userNames = new ArrayList<>();
        rollNos = new ArrayList<>();
        programs = new ArrayList<>();

        DatabaseReference reference = database.getReference("newDb").child("students");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Student student;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    student = ds.getValue(Student.class);

                    userNames.add(student.getUsername());
                    rollNos.add(student.getRollNo());
                    programs.add(student.getProgram());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int flag = 0;

                Student student = new Student();


                student.setName(name.getText().toString().trim());
                int batch;
                if (spinner2.getSelectedItemPosition() == 0)
                    batch = Constants.MORNING;
                else
                    batch = Constants.AFTERNOON;
                student.setBatch(batch);
                //Simlarly program
                int program = 0;
                if (spinnerProgram.getSelectedItemPosition() == 0)
                    program = Constants.DAYCARE;
                else if (spinnerProgram.getSelectedItemPosition() == 1)
                    program = Constants.SEEDING;
                else if (spinnerProgram.getSelectedItemPosition() == 2)
                    program = Constants.BUDDING;
                else if (spinnerProgram.getSelectedItemPosition() == 3)
                    program = Constants.BLOSSOMING;
                else if (spinnerProgram.getSelectedItemPosition() == 4)
                    program = Constants.FLOURISHING;

                student.setProgram(program);
                if (userNames.contains(username.getText().toString())) {

                    flag = 1;
                    Toast.makeText(tempParentRegistration.this, "Username already exists!Please select another!", Toast.LENGTH_SHORT).show();

                }

                if (rollNos.contains(rollNo.getText().toString().trim())) {
                    int position = rollNos.indexOf(rollNo.getText().toString().trim());
                    if (programs.get(position) == program) {
                        flag = 1;
                        Toast.makeText(tempParentRegistration.this, "Roll No already exists!", Toast.LENGTH_LONG).show();
                    }
                }

                student.setRollNo(rollNo.getText().toString().trim());
                student.setUsername(username.getText().toString().trim());
                student.setPassword(password.getText().toString().trim());
                if (flag == 0) {
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("newDb").child("students");
                    databaseReference.push().setValue(student);
                    Toast.makeText(tempParentRegistration.this, "Entry Added!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}