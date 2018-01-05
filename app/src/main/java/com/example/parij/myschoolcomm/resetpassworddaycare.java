package com.example.parij.myschoolcomm;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class resetpassworddaycare extends AppCompatActivity{


    Button confirm;
    EditText oldpass;
    EditText newpass;
    FirebaseDatabase database;
    Bundle bundle;

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
       // SessionManagement.retrieveSharedPreferences(resetpassworddaycare.this);

       // final String username = SessionManagement.username;
        bundle = getIntent().getExtras();
        final String username = bundle.getString("Username");


       // Toast.makeText(getApplicationContext(),username,Toast.LENGTH_SHORT).show();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Reset Password");
        //  toolbar.setNavigationIcon(R.drawable.childprofbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);



        oldpass=(EditText)findViewById(R.id.oldPassword);
        newpass=(EditText)findViewById(R.id.newPassword);
        confirm=(Button)findViewById(R.id.confirm);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                final String old= oldpass.getText().toString();
                final String newp=newpass.getText().toString();
                String type="";int flag=0;
                final String[] check = {""};
                String checkstr="";

                database=FirebaseDatabase.getInstance();
                if(!TextUtils.isEmpty(old))
                {
                    if(username.contains("Bloss"))
                    {
                        type="Blossoming";

                    }
                    else if(username.contains("Budd"))
                    {
                        type="Budding";

                    }
                    else if(username.contains("Flou"))
                    {
                        type="Flourishing";
                    }
                    else if(username.contains("Seed"))
                    {
                        type="Seeding";

                    }

                    else if(username.contains("Dayc"))
                    {
                        type="DayCare";

                    }

                }
                else
                {
                    flag=1;
                    oldpass.setError("Please enter the old password!");

                }

                if(flag==0)
                {
                    if(TextUtils.isEmpty(newp))
                    {

                        newpass.setError("Please enter a password!");


                    }
                    else
                    {
                        final boolean[] flag1 = {false};
                        final DatabaseReference reference= database.getReference("UserNames").child(type);
                        final String finalType = type;
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for(DataSnapshot data : dataSnapshot.getChildren())
                                {

                                    if(username.equals(data.getKey()))

                                    {
                                        //Toast.makeText(getApplicationContext(),"Passwords1 Matched!",Toast.LENGTH_LONG).show();
                                        check[0] = (String) data.child("password").getValue();


                                        func(check[0],old,newp, finalType,reference);
                                        flag1[0]=true;
                                        /*
                                        {

                                         reference.child(username).setValue(newp);
                                         Toast.makeText(getApplicationContext(),"Password Changed!",Toast.LENGTH_LONG).show();


                                        }

*/

                                        break;
                                    }

                                    if(flag1[0])
                                        break;;
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                                Toast.makeText(getApplicationContext(),"Error occured",Toast.LENGTH_LONG).show();

                            }
                        });


                    }

                }

            }
        });

    }

    public void func(String check,String old,String newpas,String type,DatabaseReference reference)
    {
      //  SessionManagement.retrieveSharedPreferences(resetpassworddaycare.this);
      //  final String username = SessionManagement.username;

        bundle = getIntent().getExtras();
        final String username = bundle.getString("Username");

        if(check.equals(old))
        {



            //    DatabaseReference databaseReference = database.getReference("UserNames").child(type);

            reference.child(username).child("password").setValue(newpas);

            Toast.makeText(getApplicationContext(),"Passwords Changed! Please Login again!",Toast.LENGTH_LONG).show();

            oldpass.getText().clear();
            newpass.getText().clear();
            //return true;

            // this.finish();

            startActivity(new Intent(resetpassworddaycare.this, MainActivity.class));
            finish();



        }
        else
        {
            Log.println(Log.INFO,"message","inside"+ check+" "+old);
            // Toast.makeText(getApplicationContext(),"Passwords Not Matched!",Toast.LENGTH_LONG).show();
            oldpass.setError("Please enter correct password!");
            // return false;
        }



    }

}
