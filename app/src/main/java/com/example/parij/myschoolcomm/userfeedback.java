package com.example.parij.myschoolcomm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class userfeedback extends AppCompatActivity {

    RatingBar one;
    RatingBar two;
    RatingBar three;
    FirebaseDatabase database;
    Button submit;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userfeedback);


        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Feedback");

        toolbar.setTitleTextColor(0xFFFFFFFF);


        one = (RatingBar) findViewById(R.id.ratingBar4);
        two = (RatingBar) findViewById(R.id.ratingBar5);
        three = (RatingBar) findViewById(R.id.ratingBar6);
        submit = (Button) findViewById(R.id.button4);

        database = FirebaseDatabase.getInstance();

        final ArrayList<Float> mylist = new ArrayList<Float>();
        final HashMap<Integer, Float> mymap = new HashMap<Integer, Float>();

        one.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating1, boolean fromUser) {

                mymap.put(1, rating1);

            }
        });

        two.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating2, boolean fromUser) {
                mymap.put(2, rating2);

            }
        });

        three.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating3, boolean fromUser) {

                mymap.put(3, rating3);

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SessionManagement.retrieveSharedPreferences(userfeedback.this);

                final String username = SessionManagement.username;

                if (mymap.size() == 3) {

                    DatabaseReference reference = database.getReference("Feedback").child(username).child("Qn1");


                    reference.setValue(mymap.get(1));


                    reference = database.getReference("Feedback").child(username).child("Qn2");

                    reference.setValue(mymap.get(2));


                    reference = database.getReference("Feedback").child(username).child("Qn3");

                    reference.setValue(mymap.get(3));

                    Toast.makeText(userfeedback.this, "Thank You!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(userfeedback.this, "Please fill all the fields!", Toast.LENGTH_SHORT).show();

                }

            }
        });




    }
}
