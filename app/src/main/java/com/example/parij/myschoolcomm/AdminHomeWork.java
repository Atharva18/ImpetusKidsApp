package com.example.parij.myschoolcomm;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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
    ImageView startdatebutton, enddatebutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_homework);

        Intent intent=getIntent();

        spinner = findViewById(R.id.spinner4);
        startdate = findViewById(R.id.textView);
        enddate = findViewById(R.id.textView2);
        ok = findViewById(R.id.ok);
        monday = findViewById(R.id.monday);
        tuesday = findViewById(R.id.tuesday);
        wednesday = findViewById(R.id.wednesday);
        thursday = findViewById(R.id.thursday);
        friday = findViewById(R.id.friday);
        saturday = findViewById(R.id.saturday);
        startdatebutton = findViewById(R.id.startdatebutton);
        enddatebutton = findViewById(R.id.enddatebutton);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Homework");
       // toolbar.setNavigationIcon(R.drawable.hwbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);

        final Calendar mycalender=Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                mycalender.set(Calendar.YEAR,i);
                mycalender.set(Calendar.MONTH,i1);
                mycalender.set(Calendar.DAY_OF_MONTH,i2);
                UpdateLabel();
            }

            private void UpdateLabel() {
                String myFormat = "dd/MM/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                startdate.setText(sdf.format(mycalender.getTime()));

            }
        };

        startdatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DatePickerDialog datePickerDialog = new DatePickerDialog(AdminHomeWork.this, date, mycalender.get(Calendar.YEAR),
                        mycalender.get(Calendar.MONTH), mycalender.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        final Calendar mycalender2 = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date2=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                mycalender2.set(Calendar.YEAR, i);
                mycalender2.set(Calendar.MONTH, i1);
                mycalender2.set(Calendar.DAY_OF_MONTH, i2);
                UpdateLabel();
            }

            private void UpdateLabel() {
                String myFormat = "dd/MM/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                enddate.setText(sdf.format(mycalender2.getTime()));

            }
        };


        enddatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(AdminHomeWork.this, date2, mycalender2.get(Calendar.YEAR),
                        mycalender2.get(Calendar.MONTH), mycalender2.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });


        arrayAdapter = ArrayAdapter.createFromResource(this, R.array.programs, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        final String[] type = new String[1];
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                type[0] = (String) parent.getItemAtPosition(position);

                selectProgram(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String program=type[0];
                String startdate1= startdate.getText().toString();
                String enddate1= enddate.getText().toString();
                String monday1=monday.getText().toString();
                String tuesday1= tuesday.getText().toString();
                String wednesday1=wednesday.getText().toString();
                String thursday1= thursday.getText().toString();
                String friday1= friday.getText().toString();
                String saturday1= saturday.getText().toString();

                long start = mycalender.getTimeInMillis();
                long end = mycalender2.getTimeInMillis();

                int flag=0;
                if (startdate1.trim().equals(""))
                {
                    flag++;
                    Toast.makeText(AdminHomeWork.this, "Please select start date!", Toast.LENGTH_LONG).show();
                } else if (enddate1.trim().equals("")) {
                    flag++;
                    Toast.makeText(AdminHomeWork.this, "Please select end date!", Toast.LENGTH_LONG).show();
                } else if (start > end) {
                    flag++;
                    Toast.makeText(AdminHomeWork.this, "Please enter valid dates!", Toast.LENGTH_SHORT).show();

                } else if (flag == 0)
                {

                    database=FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = database.getReference("newDb").child("HomeWork").child(program);

                    HomeWork obj = new HomeWork(monday1,tuesday1,wednesday1,thursday1,friday1,saturday1,startdate1
                    ,enddate1);
                    databaseReference.setValue(obj);
                    Toast.makeText(AdminHomeWork.this,"Successfully Updated!",Toast.LENGTH_LONG).show();

                }
            }
        });

        }

    public void selectProgram(int position) {

        if (position == 0)
            display("Seeding");
        else if (position == 1)
            display("Budding");
        else if (position == 2)
            display("Blossoming");
        else if (position == 3)
            display("Flourishing");

    }

    public void display(String program) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference newreference = database.getReference("newDb").child("HomeWork").child(program);

        newreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                startdate.setText((CharSequence) dataSnapshot.child("startdate").getValue());
                enddate.setText((CharSequence) dataSnapshot.child("enddate").getValue());
                monday.setText((CharSequence) dataSnapshot.child("monday").getValue());
                tuesday.setText((CharSequence) dataSnapshot.child("tuesday").getValue());
                wednesday.setText((CharSequence) dataSnapshot.child("wednesday").getValue());
                thursday.setText((CharSequence) dataSnapshot.child("thursday").getValue());
                friday.setText((CharSequence) dataSnapshot.child("friday").getValue());
                saturday.setText((CharSequence) dataSnapshot.child("saturday").getValue());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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

    @Override
    protected void onStart() {
        super.onStart();
        display("Seeding");

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
