package com.example.parij.myschoolcomm;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.parij.myschoolcomm.Models.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SyllabusCoverage extends AppCompatActivity implements View.OnClickListener {

    TextView programtxt;
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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Syllabus Coverage");
      //  toolbar.setNavigationIcon(R.drawable.homeclassbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);

        programtxt = findViewById(R.id.program);
        startdate = findViewById(R.id.text);
        enddate = findViewById(R.id.text2);
        link = findViewById(R.id.link);

        link.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.link:
                openLink(link.getText().toString());
                break;

        }
    }

    void openLink(String link) {
        if (link.length() > 3) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(link));
            startActivity(i);
        }
    }
    @Override
    protected void onStart() {
        super.onStart();

        SessionManagement.retrieveSharedPreferences(SyllabusCoverage.this);

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


                        DatabaseReference newreference = database.getReference("newDb").child("Syllabus_Coverage").child(program);

                        programtxt.setText(program);

                        newreference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                startdate.setText((CharSequence) dataSnapshot.child("startdate").getValue());
                                enddate.setText((CharSequence) dataSnapshot.child("enddate").getValue());
                                link.setText((CharSequence) dataSnapshot.child("link").getValue());
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
