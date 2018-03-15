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

public class daycaredashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ImageButton foodmenu;
    ImageButton SProfile;
    ImageButton PProfile;
    ImageButton Timetable;
    ImageButton Announcements;
    ImageButton reqforLeave;
    ImageButton HW;
    ImageButton AuthPerson;
    ImageButton hWClassreport;
    ImageButton Emergency;
    ImageButton timetable;
    //  Button Logout;
    DrawerLayout drawer;
    ImageView nav_head;
    TextView name;
    TextView rollNo;
    TextView program;
    ImageView photo;
    TextView hometxt, spokentxt, reqtxt, emertxt, authtxt, syllabustxt, announcetxt;
    TextView scheduletxt, parenttxt, childtxt, menutxt;

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

                        SessionManagement.retrieveSharedPreferences(daycaredashboard.this);
                        SessionManagement.rememberMe=false;
                        SessionManagement.username="NA";
                        SessionManagement.lastLoginTimestamp=0;
                        SessionManagement.updateSharedPreferences();

                        Intent intent = new Intent(daycaredashboard.this, Logout.class);
                        startActivity(intent);
                        daycaredashboard.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daycaredashboard);
        final Bundle bundle=getIntent().getExtras();

        SessionManagement.retrieveSharedPreferences(daycaredashboard.this);
        final String username = SessionManagement.username;
        SessionManagement.updateSharedPreferences();


        FirebaseDatabase.getInstance().getReference().child(Constants.FBDB).keepSynced(true);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("        Dashboard");
        drawer = findViewById(R.id.drawerlayout);

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


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        initiate();

        View header=navigationView.getHeaderView(0);
