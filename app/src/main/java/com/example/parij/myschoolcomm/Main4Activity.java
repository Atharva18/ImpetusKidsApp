package com.example.parij.myschoolcomm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.parij.myschoolcomm.Models.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

public class Main4Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
   // Button Logout;
    ImageButton SProfile;
    ImageButton PProfile;
    ImageButton Timetable;
    ImageButton Announcements;
    ImageButton reqforLeave;
    ImageButton HW;
    ImageButton AuthPerson;
    ImageButton hWClassreport;
    ImageButton Emergency;
    ImageView nav_head;
    FirebaseDatabase database;
    TextView name;
    TextView rollNo;
    TextView program;
    ImageView photo;
    String username;
    TextView hometxt,spokentxt,reqtxt,emertxt,authtxt,parenttxt,childtxt,syllabustxt,announcetxt;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            JZVideoPlayer.releaseAllVideos();
            return;
        }
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Main4Activity.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FirebaseDatabase.getInstance().getReference().child(Constants.FBDB).keepSynced(true);
        drawer = (DrawerLayout) findViewById(R.id.drawerlayout);

        findViewById(R.id.drawer_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open right drawer

                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                else
                    drawer.openDrawer(GravityCompat.START);
            }
        });



        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(false);
        drawer.addDrawerListener(toggle);

        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        View header=navigationView.getHeaderView(0);
