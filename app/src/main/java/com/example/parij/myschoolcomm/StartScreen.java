package com.example.parij.myschoolcomm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.FirebaseDatabase;


public class StartScreen extends AppCompatActivity {


    Button next;
    Button skip;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        FirebaseDatabase.getInstance().getReference().keepSynced(true);
        //databaseReference.keepSynced(true);
        setContentView(R.layout.activity_start_screen);

        next=(Button)findViewById(R.id.next);
        skip=(Button)findViewById(R.id.skipButton);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(StartScreen.this,Start_Screen2.class);
                startActivity(intent);
                finish();

            }
        });


        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(StartScreen.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });




    }
}
