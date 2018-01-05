package com.example.parij.myschoolcomm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Start_Screen2 extends AppCompatActivity {

    Button next;
    Button skip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start__screen2);

        next=(Button)findViewById(R.id.next);
        skip=(Button)findViewById(R.id.skipButton);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(Start_Screen2.this,start_screen3.class);
                startActivity(intent);

            }
        });


        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(Start_Screen2.this,MainActivity.class);
                startActivity(intent);


            }
        });




    }
}
