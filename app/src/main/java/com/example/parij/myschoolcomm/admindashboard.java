package com.example.parij.myschoolcomm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

public class admindashboard extends AppCompatActivity {
    Button Logout;
    FloatingActionButton floatButton;
    TextView emergencytxt, authorizedtxt, parenttxt, childtxt, syllabustxt, announcementtxt, homeworktxt, spokentxt, requesttxt;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SessionManagement.retrieveSharedPreferences(admindashboard.this);
                        SessionManagement.rememberMe=false;
                        SessionManagement.username="NA";
                        SessionManagement.lastLoginTimestamp=0;
                        SessionManagement.updateSharedPreferences();
                        admindashboard.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admindashboard);
        //final Bundle bundle=getIntent().getExtras();

       // drawerLayout=(DrawerLayout)findViewById(R.id.drawerlayout);

       // actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);

        //drawerLayout.addDrawerListener(actionBarDrawerToggle);
        //actionBarDrawerToggle.syncState();
        FirebaseDatabase.getInstance().getReference().child(Constants.FBDB).keepSynced(true);
        Logout=(Button)findViewById(R.id.Logout);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);


        getSupportActionBar().setTitle("  Dashboard");
        //toolbar.setNavigationIcon(R.drawable.childprofbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
       /* getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/

        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        //emergencytxt=(TextView)findViewById(R.id.textemergency);
        //authorizedtxt=(TextView)findViewById(R.id.textauthorized);
        // parenttxt=(TextView)findViewById(R.id.textparent);
        childtxt=(TextView)findViewById(R.id.textchild);
        syllabustxt=(TextView)findViewById(R.id.textsyllabus);
        announcementtxt=(TextView)findViewById(R.id.textannouncements);
        homeworktxt=(TextView)findViewById(R.id.texthomework);
        spokentxt=(TextView)findViewById(R.id.textspoken);
        requesttxt=(TextView)findViewById(R.id.textrequest);

        // ImageButton ParentProfile=(ImageButton) findViewById(R.id.parentsprofile);
        ImageButton ChildProfile=(ImageButton) findViewById(R.id.childprofile);
        // ImageButton emerg=(ImageButton)findViewById(R.id.emergency);
        //ImageButton authper=(ImageButton)findViewById(R.id.authorisedperson);
        ImageButton Timetable=(ImageButton)findViewById(R.id.theme);
        ImageButton Announcements=(ImageButton)findViewById(R.id.Announcements);
        ImageButton reqforLeave=(ImageButton)findViewById(R.id.reqforLeave);
        ImageButton HW=(ImageButton)findViewById(R.id.Homework);
        ImageButton hWClassreport=(ImageButton)findViewById(R.id.homeclassreport);
        floatButton=(FloatingActionButton)findViewById(R.id.floatButton);

        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(admindashboard.this, tempParentRegistration.class);
                startActivity(intent);

              //  Toast.makeText(admindashboard.this,"Coming Soon!",Toast.LENGTH_LONG).show();

            }
        });

/*
        parenttxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent parent=new Intent(admindashboard.this,parentwindow.class);

                startActivity(parent);
            }
        });

        ParentProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent parent=new Intent(admindashboard.this,parentwindow.class);

                startActivity(parent);
            }
        });
*/
        childtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent childprof=new Intent(admindashboard.this,childdetailswindow.class);

                startActivity(childprof);


            }
        });

        ChildProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent childprof=new Intent(admindashboard.this,childdetailswindow.class);

                startActivity(childprof);
            }

        });
/*
        emergencytxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent emergperson=new Intent(admindashboard.this,emergencywindow.class);
                startActivity(emergperson);


            }
        });


        emerg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emergperson=new Intent(admindashboard.this,emergencywindow.class);
                startActivity(emergperson);
            }
        });

        authorizedtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent authper=new Intent(admindashboard.this,authorizedwindow.class);

                startActivity(authper);

            }
        });

        authper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent authper=new Intent(admindashboard.this,authorizedwindow.class);

                startActivity(authper);
            }
        });
*/
      syllabustxt.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              Intent intent = new Intent(admindashboard.this,syllabusadmin.class);
              startActivity(intent);

          }
      });

        hWClassreport.setOnClickListener(new View.OnClickListener()
                                         {
                                             @Override
                                             public void onClick(View view)
                                             {
                                                 Intent intent = new Intent(admindashboard.this,syllabusadmin.class);
                                                 startActivity(intent);
                                                 
                                             }



                                         }

        );
        announcementtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(admindashboard.this,announcementadmin.class);
                startActivity(intent);

            }
        });

        Announcements.setOnClickListener(new View.OnClickListener()
                                         {
                                             @Override
                                             public void onClick(View view)
                                             {
                                                 Intent intent = new Intent(admindashboard.this,announcementadmin.class);
                                                 startActivity(intent);


                                             }







                                         }

        );

        homeworktxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(admindashboard.this,AdminHomeWork.class);
                startActivity(intent);
            }
        });
        HW.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(admindashboard.this,AdminHomeWork.class);
                startActivity(intent);
            }




        });

        spokentxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(admindashboard.this,ThemeAdmin.class);
                // Intent intent=new Intent(Main4Activity.this,AuthorizedPersonActivity.class);
                //intent.putExtras(bundle);
                startActivity(intent);

            }
        });
        Timetable.setOnClickListener(new View.OnClickListener()
                                     {
                                         @Override
                                         public void onClick(View view)
                                         {
                                             Intent intent=new Intent(admindashboard.this,ThemeAdmin.class);
                                             // Intent intent=new Intent(Main4Activity.this,AuthorizedPersonActivity.class);
                                             //intent.putExtras(bundle);
                                             startActivity(intent);


                                         }

                                     }



        );

        requesttxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(admindashboard.this,reqleaveadmin.class);
                //intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        reqforLeave.setOnClickListener(new View.OnClickListener()
                                       {
                                           @Override
                                           public void onClick(View view)
                                           {
                                               Intent intent=new Intent(admindashboard.this,reqleaveadmin.class);
                                               //intent.putExtras(bundle);
                                               startActivity(intent);

                                           }



                                       }

        );


        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(admindashboard.this);

                // set title
                alertDialogBuilder.setTitle("Logout?");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Click yes to exit!")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, close
                                // current activity
                                /*
                                SessionManagement.retrieveSharedPreferences(admindashboard.this);
                                SessionManagement.rememberMe=false;
                                SessionManagement.username="NA";
                                SessionManagement.lastLoginTimestamp=0;
                                SessionManagement.updateSharedPreferences();
                                */
                                Intent intent = new Intent(admindashboard.this, Logout.class);
                                startActivity(intent);
                                admindashboard.this.finish();
                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        });



    }
}
