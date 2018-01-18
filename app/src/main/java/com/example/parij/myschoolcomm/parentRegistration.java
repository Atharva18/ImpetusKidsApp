package com.example.parij.myschoolcomm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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

public class parentRegistration extends AppCompatActivity {


    static int count = 0;
    EditText musername,password,rollNo,name;
    Spinner spinner2;
    Button add;
    ArrayAdapter<CharSequence> arrayAdapter;
    FirebaseDatabase database;

    public static int getcount()
    {

        return ++count;
    }
    public static void setcount()
    {

        count=0;

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

        musername=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        rollNo=(EditText)findViewById(R.id.rollNo);
        name =(EditText)findViewById(R.id.name);
        //spinner1=(Spinner)findViewById(R.id.spinner);
        spinner2 = (Spinner) findViewById(R.id.spinnerBatch);
        add=(Button)findViewById(R.id.adduser);





        arrayAdapter=ArrayAdapter.createFromResource(this,R.array.Batch,android.R.layout.simple_spinner_item);
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




        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setcount();

                  String  program = "";
                final String userName,password1 ,roll, name1,batch;
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
            int flag3=0;
                if(TextUtils.isEmpty(userName)||TextUtils.isEmpty(password1)||TextUtils.isEmpty(roll)
            ||TextUtils.isEmpty(name1))
                {

                    Toast.makeText(parentRegistration.this, "Please fill all the fields", Toast.LENGTH_LONG).show();
                    flag3=1;
                }


                if (flag == 1)
                {
                    Toast.makeText(parentRegistration.this, "Invalid Username", Toast.LENGTH_LONG).show();
                }
                else if(flag3!=1)
                    {

                    database = FirebaseDatabase.getInstance();
                   final DatabaseReference reference;


                    reference = database.getReference("UserNames").child(program);


                    final int[] flag1 = {0};
                    reference.addValueEventListener(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot)
                        {


                            for (DataSnapshot data : dataSnapshot.getChildren()) {

                                if (data.getKey().equals(userName))
                                {

                                  //  Toast.makeText(parentRegistration.this, "UserName already taken!", Toast
                                          //  .LENGTH_LONG).show();

                                    flag1[0] = 1;

                                }

                               // Log.println(Log.ERROR,"Error", (String) data.getValue()) ;

                            }
                                int flag4=getcount();


                            if (flag1[0] != 1 && flag4==1) {


                                final int[] flag2 = {0};

                                DatabaseReference reference1=null;

                                String program1="";

                                if (userName.contains("Dayc")) {
                                    program1 = "DayCare";

                                } else if (userName.contains("Bloss")) {
                                    program1 = "Blossoming";

                                } else if (userName.contains("Budd")) {
                                    program1 = "Budding";

                                } else if (userName.contains("Flou")) {
                                    program1 = "Flourishing";

                                } else if (userName.contains("Seed")) {
                                    program1 = "Seeding";
                                }



                                if(program1.equals("DayCare"))
                                {
                                    reference1=database.getReference("studinfo").child("Day-Care").child(batch);
                                }
                                else
                                {
                                    reference1 = database.getReference("studinfo").child(program1).child(batch);
                                }
                                final DatabaseReference finalReference = reference1;
                                reference1.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        for (DataSnapshot data : dataSnapshot.getChildren()) {

                                            if (data.getKey().equals(roll))
                                            {

                                                //Toast.makeText(parentRegistration.this, "Roll No already in use!", Toast
                                                //  .LENGTH_LONG).show();

                                                flag2[0] = 1;

                                            }

                                            // Log.println(Log.ERROR,"Error", (String) data.getValue()) ;

                                        }

                                        if (flag2[0] != 1) {

                                            reference.child(userName).child("password").setValue(password1);
                                           // Toast.makeText(parentRegistration.this, "UserName added!", Toast
                                             //       .LENGTH_LONG).show();
                                            finalReference.child(roll).child("name").setValue(name1);
                                            finalReference.child(roll).child("username").setValue(userName);
                                            Toast.makeText(parentRegistration.this, "Entry added!", Toast
                                                    .LENGTH_LONG).show();

                                        }
                                        else
                                        {
                                            rollNo.setError("Roll No. already in use!");
                                        }


                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                                //Toast.makeText(parentRegistration.this, "1!", Toast
                                      //  .LENGTH_LONG).show();

                            }
                            else
                            {
                               // Toast.makeText(parentRegistration.this, "2!", Toast
                                      //  .LENGTH_LONG).show();
                                musername.setError("Username already taken!");
                            }


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });




                }

            }
        });

    }
}
