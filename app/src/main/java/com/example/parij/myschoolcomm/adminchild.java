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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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

public class adminchild extends AppCompatActivity {


    ImageView childphoto;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    DatePickerDialog.OnDateSetListener onDateSetListener2;
      Button choosePhoto;
      Spinner program;
      Spinner batch;
    Spinner bloodgroup;
    Spinner genderspinner;
      ArrayAdapter programadapter;
      ArrayAdapter batchadapter;
    ArrayAdapter bloodgroupadapter;
    ArrayAdapter genderadapter;
    Bitmap bitmap;
    Uri filePath;
    ProgressDialog pd;
    Button save;
    int PICK_IMAGE_REQUEST=111;
    EditText classTeacher, contactNo;
    FirebaseDatabase database;
    TextView dateOfBirth, admissiondate;
    ImageView dateofbirthbutton, admissionbutton;
    ViewGroup rootScrollView;
    ArrayList<Student> studentArrayList;
    ArrayList<String> keysArrayList;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://impetus-kids-7e284.appspot.com/Child_Profile");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminchild);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Child Profile");
       // toolbar.setNavigationIcon(R.drawable.childprofbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        SessionManagement.retrieveSharedPreferences(adminchild.this);
        final String username = SessionManagement.username;


        initialise();

        pd = new ProgressDialog(this);
        pd.setMessage("Uploading....");

        program = findViewById(R.id.class1);
        batch = findViewById(R.id.division);
        bloodgroup = findViewById(R.id.bloodGrpSpinner);
        genderspinner = findViewById(R.id.genderSpinner);

        programadapter = ArrayAdapter.createFromResource(this, R.array.Type, android.R.layout.simple_spinner_item);
        programadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        program.setAdapter(programadapter);

        batchadapter = ArrayAdapter.createFromResource(this, R.array.Batch, android.R.layout.simple_spinner_item);
        batchadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        batch.setAdapter(batchadapter);


        bloodgroupadapter = ArrayAdapter.createFromResource(this, R.array.bloodgroup, android.R.layout.simple_spinner_item);
        bloodgroupadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodgroup.setAdapter(bloodgroupadapter);

