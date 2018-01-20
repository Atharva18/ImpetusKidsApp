package com.example.parij.myschoolcomm;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.parij.myschoolcomm.Models.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * Created by admin on 13/11/2017.
 */

public class fragment1_adminparent extends Fragment  {

    final int ImgReq = 1;
    Bundle bundle;
    FirebaseDatabase database;
    TextView name1,contact1,email1;
    ImageView photo;
    Button call;
    String username="";
    Context context;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment1_adminparent, container, false);
        context = rootView.getContext();


        name1=(TextView) rootView.findViewById(R.id.name);
        contact1=(TextView) rootView.findViewById(R.id.contact);
        email1=(TextView) rootView.findViewById(R.id.email);
        photo=(ImageView) rootView.findViewById(R.id.photo);
        call=(Button)rootView.findViewById(R.id.call);

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

        database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("newDb").child("students");

        SessionManagement.retrieveSharedPreferences(context);

        final String username = SessionManagement.username;

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Student student;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    student = ds.getValue(Student.class);

                    if (student.getUsername().equals(username)) {

                        name1.setText(student.getFather().getName());
                        contact1.setText(student.getFather().getPhone());
                        email1.setText(student.getFather().getEmail());

                        String url = student.getFather().getImageLink();

                        if (!url.equals(""))
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