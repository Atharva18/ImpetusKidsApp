package com.example.parij.myschoolcomm;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class MainActivity extends AppCompatActivity {
    String user,pass;
    Button Login;
    Bundle bundle;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    AlertDialog dialog = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SessionManagement.retrieveSharedPreferences(MainActivity.this);
        //Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

        //getSupportActionBar().setTitle("Impetus Kids");
       // toolbar.setNavigationIcon(R.drawable.main1);
        //toolbar.setTitleTextColor(0xFFFFFFFF);
        main_initiate();


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder build=new AlertDialog.Builder(MainActivity.this);
                View mView=getLayoutInflater().inflate(R.layout.dialog_login,null);

                final EditText UserName=(EditText)mView.findViewById(R.id.uname);
                final EditText PassWord=(EditText)mView.findViewById(R.id.pwd);
                final Button lgn;


                lgn=(Button)mView.findViewById(R.id.LGNBTN);

                lgn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String username = UserName.getText().toString().trim();
                        final String passwordtxt = PassWord.getText().toString().trim();

                        final ProgressDialog pd = new ProgressDialog(MainActivity.this);
                        pd.setMessage("Loading...");
                        pd.show();

                        if(username.contains("Admin"))
                        {

                            if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(passwordtxt)) {

                                 DatabaseReference reference = database.getReference("UserNames").child("Admin");

                                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        String name=null,password=null;
                                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                                            name = data.getKey();
                                            password = data.child("password").getValue(String.class);

                                            if(username.equals(name))
                                                break;
                                        }

                                            if(username.equals(name) && passwordtxt.equals(password))
                                        {

                                            Toast.makeText(getApplicationContext(),"Logging you in",Toast.LENGTH_LONG).show();
                                            Intent intent=new Intent(MainActivity.this,admindashboard.class);
                                            Bundle bundle=new Bundle();
                                            bundle.putString("Username",username);
                                            intent.putExtras(bundle);
                                            pd.dismiss();
                                            dialog.dismiss();
                                            startActivity(intent);
                                            finish();


                                        }
                                        else
                                        {
                                            pd.dismiss();
                                            Toast.makeText(getApplicationContext(),"Incorrect username or password!",Toast.LENGTH_LONG).show();
                                            }

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        pd.dismiss();
                                        Toast.makeText(getApplicationContext(),"Something went wrong!",Toast.LENGTH_LONG).show();

                                    }

                                });

                            }
                            else
                            {

                                pd.dismiss();
                                Toast.makeText(getApplicationContext(),"Username or Password cannot be empty!",Toast.LENGTH_LONG).show();
                            }

                        }
                        else if(username.contains("Bloss"))
                        {
                            if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(passwordtxt)) {

                                DatabaseReference reference = database.getReference("UserNames").child("Blossoming");

                                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        String name=null,password=null;
                                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                                            name = data.getKey();
                                            password = data.child("password").getValue(String.class);

                                            if(username.equals(name))
                                                break;
                                        }

                                        if(username.equals(name) && passwordtxt.equals(password))
                                        {

                                            Toast.makeText(getApplicationContext(),"Redirecting to Blossoming Dashboard",Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(MainActivity.this,Main4Activity.class);
                                          //  Bundle bundle=new Bundle();
                                           // bundle.putString("Username",username);
                                           // intent.putExtras(bundle);
                                          //  pd.dismiss();
                                            pd.dismiss();
                                            dialog.dismiss();
                                            SessionManagement.username = username;
                                            SessionManagement.updateSharedPreferences();
                                            startActivity(intent);
                                            finish();
                                        }
                                        else
                                        {
                                            pd.dismiss();
                                            Toast.makeText(MainActivity.this,"Incorrect credentials!Please Try Again!",Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        pd.dismiss();
                                        Toast.makeText(getApplicationContext(),"Something went wrong!",Toast.LENGTH_LONG).show();

                                    }

                                });

                            }
                            else
                            {

                                pd.dismiss();
                                Toast.makeText(getApplicationContext(),"Username or Password cannot be empty!",Toast.LENGTH_LONG).show();
                            }
                        }
                        else if(username.contains("Dayc"))
                        {
                            if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(passwordtxt)) {

                                DatabaseReference reference = database.getReference("UserNames").child("DayCare");

                                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        String name=null,password=null;
                                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                                            name = data.getKey();
                                            password = data.child("password").getValue(String.class);

                                            if(username.equals(name))
                                                break;
                                        }

                                        if(username.equals(name) && passwordtxt.equals(password))
                                        {

                                            Toast.makeText(getApplicationContext(),"Redirecting to Day Care Dashboard",Toast.LENGTH_LONG).show();
                                            Intent intent=new Intent(MainActivity.this,daycaredashboard.class);
                                            Bundle bundle=new Bundle();
                                            bundle.putString("Username",username);
                                            intent.putExtras(bundle);
                                            pd.dismiss();
                                            dialog.dismiss();
                                            startActivity(intent);
                                            finish();
                                        }
                                        else
                                        {
                                            pd.dismiss();
                                            Toast.makeText(getApplicationContext(),"Incorrect username or password!",Toast.LENGTH_LONG).show();
                                        }

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        pd.dismiss();
                                        Toast.makeText(getApplicationContext(),"Something went wrong!",Toast.LENGTH_LONG).show();

                                    }

                                });

                            }
                            else
                            {

                                pd.dismiss();
                                Toast.makeText(getApplicationContext(),"Username or Password cannot be empty!",Toast.LENGTH_LONG).show();
                            }

                        }
                        else if(username.contains("Budd"))
                        {
                            if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(passwordtxt)) {

                                DatabaseReference reference = database.getReference("UserNames").child("Budding");

                                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String name=null,password=null;
                                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                                            name = data.getKey();
                                            password = data.child("password").getValue(String.class);

                                            if(username.equals(name))
                                                break;
                                        }

                                        if(username.equals(name) && passwordtxt.equals(password))
                                        {

                                            Toast.makeText(getApplicationContext(),"Redirecting to Budding Dashboard",Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(MainActivity.this,Main4Activity.class);
                                           // Bundle bundle=new Bundle();
                                            //bundle.putString("Username",username);
                                            //intent.putExtras(bundle);
                                           // pd.dismiss();
                                            pd.dismiss();
                                            dialog.dismiss();
                                            SessionManagement.username = username;
                                            SessionManagement.updateSharedPreferences();
                                            startActivity(intent);
                                            finish();

                                        }
                                        else
                                        {
                                            pd.dismiss();
                                            Toast.makeText(getApplicationContext(),"Incorrect username or password!",Toast.LENGTH_LONG).show();
                                        }

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        pd.dismiss();
                                        Toast.makeText(getApplicationContext(),"Something went wrong!",Toast.LENGTH_LONG).show();

                                    }

                                });

                            }
                            else
                            {

                                pd.dismiss();
                                Toast.makeText(getApplicationContext(),"Username or Password cannot be empty!",Toast.LENGTH_LONG).show();
                            }

                        }
                        else if(username.contains("Seed"))
                        {
                            if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(passwordtxt)) {

                                DatabaseReference reference = database.getReference("UserNames").child("Seeding");

                                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String name=null,password=null;
                                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                                            name = data.getKey();
                                            password = data.child("password").getValue(String.class);

                                            if(username.equals(name))
                                                break;
                                        }

                                        if(username.equals(name) && passwordtxt.equals(password))
                                        {



                                            Toast.makeText(getApplicationContext(),"Redirecting to Seeding Dashboard",Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(MainActivity.this,Main4Activity.class);
                                            pd.dismiss();
                                            dialog.dismiss();
                                            SessionManagement.username = username;
                                            SessionManagement.updateSharedPreferences();
                                            startActivity(intent);
                                            finish();

                                        }
                                        else
                                        {
                                            Log.println(Log.ERROR,"msg",username+" "+passwordtxt);
                                            //pd.dismiss();
                                            Toast.makeText(getApplicationContext(),"Incorrect username or password",Toast.LENGTH_LONG).show();
                                        }

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                /*reference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {


                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        pd.dismiss();
                                        Toast.makeText(getApplicationContext(),"Something went wrong!",Toast.LENGTH_LONG).show();

                                    }

                                });*/

                            }
                            else
                            {

                                pd.dismiss();
                                Toast.makeText(getApplicationContext(),"Username or Password cannot be empty!",Toast.LENGTH_LONG).show();
                            }

                        }
                        else if(username.contains("Flou"))
                        {
                            if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(passwordtxt)) {

                                DatabaseReference reference = database.getReference("UserNames").child("Flourishing");

                                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        String name=null,password=null;
                                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                                            name = data.getKey();
                                            password = data.child("password").getValue(String.class);

                                            if(username.equals(name))
                                                break;
                                        }

                                        if(username.equals(name) && passwordtxt.equals(password))
                                        {

                                            Toast.makeText(getApplicationContext(),"Redirecting to Flourishing Dashboard",Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(MainActivity.this,Main4Activity.class);
                                            //Bundle bundle=new Bundle();
                                          //  bundle.putString("Username",username);
                                        //    intent.putExtras(bundle);
                                            pd.dismiss();
                                            dialog.dismiss();
                                            SessionManagement.username = username;
                                            SessionManagement.updateSharedPreferences();
                                            startActivity(intent);
                                            finish();

                                        }
                                        else
                                        {
                                            pd.dismiss();
                                            Toast.makeText(getApplicationContext(),"Incorrect username or password!",Toast.LENGTH_LONG).show();
                                        }

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        pd.dismiss();
                                        Toast.makeText(getApplicationContext(),"Something went wrong!",Toast.LENGTH_LONG).show();

                                    }

                                });

                            }
                            else
                            {

                                pd.dismiss();
                                Toast.makeText(getApplicationContext(),"Username or Password cannot be empty!",Toast.LENGTH_LONG).show();
                            }


                        }


                    }






                });
                build.setView(mView);
                dialog=build.create();
                dialog.show();






            }

                    });
    }



    public void main_initiate(){
       // UserName=(EditText)findViewById(R.id.editText2);
        //PassWord=(EditText) findViewById(R.id.editText3);
        Login=(Button)findViewById(R.id.Login);

        //Register=(Button)findViewById(R.id.NewUser);
    }
}
