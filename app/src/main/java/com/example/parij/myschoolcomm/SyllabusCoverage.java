package com.example.parij.myschoolcomm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SyllabusCoverage extends AppCompatActivity {

    TextView program;
    TextView startdate;
    TextView enddate;
    TextView link;
    Bundle bundle;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.syllabus_coverage);
       // Intent intent=getIntent();

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Syllabus Coverage");
      //  toolbar.setNavigationIcon(R.drawable.homeclassbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);

        program=(TextView)findViewById(R.id.program);
        startdate=(TextView)findViewById(R.id.text);
        enddate=(TextView)findViewById(R.id.text2);
        link=(TextView)findViewById(R.id.link);

    }

    @Override
    protected void onStart() {
        super.onStart();

      //  bundle = getIntent().getExtras();
       // final String username = bundle.getString("Username");

        SessionManagement.retrieveSharedPreferences(SyllabusCoverage.this);

        final String username = SessionManagement.username;
        database = FirebaseDatabase.getInstance();

        if(username.contains("Seed"))
        {
            DatabaseReference databaseReference= database.getReference("Syllabus_Coverage").child("Seeding");

            program.setText("Seeding");

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    startdate.setText((CharSequence) dataSnapshot.child("startdate").getValue());
                    enddate.setText((CharSequence) dataSnapshot.child("enddate").getValue());
                    link.setText((CharSequence)dataSnapshot.child("link").getValue());

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }
        else if(username.contains("Bloss"))
        {

            DatabaseReference databaseReference= database.getReference("Syllabus_Coverage").child("Blossoming");

            program.setText("Blossoming");

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    startdate.setText((CharSequence) dataSnapshot.child("startdate").getValue());
                    enddate.setText((CharSequence) dataSnapshot.child("enddate").getValue());
                    link.setText((CharSequence)dataSnapshot.child("link").getValue());

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



        }
        else if(username.contains("Flou"))
        {

            DatabaseReference databaseReference= database.getReference("Syllabus_Coverage").child("Flourishing");

            program.setText("Flourishing");

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    startdate.setText((CharSequence) dataSnapshot.child("startdate").getValue());
                    enddate.setText((CharSequence) dataSnapshot.child("enddate").getValue());
                    link.setText((CharSequence)dataSnapshot.child("link").getValue());

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });




        }
        else if(username.contains("Budd"))
        {

            DatabaseReference databaseReference= database.getReference("Syllabus_Coverage").child("Budding");

            program.setText("Budding");

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    startdate.setText((CharSequence) dataSnapshot.child("startdate").getValue());
                    enddate.setText((CharSequence) dataSnapshot.child("enddate").getValue());
                    link.setText((CharSequence)dataSnapshot.child("link").getValue());

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }




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
