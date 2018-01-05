package com.example.parij.myschoolcomm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class start_screen3 extends AppCompatActivity {
Button next;
Button gotit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen3);

        next=(Button)findViewById(R.id.next);
        gotit=(Button)findViewById(R.id.gotit);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(start_screen3.this,MainActivity.class);
                startActivity(intent);

            }
        });

        gotit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1=new Intent(start_screen3.this,MainActivity.class);
                startActivity(intent1);
            }
        });



    }
}
