package com.example.parij.myschoolcomm;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.parij.myschoolcomm.R;
//import com.fasterxml.jackson.databind.util.BeanUtil;

public class Weeklymenu extends AppCompatActivity {

    private Button mon,tues,wed,thurs,fri,sat;

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
        setContentView(R.layout.activity_weeklymenu);

        Intent intent=getIntent();

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Food Menu");
        //toolbar.setNavigationIcon(R.drawable.tbbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);


        mainintiate();


        mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder tt1=new AlertDialog.Builder(Weeklymenu.this);
                View day1=getLayoutInflater().inflate(R.layout.dialog_monday,null);





                tt1.setView(day1);
                final AlertDialog dialog=tt1.create();
                dialog.show();
               /* Button ok=(Button)findViewById(R.id.okbtn);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            dialog.dismiss();
                    }
                });*/
            }
        });

        tues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder tt2=new AlertDialog.Builder(Weeklymenu.this);
                View day2=getLayoutInflater().inflate(R.layout.dialog_tuesday,null);
                /*
                final Button ok=(Button)findViewById(R.id.okbtn);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent backtosame=new Intent(Maintimetable.this,Maintimetable.class);
                        startActivity(backtosame);
                    }
                });*/



                tt2.setView(day2);
                AlertDialog dialog=tt2.create();
                dialog.show();
            }
        });

        wed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder tt3=new AlertDialog.Builder(Weeklymenu.this);
                View day3=getLayoutInflater().inflate(R.layout.dialog_wednesday,null);
                /*
                final Button ok=(Button)findViewById(R.id.okbtn);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent backtosame=new Intent(Maintimetable.this,Maintimetable.class);
                        startActivity(backtosame);
                    }
                });*/



                tt3.setView(day3);
                AlertDialog dialog=tt3.create();
                dialog.show();
            }
        });

        thurs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder tt4=new AlertDialog.Builder(Weeklymenu.this);
                View day4=getLayoutInflater().inflate(R.layout.dialog_thursday,null);
                /*
                final Button ok=(Button)findViewById(R.id.okbtn);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent backtosame=new Intent(Maintimetable.this,Maintimetable.class);
                        startActivity(backtosame);
                    }
                });*/



                tt4.setView(day4);
                AlertDialog dialog=tt4.create();
                dialog.show();
            }
        });

        fri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder tt5=new AlertDialog.Builder(Weeklymenu.this);
                View day5=getLayoutInflater().inflate(R.layout.dialog_friday,null);
                /*
                final Button ok=(Button)findViewById(R.id.okbtn);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent backtosame=new Intent(Maintimetable.this,Maintimetable.class);
                        startActivity(backtosame);
                    }
                });*/



                tt5.setView(day5);
                AlertDialog dialog=tt5.create();
                dialog.show();
            }
        });



        sat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder tt6=new AlertDialog.Builder(Weeklymenu.this);
                View day6=getLayoutInflater().inflate(R.layout.dialog_saturday,null);
                /*
                final Button ok=(Button)findViewById(R.id.okbtn);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent backtosame=new Intent(Maintimetable.this,Maintimetable.class);
                        startActivity(backtosame);
                    }
                });*/



                tt6.setView(day6);
                AlertDialog dialog=tt6.create();
                dialog.show();
            }
        });
    }

    private void mainintiate() {
        mon=(Button)findViewById(R.id.day1);
        tues=(Button)findViewById(R.id.day2);
        wed=(Button)findViewById(R.id.day3);
        thurs=(Button)findViewById(R.id.day4);
        fri=(Button)findViewById(R.id.day5);
        sat=(Button)findViewById(R.id.day6);
    }
}
