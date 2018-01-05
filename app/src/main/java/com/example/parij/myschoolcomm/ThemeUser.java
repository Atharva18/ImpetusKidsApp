package com.example.parij.myschoolcomm;

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

public class ThemeUser extends AppCompatActivity {

    TextView startdate,enddate,program,theme;



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
        setContentView(R.layout.activity_theme_user);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Spoken English");

        toolbar.setTitleTextColor(0xFFFFFFFF);

        startdate=(TextView)findViewById(R.id.start);
        enddate=(TextView)findViewById(R.id.end);
        program=(TextView)findViewById(R.id.program);
        theme=(TextView)findViewById(R.id.theme);
        startdate=(TextView)findViewById(R.id.start);

    }


    @Override
    protected void onStart() {
        super.onStart();
        Bundle bundle;
        FirebaseDatabase database;
        SessionManagement.retrieveSharedPreferences(ThemeUser.this);

        final String username = SessionManagement.username;
      //  bundle = getIntent().getExtras();
       // final String username = bundle.getString("Username");
        database = FirebaseDatabase.getInstance();

        if(username.contains("Seed"))
        {
            DatabaseReference databaseReference= database.getReference("Theme").child("Seeding");

            program.setText("Seeding");

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    startdate.setText((CharSequence) dataSnapshot.child("startdate").getValue());
                    enddate.setText((CharSequence) dataSnapshot.child("enddate").getValue());
                    theme.setText((CharSequence)dataSnapshot.child("theme").getValue());

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }
        else if(username.contains("Bloss"))
        {

            DatabaseReference databaseReference= database.getReference("Theme").child("Blossoming");

            program.setText("Blossoming");

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    startdate.setText((CharSequence) dataSnapshot.child("startdate").getValue());
                    enddate.setText((CharSequence) dataSnapshot.child("enddate").getValue());
                    theme.setText((CharSequence)dataSnapshot.child("theme").getValue());

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



        }
        else if(username.contains("Flou"))
        {

            DatabaseReference databaseReference= database.getReference("Theme").child("Flourishing");

            program.setText("Flourishing");

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    startdate.setText((CharSequence) dataSnapshot.child("startdate").getValue());
                    enddate.setText((CharSequence) dataSnapshot.child("enddate").getValue());
                    theme.setText((CharSequence)dataSnapshot.child("theme").getValue());

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });




        }
        else if(username.contains("Budd"))
        {

            DatabaseReference databaseReference= database.getReference("Theme").child("Budding");

            program.setText("Budding");

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    startdate.setText((CharSequence) dataSnapshot.child("startdate").getValue());
                    enddate.setText((CharSequence) dataSnapshot.child("enddate").getValue());
                    theme.setText((CharSequence)dataSnapshot.child("theme").getValue());

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }




    }

}