/*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/
        name = header.findViewById(R.id.name);
        rollNo = header.findViewById(R.id.roll);
        program = header.findViewById(R.id.program);
        photo = header.findViewById(R.id.photo);
        //TODO Remove Bundle usage and use firebasereference to set the data


        FirebaseDatabase database;
        database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("newDb").child("students");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Student student;

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    student = ds.getValue(Student.class);
                    if (username.equals(student.getUsername())) {
                        name.setText("Name :" + student.getName());
                        rollNo.setText("Roll No :" + student.getRollNo());
                        int programCode = student.getProgram();

                        if (programCode == Constants.DAYCARE) {
                            program.setText("Program : Day Care");

                        }

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

        spokentxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(daycaredashboard.this, ThemeUser.class);
                // Intent intent=new Intent(Main4Activity.this,AuthorizedPersonActivity.class);
                //intent.putExtras(bundle);
                startActivity(intent);


            }
        });
        Timetable.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             Intent intent = new Intent(daycaredashboard.this, ThemeUser.class);
                                             // Intent intent=new Intent(Main4Activity.this,AuthorizedPersonActivity.class);
                                             //intent.putExtras(bundle);
                                             startActivity(intent);


                                         }

                                     }


        );

        reqtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(daycaredashboard.this, ReqLeaveActivity.class);
                //intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        reqforLeave.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               Intent intent = new Intent(daycaredashboard.this, ReqLeaveActivity.class);
                                               //intent.putExtras(bundle);
                                               startActivity(intent);

                                           }


                                       }

        );

        emertxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(daycaredashboard.this, EmergencyActivity.class);
                //intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        Emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(daycaredashboard.this, EmergencyActivity.class);
                //intent.putExtras(bundle);
                startActivity(intent);

            }


        });

        authtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(daycaredashboard.this, AuthorizedPersonActivity.class);

                // Intent intent=new Intent(Main4Activity.this,AuthorizedPersonActivity.class);
                //intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        AuthPerson.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {
                                              Intent intent = new Intent(daycaredashboard.this, AuthorizedPersonActivity.class);

                                              // Intent intent=new Intent(Main4Activity.this,AuthorizedPersonActivity.class);
                                              //intent.putExtras(bundle);
                                              startActivity(intent);

                                          }

                                      }

        );

        announcetxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(daycaredashboard.this, announcementuser.class);
                startActivity(intent);
            }
        });

        Announcements.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View view) {
                                                 Intent intent = new Intent(daycaredashboard.this, announcementuser.class);
                                                 startActivity(intent);


                                             }


                                         }

        );

        childtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent l = new Intent(daycaredashboard.this, Main5Activity.class);

                startActivity(l);


            }
        });

        SProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent l=new Intent(daycaredashboard.this,Main5Activity.class);

                startActivity(l);

            }
        });
        parenttxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent l = new Intent(daycaredashboard.this, daycare_parentmain.class);

                startActivity(l);


            }
        });

        PProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(daycaredashboard.this,parentmain.class));
                //Toast.makeText(daycaredashboard.this,"Coming Soon!",Toast.LENGTH_SHORT).show();

                Intent l=new Intent(daycaredashboard.this,daycare_parentmain.class);

                startActivity(l);



            }
        });


        menutxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(daycaredashboard.this,Weeklymenu.class));
            }
        });
        foodmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(daycaredashboard.this,Weeklymenu.class));
            }
        });
        final String url="http://cdn.streamonweb.com:1935/ipcamlive/impetus_cam1/playlist.m3u8";





        scheduletxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(daycaredashboard.this, daycaretimetable.class));
            }
        });
        timetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(daycaredashboard.this, daycaretimetable.class));
            }
        });

    }

    void initiate(){

        foodmenu = findViewById(R.id.foodmenu);
        timetable = findViewById(R.id.timetable);
        scheduletxt = findViewById(R.id.textschedule);
        menutxt = findViewById(R.id.textmenu);
        SProfile = findViewById(R.id.childprofile);
        PProfile = findViewById(R.id.parentsprofile);
        Timetable = findViewById(R.id.theme);
        Announcements = findViewById(R.id.Announcements);
        reqforLeave = findViewById(R.id.reqforLeave);
        HW = findViewById(R.id.Homework);
        AuthPerson = findViewById(R.id.authorisedperson);
        hWClassreport = findViewById(R.id.homeclassreport);
        Emergency = findViewById(R.id.emergency);
        nav_head = findViewById(R.id.photo);

        hometxt = findViewById(R.id.texthomework);
        spokentxt = findViewById(R.id.textspoken);
        reqtxt = findViewById(R.id.textrequest);
        emertxt = findViewById(R.id.textemergency);
        authtxt = findViewById(R.id.textauthorized);
        parenttxt = findViewById(R.id.textparent);
        childtxt = findViewById(R.id.textchild);
        syllabustxt = findViewById(R.id.textsyllabus);
        announcetxt = findViewById(R.id.textannouncements);

        // Logout=(Button)findViewById(R.id.Logout);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        final Bundle bundle = getIntent().getExtras();
        if (id == R.id.resetpassword) {

            Intent intent = new Intent(daycaredashboard.this, ResetPassword.class);
            //intent.putExtras(bundle);
            startActivity(intent);
            //Toast.makeText(getApplicationContext(), "Reset Password clicked", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.CallUs) {
            // Toast.makeText(getApplicationContext(), "Call Us is clicked", Toast.LENGTH_SHORT).show();

            final TextView phone1,phone2,phone3;
            Button button1,button2,button3;
            android.support.v7.app.AlertDialog.Builder build=new android.support.v7.app.AlertDialog.Builder(daycaredashboard.this);
            View mView=getLayoutInflater().inflate(R.layout.calluspopup,null);

            phone1 = mView.findViewById(R.id.phone1);
            phone2 = mView.findViewById(R.id.phone2);
            phone3 = mView.findViewById(R.id.phone3);

            button1 = mView.findViewById(R.id.phoneButton1);
            button2 = mView.findViewById(R.id.phoneButton2);
            button3 = mView.findViewById(R.id.phoneButton3);


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
            JZVideoPlayerStandard.startFullscreen(daycaredashboard.this, JZVideoPlayerStandard.class, url, "CCTV");

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

            Intent intent = new Intent(daycaredashboard.this, userfeedback.class);
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

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(daycaredashboard.this);

            // set title
            alertDialogBuilder.setTitle("Logout?");

            // set dialog message
            alertDialogBuilder
                    .setMessage("Click yes to exit!")
                    .setCancelable(false)
                    .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // if this button is clicked, close
                            // current activity
                            /*
                            SessionManagement.retrieveSharedPreferences(Main4Activity.this);
                            SessionManagement.rememberMe = false;
                            SessionManagement.username = "NA";
                            SessionManagement.lastLoginTimestamp = 0;
                            SessionManagement.updateSharedPreferences();
                            */
                            Intent intent = new Intent(daycaredashboard.this, Logout.class);
                            startActivity(intent);

                            daycaredashboard.this.finish();

                        }
                    })
                    .setNegativeButton("No",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            dialog.cancel();
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();


        } else if (id == R.id.memories) {
            Intent intent = new Intent(daycaredashboard.this, MemoriesUser.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawerlayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
