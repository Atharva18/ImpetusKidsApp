package com.example.parij.myschoolcomm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Logout extends AppCompatActivity {

    Button logout;

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        SessionManagement.retrieveSharedPreferences(Logout.this);
        SessionManagement.rememberMe = false;
        SessionManagement.username = "NA";
        SessionManagement.lastLoginTimestamp = 0;
        SessionManagement.updateSharedPreferences();
        Logout.this.finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        logout = (Button) findViewById(R.id.Logout);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SessionManagement.retrieveSharedPreferences(Logout.this);
                SessionManagement.rememberMe = false;
                SessionManagement.username = "NA";
                SessionManagement.lastLoginTimestamp = 0;
                SessionManagement.updateSharedPreferences();

                Intent intent = new Intent(Logout.this, MainActivity.class);
                startActivity(intent);
                Logout.this.finish();

            }
        });


    }
}
