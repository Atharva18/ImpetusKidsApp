package com.example.parij.myschoolcomm;

import android.app.DatePickerDialog;
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

public class ThemeAdmin extends AppCompatActivity {

    EditText theme;
    EditText parenttheme;
    TextView start,end;
    Button button;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    DatePickerDialog.OnDateSetListener onDateSetListener2;
    Spinner spinner;
    ArrayAdapter<CharSequence> arrayAdapter;
    FirebaseDatabase database;
    ImageView startbutton, endbutton;


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
        setContentView(R.layout.activity_theme_admin);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Spoken English");
        //  toolbar.setNavigationIcon(R.drawable.homeclassbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);

        theme = findViewById(R.id.theme);
        parenttheme = findViewById(R.id.parenttheme);
        start = findViewById(R.id.startdate);
        end = findViewById(R.id.enddate);
        button = findViewById(R.id.ok);
        spinner = findViewById(R.id.spinner);
        startbutton = findViewById(R.id.startbutton);
        endbutton = findViewById(R.id.endbutton);

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

                start.setText(sdf.format(mycalender.getTime()));

            }
        };


        startbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DatePickerDialog datePickerDialog = new DatePickerDialog(ThemeAdmin.this, date, mycalender.get(Calendar.YEAR),
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

                end.setText(sdf.format(mycalender2.getTime()));

            }
        };


        endbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DatePickerDialog datePickerDialog = new DatePickerDialog(ThemeAdmin.this, date2, mycalender2.get(Calendar.YEAR),
                        mycalender2.get(Calendar.MONTH), mycalender2.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });


        arrayAdapter = ArrayAdapter.createFromResource(this, R.array.TypeAll, android.R.layout.simple_spinner_item);
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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {


                String startdate= start.getText().toString();
                String enddate= end.getText().toString();
                String program =type[0];
                String themetxt= theme.getText().toString().trim();
                String parenttxt = parenttheme.getText().toString().trim();
                long start = mycalender.getTimeInMillis();
                long end = mycalender2.getTimeInMillis();

                if (startdate.trim().equals("")) {
                    Toast.makeText(ThemeAdmin.this, "Please select start date!", Toast.LENGTH_LONG).show();

                } else if (enddate.trim().equals("")) {
                    Toast.makeText(ThemeAdmin.this, "Please select end date!", Toast.LENGTH_LONG).show();
                } else if (start > end) {

                    Toast.makeText(getApplicationContext(), "Please enter valid dates!", Toast.LENGTH_SHORT).show();

                } else if (themetxt.length() <= 0 || parenttxt.length() <= 0) {

                    Toast.makeText(ThemeAdmin.this, "Please set the theme!", Toast.LENGTH_LONG).show();

                } else {
                    check(spinner.getSelectedItemPosition());

                }


            }
        });

    }

    public void selectprogram(int position) {

        if (position == 1) {
            displaystart("Day-Care");

        } else if (position == 2) {
            displaystart("Seeding");


        } else if (position == 3) {
            displaystart("Budding");


        } else if (position == 4) {
            displaystart("Blossoming");

        } else if (position == 5) {
            displaystart("Flourishing");


        }


    }

    public void displaystart(String program) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference newreference = database.getReference("newDb").child("SpokenEnglish").child(program);

        newreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                start.setText((CharSequence) dataSnapshot.child("startdate").getValue());
                end.setText((CharSequence) dataSnapshot.child("enddate").getValue());
                parenttheme.setText((CharSequence) dataSnapshot.child("parenttheme").getValue());
                theme.setText((CharSequence) dataSnapshot.child("theme").getValue());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }






    public void check(int position) {

        if (position == 0) {
            display("Day-Care");
            display("Seeding");
            display("Flourishing");
            display("Budding");
            display("Blossoming");
            Toast.makeText(getApplicationContext(), "Theme Set!", Toast.LENGTH_SHORT).show();
        } else if (position == 1) {
            display("Day-Care");
            Toast.makeText(getApplicationContext(), "Theme Set!", Toast.LENGTH_SHORT).show();
        } else if (position == 2) {
            display("Seeding");
            Toast.makeText(getApplicationContext(), "Theme Set!", Toast.LENGTH_SHORT).show();

        } else if (position == 3) {
            display("Budding");
            Toast.makeText(getApplicationContext(), "Theme Set!", Toast.LENGTH_SHORT).show();

        } else if (position == 4) {
            display("Blossoming");
            Toast.makeText(getApplicationContext(), "Theme Set!", Toast.LENGTH_SHORT).show();
        } else if (position == 5) {
            display("Flourishing");
            Toast.makeText(getApplicationContext(), "Theme Set!", Toast.LENGTH_SHORT).show();

        }


    }


    public void display(String program) {
        String startdate = start.getText().toString();
        String enddate = end.getText().toString();
        String themetxt = theme.getText().toString().trim();
        String parenttxt = parenttheme.getText().toString().trim();
        database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("newDb").child("SpokenEnglish").child(program);
        themes obj = new themes(startdate, enddate, themetxt, parenttxt);
        reference.setValue(obj);
    }

}

class themes
{

    String startdate;
    String enddate;
    String theme;
    String parenttheme;

    public themes()
    {

    }

    public themes(String startdate, String enddate, String theme, String parenttheme) {
        this.startdate = startdate;
        this.enddate = enddate;
        this.theme = theme;
        this.parenttheme = parenttheme;
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

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getParenttheme() {
        return parenttheme;
    }

    public void setParenttheme(String parenttheme) {
        this.parenttheme = parenttheme;
    }
}