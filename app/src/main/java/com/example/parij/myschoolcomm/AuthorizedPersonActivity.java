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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parij.myschoolcomm.Models.AuthorizedPerson;
import com.example.parij.myschoolcomm.Models.Student;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
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
    ArrayList<Student> studentArrayList;
    ArrayList<String> keysArrayList;

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


        final Calendar mycalender=Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                mycalender.set(Calendar.YEAR,i);
                // int year = mycalender.get(Calendar.YEAR);
                mycalender.set(Calendar.MONTH,i1);
                // int month = mycalender.get(Calendar.MONTH);
                mycalender.set(Calendar.DAY_OF_MONTH,i2);
                //  int day = mycalender.get(Calendar.DAY_OF_MONTH);
                UpdateLabel();
            }

            private void UpdateLabel() {
                String myFormat = "dd/MM/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                date1.setText(sdf.format(mycalender.getTime()));

            }
        };

        date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new DatePickerDialog(AuthorizedPersonActivity.this,date,mycalender.get(Calendar.YEAR),mycalender.get(Calendar.MONTH),mycalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        final Calendar mycalender2 = Calendar.getInstance();


        final DatePickerDialog.OnDateSetListener date11=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                // datePicker.setMinDate(Long.parseLong(date1.getText().toString()));
                mycalender2.set(Calendar.YEAR, i);
                mycalender2.set(Calendar.MONTH, i1);
                mycalender2.set(Calendar.DAY_OF_MONTH, i2);
                UpdateLabel();

            }


            private void UpdateLabel() {
                String myFormat = "dd/MM/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                date2.setText(sdf.format(mycalender2.getTime()));

            }
        };


        date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AuthorizedPersonActivity.this, date11, mycalender2.get(Calendar.YEAR), mycalender2.get(Calendar.MONTH), mycalender2.get(Calendar.DAY_OF_MONTH)).show();
            }
        });






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

        studentArrayList = new ArrayList<Student>();
        keysArrayList = new ArrayList<String>();

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("newDb").child("students");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Student student;
                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    student = ds.getValue(Student.class);
                    studentArrayList.add(student);
                    keysArrayList.add(ds.getKey());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mname= name.getText().toString().trim();
                String mcontactNo=contactNo.getText().toString().trim();
                String mrelation= relation.getText().toString().trim();
                String datestart = date1.getText().toString().trim();
                long start = mycalender.getTimeInMillis();
                long end = mycalender2.getTimeInMillis();
                String dateend= date2.getText().toString().trim();

                int flag=0;

                //Log.println(Log.ERROR,"msg", String.valueOf(start)+" "+String.valueOf(end));

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
                int check = 0;

                if (TextUtils.isEmpty(datestart) || datestart.contains("date"))
               {

                   check = 1;
                   if (flag == 0)
                       Toast.makeText(AuthorizedPersonActivity.this, "Please enter the start date", Toast.LENGTH_SHORT).show();
                   flag++;
               }

                if (TextUtils.isEmpty(dateend) || dateend.contains("date"))
                {
                    flag++;
                    if (check == 0)
                        Toast.makeText(AuthorizedPersonActivity.this, "Please enter the end date", Toast.LENGTH_SHORT).show();
                }

                if (start > end) {

                    flag++;
                    Toast.makeText(AuthorizedPersonActivity.this, "Please enter valid dates!", Toast.LENGTH_LONG).show();

                }
                if(flag==0)
                {

                    StorageReference childRef = storageRef.child(username+"image.jpg");
                    int position = 0;
                    if(filePath==null && personphoto.getDrawable()==null)
                    {
                        Toast.makeText(getApplicationContext(),"Please Select a image!",Toast.LENGTH_LONG).show();
                        pd.dismiss();
                    }
                    else {
                        //uploading the image

                        Student student = new Student();
                        for (Student stud : studentArrayList) {
                            if (stud.getUsername().equals(username)) {
                                student = stud;
                                position = studentArrayList.indexOf(student);
                                break;
                            }
                        }

                        String key = keysArrayList.get(position);

                        final AuthorizedPerson authorizedPerson = new AuthorizedPerson();
                        authorizedPerson.setRelation(mrelation);
                        authorizedPerson.setName(mname);
                        authorizedPerson.setPhone(mcontactNo);
                        authorizedPerson.setFromDate(datestart);
                        authorizedPerson.setToDate(dateend);

                        int status = 0;

                        if (filePath != null) {
                            status = 1;
                            pd.show();
                            UploadTask uploadTask = childRef.putFile(filePath);
                            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    taskSnapshot.getMetadata();
                                    String downloadurl = taskSnapshot.getDownloadUrl().toString();
                                    database = FirebaseDatabase.getInstance();
                                    //   DatabaseReference reference = database.getReference("Images").child("Authorized_Person");
                                    //  reference.child(username).setValue(downloadurl);
                                    authorizedPerson.setImageLink(downloadurl);
                                    pd.dismiss();
                                    Toast.makeText(AuthorizedPersonActivity.this, "Successfully Updated!", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    pd.dismiss();
                                    Toast.makeText(AuthorizedPersonActivity.this, "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }


                        student.setAuthorizedPerson(authorizedPerson);
                        reference.child(key).setValue(student);
                        if (status == 0)
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
