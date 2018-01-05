package com.example.parij.myschoolcomm;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


/**
 * Created by admin on 13/11/2017.
 */

public class fragment3_parentmain extends Fragment implements View.OnClickListener {

   // Button upload;
    Button save;
    Bundle bundle;
    Button choosePhoto;
    FirebaseDatabase database;
    EditText name1,contact1,email1;
    String username="";
     int PICK_IMAGE_REQUEST=1;
     ProgressDialog pd;
     Uri filePath;
    CircleImageView photo;
    Context context;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://myschoolcomm-a80d4.appspot.com/Parents_Profile");



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment1_parentmain, container, false);

        context = rootView.getContext();
        SessionManagement.retrieveSharedPreferences(context);
        username = SessionManagement.username;

       // upload=(Button)rootView.findViewById(R.id.upload);
        save=(Button)rootView.findViewById(R.id.save);
        name1=(EditText)rootView.findViewById(R.id.name);
        contact1=(EditText)rootView.findViewById(R.id.contact);
        email1=(EditText)rootView.findViewById(R.id.email);
        photo=(CircleImageView)rootView.findViewById(R.id.photo);
        choosePhoto=(Button) rootView.findViewById(R.id.choose);
        // bundle=getActivity().getIntent().getExtras();
        context = rootView.getContext();
        pd = new ProgressDialog(getContext());
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

                    StorageReference childRef = storageRef.child(username+"motherImage.jpg");

                    //uploading the image
                if(filePath==null)
                {
                    Toast.makeText(getContext(),"Please Select a image!",Toast.LENGTH_LONG).show();
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
                            DatabaseReference reference= database.getReference("Images").child("Parents_Profile");
                            reference.child(username+"Guardian").setValue(downloadurl);
                            pd.dismiss();
                            Toast.makeText(getContext(), "Upload successful", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(getContext(), "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                        }
                    });}

            }
        });*/
        save.setOnClickListener(this);



        return rootView;
    }

    @Override
    public void onClick(View v) {

        //Log.println(Log.INFO,"hello","present");
        database=FirebaseDatabase.getInstance();
        DatabaseReference reference= database.getReference("ParentProfile");

        String name,contact,email;

        name=name1.getText().toString().trim();
        contact=contact1.getText().toString().trim();
        email=email1.getText().toString().trim();

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




            StorageReference childRef = storageRef.child(username+"guardianImage.jpg");

            //uploading the image
            if(filePath==null && photo.getDrawable()==null)
            {
                Toast.makeText(context,"Please Select a image!",Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
            else {
                fragment1 fragobj = new fragment1(name, contact, email);

                reference.child("guardian").child(username).setValue(fragobj);

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
                            DatabaseReference reference = database.getReference("Images").child("Parents_Profile");
                            reference.child(username + "Guardian").setValue(downloadurl);
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
               // pd.dismiss();
                if(check==0)
                Toast.makeText(getContext(), "Successfully Updated!", Toast.LENGTH_SHORT).show();

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
                Bitmap b = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);
                Glide.clear(photo);

                photo.setImageBitmap(b);
                Log.e("Choose Img","Reloaded the image");
                Log.e("Choose Img", "Path : " + filePath.toString());
                Glide.with(context.getApplicationContext()).load(filePath).into(photo);

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        else
        {
            Log.e("Choose Img","If failed");
        }
    }


    @Override
    public void onStart() {
        super.onStart();


        database=FirebaseDatabase.getInstance();
         DatabaseReference databaseReference= database.getReference("ParentProfile").child("guardian");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for(DataSnapshot data:dataSnapshot.getChildren())
                {
                    if(username.equals(data.getKey()))
                    {
                        name1.setText((CharSequence) data.child("name").getValue());
                        contact1.setText((CharSequence) data.child("contact").getValue());
                        email1.setText((CharSequence) data.child("email").getValue());

                    }

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(getContext(),"Oops! Something went wrong",Toast.LENGTH_LONG).show();

            }
        });

        databaseReference = database.getReference("Images").child("Parents_Profile");
        final String user=username+"Guardian";
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot data : dataSnapshot.getChildren())
                {
                    if(user.equals(data.getKey()))
                    {

                        String url = data.getValue().toString();
                        if(url!=null)
                       Glide.with(context.getApplicationContext()).load(url).into(photo);

                           // new DownLoadImageTask(photo).execute(url);
                        else
                            Toast.makeText(getActivity(),"Something Went Wrong!Please Try Again!",Toast.LENGTH_SHORT).show();
                    }
                }




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

}


class fragment3
{

    String name;
    String contact;
    String email;


    public fragment3()
    {


    }

    public fragment3(String name,String contact, String email)
    {
        this.name=name;
        this.contact=contact;
        this.email=email;


    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