        genderadapter = ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_item);
        genderadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderspinner.setAdapter(genderadapter);


        studentArrayList = new ArrayList<Student>();
        keysArrayList = new ArrayList<String>();

        SessionManagement.retrieveSharedPreferences(adminchild.this);

        // final String username = SessionManagement.username;

        database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("newDb").child("students");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Student student;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    student = ds.getValue(Student.class);

                    if (student.getUsername().equals(username)) {
                        admissiondate.setText(student.getAdmissionDate().toString());

                        int programCode = student.getProgram();

                        if (programCode == 4)
                            program.setSelection(0);
                        else if (programCode == 5)
                            program.setSelection(1);
                        else if (programCode == 6)
                            program.setSelection(2);
                        else if (programCode == 7)
                            program.setSelection(3);
                        else if (programCode == 8)
                            program.setSelection(4);

                        int batchCode = student.getBatch();

                        if (batchCode == 9)
                            batch.setSelection(0);
                        else if (batchCode == 10)
                            batch.setSelection(1);

                        int genderCode = student.getGender();

                        if (genderCode == 18)
                            genderspinner.setSelection(0);
                        else if (genderCode == 19)
                            genderspinner.setSelection(1);

                        dateOfBirth.setText(student.getDateOfBirth());

                        int bloodGroupCode = student.getBloodGroup();

                        if (bloodGroupCode == 11)
                            bloodgroup.setSelection(0);
                        else if (bloodGroupCode == 12)
                            bloodgroup.setSelection(1);
                        else if (bloodGroupCode == 14)
                            bloodgroup.setSelection(4);
                        else if (bloodGroupCode == 15)
                            bloodgroup.setSelection(5);
                        else if (bloodGroupCode == 16)
                            bloodgroup.setSelection(6);
                        else if (bloodGroupCode == 17)
                            bloodgroup.setSelection(7);
                        else if (bloodGroupCode == 21)
                            bloodgroup.setSelection(2);
                        else if (bloodGroupCode == 22)
                            bloodgroup.setSelection(3);

                        classTeacher.setText(student.getClassTeacherName());
                        contactNo.setText(student.getClassTeacherPhone());

                        String url = student.getImageLink();

                        if (!url.equals(""))
                            Glide.with(getApplicationContext()).load(url).into(childphoto);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("newDb").child("students");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Student student;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    student = ds.getValue(Student.class);
                    studentArrayList.add(student);
                    keysArrayList.add(ds.getKey());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
                String myFormat = "dd/MM/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                admissiondate.setText(sdf.format(mycalender.getTime()));

            }
        };


        admissionbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(adminchild.this, date, mycalender.get(Calendar.YEAR),
                        mycalender.get(Calendar.MONTH), mycalender.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();

            }
        });

        final Calendar mycalender2 = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date1=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                mycalender2.set(Calendar.YEAR, i);
                mycalender2.set(Calendar.MONTH, i1);
                mycalender2.set(Calendar.DAY_OF_MONTH, i2);
                UpdateLabel();
            }

            private void UpdateLabel() {
                String myFormat = "dd/MM/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                dateOfBirth.setText(sdf.format(mycalender2.getTime()));

            }
        };


        dateofbirthbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(adminchild.this, date1, mycalender2.get(Calendar.YEAR),
                        mycalender2.get(Calendar.MONTH), mycalender2.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();


            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dateOfBirth1, contactNo1, classTeacher1, admissiondate1;

                database=FirebaseDatabase.getInstance();
                final DatabaseReference reference = database.getReference("newDb").child("students");

                dateOfBirth1=dateOfBirth.getText().toString();
                contactNo1=contactNo.getText().toString();
                classTeacher1=classTeacher.getText().toString();
                admissiondate1=admissiondate.getText().toString();

                int flag=0;

                if(TextUtils.isEmpty(dateOfBirth1))
                {
                    Toast.makeText(adminchild.this, "Please select the date of birth!", Toast.LENGTH_SHORT).show();
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
                if(TextUtils.isEmpty(admissiondate1))
                {
                   flag++;
                    Toast.makeText(adminchild.this, "Please select the admission date!", Toast.LENGTH_SHORT).show();

                }
            if(flag==0) {

                StorageReference childRef = storageRef.child(username+"image.jpg");


                if(filePath==null && childphoto.getDrawable()==null)
                {
                    Toast.makeText(getApplicationContext(),"Please Select a image!",Toast.LENGTH_LONG).show();
                    pd.dismiss();
                }
                else {

                    int check = 0;
                    int position = 0;

                    Student student = new Student();
                    for (Student stud : studentArrayList) {
                        if (stud.getUsername().equals(username)) {
                            student = stud;
                            position = studentArrayList.indexOf(student);
                            break;
                        }
                    }

                    String key = keysArrayList.get(position);

                    student.setAdmissionDate(admissiondate1);

                    if (program.getSelectedItemPosition() == 0)
                        student.setProgram(Constants.DAYCARE);
                    else if (program.getSelectedItemPosition() == 1)
                        student.setProgram(Constants.SEEDING);
                    else if (program.getSelectedItemPosition() == 2)
                        student.setProgram(Constants.BUDDING);
                    else if (program.getSelectedItemPosition() == 3)
                        student.setProgram(Constants.BLOSSOMING);
                    else if (program.getSelectedItemPosition() == 4)
                        student.setProgram(Constants.FLOURISHING);

                    if (batch.getSelectedItemPosition() == 0)
                        student.setBatch(Constants.MORNING);
                    else if (batch.getSelectedItemPosition() == 1)
                        student.setBatch(Constants.AFTERNOON);

                    if (genderspinner.getSelectedItemPosition() == 0)
                        student.setGender(Constants.MALE);
                    else if (genderspinner.getSelectedItemPosition() == 1)
                        student.setGender(Constants.FEMALE);

                    student.setDateOfBirth(dateOfBirth1);

                    if (bloodgroup.getSelectedItemPosition() == 0)
                        student.setBloodGroup(Constants.APositive);
                    else if (bloodgroup.getSelectedItemPosition() == 1)
                        student.setBloodGroup(Constants.BPositive);
                    else if (bloodgroup.getSelectedItemPosition() == 2)
                        student.setBloodGroup(Constants.ABPositive);
                    else if (bloodgroup.getSelectedItemPosition() == 3)
                        student.setBloodGroup(Constants.ABNegative);
                    else if (bloodgroup.getSelectedItemPosition() == 4)
                        student.setBloodGroup(Constants.ANegative);
                    else if (bloodgroup.getSelectedItemPosition() == 5)
                        student.setBloodGroup(Constants.BNegative);
                    else if (bloodgroup.getSelectedItemPosition() == 6)
                        student.setBloodGroup(Constants.OPositive);
                    else if (bloodgroup.getSelectedItemPosition() == 7)
                        student.setBloodGroup(Constants.ONegative);


                    student.setClassTeacherName(classTeacher1);
                    student.setClassTeacherPhone(contactNo1);

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
                                Student student = new Student();
                                int position = 0;
                                for (Student stud : studentArrayList) {
                                    if (stud.getUsername().equals(username)) {
                                        student = stud;
                                        position = studentArrayList.indexOf(student);
                                        break;
                                    }
                                }
                                student.setImageLink(downloadurl);
                                String key = keysArrayList.get(position);
                                reference.child(key).setValue(student);
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
                    if (check == 0) {
                        reference.child(key).setValue(student);
                        Toast.makeText(getApplicationContext(), "Successfully Updated!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

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

        childphoto = findViewById(R.id.childphoto);
        admissiondate = findViewById(R.id.admissionDate);
        program = findViewById(R.id.class1);
        batch = findViewById(R.id.division);
        dateOfBirth = findViewById(R.id.dateOfBirth);
        classTeacher = findViewById(R.id.classTeacher);
        contactNo = findViewById(R.id.contactNo);
        save = findViewById(R.id.save);
        admissionbutton = findViewById(R.id.admissionbutton);
        dateofbirthbutton = findViewById(R.id.dateofbirthbutton);
        choosePhoto = findViewById(R.id.choosePhoto);
        rootScrollView = findViewById(R.id.scrollViewAdminChild);

    }


}



