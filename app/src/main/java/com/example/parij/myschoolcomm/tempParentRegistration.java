package com.example.parij.myschoolcomm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

public class tempParentRegistration extends AppCompatActivity {


    static int count = 0;
    static int count2 = 0;
    EditText username, password, rollNo, name;

    Spinner spinner2, spinnerProgram;
    Button add;
    ArrayAdapter<CharSequence> arrayAdapter, arrayAdapterProgram;
    FirebaseDatabase database;
    ArrayList<String> userNames, rollNos, arrayListPrograms;
    DatabaseReference databaseReference;

    public static int getCount2() {
        return count2;
    }

    public static void setCount2(int count2) {
        tempParentRegistration.count2 = count2;
    }

    public static int getCount()
    {
        return count;
    }

    public static void setCount(int count) {
        tempParentRegistration.count = count;
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
        //spinner1=(Spinner)findViewById(R.id.spinner);

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


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Student student = new Student();
                student.setRollNo(rollNo.getText().toString().trim());
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
                student.setUsername(username.getText().toString().trim());
                student.setPassword(password.getText().toString().trim());
                databaseReference = FirebaseDatabase.getInstance().getReference().child("newDb").child("students");
                databaseReference.push().setValue(student);

                Toast.makeText(tempParentRegistration.this, "Entry Added!", Toast.LENGTH_LONG).show();

            }
        });





    }
    boolean checkRoll(final String userName, final String roll, final String password, final String program1, final String name1, final String batch)
    {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("studinfo");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    for(DataSnapshot dsBatch : ds.getChildren())
                    {
                        for(DataSnapshot dsrolls : dsBatch.getChildren())
                        {
                            Log.e("REGISTER", "Prog: " + ds.getKey() + " Batch: " + ds.getKey());
                            if (ds.getKey().equalsIgnoreCase(program1) && dsBatch.getKey().equalsIgnoreCase(batch))
                                rollNos.add(dsrolls.getKey());
                            Log.println(Log.ERROR, "msg", String.valueOf(rollNos));
                        }
                    }
                }
                if(!rollNos.contains(roll))
                {
                    String program = program1;
                    database=FirebaseDatabase.getInstance();
                    DatabaseReference reference= database.getReference("UserNames").child(program);
                    reference.child(userName).child("password").setValue(password);

                    if(program.equals("DayCare"))
                        program="Day-Care";

                    reference=database.getReference("studinfo").child(program).child(batch);
                    reference.child(roll).child("name").setValue(name1);
                    reference.child(roll).child("username").setValue(userName);
                    Toast.makeText(tempParentRegistration.this, "Entry added!", Toast
                            .LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Roll no exists", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return false;
    }


    public boolean checkUser(final String user, String program) {

        setCount(0);
        database = FirebaseDatabase.getInstance();
        final DatabaseReference reference,reference1;

        reference = database.getReference("UserNames").child(program);

        final int[] flag1 = {0};
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    String key = data.getKey();
                    Log.println(Log.ERROR,"msg",key);

                    if (data.getKey().equals(user)) {
                        username.setError("UserName already Exists!");
                      //  flag1[0] = 1;
                        setCount(1);
                        Log.println(Log.ERROR,"msg1", String.valueOf(String.valueOf(getCount())));

                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Log.println(Log.ERROR,"msg2", String.valueOf(String.valueOf(getCount())));
        return getCount() == 0;


    }

    public boolean checkroll(String program, String batch, final String roll)
    {
        setCount2(0);
        if(program.equals("DayCare"))
            program="Day-Care";

        database=FirebaseDatabase.getInstance();

        DatabaseReference reference = database.getReference("studinfo").child(program).child(batch);



        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot data : dataSnapshot.getChildren()) {


                    if (data.getKey().equals(roll)) {


                        rollNo.setError("Roll No is already in use!");
                        setCount2(1);
                        Log.println(Log.ERROR,"msg3", String.valueOf(String.valueOf(getCount())));
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Log.println(Log.ERROR,"msg4", String.valueOf(String.valueOf(getCount())));
        return getCount2() == 0;

    }

    /*

                    //Toast.makeText(tempParentRegistration.this, "Inside2", Toast.LENGTH_LONG).show();


                    if (checkUser(userName, program))
                    {

                       // Toast.makeText(tempParentRegistration.this, "Inside", Toast.LENGTH_LONG).show();

                        if(checkroll(program,batch,roll) )
                        {
                          //  Toast.makeText(tempParentRegistration.this, "Inside5", Toast.LENGTH_LONG).show();
                            database=FirebaseDatabase.getInstance();
                            DatabaseReference reference= database.getReference("UserNames").child(program);
                            reference.child(userName).child("password").setValue(password1);

                            if(program.equals("DayCare"))
                                program="Day-Care";

                            reference=database.getReference("studinfo").child(program).child(batch);
                            reference.child(roll).child("name").setValue(name1);
                            reference.child(roll).child("username").setValue(userName);
                            Toast.makeText(tempParentRegistration.this, "Entry added!", Toast
                                    .LENGTH_LONG).show();


                        }

                    }


                }

            }
        });
    */

}