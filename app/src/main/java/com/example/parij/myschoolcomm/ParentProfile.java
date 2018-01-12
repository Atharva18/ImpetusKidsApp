package com.example.parij.myschoolcomm;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ParentProfile extends AppCompatActivity {

    final int ImgReq = 1;
    EditText fatherName,motherName,ocupation,contactNo,emailId,qualification,officeNo,city,street,landmark,pincode;
    FirebaseDatabase database;
    Button update;
    Bundle bundle;
    Button upload;
    ImageView childphoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_profile);

        bundle=getIntent().getExtras();
        final String username = bundle.getString("Username");

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Parent Profile");
       // toolbar.setNavigationIcon(R.drawable.parentbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);

        initialise();

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                 startActivityForResult(intent,ImgReq);



            }
        });



        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        String fathername,mothername,occupation,contactno,emailid,qualification1,officeno,city1,street1;
        String landmark1,pincode1;
        database=FirebaseDatabase.getInstance();
        DatabaseReference reference= database.getReference("ParentProfile");

        fathername=fatherName.getText().toString().trim();
        mothername=motherName.getText().toString().trim();
        occupation=ocupation.getText().toString().trim();
        contactno=contactNo.getText().toString().trim();
        emailid=emailId.getText().toString().trim();
        qualification1=qualification.getText().toString().trim();
        officeno=officeNo.getText().toString().trim();
        city1=city.getText().toString().trim();
        street1=street.getText().toString().trim();
        landmark1=landmark.getText().toString().trim();
        pincode1=pincode.getText().toString().trim();

        ParentClass parentClass=new ParentClass(fathername,mothername,occupation,
        contactno,emailid,qualification1,officeno,city1,street1,landmark1,pincode1
        );

        reference.child(username).setValue(parentClass);

                Toast.makeText(getApplicationContext(),"Successfully Updated!",Toast.LENGTH_LONG).show();




            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ImgReq && requestCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            childphoto.setImageURI(selectedImage);
        }



    }

    @Override
    protected void onStart() {
        super.onStart();

        bundle=getIntent().getExtras();
        final String username = bundle.getString("Username");
        database=FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference= database.getReference("ParentProfile");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for(DataSnapshot data:dataSnapshot.getChildren())
                {
                    if(username.equals(data.getKey()))
                    {
                        fatherName.setText((CharSequence) data.child("fathername").getValue());
                        motherName.setText((CharSequence) data.child("mothername").getValue());
                        ocupation.setText((CharSequence) data.child("occupation").getValue());
                        contactNo.setText((CharSequence) data.child("contactno").getValue());
                        emailId.setText((CharSequence) data.child("emailid").getValue());
                        qualification.setText((CharSequence) data.child("qualification").getValue());
                        officeNo.setText((CharSequence) data.child("officeno").getValue());
                        city.setText((CharSequence) data.child("city").getValue());
                        street.setText((CharSequence) data.child("street").getValue());
                        landmark.setText((CharSequence) data.child("landmark").getValue());
                        pincode.setText((CharSequence) data.child("pincode").getValue());






                    }

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(getApplicationContext(),"Oops! Something went wrong",Toast.LENGTH_LONG).show();

            }
        });

    }
    public void initialise()
    {
        update=(Button)findViewById(R.id.update);
        fatherName=(EditText)findViewById(R.id.fatherName);
        motherName=(EditText)findViewById(R.id.motherName);
        ocupation=(EditText)findViewById(R.id.occupation);
        contactNo=(EditText)findViewById(R.id.contactNo);
        emailId=(EditText)findViewById(R.id.emailId);
        qualification=(EditText)findViewById(R.id.qualification);
        officeNo=(EditText)findViewById(R.id.officeNo);
        city=(EditText)findViewById(R.id.city);
        street=(EditText)findViewById(R.id.street);
        landmark=(EditText)findViewById(R.id.landmark);
        pincode=(EditText)findViewById(R.id.pincode);
        childphoto=(ImageView)findViewById(R.id.childphoto);
        upload = (Button) findViewById(R.id.choosePhoto);


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





