package com.example.parij.myschoolcomm;

import android.provider.ContactsContract;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class temp extends AppCompatActivity {


    static int count = 0;
    static int count2 = 0;
    EditText musername, password, rollNo, name;
    Spinner spinner2;
    Button add;
    ArrayAdapter<CharSequence> arrayAdapter;
    FirebaseDatabase database;
    ArrayList<String> userNames, rollNos;
    DatabaseReference databaseReference;

    public static int getCount2() {
        return count2;
    }

    public static void setCount2(int count2) {
        temp.count2 = count2;
    }

    public static int getCount()
    {
        return count;
    }

    public static void setCount(int count) {
        temp.count = count;
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

        musername = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        rollNo = (EditText) findViewById(R.id.rollNo);
        name = (EditText) findViewById(R.id.name);
        //spinner1=(Spinner)findViewById(R.id.spinner);
        spinner2 = (Spinner) findViewById(R.id.spinner5);
        add = (Button) findViewById(R.id.adduser);


        arrayAdapter = ArrayAdapter.createFromResource(this, R.array.Batch, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(arrayAdapter);

        final String[] type1 = new String[1];
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                type1[0] = (String) parent.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        userNames = new ArrayList<>();
        rollNos = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("UserNames");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    for (DataSnapshot dsChild : ds.getChildren()) {
                        userNames.add(dsChild.getKey());
                    }
                }
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Log.println(Log.ERROR, "msg", String.valueOf(userNames));
                        String program = "";
                        final String userName, password1, roll, name1, batch;
                        userName = musername.getText().toString().trim();
                        password1 = password.getText().toString().trim();
                        roll = rollNo.getText().toString().trim();
                        name1 = name.getText().toString().trim();
                        batch = type1[0];

                        int flag = 0;
                        if (userName.contains("Dayc")) {
                            program = "DayCare";

                        } else if (userName.contains("Bloss")) {
                            program = "Blossoming";

                        } else if (userName.contains("Budd")) {
                            program = "Budding";

                        } else if (userName.contains("Flou")) {
                            program = "Flourishing";

                        } else if (userName.contains("Seed")) {
                            program = "Seeding";

                        } else {
                            flag = 1;
                        }
                        int flag3 = 0;
                        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password1) || TextUtils.isEmpty(roll)
                                || TextUtils.isEmpty(name1)) {

                            Toast.makeText(temp.this, "Please fill all the fields", Toast.LENGTH_LONG).show();
                            flag3 = 1;
                        }


                        if (flag == 1)
                        {
                            Toast.makeText(temp.this, "Invalid Username", Toast.LENGTH_LONG).show();
                        } else if (flag3 != 1)
                        {
                            if (!userNames.contains(userName))
                            {
                                checkRoll(userName, roll, password1, program, name1, batch);
                            } else {
                                Toast.makeText(getApplicationContext(), "Username exists", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

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
                    Toast.makeText(temp.this, "Entry added!", Toast
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
                        musername.setError("UserName already Exists!");
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

                    //Toast.makeText(temp.this, "Inside2", Toast.LENGTH_LONG).show();


                    if (checkUser(userName, program))
                    {

                       // Toast.makeText(temp.this, "Inside", Toast.LENGTH_LONG).show();

                        if(checkroll(program,batch,roll) )
                        {
                          //  Toast.makeText(temp.this, "Inside5", Toast.LENGTH_LONG).show();
                            database=FirebaseDatabase.getInstance();
                            DatabaseReference reference= database.getReference("UserNames").child(program);
                            reference.child(userName).child("password").setValue(password1);

                            if(program.equals("DayCare"))
                                program="Day-Care";

                            reference=database.getReference("studinfo").child(program).child(batch);
                            reference.child(roll).child("name").setValue(name1);
                            reference.child(roll).child("username").setValue(userName);
                            Toast.makeText(temp.this, "Entry added!", Toast
                                    .LENGTH_LONG).show();


                        }

                    }


                }

            }
        });
    */

}