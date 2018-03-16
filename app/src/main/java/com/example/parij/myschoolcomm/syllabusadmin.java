package com.example.parij.myschoolcomm;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
    ImageView startdatebutton, enddatebutton;

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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Syllabus Coverage");

        toolbar.setTitleTextColor(0xFFFFFFFF);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        textView = findViewById(R.id.text);
        textView2 = findViewById(R.id.text2);
        spinner = findViewById(R.id.spinner3);
        ok = findViewById(R.id.OK);
        link = findViewById(R.id.link);
        startdatebutton = findViewById(R.id.startdatebutton);
        enddatebutton = findViewById(R.id.enddatebutton);

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


        startdatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(syllabusadmin.this, date, mycalender.get(Calendar.YEAR),
                        mycalender.get(Calendar.MONTH), mycalender.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });


        final Calendar mycalender2 = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date1=new DatePickerDialog.OnDateSetListener() {
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

                textView2.setText(sdf.format(mycalender2.getTime()));

            }
        };
        enddatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DatePickerDialog datePickerDialog = new DatePickerDialog(syllabusadmin.this, date1, mycalender2.get(Calendar.YEAR),
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
                selectprogram(position);

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
                long start = mycalender.getTimeInMillis();
                long end = mycalender2.getTimeInMillis();

                if (TextUtils.isEmpty(drivelink) || TextUtils.isEmpty(program))
                {
                    flag=1;
                    Toast.makeText(syllabusadmin.this,"Please fill all the details",Toast.LENGTH_LONG).show();
                } else if (start > end) {
                    flag++;
                    Toast.makeText(syllabusadmin.this, "Please enter valid dates!", Toast.LENGTH_LONG).show();

                } else if (startdate.trim().equals("")) {
                    flag++;
                    Toast.makeText(syllabusadmin.this, "Please select start date!", Toast.LENGTH_LONG).show();

                } else if (enddate.trim().equals("")) {
                    flag++;
                    Toast.makeText(syllabusadmin.this, "Please select end date!", Toast.LENGTH_LONG).show();
                } else if (flag == 0)
                {

                   database=FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = database.getReference("newDb").child("Syllabus_Coverage").child(program);


                    syllabus obj = new syllabus(startdate,enddate,drivelink);

                    databaseReference.setValue(obj);

                    Toast.makeText(syllabusadmin.this,"Successfully Updated!",Toast.LENGTH_LONG).show();

                }

            }
        });


    }

    public void selectprogram(int position) {
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
        DatabaseReference newreference = database.getReference("newDb").child("Syllabus_Coverage").child(program);

        newreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                textView.setText((CharSequence) dataSnapshot.child("startdate").getValue());
                textView2.setText((CharSequence) dataSnapshot.child("enddate").getValue());
                link.setText((CharSequence) dataSnapshot.child("link").getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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