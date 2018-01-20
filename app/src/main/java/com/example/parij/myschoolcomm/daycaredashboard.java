package com.example.parij.myschoolcomm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
    ImageButton childprof;
    ImageButton parentprof;
    ImageButton foodmenu;
    ImageButton livefeed;
    ImageButton Photos;
    ImageButton timetable;
    //  Button Logout;
    DrawerLayout drawer;
    TextView name;
    TextView rollNo;
    TextView program;
    ImageView photo;

    TextView memoriestxt,cctvtxt,scheduletxt,parenttxt,childtxt,menutxt;

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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("        Dashboard");
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
        initiate();

        View header=navigationView.getHeaderView(0);
/*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/
        name = (TextView)header.findViewById(R.id.name);
        rollNo = (TextView)header.findViewById(R.id.roll);
        program = (TextView)header.findViewById(R.id.program);
        photo =(ImageView)header.findViewById(R.id.photo);
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
                            program.setText("Program : Day-Care");

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


        childtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent l = new Intent(daycaredashboard.this, Main5Activity.class);

                startActivity(l);


            }
        });

        childprof.setOnClickListener(new View.OnClickListener() {
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

        parentprof.setOnClickListener(new View.OnClickListener() {
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

        cctvtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JZVideoPlayerStandard.startFullscreen(daycaredashboard.this, JZVideoPlayerStandard.class, url, "CCTV");
                /*final Dialog dialog = new Dialog(daycaredashboard.this);
                dialog.setContentView(R.layout.dialog_cctv);
                Button buttonOk = (Button)dialog.findViewById(R.id.buttonCCTVOk);
                buttonOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();*/
                /*Uri uri=Uri.parse(url);
                Intent live= new Intent(Intent.ACTION_VIEW);
                live.setDataAndType(uri,"video/*");
                startActivity(live);*/

            }
        });

        livefeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JZVideoPlayerStandard.startFullscreen(daycaredashboard.this, JZVideoPlayerStandard.class, url, "CCTV");

                /*final Dialog dialog = new Dialog(daycaredashboard.this);
                dialog.setContentView(R.layout.dialog_cctv);
                JZVideoPlayerStandard jzVideoPlayerStandard = (JZVideoPlayerStandard) dialog.findViewById(R.id.videoplayer);
                jzVideoPlayerStandard.setUp(url, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "CCTV");
                Button buttonOk = (Button)dialog.findViewById(R.id.buttonCCTVOk);
                buttonOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        JZVideoPlayer.releaseAllVideos();
                        dialog.dismiss();
                    }
                });
                dialog.show();*//*
                Uri uri=Uri.parse(url);
                Intent live= new Intent(Intent.ACTION_VIEW);
                live.setDataAndType(uri,"video/*");
                startActivity(live);*/

                //startActivity(new Intent(daycaredashboard.this,LiveFeed.class));
            }
        });

        memoriestxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(daycaredashboard.this, Memories.class));
            }
        });
        Photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(daycaredashboard.this, Memories.class));
            }
        });

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
        childprof=(ImageButton)findViewById(R.id.childprofile);
        parentprof=(ImageButton)findViewById(R.id.parentsprofile);
        foodmenu=(ImageButton)findViewById(R.id.foodmenu);
        livefeed=(ImageButton)findViewById(R.id.dayatimeptus);
        Photos=(ImageButton)findViewById(R.id.memories);
        timetable=(ImageButton)findViewById(R.id.timetable);

        memoriestxt=(TextView)findViewById(R.id.textmemory);
        cctvtxt=(TextView)findViewById(R.id.textlive);
        scheduletxt=(TextView)findViewById(R.id.textschedule);
        parenttxt=(TextView)findViewById(R.id.textparent);
        childtxt=(TextView)findViewById(R.id.textchild);
        menutxt=(TextView)findViewById(R.id.textmenu);


        // Logout=(Button)findViewById(R.id.Logout);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {



        int id = item.getItemId();

        if (id == R.id.resetpassword) {

            Intent intent = new Intent(daycaredashboard.this, ResetPassword.class);
            startActivity(intent);
            //Toast.makeText(getApplicationContext(), "Reset Password clicked", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.CallUs) {

            //Toast.makeText(getApplicationContext(), "Call Us is clicked", Toast.LENGTH_SHORT).show();
            final TextView phone1,phone2,phone3;
            Button button1,button2,button3;
            android.support.v7.app.AlertDialog.Builder build=new android.support.v7.app.AlertDialog.Builder(daycaredashboard.this);
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

            JZVideoPlayerStandard.startFullscreen(daycaredashboard.this, JZVideoPlayerStandard.class, url, "CCTV");
        } else if (id == R.id.feedback) {
            //Toast.makeText(getApplicationContext(), "Coming Soon!", Toast.LENGTH_SHORT).show();


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
            //  Toast.makeText(getApplicationContext(), "Logout Clicked clicked", Toast.LENGTH_SHORT).show();

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

                            SessionManagement.retrieveSharedPreferences(daycaredashboard.this);
                            SessionManagement.rememberMe=false;
                            SessionManagement.username="NA";
                            SessionManagement.lastLoginTimestamp=0;
                            SessionManagement.updateSharedPreferences();
                            daycaredashboard.this.finish();
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
