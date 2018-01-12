package com.example.parij.myschoolcomm;

import android.app.DatePickerDialog;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class syllabusadmin extends AppCompatActivity {

    TextView textView;
    TextView textView2;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    DatePickerDialog.OnDateSetListener onDateSetListener2;
    Spinner spinner;
    ArrayAdapter<CharSequence> arrayAdapter;
    Button ok;
    EditText link;
    FirebaseDatabase database;


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
        setContentView(R.layout.syllabusadmin);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Syllabus Coverage");

        toolbar.setTitleTextColor(0xFFFFFFFF);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        textView=(TextView)findViewById(R.id.text);
        textView2=(TextView)findViewById(R.id.text2);
        spinner=(Spinner)findViewById(R.id.spinner3);
        ok=(Button)findViewById(R.id.OK);
        link=(EditText) findViewById(R.id.link);

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

                textView.setText(sdf.format(mycalender.getTime()));

            }
        };


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(syllabusadmin.this,date,mycalender.get(Calendar.YEAR),mycalender.get(Calendar.MONTH),mycalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



        final DatePickerDialog.OnDateSetListener date1=new DatePickerDialog.OnDateSetListener() {
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

                textView2.setText(sdf.format(mycalender.getTime()));

            }
        };


        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(syllabusadmin.this,date1,mycalender.get(Calendar.YEAR),mycalender.get(Calendar.MONTH),mycalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


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
                int flag=0;

                String program = type[0];
                 String startdate = textView.getText().toString();
                 String enddate= textView2.getText().toString();
                 String drivelink=link.getText().toString().trim();

                if(TextUtils.isEmpty(drivelink)||TextUtils.isEmpty(enddate)||TextUtils.isEmpty(program)||TextUtils.isEmpty(startdate))
                {
                    flag=1;
                    Toast.makeText(syllabusadmin.this,"Please fill all the details",Toast.LENGTH_LONG).show();
                }

                if(flag==0)
                {

                   database=FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = database.getReference("Syllabus_Coverage").child(program);


                    syllabus obj = new syllabus(startdate,enddate,drivelink);

                    databaseReference.setValue(obj);

                    Toast.makeText(syllabusadmin.this,"Successfully Updated!",Toast.LENGTH_LONG).show();

                }

            }
        });


    }
}

 class syllabus
{
    String startdate;
    String enddate;
    String link;

    public syllabus()
    {

    }

    public syllabus(String startdate, String enddate, String link) {
        this.startdate = startdate;
        this.enddate = enddate;
        this.link = link;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}