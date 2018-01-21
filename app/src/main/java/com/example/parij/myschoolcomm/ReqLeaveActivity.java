package com.example.parij.myschoolcomm;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ReqLeaveActivity extends AppCompatActivity {

    TextView from, to;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Message message;
    Bundle bundle;
    private Button sendbtn;
    private EditText reason;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_req_leave);

       // bundle=getIntent().getExtras();
        //final String username = bundle.getString("Username");

        //Intent intent=getIntent();

        SessionManagement.retrieveSharedPreferences(ReqLeaveActivity.this);

        final String username = SessionManagement.username;

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Request Leave");
        //  toolbar.setNavigationIcon(R.drawable.homeclassbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);



        main_initate();
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

                from.setText(sdf.format(mycalender.getTime()));

            }
        };


        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DatePickerDialog datePickerDialog = new DatePickerDialog(ReqLeaveActivity.this, date, mycalender.get(Calendar.YEAR),
                        mycalender.get(Calendar.MONTH), mycalender.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(mycalender.getTimeInMillis());
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

                to.setText(sdf.format(mycalender2.getTime()));

            }
        };
        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DatePickerDialog datePickerDialog = new DatePickerDialog(ReqLeaveActivity.this, date1, mycalender2.get(Calendar.YEAR),
                        mycalender2.get(Calendar.MONTH), mycalender2.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(mycalender2.getTimeInMillis());
                datePickerDialog.show();
            }
        });


        //final String msg_body="A request for leave from: "+from.getText().toString()+" to: "+to.getText().toString()+" for the following reason: "+reason.getText().toString();

        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long start = mycalender.getTimeInMillis();
                long end = mycalender2.getTimeInMillis();

                if (from.getText().toString().contains("Date")) {
                    Toast.makeText(ReqLeaveActivity.this, "Please select start date!", Toast.LENGTH_LONG).show();
                } else if (to.getText().toString().contains("Date")) {
                    Toast.makeText(ReqLeaveActivity.this, "Please select end date!", Toast.LENGTH_LONG).show();

                } else if (start > end) {
                    Toast.makeText(ReqLeaveActivity.this, "Please select valid dates!", Toast.LENGTH_LONG).show();
                } else if (reason.getText().toString().trim().length() <= 0) {
                    Toast.makeText(ReqLeaveActivity.this, "Please enter the reason!", Toast.LENGTH_LONG).show();
                } else if (from.getText().toString() != null || to.getText().toString() != null || reason.getText().toString() != null) {
                    //message=new Message(msg_body,System.currentTimeMillis(),username);
                    message = new Message(reason.getText().toString().trim(), from.getText().toString().trim(), to.getText().toString().trim(), System.currentTimeMillis(), username);
                    databaseReference.push().setValue(message);
                    Toast.makeText(ReqLeaveActivity.this,"Request Sent!",Toast.LENGTH_LONG).show();
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

    private void main_initate() {

        sendbtn=(Button)findViewById(R.id.sendbtn);
        from = (TextView) findViewById(R.id.fromdate);
        to = (TextView) findViewById(R.id.todate);
        reason=(EditText)findViewById(R.id.reason);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("Leave_request");

    }
}
