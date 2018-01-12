package com.example.parij.myschoolcomm;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class adminemergency extends AppCompatActivity {
    Button dialcontact,alternativebutton,dialdoctor;
    Button submit;
    TextView contactNo,alternativeNo,familyDoctor,doctorNo;
    FirebaseDatabase database;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminemergency);

        Intent intent=getIntent();

        bundle=getIntent().getExtras();
        final String username = bundle.getString("username");

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Emergency");
       // toolbar.setNavigationIcon(R.drawable.emergencybar);
        toolbar.setTitleTextColor(0xFFFFFFFF);

        initialise();


        dialcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String number = contactNo.getText().toString().trim();

                if(!TextUtils.isEmpty(number)) {
                    String dial = "tel:" + number;
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
                }else {
                    Toast.makeText(adminemergency.this, "Enter a phone number", Toast.LENGTH_SHORT).show();
                }

            }
        });


        alternativebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String number = alternativeNo.getText().toString().trim();

                if(!TextUtils.isEmpty(number)) {
                    String dial = "tel:" + number;
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
                }else {
                    Toast.makeText(adminemergency.this, "Enter a phone number", Toast.LENGTH_SHORT).show();
                }



            }
        });


        dialdoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String number = doctorNo.getText().toString();

                if(!TextUtils.isEmpty(number)) {
                    String dial = "tel:" + number;
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
                }else {
                    Toast.makeText(adminemergency.this, "Enter a phone number", Toast.LENGTH_SHORT).show();
                }



            }
        });




  /*      submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String contact =contactNo.getText().toString().trim();
                String alternative=alternativeNo.getText().toString().trim();
                String doctorname=familyDoctor.getText().toString().trim();
                String doctorno=doctorNo.getText().toString().trim();

                int flag=0;

                if(TextUtils.isEmpty(contact))
                {
                    flag++;
                    contactNo.setError("Cannot be Empty !");
                }
                if(!TextUtils.isEmpty(contact))
                {

                    String regex = "[0-9*#+() -]*";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(contact);

                    if (!matcher.matches()) {

                        contactNo.setError("Enter a valid number!");
                        // Toast.makeText(getApplicationContext(),"Please enter a valid contact no!",Toast.LENGTH_LONG).show();
                    }

                }
                if(TextUtils.isEmpty(alternative) )
                {
                    flag++;
                    alternativeNo.setError("Cannot be Empty !");


                }

                if(!TextUtils.isEmpty(alternative))
                {

                    String regex = "[0-9*#+() -]*";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(alternative);

                    if (!matcher.matches()) {
                        flag++;
                        alternativeNo.setError("Enter a valid number!");
                        // Toast.makeText(getApplicationContext(),"Please enter a valid contact no!",Toast.LENGTH_LONG).show();
                    }

                }
                if(!TextUtils.isEmpty(doctorname))
                {

                    String regex = "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(doctorname);

                    if(!matcher.matches())
                    {
                        flag++;
                        familyDoctor.setError("Please enter a valid name!");
                    }



                }
                if(!TextUtils.isEmpty(doctorno))
                {
                    String regex = "[0-9*#+() -]*";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(contact);

                    if (!matcher.matches()) {
                        flag++;
                        doctorNo.setError("Enter a valid number!");
                        // Toast.makeText(getApplicationContext(),"Please enter a valid contact no!",Toast.LENGTH_LONG).show();
                    }



                }
                if(flag==0)
                {
                    database=FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference= database.getReference("emergency");

                    emergencyclass emergency = new emergencyclass(contact,alternative,doctorname,doctorno);

                    databaseReference.child(username).setValue(emergency);

                    Toast.makeText(getApplicationContext(),"Your Response has been added",Toast.LENGTH_LONG).show();

                }
                else
                {

                    // Toast.makeText(getApplicationContext(),"Please fill all the mandatory fields!",Toast.LENGTH_LONG).show();
                }


            }
        });*/
    }

    @Override
    protected void onStart()
    {
        super.onStart();


        bundle=getIntent().getExtras();
        final String username = bundle.getString("username");
        database=FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference= database.getReference("emergency");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {

                for(DataSnapshot data : dataSnapshot.getChildren())
                {
                    if(username.equals(data.getKey()))
                    {
                        // contactNo.setText((CharSequence) data.child("mcontact"));
                        contactNo.setText((CharSequence) data.child("contact").getValue());
                        alternativeNo.setText((CharSequence)data.child("alternativeContact").getValue());
                        familyDoctor.setText((CharSequence)data.child("doctorname").getValue());
                        doctorNo.setText((CharSequence)data.child("doctorno").getValue());

                    }


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

                Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_LONG).show();

            }
        });







    }

    public void initialise()
    {
        dialcontact=(Button)findViewById(R.id.dialcontact);
        alternativebutton=(Button)findViewById(R.id.alternativebutton);
        dialdoctor=(Button)findViewById(R.id.dialdoctor);
//        submit=(Button)findViewById(R.id.submit);
        contactNo=(TextView) findViewById(R.id.contactNo);
        alternativeNo=(TextView)findViewById(R.id.alternativeNo);
        familyDoctor=(TextView) findViewById(R.id.familyDoctor);
        doctorNo=(TextView) findViewById(R.id.doctorNo);
    }



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






}
