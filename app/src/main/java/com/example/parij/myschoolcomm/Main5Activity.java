package com.example.parij.myschoolcomm;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.parij.myschoolcomm.Models.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Main5Activity extends AppCompatActivity {

    final int ImgReq = 1;
    ImageView childphoto;
    Bundle bundle;
    Button phone;
    Bitmap bitmap;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    DatePickerDialog.OnDateSetListener onDateSetListener2;

    TextView grNo, admissionNo, category, admissiondate, class1, division, gender, dateOfBirth, bloodGroup, classTeacher, contactNo;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Child Profile");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitleTextColor(0xFFFFFFFF);

        initialise();

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String number = contactNo.getText().toString();

                if(!TextUtils.isEmpty(number)) {
                    String dial = "tel:" + number;
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
                }

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        SessionManagement.retrieveSharedPreferences(Main5Activity.this);
        final String username = SessionManagement.username;
        database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("newDb").child("students");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Student student = new Student();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    student = ds.getValue(Student.class);

                    if (student.getUsername().equals(username)) {
                        admissiondate.setText(student.getAdmissionDate().toString());

                        int program = student.getProgram();

                        if (Constants.BLOSSOMING == program)
                            class1.setText("Blossoming");
                        else if (Constants.BUDDING == program)
                            class1.setText("Budding");
                        else if (Constants.FLOURISHING == program)
                            class1.setText("Flourishing");
                        else if (Constants.SEEDING == program)
                            class1.setText("Seeding");
                        else if (Constants.DAYCARE == program)
                            class1.setText("DayCare");

                        int batch = student.getBatch();

                        if (Constants.AFTERNOON == batch)
                            division.setText("Afternoon");
                        else
                            division.setText("Morning");

                        int Gender = student.getGender();

                        if (Gender == Constants.MALE)
                            gender.setText("Male");
                        else if (Gender == Constants.FEMALE)
                            gender.setText("Female");

                        dateOfBirth.setText(student.getDateOfBirth().toString());

                        int bGroup = student.getBloodGroup();

                        if (bGroup == Constants.ANegative)
                            bloodGroup.setText("A -ve");
                        else if (bGroup == Constants.BNegative)
                            bloodGroup.setText("B -ve");
                        else if (bGroup == Constants.AB)
                            bloodGroup.setText("AB");
                        else if (bGroup == Constants.BPositive)
                            bloodGroup.setText("B +ve");
                        else if (bGroup == Constants.APositive)
                            bloodGroup.setText("A +ve");
                        else if (bGroup == Constants.ONegative)
                            bloodGroup.setText("O -ve");
                        else if (bGroup == Constants.OPositive)
                            bloodGroup.setText("O +ve");

                        classTeacher.setText(student.getClassTeacherName());
                        contactNo.setText(student.getClassTeacherPhone());

                        String url = student.getImageLink().toString();

                        if (!url.equals("")) {
                            Glide.with(getApplicationContext()).load(url).into(childphoto);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Something went wrong,Please Try Again !",Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    public void initialise() {

        childphoto = (ImageView) findViewById(R.id.photo);

        admissiondate = (TextView) findViewById(R.id.admissionDate);
        category = (TextView) findViewById(R.id.category);
        class1 = (TextView) findViewById(R.id.class1);
        division = (TextView) findViewById(R.id.division);
        gender = (TextView) findViewById(R.id.gender);
        dateOfBirth = (TextView) findViewById(R.id.dateOfBirth);
        bloodGroup = (TextView) findViewById(R.id.bloodGroup);
        classTeacher = (TextView) findViewById(R.id.classTeacher);
        contactNo = (TextView) findViewById(R.id.contactNo);
        phone=(Button)findViewById(R.id.phone);

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








