package com.example.parij.myschoolcomm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class authorizedwindow extends AppCompatActivity {

    Spinner spinner;
    ArrayAdapter<CharSequence> arrayAdapter;
    EditText name,roll;
    Button ok;
    Spinner spinner2;

    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorizedwindow);

        spinner=(Spinner)findViewById(R.id.spinner4);
        name=(EditText)findViewById(R.id.name);
        roll=(EditText)findViewById(R.id.roll);
        ok=(Button)findViewById(R.id.ok);
        spinner2=(Spinner)findViewById(R.id.spinner2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Authorized Person");
        //  toolbar.setNavigationIcon(R.drawable.childprofbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);


        arrayAdapter=ArrayAdapter.createFromResource(this,R.array.Type,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        final String[] type = new String[1];
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                type[0] = (String) parent.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] user = new String[1];
                final String mname=name.getText().toString().trim();
                final String mroll= roll.getText().toString().trim();
                final String program=type[0];
                final String batch=type1[0];

                if(TextUtils.isEmpty(mname)|| TextUtils.isEmpty(mroll)||TextUtils.isEmpty(program)||
                        TextUtils.isEmpty(batch))
                {
                    Toast.makeText(getApplicationContext(),"Please fill all the details!",Toast.LENGTH_LONG).show();
                }
                else {
                    database = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = database.getReference("studinfo").child(program).child(batch);

                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            int flag = 0;

                            for (DataSnapshot data : dataSnapshot.getChildren()) {

                                if (mroll.equals(data.getKey())) {

                                    String dname = (String) data.child("name").getValue();
                                  //  String droll = String.valueOf( data.child("roll").getValue());
                                    user[0] = (String) data.child("username").getValue();
                                    if (dname.equals(mname)) {
                                        flag = 1;
                                        break;
                                    }


                                }

                                if (flag == 1)
                                    break;


                            }


                            if (flag == 1) {
                                Toast.makeText(getApplicationContext(), "Entry found!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(authorizedwindow.this,adminauthorized.class);
                                Bundle bundle=new Bundle();
                                bundle.putString("username", user[0]);
                                intent.putExtras(bundle);
                                startActivity(intent);


                            } else {
                                Toast.makeText(getApplicationContext(), "Entry doesn't exits! Please try again!", Toast.LENGTH_LONG).show();
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
}
