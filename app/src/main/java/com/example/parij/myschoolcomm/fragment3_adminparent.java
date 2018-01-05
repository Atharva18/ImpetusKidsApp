package com.example.parij.myschoolcomm;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.app.Activity.RESULT_OK;


/**
 * Created by admin on 13/11/2017.
 */

public class fragment3_adminparent extends Fragment {

   // Button upload;
   // Button save;
    Bundle bundle;
    FirebaseDatabase database;
    TextView name1,contact1,email1;
    String username="";
    final int ImgReq=1;
    Button call;
    ImageView photo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_fragment3_adminparent, container, false);

        bundle=getActivity().getIntent().getExtras();
        username = bundle.getString("Username");

       // upload=(Button)rootView.findViewById(R.id.upload);
       // save=(Button)rootView.findViewById(R.id.save);
        name1=(TextView) rootView.findViewById(R.id.name);
        contact1=(TextView) rootView.findViewById(R.id.contact);
        email1=(TextView) rootView.findViewById(R.id.email);
        photo=(ImageView)rootView.findViewById(R.id.photo);
        call=(Button)rootView.findViewById(R.id.call);

        // bundle=getActivity().getIntent().getExtras();

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String number = contact1.getText().toString().trim();

                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));




            }
        });


        return rootView;
    }




    @Override
    public void onStart() {
        super.onStart();

        bundle=getActivity().getIntent().getExtras();
        final String username = bundle.getString("username");
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
                        Glide.with(getActivity().getApplicationContext()).load(url).into(photo);

                    }
                }




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }







}



