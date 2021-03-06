package com.example.parij.myschoolcomm;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.parij.myschoolcomm.Models.Parent;
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

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.app.Activity.RESULT_OK;


/**
 * Created by admin on 13/11/2017.
 */

public class fragment2_parentmain extends Fragment implements View.OnClickListener {

  //  Button upload;
    Button save;
    Bundle bundle;
    Button choosePhoto;
    FirebaseDatabase database;
    EditText name1,contact1,email1;
    String username="";
     int PICK_IMAGE_REQUEST=111;
    ImageView photo;
    ProgressDialog pd;
    Uri filePath;
    Context context;
    ArrayList<Student> studentArrayList;
    ArrayList<String> keysArrayList;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://impetus-kids-7e284.appspot.com/Parents_Profile");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment1_parentmain, container, false);
        Log.e("Fragment", "Fragment 2-Mother");
        context = rootView.getContext();
        SessionManagement.retrieveSharedPreferences(context);
        username = SessionManagement.username;

      //  upload=(Button)rootView.findViewById(R.id.upload);
        save = rootView.findViewById(R.id.save);
        name1 = rootView.findViewById(R.id.name);
        contact1 = rootView.findViewById(R.id.contact);
        email1 = rootView.findViewById(R.id.email);
        photo = rootView.findViewById(R.id.photo);
        choosePhoto = rootView.findViewById(R.id.choose);
        // bundle=getActivity().getIntent().getExtras();
        pd = new ProgressDialog(getContext());
        pd.setMessage("Uploading....");


        studentArrayList = new ArrayList<Student>();
        keysArrayList = new ArrayList<String>();

        database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("newDb").child("students");
        SessionManagement.retrieveSharedPreferences(context);
        username = SessionManagement.username;
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Student student;

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    student = ds.getValue(Student.class);
                    if (student.getUsername().equals(username)) {
                        name1.setText(student.getMother().getName().toString());
                        contact1.setText(student.getMother().getPhone().toString());
                        email1.setText(student.getMother().getEmail().toString());

                        String url = student.getMother().getImageLink();
                        if (!url.equals("")) {
                            Glide.with(context.getApplicationContext()).load(url).into(photo);
                        }

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

        save.setOnClickListener(this);

        return rootView;
    }


    @Override
    public void onClick(View v) {

        //Log.println(Log.INFO,"hello","present");
        String name,contact,email;

        name=name1.getText().toString().trim();
        contact=contact1.getText().toString().trim();
        email=email1.getText().toString().trim();
        database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("newDb").child("students");


        int flag=0;

        if(TextUtils.isEmpty(name)|| TextUtils.isEmpty(contact)||TextUtils.isEmpty(email))
        {
            flag++;
            Toast.makeText(getContext(),"Please fill all the fields!",Toast.LENGTH_LONG).show();
        }
        if(!TextUtils.isEmpty(email))
        {

            String regex = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(email);
            if (!matcher.matches()) {
                flag++;
                Toast.makeText(getContext(),"Please enter a valid email!",Toast.LENGTH_LONG).show();

            }

        }
        if(!TextUtils.isEmpty(contact))
        {

            String regex = "[0-9*#+() -]*";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(contact);

            if (!matcher.matches()) {
                flag++;
                Toast.makeText(getContext(),"Please enter a valid contact no!",Toast.LENGTH_LONG).show();
            }

        }

        if(flag==0) {

            StorageReference childRef = storageRef.child(username+"motherImage.jpg");

            if(filePath==null && photo.getDrawable()==null)
            {
                Toast.makeText(context,"Please Select a image!",Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
            else {
                //uploading the image


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
                final Parent parent = new Parent();
                parent.setEmail(email);
                parent.setName(name);
                parent.setPhone(contact);
                int check=0;

                if (filePath != null) {
                    check=1;
                    pd.show();
                    UploadTask uploadTask = childRef.putFile(filePath);

                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            taskSnapshot.getMetadata();
                            String downloadurl = taskSnapshot.getDownloadUrl().toString();
                            database = FirebaseDatabase.getInstance();
                            DatabaseReference reference = database.getReference("newDb").child("students");
                            parent.setImageLink(downloadurl);
                            Student student = new Student();
                            int position = 0;
                            for (Student stud : studentArrayList) {
                                if (stud.getUsername().equals(username)) {
                                    student = stud;
                                    position = studentArrayList.indexOf(student);
                                    break;
                                }
                            }
                            String key = keysArrayList.get(position);
                            ((parentmain) getActivity()).studentMain.setMother(parent);
                            reference.child(key).setValue(((parentmain) getActivity()).studentMain);
                            pd.dismiss();
                           Toast.makeText(getContext(), "Successfully updated!", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(getContext(), "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
             //   pd.dismiss();
                if (check == 0) {

                    database = FirebaseDatabase.getInstance();
                    reference = database.getReference("newDb").child("students");
                    ((parentmain) getActivity()).studentMain.setMother(parent);
                    reference.child(key).setValue(((parentmain) getActivity()).studentMain);
                    Toast.makeText(getContext(), "Successfully Updated!", Toast.LENGTH_SHORT).show();
                }
            }

            //Toast.makeText(getContext(), "Successfully Updated!", Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            filePath = data.getData();

            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), filePath);
                Bitmap resized = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
                photo.setImageBitmap(resized);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }



}
