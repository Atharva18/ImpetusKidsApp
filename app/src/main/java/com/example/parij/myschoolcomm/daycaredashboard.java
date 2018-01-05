package com.example.parij.myschoolcomm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
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
        /*
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        setSupportActionBar(toolbar);


        getSupportActionBar().setTitle("      Dashboard");*/



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

        final String username2 = bundle.getString("Username");

        FirebaseDatabase database;

            program.setText("Program : Day-Care");
            final int[] flag = {0};
            database=FirebaseDatabase.getInstance();
            DatabaseReference databaseReference= database.getReference("studinfo").child("Day-Care").child("Morning");

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for(DataSnapshot data : dataSnapshot.getChildren())
                    {

                        String name1 = (String) data.child("name").getValue();
                        String username1=(String)data.child("username").getValue();

                        if(username2.equals(username1))
                        {
                            flag[0] =1;
                            name.setText("Name : "+name1);
                            rollNo.setText("Batch :  Morning");

                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            databaseReference = database.getReference("Images").child("Child_Profile");
            final String user=username2;
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for(DataSnapshot data : dataSnapshot.getChildren())
                    {
                        if(user.equals(data.getKey()))
                        {

                            String url = data.getValue().toString();
                            if(url!=null) {
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

                    Intent l=new Intent(daycaredashboard.this,Main5Activity.class);
                    l.putExtras(bundle);
                    startActivity(l);


                }
            });

            childprof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent l=new Intent(daycaredashboard.this,Main5Activity.class);
                l.putExtras(bundle);
                startActivity(l);

            }
        });
            parenttxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent l=new Intent(daycaredashboard.this,daycare_parentmain.class);
                    l.putExtras(bundle);
                    startActivity(l);


                }
            });

        parentprof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(daycaredashboard.this,parentmain.class));
                //Toast.makeText(daycaredashboard.this,"Coming Soon!",Toast.LENGTH_SHORT).show();

                Intent l=new Intent(daycaredashboard.this,daycare_parentmain.class);
                l.putExtras(bundle);
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

                Uri uri=Uri.parse(url);
                Intent live= new Intent(Intent.ACTION_VIEW);
                live.setDataAndType(uri,"video/*");
                startActivity(live);

            }
        });

        livefeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri=Uri.parse(url);
                Intent live= new Intent(Intent.ACTION_VIEW);
                live.setDataAndType(uri,"video/*");
                startActivity(live);

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
        final Bundle bundle=getIntent().getExtras();
        if (id == R.id.resetpassword) {

            Intent intent=new Intent(daycaredashboard.this,resetpassworddaycare.class);
            intent.putExtras(bundle);
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



        }
         else if (id == R.id.feedback) {
            Toast.makeText(getApplicationContext(), "Coming Soon!", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.rateus) {
            Toast.makeText(getApplicationContext(), "Coming Soon!", Toast.LENGTH_SHORT).show();

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
                        public void onClick(DialogInterface dialog,int id) {
                            // if this button is clicked, close
                            // current activity
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
