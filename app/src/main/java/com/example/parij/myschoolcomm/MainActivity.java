package com.example.parij.myschoolcomm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.parij.myschoolcomm.Models.Admin;
import com.example.parij.myschoolcomm.Models.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    String user,pass;
    Button Login;
    Bundle bundle;
    ImageView imageViewLogin;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    ProgressDialog pd;
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
        checkSavedLogin();

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    public void loginAdmin(Admin admin) {
        Toast.makeText(getApplicationContext(), "Logging you in", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, admindashboard.class);

        SessionManagement.username = admin.getUsername();
        SessionManagement.lastLoginTimestamp = System.currentTimeMillis();
        SessionManagement.isAdmin = true;
        SessionManagement.updateSharedPreferences();

        pd.dismiss();
        dialog.dismiss();
        startActivity(intent);
        finish();
    }

    public void loginUser(Student student) {
        Toast.makeText(getApplicationContext(), "Logging you in", Toast.LENGTH_SHORT).show();
        Intent intent;
        if (student.getProgram() == Constants.DAYCARE)
            intent = new Intent(MainActivity.this, daycaredashboard.class);
        else
            intent = new Intent(MainActivity.this, Main4Activity.class);
        SessionManagement.username = student.getUsername();
        SessionManagement.lastLoginTimestamp = System.currentTimeMillis();
        SessionManagement.updateSharedPreferences();

        pd.dismiss();
        dialog.dismiss();
        startActivity(intent);
        finish();
    }
    public void checkSavedLogin() {
        long currentTimeMillis = System.currentTimeMillis();
        Log.d("check login", SessionManagement.username + " : " + SessionManagement.rememberMe + " : " + (currentTimeMillis - SessionManagement.lastLoginTimestamp));
        if ((!SessionManagement.username.equals("NA") &&
                (SessionManagement.lastLoginTimestamp > 0 &&
                        currentTimeMillis - SessionManagement.lastLoginTimestamp < 1209600000)) &&
                SessionManagement.rememberMe) {
            if (SessionManagement.isAdmin) {
                Intent intent = new Intent(MainActivity.this, admindashboard.class);
                startActivity(intent);
                finish();
            } else {
                DatabaseReference referenceUser = database.getReference("newDb").child("students");
                referenceUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Intent intent;
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            if (SessionManagement.username.equals(data.child("username").getValue().toString())) {
                                int program = data.child("program").getValue(Integer.class);
                                if (program == Constants.DAYCARE)
                                    intent = new Intent(MainActivity.this, daycaredashboard.class);
                                else
                                    intent = new Intent(MainActivity.this, Main4Activity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        }
    }

    public void main_initiate() {
        // UserName=(EditText)findViewById(R.id.editText2);
        //PassWord=(EditText) findViewById(R.id.editText3);
        Login = (Button) findViewById(R.id.Login);
        imageViewLogin = (ImageView) findViewById(R.id.imageViewLogin);
        imageViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        //Register=(Button)findViewById(R.id.NewUser);
    }

    void login() {
        AlertDialog.Builder build = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_login, null);

        final EditText UserName = (EditText) mView.findViewById(R.id.uname);
        final EditText PassWord = (EditText) mView.findViewById(R.id.pwd);
        final CheckBox rememberMe = (CheckBox) mView.findViewById(R.id.checkboxRememberMe);
        final Button lgn;


        lgn = (Button) mView.findViewById(R.id.LGNBTN);

        lgn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = UserName.getText().toString().trim();
                final String password = PassWord.getText().toString().trim();

                if (rememberMe.isChecked()) {
                    SessionManagement.rememberMe = true;
                }

                pd = new ProgressDialog(MainActivity.this);

                pd.setMessage("Loading...");
                pd.show();

                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {

                    DatabaseReference referenceAdmin = database.getReference("newDb").child("admins");

                    referenceAdmin.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Admin admin = new Admin();

                            int check = 0;

                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                admin.setUsername(data.child("username").getValue().toString());
                                admin.setPassword(data.child("password").getValue().toString());
                                if (username.equals(admin.getUsername()) && password.equals(admin.getPassword())) {
                                    loginAdmin(admin);
                                    check = 1;
                                }
                            }

                            if (check == 0) {
                                DatabaseReference referenceUser = database.getReference("newDb").child("students");
                                referenceUser.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        Student student;
                                        int flag = 0;
                                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                                            student = (data.getValue(Student.class));
                                            if (username.equals(student.getUsername()) && password.equals(student.getPassword())) {
                                                Log.d("daycare", data.getValue().toString() + "");
                                                flag = 1;
                                                loginUser(student);
                                            }
                                        }

                                        if (flag == 0) {
                                            pd.dismiss();
                                            Toast.makeText(getApplicationContext(), "Incorrect username or password!", Toast.LENGTH_LONG).show();
                                        }

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            pd.dismiss();
                            Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();

                        }

                    });

                } else {

                    pd.dismiss();
                    Toast.makeText(getApplicationContext(), "Username or Password cannot be empty!", Toast.LENGTH_LONG).show();
                }


            }


        });
        build.setView(mView);
        dialog = build.create();
        dialog.show();


    }

}

