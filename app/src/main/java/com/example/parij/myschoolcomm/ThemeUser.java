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

public class ThemeUser extends AppCompatActivity {

    TextView startdate, enddate, programtxt, theme;
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
        programtxt = (TextView) findViewById(R.id.program);
        theme=(TextView)findViewById(R.id.theme);

    }
    @Override
    protected void onStart() {
        super.onStart();
        Bundle bundle;
        final FirebaseDatabase database;
        SessionManagement.retrieveSharedPreferences(ThemeUser.this);
        final String username = SessionManagement.username;
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


                        DatabaseReference newreference = database.getReference("newDb").child("SpokenEnglish").child(program);

                        programtxt.setText(program);

                        newreference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                startdate.setText((CharSequence) dataSnapshot.child("startdate").getValue());
                                enddate.setText((CharSequence) dataSnapshot.child("enddate").getValue());
                                theme.setText((CharSequence) dataSnapshot.child("theme").getValue());
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
