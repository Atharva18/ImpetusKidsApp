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

public class adminauthorized extends AppCompatActivity {

    final int ImgReq = 1;
    TextView name,relation,contactNo,date1,date2;
    FirebaseDatabase database;
    //Button submit;
    Bundle bundle;
    ImageView personphoto;
    //Button upload;
    Button call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminauthorized);

        // Intent intent = getIntent();

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Authorize to collect");
        //toolbar.setNavigationIcon(R.drawable.parentbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);

        initialise();

        bundle=getIntent().getExtras();
        final String username = bundle.getString("username");

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String number = contactNo.getText().toString();

                if(!TextUtils.isEmpty(number)) {
                    String dial = "tel:" + number;
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
                }else {
                    Toast.makeText(adminauthorized.this, "Enter a phone number", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    protected void onStart()
    {
        super.onStart();

        SessionManagement.retrieveSharedPreferences(adminauthorized.this);

        final String username = SessionManagement.username;

        database=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("newDb").child("students");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Student student;

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    student = ds.getValue(Student.class);

                    if (student.getUsername().equals(username)) {
                        name.setText(student.getAuthorizedPerson().getName());
                        contactNo.setText(student.getAuthorizedPerson().getPhone());
                        relation.setText(student.getAuthorizedPerson().getRelation());
                        date1.setText(student.getAuthorizedPerson().getFromDate());
                        date2.setText(student.getAuthorizedPerson().getToDate());

                        String url = student.getAuthorizedPerson().getImageLink();

                        if (!url.equals(""))
                            Glide.with(getApplicationContext()).load(url).into(personphoto);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ImgReq && requestCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            personphoto.setImageURI(selectedImage);
        }
    }

    public void initialise()
    {
        //upload=(Button)findViewById(R.id.upload);
        personphoto=(ImageView)findViewById(R.id.personphoto);
       // submit=(Button)findViewById(R.id.submit);
        name=(TextView)findViewById(R.id.name);
        contactNo=(TextView)findViewById(R.id.contactno);
        relation=(TextView)findViewById(R.id.relation);
        date1=(TextView)findViewById(R.id.date1);
        date2=(TextView)findViewById(R.id.date2);
        call=(Button)findViewById(R.id.call);

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
