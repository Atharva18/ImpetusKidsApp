package com.example.parij.myschoolcomm;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class adminchild extends AppCompatActivity {


    ImageView childphoto;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    DatePickerDialog.OnDateSetListener onDateSetListener2;
    Bundle bundle;
     // Button upload;
      Button choosePhoto;
    //  Button update;
   // Button phone;
    Bitmap bitmap;
    Uri filePath;
    ProgressDialog pd;
    Button save;
    int PICK_IMAGE_REQUEST=111;
    EditText grNo, admissionNo, category, class1, division, gender, bloodGroup, classTeacher, contactNo;
    FirebaseDatabase database;
    TextView admissiondate, dateOfBirth;
    ViewGroup rootScrollView;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://myschoolcomm-a80d4.appspot.com/Child_Profile");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminchild);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Child Profile");
       // toolbar.setNavigationIcon(R.drawable.childprofbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        bundle = getIntent().getExtras();
        final String username = bundle.getString("username");
        initialise();
         database= FirebaseDatabase.getInstance();
          DatabaseReference reference= database.getReference("ChildProfile");
        pd = new ProgressDialog(this);
        pd.setMessage("Uploading....");

        choosePhoto.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);

            }
        });

/*
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    pd.show();

                    StorageReference childRef = storageRef.child(username+"image.jpg");

                    //uploading the image

                if(filePath==null)
                {
                    Toast.makeText(getApplicationContext(),"Please Select a image!",Toast.LENGTH_LONG).show();
                    pd.dismiss();
                }
                else
                {
                    UploadTask uploadTask = childRef.putFile(filePath);

                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            taskSnapshot.getMetadata();
                            String downloadurl= taskSnapshot.getDownloadUrl().toString();
                            database=FirebaseDatabase.getInstance();
                            DatabaseReference reference= database.getReference("Images").child("Child_Profile");
                            reference.child(username).setValue(downloadurl);
                            pd.dismiss();
                            Toast.makeText(adminchild.this, "Upload successful", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(adminchild.this, "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                        }
                    });}

            }
        });

*/


        final Calendar mycalender=Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                mycalender.set(Calendar.YEAR,i);
                mycalender.set(Calendar.MONTH,i1);
                mycalender.set(Calendar.DAY_OF_MONTH,i2);
                UpdateLabel();
            }

            private void UpdateLabel() {
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                admissiondate.setText(sdf.format(mycalender.getTime()));

            }
        };


        admissiondate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(adminchild.this,date,mycalender.get(Calendar.YEAR),mycalender.get(Calendar.MONTH),mycalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



        final DatePickerDialog.OnDateSetListener date1=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                mycalender.set(Calendar.YEAR,i);
                mycalender.set(Calendar.MONTH,i1);
                mycalender.set(Calendar.DAY_OF_MONTH,i2);
                UpdateLabel();
            }

            private void UpdateLabel() {
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                dateOfBirth.setText(sdf.format(mycalender.getTime()));

            }
        };


        dateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(adminchild.this,date1,mycalender.get(Calendar.YEAR),mycalender.get(Calendar.MONTH),mycalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });





        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String grNo1,gender1,division1,dateOfBirth1,contactNo1,classTeacher1,class11,category1,bloodGroup1,admissiondate1,admissionNo1;

                database=FirebaseDatabase.getInstance();
                DatabaseReference reference= database.getReference("ChildProfile");

                grNo1=grNo.getText().toString();
                division1=division.getText().toString();
                dateOfBirth1=dateOfBirth.getText().toString();
                contactNo1=contactNo.getText().toString();
                classTeacher1=classTeacher.getText().toString();
                class11=class1.getText().toString();
                category1=category.getText().toString();
                bloodGroup1=bloodGroup.getText().toString();
                admissiondate1=admissiondate.getText().toString();
                admissionNo1=admissionNo.getText().toString();
                gender1=gender.getText().toString();



                int flag=0;

                if(TextUtils.isEmpty(dateOfBirth1))
                {
                    dateOfBirth.setError("Please enter the date!");
                    flag++;

                }
                if(!TextUtils.isEmpty(contactNo1))
                {

                    String regex = "[0-9*#+() -]*";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(contactNo1);

                    if (!matcher.matches()) {
                        flag++;
                        contactNo.setError("Enter a valid number!");
                        // Toast.makeText(getApplicationContext(),"Please enter a valid contact no!",Toast.LENGTH_LONG).show();
                    }

                }

                if(!TextUtils.isEmpty(classTeacher1))
                {
                    String regex = "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(classTeacher1);

                    if(!matcher.matches())
                    {
                        flag++;
                        classTeacher.setError("Please enter a valid name!");
                    }

                }

                if(!TextUtils.isEmpty(category1))
                {
                    String regex = "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(category1);

                    if(!matcher.matches())
                    {
                        flag++;
                        category.setError("Please enter a valid category!");
                    }

                }

                if(TextUtils.isEmpty(admissiondate1))
                {
                   flag++;
                   admissiondate.setError("Please enter the date");

                }


            if(flag==0) {



                StorageReference childRef = storageRef.child(username+"image.jpg");

                //uploading the image

                if(filePath==null && childphoto.getDrawable()==null)
                {
                    Toast.makeText(getApplicationContext(),"Please Select a image!",Toast.LENGTH_LONG).show();
                    pd.dismiss();
                }
                else {

                    int check = 0;

                    childdet obj = new childdet(grNo1, gender1, division1, dateOfBirth1,
                            contactNo1, classTeacher1, class11, category1, bloodGroup1, admissiondate1, admissionNo1);

                    reference.child(username).setValue(obj);

                    if (filePath != null) {
                        pd.show();
                        check = 1;
                        UploadTask uploadTask = childRef.putFile(filePath);

                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                taskSnapshot.getMetadata();
                                String downloadurl = taskSnapshot.getDownloadUrl().toString();
                                database = FirebaseDatabase.getInstance();
                                DatabaseReference reference = database.getReference("Images").child("Child_Profile");
                                reference.child(username).setValue(downloadurl);
                                pd.dismiss();
                                Toast.makeText(adminchild.this, "Successfully Updated!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                pd.dismiss();
                                Toast.makeText(adminchild.this, "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    if (check == 0)
                    Toast.makeText(getApplicationContext(), "Successfully Updated!", Toast.LENGTH_SHORT).show();
                }





//                Toast.makeText(getApplicationContext(), "Successfully Updated!", Toast.LENGTH_LONG).show();


            }

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        bundle = getIntent().getExtras();
        final String username = bundle.getString("username");
        database = FirebaseDatabase.getInstance();
         DatabaseReference databaseReference = database.getReference("ChildProfile");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    if (username.equals(data.getKey())) {


                         grNo.setText((CharSequence) data.child("grNo").getValue());
                        admissionNo.setText((CharSequence) data.child("admissionNo").getValue());
                        admissiondate.setText((CharSequence) data.child("admissiondate").getValue());
                        category.setText((CharSequence) data.child("category").getValue());
                        class1.setText((CharSequence) data.child("class1").getValue());
                        division.setText((CharSequence) data.child("division").getValue());
                        gender.setText((CharSequence) data.child("gender").getValue());
                        dateOfBirth.setText((CharSequence) data.child("dateOfBirth").getValue());
                        bloodGroup.setText((CharSequence) data.child("bloodGroup").getValue());
                        classTeacher.setText((CharSequence) data.child("classTeacher").getValue());
                       contactNo.setText((CharSequence) data.child("contactNo").getValue());


                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databaseReference = database.getReference("Images").child("Child_Profile");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot data : dataSnapshot.getChildren())
                {
                    if(username.equals(data.getKey()))
                    {

                        String url = data.getValue().toString();
                        if(url!=null)
                        Glide.with(getApplicationContext()).load(url).into(childphoto);
                        else
                            Toast.makeText(adminchild.this,"Something went wrong!Please try again!",Toast.LENGTH_SHORT).show();
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            filePath = data.getData();

            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                bitmap = Bitmap.createScaledBitmap(bitmap, (int) (150 * getResources().getDisplayMetrics().density), (int) (150 * getResources().getDisplayMetrics().density), false);


                childphoto.setImageBitmap(bitmap);
                rootScrollView.invalidate();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    public void initialise() {
        //update=(Button)findViewById(R.id.update);
         childphoto = (ImageView) findViewById(R.id.childphoto);
       // upload = (Button) findViewById(R.id.upload);
        admissiondate = (TextView) findViewById(R.id.admissionDate);
        category = (EditText) findViewById(R.id.category);
        class1 = (EditText) findViewById(R.id.class1);
        division = (EditText) findViewById(R.id.division);
        gender = (EditText) findViewById(R.id.gender);
        dateOfBirth = (TextView) findViewById(R.id.dateOfBirth);
        bloodGroup = (EditText) findViewById(R.id.bloodGroup);
        classTeacher = (EditText) findViewById(R.id.classTeacher);
        contactNo = (EditText) findViewById(R.id.contactNo);
        grNo = (EditText) findViewById(R.id.grNo);
        admissionNo = (EditText) findViewById(R.id.admissionNo);
        save=(Button)findViewById(R.id.save);
        choosePhoto=(Button)findViewById(R.id.choosePhoto);
       // phone=(Button)findViewById(R.id.phone);
        rootScrollView = (ViewGroup) findViewById(R.id.scrollViewAdminChild);

    }


}

class childdet
{

    String grNo,gender,division,dateOfBirth,contactNo,classTeacher,class1,category,bloodGroup,admissiondate,admissionNo;

    public childdet()
    {

    }

    public childdet(String grNo, String gender, String division, String dateOfBirth, String contactNo, String classTeacher, String class1, String category, String bloodGroup, String admissiondate, String admissionNo) {
        this.grNo = grNo;
        this.gender = gender;
        this.division = division;
        this.dateOfBirth = dateOfBirth;
        this.contactNo = contactNo;
        this.classTeacher = classTeacher;
        this.class1 = class1;
        this.category = category;
        this.bloodGroup = bloodGroup;
        this.admissiondate = admissiondate;
        this.admissionNo = admissionNo;
    }


    public String getGrNo() {
        return grNo;
    }

    public void setGrNo(String grNo) {
        this.grNo = grNo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getClassTeacher() {
        return classTeacher;
    }

    public void setClassTeacher(String classTeacher) {
        this.classTeacher = classTeacher;
    }

    public String getClass1() {
        return class1;
    }

    public void setClass1(String class1) {
        this.class1 = class1;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getAdmissiondate() {
        return admissiondate;
    }

    public void setAdmissiondate(String admissiondate) {
        this.admissiondate = admissiondate;
    }

    public String getAdmissionNo() {
        return admissionNo;
    }

    public void setAdmissionNo(String admissionNo) {
        this.admissionNo = admissionNo;
    }
}


