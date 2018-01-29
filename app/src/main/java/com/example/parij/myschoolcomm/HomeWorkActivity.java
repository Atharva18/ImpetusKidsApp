package com.example.parij.myschoolcomm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.parij.myschoolcomm.Models.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeWorkActivity extends AppCompatActivity {

    TextView programtxt, startdate, enddate, monday, tuesday, wednesday, thursday, friday, saturday;
    // Bundle bundle;
    FirebaseDatabase database;


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
        setContentView(R.layout.activity_home_work);

        programtxt = (TextView) findViewById(R.id.program);
        startdate = (TextView) findViewById(R.id.textView);
        enddate = (TextView) findViewById(R.id.textView2);
        monday = (TextView) findViewById(R.id.monday);
        tuesday = (TextView) findViewById(R.id.tuesday);
        wednesday = (TextView) findViewById(R.id.wednesday);
        thursday = (TextView) findViewById(R.id.thursday);
        friday = (TextView) findViewById(R.id.friday);
        saturday = (TextView) findViewById(R.id.saturday);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Homework");
        //  toolbar.setNavigationIcon(R.drawable.homeclassbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);

    }

    @Override
    protected void onStart() {
        super.onStart();

        SessionManagement.retrieveSharedPreferences(HomeWorkActivity.this);

        final String username = SessionManagement.username;
        // bundle = getIntent().getExtras();
        //final String username = bundle.getString("Username");
        database = FirebaseDatabase.getInstance();

        DatabaseReference reference = database.getReference("newDb").child("students");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Student student;

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    student = ds.getValue(Student.class);
                    if (student.getUsername().equals(username)) {
                        int programCode = student.getProgram();
                        String program = "";
                        if (programCode == Constants.SEEDING)
                            program = "Seeding";
                        else if (programCode == Constants.BUDDING)
                            program = "Budding";
                        else if (programCode == Constants.FLOURISHING)
                            program = "Flourishing";
                        else if (programCode == Constants.BLOSSOMING)
                            program = "Blossoming";


                        DatabaseReference newreference = database.getReference("newDb").child("HomeWork").child(program);

                        programtxt.setText(program);

                        newreference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                startdate.setText((CharSequence) dataSnapshot.child("startdate").getValue());
                                enddate.setText((CharSequence) dataSnapshot.child("enddate").getValue());
                                monday.setText((CharSequence) dataSnapshot.child("monday").getValue());
                                tuesday.setText((CharSequence) dataSnapshot.child("tuesday").getValue());
                                wednesday.setText((CharSequence) dataSnapshot.child("wednesday").getValue());
                                thursday.setText((CharSequence) dataSnapshot.child("thursday").getValue());
                                friday.setText((CharSequence) dataSnapshot.child("friday").getValue());
                                saturday.setText((CharSequence) dataSnapshot.child("saturday").getValue());

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
/*
                    startdate.setText((CharSequence) dataSnapshot.child("startdate").getValue());
                    enddate.setText((CharSequence) dataSnapshot.child("enddate").getValue());
                    monday.setText((CharSequence) dataSnapshot.child("monday").getValue());
                    tuesday.setText((CharSequence) dataSnapshot.child("tuesday").getValue());
                    wednesday.setText((CharSequence) dataSnapshot.child("wednesday").getValue());
                    thursday.setText((CharSequence) dataSnapshot.child("thursday").getValue());
                    friday.setText((CharSequence) dataSnapshot.child("friday").getValue());
                    saturday.setText((CharSequence) dataSnapshot.child("saturday").getValue());

 */