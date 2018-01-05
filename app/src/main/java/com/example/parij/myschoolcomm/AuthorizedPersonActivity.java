package com.example.parij.myschoolcomm;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthorizedPersonActivity extends AppCompatActivity {

    EditText name,relation,contactNo;
    TextView date1,date2;
    FirebaseDatabase database;
    Button submit;
    Button chooseButton;
    Bundle bundle;
    ArrayAdapter<CharSequence> arrayAdapter;
    ImageView personphoto;
  //  Button upload;
    Uri filePath;
    int PICK_IMAGE_REQUEST = 111;
    ProgressDialog pd;
    Button choosePhoto;

    DatePickerDialog.OnDateSetListener onDateSetListener;
    DatePickerDialog.OnDateSetListener onDateSetListener2;

    //creating reference to firebase storage
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://myschoolcomm-a80d4.appspot.com/Play_Authorized");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorized_person);

       // Intent intent = getIntent();

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Authorized to Collect");
        //toolbar.setNavigationIcon(R.drawable.parentbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);

        initialise();

       // bundle=getIntent().getExtras();
       // final String username = bundle.getString("Username");

        SessionManagement.retrieveSharedPreferences(AuthorizedPersonActivity.this);

        final String username = SessionManagement.username;
        pd = new ProgressDialog(this);
        pd.setMessage("Uploading....");


        date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year= cal.get(Calendar.YEAR);
                int month= cal.get(Calendar.MONTH);
                int day= cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog=new DatePickerDialog(AuthorizedPersonActivity.this,
                        android.R.style.Theme,onDateSetListener,year,month,day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();


            }
        });
        final String[] date11 = new String[1];
        onDateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                date11[0] = dayOfMonth+"/"+month+"/"+year;
                date1.setText(date11[0]);



            }
        };

        date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year= cal.get(Calendar.YEAR);
                int month= cal.get(Calendar.MONTH);
                int day= cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog=new DatePickerDialog(AuthorizedPersonActivity.this,
                        android.R.style.Theme,onDateSetListener2,year,month,day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();


            }
        });
        final String[] date22 = new String[1];
        onDateSetListener2=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                date22[0] = dayOfMonth+"/"+month+"/"+year;
                date2.setText(date22[0]);

            }
        };






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


      /*  upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    pd.show();

                    StorageReference childRef = storageRef.child(username+"image.jpg");

                if(filePath==null)
                {
                    Toast.makeText(getApplicationContext(),"Please Select a image!",Toast.LENGTH_LONG).show();
                    pd.dismiss();
                }
                else
                {
                    //uploading the image

                    UploadTask uploadTask = childRef.putFile(filePath);

                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            taskSnapshot.getMetadata();
                            String downloadurl= taskSnapshot.getDownloadUrl().toString();
                            database=FirebaseDatabase.getInstance();
                            DatabaseReference reference= database.getReference("Images").child("Authorized_Person");
                            reference.child(username).setValue(downloadurl);
                            pd.dismiss();
                            Toast.makeText(AuthorizedPersonActivity.this, "Upload successful", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(AuthorizedPersonActivity.this, "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                        }
                    });}

            }
        });*/


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mname= name.getText().toString().trim();
                String mcontactNo=contactNo.getText().toString().trim();
                String mrelation= relation.getText().toString().trim();
               String datestart = date1.getText().toString().trim();
                String dateend= date2.getText().toString().trim();

                int flag=0;

               if(TextUtils.isEmpty(mname))
               {
                   name.setError("Please enter the name");
                   flag++;
               }
               if(!TextUtils.isEmpty(mname) && flag==0)
               {
                   String regex = "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$";
                   Pattern pattern = Pattern.compile(regex);
                   Matcher matcher = pattern.matcher(mname);

                   if(!matcher.matches())
                   {
                       flag++;
                       name.setError("Please enter a valid name!");
                   }

               }
               if(TextUtils.isEmpty(mcontactNo))
               {
                   contactNo.setError("Please enter the contact number");
                   flag++;
               }
               if(!TextUtils.isEmpty(mcontactNo)&& flag==0)
               {
                   String regex = "[0-9*#+() -]*";
                   Pattern pattern = Pattern.compile(regex);
                   Matcher matcher = pattern.matcher(mcontactNo);

                   if (!matcher.matches()) {
                        flag++;
                       contactNo.setError("Enter a valid number!");
                       // Toast.makeText(getApplicationContext(),"Please enter a valid contact no!",Toast.LENGTH_LONG).show();
                   }

               }
               if(TextUtils.isEmpty(mrelation))
               {
                   relation.setError("Please enter the relation");
                   flag++;
               }
               if(!TextUtils.isEmpty(mrelation)&& flag==0)
               {
                   String regex = "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$";
                   Pattern pattern = Pattern.compile(regex);
                   Matcher matcher = pattern.matcher(mname);

                   if(!matcher.matches())
                   {
                       flag++;
                       name.setError("Invalid!");
                   }



               }
               if(TextUtils.isEmpty(datestart))
               {
                   flag++;
                   date1.setError("Please enter the starting date");
               }

                if(TextUtils.isEmpty(dateend))
                {
                    flag++;
                    date2.setError("Please enter the ending date");
                }

                if(flag==0)
                {

                    pd.show();

                    StorageReference childRef = storageRef.child(username+"image.jpg");

                    if(filePath==null && personphoto.getDrawable()==null)
                    {
                        Toast.makeText(getApplicationContext(),"Please Select a image!",Toast.LENGTH_LONG).show();
                        pd.dismiss();
                    }
                    else {
                        //uploading the image
                        database = FirebaseDatabase.getInstance();
                        DatabaseReference reference = database.getReference("authorizeToCollect");

                        pd.show();

                        if (filePath != null) {

                            authorizeclass authorize = new authorizeclass(mname, mcontactNo, mrelation, datestart, dateend);

                            reference.child(username).setValue(authorize);


                            UploadTask uploadTask = childRef.putFile(filePath);

                            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    taskSnapshot.getMetadata();
                                    String downloadurl = taskSnapshot.getDownloadUrl().toString();
                                    database = FirebaseDatabase.getInstance();
                                    DatabaseReference reference = database.getReference("Images").child("Authorized_Person");
                                    reference.child(username).setValue(downloadurl);
                                    pd.dismiss();
                                   // Toast.makeText(AuthorizedPersonActivity.this, "Successfully Updated!", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    pd.dismiss();
                                    Toast.makeText(AuthorizedPersonActivity.this, "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), "Successfully Updated!", Toast.LENGTH_SHORT).show();

                    }



                    //   Toast.makeText(getApplicationContext(),"Response submitted",Toast.LENGTH_LONG).show();



                }



            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            filePath = data.getData();

            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);


                personphoto.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    public void initialise()
    {
        //upload=(Button)findViewById(R.id.upload);
        personphoto=(ImageView)findViewById(R.id.personphoto);
        submit=(Button)findViewById(R.id.submit);
        name=(EditText)findViewById(R.id.name);
        contactNo=(EditText)findViewById(R.id.contactno);
        relation=(EditText)findViewById(R.id.relation);
        date1=(TextView) findViewById(R.id.date1);
       date2=(TextView) findViewById(R.id.date2);

        choosePhoto =(Button)findViewById(R.id.choose);
      //  chooseButton=(Button)findViewById(R.id.chooseButton);

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
