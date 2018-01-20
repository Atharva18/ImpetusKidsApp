package com.example.parij.myschoolcomm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.parij.myschoolcomm.Models.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ResetPassword extends AppCompatActivity{


    Button confirm;
    EditText oldpass;
    EditText newpass;
    FirebaseDatabase database;
    ArrayList<String> passwords;


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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_reset_password);
        SessionManagement.retrieveSharedPreferences(ResetPassword.this);

        final String username = SessionManagement.username;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Reset Password");
        toolbar.setTitleTextColor(0xFFFFFFFF);

        oldpass=(EditText)findViewById(R.id.oldPassword);
        newpass=(EditText)findViewById(R.id.newPassword);
        confirm=(Button)findViewById(R.id.confirm);


        database = FirebaseDatabase.getInstance();
        final DatabaseReference reference = database.getReference("newDb").child("students");

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String oldpassword = oldpass.getText().toString();
                final String newpassword = newpass.getText().toString();

                if (TextUtils.isEmpty(oldpassword))
                    oldpass.setError("Please enter the old password");
                else if (TextUtils.isEmpty(newpassword))
                    newpass.setText("Please enter the new password");
                else {
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Student student;

                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                student = ds.getValue(Student.class);

                                if (student.getUsername().equals(username)) {


                                    if (student.getPassword().equals(oldpassword)) {
                                        student.setPassword(newpassword);
                                        reference.child(ds.getKey()).setValue(student);
                                        Toast.makeText(ResetPassword.this, "Passwords Changed! Please Login Again!", Toast.LENGTH_SHORT).show();
                                        oldpass.getText().clear();
                                        newpass.getText().clear();
                                        startActivity(new Intent(ResetPassword.this, MainActivity.class));
                                        finish();
                                    } else {
                                        oldpass.setError("Please enter correct password");

                                    }

                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            }
        });
}
}