/*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/
        name = (TextView)header.findViewById(R.id.name);
        rollNo = (TextView)header.findViewById(R.id.roll);
        program = (TextView)header.findViewById(R.id.program);
        photo =(ImageView)header.findViewById(R.id.photo);


        SessionManagement.retrieveSharedPreferences(Main4Activity.this);
        username = SessionManagement.username;
        SessionManagement.updateSharedPreferences();

        final FirebaseDatabase database;
        database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("newDb").child("students");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Student student;

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    student = ds.getValue(Student.class);
                    if (student.getUsername().equals(username)) {
                        name.setText("Name :" + student.getName());
                        rollNo.setText("Roll No :" + student.getRollNo());
                        int programCode = student.getProgram();

                        if (Constants.BLOSSOMING == programCode)
                            program.setText("Program:Blossoming");
                        else if (Constants.BUDDING == programCode)
                            program.setText("Program:Budding");
                        else if (Constants.FLOURISHING == programCode)
                            program.setText("Program:Flourishing");
                        else if (Constants.SEEDING == programCode)
                            program.setText("Program: Seeding");

                        String url = student.getImageLink();
                        if (!url.equals("")) {
                            Glide.with(getApplicationContext().getApplicationContext()).load(url).into(photo);
                        }
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Intialise();


        childtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent l=new Intent(Main4Activity.this,Main5Activity.class);
                //l.putExtras(bundle);
                startActivity(l);
            }
        });

        SProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getApplicationContext(),"Jumping into student profile",Toast.LENGTH_LONG).show();
                Intent l=new Intent(Main4Activity.this,Main5Activity.class);
                //l.putExtras(bundle);
                startActivity(l);
            }
        });

        parenttxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Main4Activity.this,parentmain.class);
                //intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        PProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getApplicationContext(),"Parents Profile",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(Main4Activity.this,parentmain.class);
                //intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        hometxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Main4Activity.this,HomeWorkActivity.class);
                //intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        HW.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(Main4Activity.this,HomeWorkActivity.class);
                //intent.putExtras(bundle);
                startActivity(intent);
            }




        });

        spokentxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Main4Activity.this,ThemeUser.class);
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
                                             Intent intent=new Intent(Main4Activity.this,ThemeUser.class);
                                             // Intent intent=new Intent(Main4Activity.this,AuthorizedPersonActivity.class);
                                             //intent.putExtras(bundle);
                                             startActivity(intent);


                                         }

                                     }



        );

        reqtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Main4Activity.this,ReqLeaveActivity.class);
                //intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        reqforLeave.setOnClickListener(new View.OnClickListener()
                                       {
                                           @Override
                                           public void onClick(View view)
                                           {
                                               Intent intent=new Intent(Main4Activity.this,ReqLeaveActivity.class);
                                               //intent.putExtras(bundle);
                                               startActivity(intent);

                                           }



                                       }

        );

        emertxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Main4Activity.this,EmergencyActivity.class);
                //intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        Emergency.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(Main4Activity.this,EmergencyActivity.class);
                //intent.putExtras(bundle);
                startActivity(intent);

            }



        });

        authtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(Main4Activity.this,AuthorizedPersonActivity.class);

                // Intent intent=new Intent(Main4Activity.this,AuthorizedPersonActivity.class);
                //intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        AuthPerson.setOnClickListener(new View.OnClickListener()
                                      {
                                          @Override
                                          public void onClick(View view)
                                          {
                                              Intent intent= new Intent(Main4Activity.this,AuthorizedPersonActivity.class);

                                              // Intent intent=new Intent(Main4Activity.this,AuthorizedPersonActivity.class);
                                              //intent.putExtras(bundle);
                                              startActivity(intent);

                                          }

                                      }

        );

        syllabustxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Main4Activity.this,SyllabusCoverage.class);
                //intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        hWClassreport.setOnClickListener(new View.OnClickListener()
                                         {
                                             @Override
                                             public void onClick(View view)
                                             {
                                                 Intent intent = new Intent(Main4Activity.this,SyllabusCoverage.class);
                                                 //intent.putExtras(bundle);
                                                 startActivity(intent);


                                             }



                                         }

        );

        announcetxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Main4Activity.this,announcementuser.class);
                startActivity(intent);
            }
        });

        Announcements.setOnClickListener(new View.OnClickListener()
                                         {
                                             @Override
                                             public void onClick(View view)
                                             {
                                                 Intent intent = new Intent(Main4Activity.this,announcementuser.class);
                                                 startActivity(intent);


                                             }







                                         }

        );

    }

    public void Intialise()
    {
       // Logout=(Button)findViewById(R.id.Logout);
        SProfile=(ImageButton)findViewById(R.id.childprofile);
        PProfile=(ImageButton)findViewById(R.id.parentsprofile);
        Timetable=(ImageButton)findViewById(R.id.theme);
        Announcements=(ImageButton)findViewById(R.id.Announcements);
        reqforLeave=(ImageButton)findViewById(R.id.reqforLeave);
        HW=(ImageButton)findViewById(R.id.Homework);
        AuthPerson=(ImageButton)findViewById(R.id.authorisedperson);
        hWClassreport=(ImageButton)findViewById(R.id.homeclassreport);
        Emergency=(ImageButton)findViewById(R.id.emergency);
        nav_head=(ImageView)findViewById(R.id.photo);

        hometxt=(TextView)findViewById(R.id.texthomework);
        spokentxt=(TextView)findViewById(R.id.textspoken);
        reqtxt=(TextView)findViewById(R.id.textrequest);
        emertxt=(TextView)findViewById(R.id.textemergency);
        authtxt=(TextView)findViewById(R.id.textauthorized);
        parenttxt=(TextView)findViewById(R.id.textparent);
        childtxt=(TextView)findViewById(R.id.textchild);
        syllabustxt=(TextView)findViewById(R.id.textsyllabus);
        announcetxt=(TextView)findViewById(R.id.textannouncements);




      //  name=(TextView)findViewById(R.id.name);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        final Bundle bundle=getIntent().getExtras();
        if (id == R.id.resetpassword) {

            Intent intent=new Intent(Main4Activity.this,ResetPassword.class);
            //intent.putExtras(bundle);

            startActivity(intent);

            //Toast.makeText(getApplicationContext(), "Reset Password clicked", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.CallUs) {
           // Toast.makeText(getApplicationContext(), "Call Us is clicked", Toast.LENGTH_SHORT).show();

            final TextView phone1,phone2,phone3;
            Button button1,button2,button3;
            android.support.v7.app.AlertDialog.Builder build=new android.support.v7.app.AlertDialog.Builder(Main4Activity.this);
            View mView=getLayoutInflater().inflate(R.layout.calluspopup,null);

            phone1=(TextView)mView.findViewById(R.id.phone1);
            phone2=(TextView)mView.findViewById(R.id.phone2);
            phone3=(TextView)mView.findViewById(R.id.phone3);

            button1=(Button)mView.findViewById(R.id.phoneButton1);
            button2=(Button)mView.findViewById(R.id.phoneButton2);
            button3=(Button)mView.findViewById(R.id.phoneButton3);


            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String number = phone1.getText().toString();


                        String dial = "tel:" + number;
                        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));



                }
            });

            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String number = phone2.getText().toString();


                    String dial = "tel:" + number;
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));



                }
            });

            button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String number = phone3.getText().toString();


                    String dial = "tel:" + number;
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));



                }
            });

            build.setView(mView);
            android.support.v7.app.AlertDialog dialog=build.create();
            dialog.show();

        } else if (id == R.id.cctv) {
            final String url = "http://cdn.streamonweb.com:1935/ipcamlive/impetus_cam1/playlist.m3u8";
            JZVideoPlayerStandard.startFullscreen(Main4Activity.this, JZVideoPlayerStandard.class, url, "CCTV");

        } /*else if (id == R.id.notifications) {
            // Toast.makeText(getApplicationContext(), "Coming Soon!", Toast.LENGTH_SHORT).show();

            if (username.contains("Seed")) {
                Intent intent = new Intent(Main4Activity.this, Timetable_playgroup.class);
                startActivity(intent);
            } else if (username.contains("Budd")) {
                Intent intent = new Intent(Main4Activity.this, Timetable_nursery.class);
                startActivity(intent);

            } else if (username.contains("Bloss")) {
                Intent intent = new Intent(Main4Activity.this, Timetable_jrandsrkg.class);
                startActivity(intent);

            } else if (username.contains("Flou")) {
                Intent intent = new Intent(Main4Activity.this, Timetable_jrandsrkg.class);
                startActivity(intent);

            }



        } */ else if (id == R.id.feedback) {
            // Toast.makeText(getApplicationContext(), "Coming Soon!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(Main4Activity.this, userfeedback.class);
            startActivity(intent);


        } else if (id == R.id.rateus) {
            // Toast.makeText(getApplicationContext(), "Coming Soon!", Toast.LENGTH_SHORT).show();

            final String appPackageName = getPackageName();
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
        } else if (id == R.id.logout) {
           // Toast.makeText(getApplicationContext(), "Logout Clicked clicked", Toast.LENGTH_SHORT).show();

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Main4Activity.this);

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
                            SessionManagement.retrieveSharedPreferences(Main4Activity.this);
                            SessionManagement.rememberMe = false;
                            SessionManagement.username = "NA";
                            SessionManagement.lastLoginTimestamp = 0;
                            SessionManagement.updateSharedPreferences();
                            Main4Activity.this.finish();
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerlayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
