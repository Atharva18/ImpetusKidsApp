package com.example.parij.myschoolcomm;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parij.myschoolcomm.Models.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class adminemergency extends AppCompatActivity {
    Button dialcontact,alternativebutton,dialdoctor;
    Button submit;
    TextView contactNo,alternativeNo,familyDoctor,doctorNo;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminemergency);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Emergency");
       // toolbar.setNavigationIcon(R.drawable.emergencybar);
        toolbar.setTitleTextColor(0xFFFFFFFF);

        initialise();


        dialcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String number = contactNo.getText().toString().trim();

                if(!TextUtils.isEmpty(number)) {
                    String dial = "tel:" + number;
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
                }else {
                    Toast.makeText(adminemergency.this, "Enter a phone number", Toast.LENGTH_SHORT).show();
                }

            }
        });


        alternativebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String number = alternativeNo.getText().toString().trim();

                if(!TextUtils.isEmpty(number)) {
                    String dial = "tel:" + number;
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
                }else {
                    Toast.makeText(adminemergency.this, "Enter a phone number", Toast.LENGTH_SHORT).show();
                }



            }
        });


        dialdoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String number = doctorNo.getText().toString();

                if(!TextUtils.isEmpty(number)) {
                    String dial = "tel:" + number;
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
                }else {
                    Toast.makeText(adminemergency.this, "Enter a phone number", Toast.LENGTH_SHORT).show();
                }



            }
        });

    }

    @Override
    protected void onStart()
    {
        super.onStart();

        SessionManagement.retrieveSharedPreferences(adminemergency.this);

        final String username = SessionManagement.username;
        database=FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = database.getReference("newDb").child("students");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Student student;

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    student = ds.getValue(Student.class);

                    if (student.getUsername().equals(username)) {
                        contactNo.setText(student.getEmergencyPerson().getPhone());
                        alternativeNo.setText(student.getEmergencyPerson().getPhoneAlternate());
                        familyDoctor.setText(student.getEmergencyPerson().getFamilyDoctorName());
                        doctorNo.setText(student.getEmergencyPerson().getFamilyDoctorPhone());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void initialise()
    {
        dialcontact=(Button)findViewById(R.id.dialcontact);
        alternativebutton=(Button)findViewById(R.id.alternativebutton);
        dialdoctor=(Button)findViewById(R.id.dialdoctor);
        contactNo=(TextView) findViewById(R.id.contactNo);
        alternativeNo=(TextView)findViewById(R.id.alternativeNo);
        familyDoctor=(TextView) findViewById(R.id.familyDoctor);
        doctorNo=(TextView) findViewById(R.id.doctorNo);
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