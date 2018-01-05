package com.example.parij.myschoolcomm;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageRequest;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

public class daycarechild extends AppCompatActivity {

    final int ImgReq = 1;
    ImageView childphoto;
    Bundle bundle;
    //  Button upload;
    //  Button update;
    Button phone;
    Bitmap bitmap;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    DatePickerDialog.OnDateSetListener onDateSetListener2;

    TextView grNo, admissionNo, category, admissiondate, class1, division, gender, dateOfBirth, bloodGroup, classTeacher, contactNo;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daycarechild);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Child Profile");
       // toolbar.setNavigationIcon(R.drawable.childprofbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);

        initialise();
        // database= FirebaseDatabase.getInstance();
        //  DatabaseReference reference= database.getReference("ParentProfile");

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




/*
        upload.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          /
                                          Intent intent = new Intent();
                                          intent.setType("image/*");
                                          intent.setAction(Intent.ACTION_GET_CONTENT);
                                          // startActivityForResult(intent,ImgReq);
                                          startActivityForResult(Intent.createChooser(intent, "Select Picture"), ImgReq);


                                          Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                          intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                          startActivityForResult(intent, 1);

                                      }

                                  }

        );
*/

    }

    @Override
    protected void onStart() {
        super.onStart();

        bundle = getIntent().getExtras();
        final String username = bundle.getString("Username");
        database = FirebaseDatabase.getInstance();
         DatabaseReference databaseReference = database.getReference("ChildProfile");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    if (username.equals(data.getKey())) {


                        // grNo.setText((CharSequence) data.child("grNo").getValue());
                        admissionNo.setText((CharSequence) data.child("admissionNo").getValue());
                        admissiondate.setText((CharSequence) data.child("admissiondate").getValue());
                        category.setText((CharSequence) data.child("category").getValue());
                        class1.setText((CharSequence) data.child("class1").getValue());
                        division.setText((CharSequence) data.child("division").getValue());
                        gender.setText((CharSequence) data.child("gender").getValue());
                        dateOfBirth.setText((CharSequence) data.child("dateOfBirth").getValue());
                        bloodGroup.setText((CharSequence) data.child("bloodGroup").getValue());
                        classTeacher.setText((CharSequence) data.child("classTeacher").getValue());
//                        contactNo.setText((CharSequence) data.child("contactNo").getValue());


                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databaseReference = database.getReference("Images").child("Child_Profile");
        final String user=username;
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot data : dataSnapshot.getChildren())
                {
                    if(user.equals(data.getKey()))
                    {

                        String url = data.getValue().toString();
                        if(url!=null) {
                            Glide.with(getApplicationContext().getApplicationContext()).load(url).into(childphoto);
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
        //update=(Button)findViewById(R.id.update);
        childphoto = (ImageView) findViewById(R.id.photo);
        //upload = (Button) findViewById(R.id.btn_image);
        admissiondate = (TextView) findViewById(R.id.admissionDate);
        category = (TextView) findViewById(R.id.category);
        class1 = (TextView) findViewById(R.id.class1);
        division = (TextView) findViewById(R.id.division);
        gender = (TextView) findViewById(R.id.gender);
        dateOfBirth = (TextView) findViewById(R.id.dateOfBirth);
        bloodGroup = (TextView) findViewById(R.id.bloodGroup);
        classTeacher = (TextView) findViewById(R.id.classTeacher);
        contactNo = (TextView) findViewById(R.id.contactNo);
        grNo = (TextView) findViewById(R.id.grNo);
        admissionNo = (TextView) findViewById(R.id.admissionNo);
        phone=(Button)findViewById(R.id.phone);

    }

/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ImgReq && requestCode == RESULT_OK && data != null) {


            Uri path=data.getData();
            try {

                Uri imageUri = data.getData();
                childphoto.setImageURI(imageUri);

                /*
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                childphoto.setImageBitmap(bitmap);
                childphoto.setVisibility(View.VISIBLE);

            } catch (Exception e) {
                e.printStackTrace();
            }


            if (resultCode == RESULT_OK) {
                if (requestCode == ImgReq) {

                    //Get ImageURi and load with help of picasso
                    //Uri selectedImageURI = data.getData();

                    Picasso.with(Main5Activity.this).load(data.getData()).noPlaceholder().centerCrop().fit()
                            .into((ImageView) findViewById(R.id.childphoto));
                }

            }



                Uri selectedImage = data.getData();
                childphoto.setImageURI(selectedImage);



        }
    }
    */

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








