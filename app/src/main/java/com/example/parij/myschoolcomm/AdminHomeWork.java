package com.example.parij.myschoolcomm;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class AdminHomeWork extends AppCompatActivity {

    Spinner spinner;
    TextView startdate;
    TextView enddate;
    EditText monday,tuesday,wednesday,thursday,friday,saturday;
    Button ok;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    DatePickerDialog.OnDateSetListener onDateSetListener2;
    ArrayAdapter<CharSequence> arrayAdapter;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_homework);

        Intent intent=getIntent();

        spinner=(Spinner)findViewById(R.id.spinner4);
        startdate=(TextView)findViewById(R.id.textView);
        enddate=(TextView)findViewById(R.id.textView2);
        ok=(Button)findViewById(R.id.ok);
        monday=(EditText)findViewById(R.id.monday);
        tuesday=(EditText)findViewById(R.id.tuesday);
        wednesday=(EditText)findViewById(R.id.wednesday);
        thursday=(EditText)findViewById(R.id.thursday);
        friday=(EditText)findViewById(R.id.friday);
        saturday=(EditText)findViewById(R.id.saturday);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Home Work");
       // toolbar.setNavigationIcon(R.drawable.hwbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);


        startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year= cal.get(Calendar.YEAR);
                int month= cal.get(Calendar.MONTH);
                int day= cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog=new DatePickerDialog(AdminHomeWork.this,
                        android.R.style.Theme,onDateSetListener,year,month,day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();


            }
        });
        final String[] date1 = new String[1];
        onDateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                date1[0] = dayOfMonth+"/"+month+"/"+year;
                startdate.setText(date1[0]);



            }
        };


        enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year= cal.get(Calendar.YEAR);
                int month= cal.get(Calendar.MONTH);
                int day= cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog=new DatePickerDialog(AdminHomeWork.this,
                        android.R.style.Theme,onDateSetListener2,year,month,day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();


            }
        });
        final String[] date2 = new String[1];
        onDateSetListener2=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                date2[0] = dayOfMonth+"/"+month+"/"+year;
                enddate.setText(date2[0]);

            }
        };

        arrayAdapter=ArrayAdapter.createFromResource(this,R.array.Type,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        final String[] type = new String[1];
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                type[0] = (String) parent.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String program=type[0];
                String startdate= date1[0];
                String enddate= date2[0];
                String monday1=monday.getText().toString();
                String tuesday1= tuesday.getText().toString();
                String wednesday1=wednesday.getText().toString();
                String thursday1= thursday.getText().toString();
                String friday1= friday.getText().toString();
                String saturday1= saturday.getText().toString();

                int flag=0;
                if(TextUtils.isEmpty(program)||TextUtils.isEmpty(startdate)||TextUtils.isEmpty(enddate))
                {
                    flag=1;
                    Toast.makeText(AdminHomeWork.this,"Please enter the startdate and enddate!",Toast.LENGTH_SHORT).show();

                }
                if(flag==0)
                {

                    database=FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference= database.getReference("HomeWork").child(program);

                    HomeWork obj = new HomeWork(monday1,tuesday1,wednesday1,thursday1,friday1,saturday1,startdate
                    ,enddate);
                    databaseReference.setValue(obj);
                    Toast.makeText(AdminHomeWork.this,"Successfully Updated!",Toast.LENGTH_LONG).show();

                }
            }
        });

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
class HomeWork
{
    String monday,tuesday,wednesday,thursday,friday,saturday,startdate,enddate;

    public HomeWork()
    {

    }

    public HomeWork(String monday, String tuesday, String wednesday, String thursday, String friday, String saturday,
                    String startdate, String enddate) {
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.startdate = startdate;
        this.enddate = enddate;
    }

    public String getMonday() {
        return monday;
    }

    public void setMonday(String monday) {
        this.monday = monday;
    }

    public String getTuesday() {
        return tuesday;
    }

    public void setTuesday(String tuesday) {
        this.tuesday = tuesday;
    }

    public String getWednesday() {
        return wednesday;
    }

    public void setWednesday(String wednesday) {
        this.wednesday = wednesday;
    }

    public String getThursday() {
        return thursday;
    }

    public void setThursday(String thursday) {
        this.thursday = thursday;
    }

    public String getFriday() {
        return friday;
    }

    public void setFriday(String friday) {
        this.friday = friday;
    }

    public String getSaturday() {
        return saturday;
    }

    public void setSaturday(String saturday) {
        this.saturday = saturday;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }
}
