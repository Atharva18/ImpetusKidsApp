package com.example.parij.myschoolcomm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.parij.myschoolcomm.Models.Announcement;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class announcementadmin extends AppCompatActivity {
    ListView listViewAnnouncement;
    Spinner spinnerFilter, spinnerAnnouncementProgram;
    ArrayAdapter arrayAdapter, arrayAdapterSpinner, arrayAdapterProgram;
    EditText editTextAnnouncementDetail, editTextAnnouncementTitle;
    Button sendbtn;
    Broadcast broadcast;
    Announcement announcement;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList<Broadcast> arrayListBroadcast, filterAnnouncementArrayList;
    ArrayList<String> arrayListMsg, spinnerArrayList, spinnerProgramArrayList;

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
        setContentView(R.layout.activity_announcementadmin);

        main_initiate();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Announcements");
        //  toolbar.setNavigationIcon(R.drawable.childprofbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);

        /*spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filter(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/


        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFormValid())
                {
                    //broadcast =  new Broadcast(editTextAnnouncementDetail.getText().toString(), System.currentTimeMillis());
                    String title = editTextAnnouncementTitle.getText().toString().trim();
                    String detail = editTextAnnouncementDetail.getText().toString().trim();
                    long timestamp = System.currentTimeMillis();
                    int program = getSelectedProgram();
                    announcement = new Announcement(title, detail, program, timestamp);
                    databaseReference.push().setValue(announcement);
                    Toast.makeText(announcementadmin.this,"Announcement Sent!",Toast.LENGTH_LONG).show();

                }
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void main_initiate() {
        //listViewAnnouncement = (ListView) findViewById(R.id.listViewAnnouncementAdmin);

        editTextAnnouncementDetail = (EditText) findViewById(R.id.announcemnttext);
        editTextAnnouncementTitle = (EditText) findViewById(R.id.editTextAnncouncementTitle);
        spinnerAnnouncementProgram = (Spinner) findViewById(R.id.spinnerAnnouncementProgram);
        sendbtn=(Button)findViewById(R.id.sendbtn);
        firebaseDatabase = FirebaseDatabase.getInstance();
        //spinnerFilter = (Spinner) findViewById(R.id.spinnerFilterAnnouncementAdmin);

        arrayListBroadcast = new ArrayList<>();
        arrayListMsg = new ArrayList<>();
        spinnerArrayList = new ArrayList<>();
        spinnerProgramArrayList = new ArrayList<>();

        spinnerProgramArrayList.add("All");
        spinnerProgramArrayList.add("Daycare");
        spinnerProgramArrayList.add("Seeding");
        spinnerProgramArrayList.add("Budding");
        spinnerProgramArrayList.add("Blossoming");
        spinnerProgramArrayList.add("Flourishing");

        //spinnerArrayList.add("All");
        //spinnerArrayList.add("5 Days");
        //spinnerArrayList.add("10 Days");
        //spinnerArrayList.add("15 Days");

        //arrayAdapterSpinner = new ArrayAdapter(announcementadmin.this, android.R.layout.simple_spinner_dropdown_item, spinnerArrayList);
        //spinnerFilter.setAdapter(arrayAdapterSpinner);

        arrayAdapterProgram = new ArrayAdapter(announcementadmin.this, android.R.layout.simple_spinner_dropdown_item, spinnerProgramArrayList);
        spinnerAnnouncementProgram.setAdapter(arrayAdapterProgram);
        databaseReference = firebaseDatabase.getReference().child(Constants.FBDB).child("announcements");
    }

    boolean isFormValid() {
        if (editTextAnnouncementDetail.getText().toString().trim().equals("")) {
            editTextAnnouncementDetail.setError("Invalid input");
            return false;
        }
        if (editTextAnnouncementTitle.getText().toString().trim().equals("")) {
            editTextAnnouncementTitle.setError("Invalid input");
            return false;
        }
        return true;
    }

    int getSelectedProgram() {
        switch (spinnerAnnouncementProgram.getSelectedItemPosition()) {
            case 0:
                return Constants.ALLPROGRAMS;
            case 1:
                return Constants.DAYCARE;
            case 2:
                return Constants.SEEDING;
            case 3:
                return Constants.BUDDING;
            case 4:
                return Constants.BLOSSOMING;
            case 5:
                return Constants.FLOURISHING;

        }
        return -1;
    }

    /*void sort(ArrayList<Broadcast> arrayListBroadcast) {
        for (int i = 0; i < arrayListBroadcast.size(); i++) {
            for (int j = 0; j < arrayListBroadcast.size() - 1; j++) {
                if (arrayListBroadcast.get(j).getTimestamp() < arrayListBroadcast.get(j + 1).getTimestamp()) {
                    Broadcast temp = arrayListBroadcast.get(j);
                    arrayListBroadcast.set(j, arrayListBroadcast.get(j + 1));
                    arrayListBroadcast.set(j + 1, temp);
                }
            }
        }
        for (int i = 0; i < arrayListBroadcast.size(); i++) {
            arrayListMsg.add("Message : " + arrayListBroadcast.get(i).getMsg());
        }
    }

    void filter(int position) {
        filterAnnouncementArrayList = new ArrayList<>();
        arrayListMsg = new ArrayList<>();
        Log.d("position", "" + position);
        if (position == 0) {
            filterAnnouncementArrayList = arrayListBroadcast;
        } else {
            int day = position * 5;
            long currentTimeStamp = System.currentTimeMillis();
            long filterTimeStamp = day * 86400000;
            for (int i = 0; i < arrayListBroadcast.size(); i++) {
                Log.d("something", (currentTimeStamp - arrayListBroadcast.get(i).getTimestamp()) + "");
                if (currentTimeStamp - arrayListBroadcast.get(i).getTimestamp() < filterTimeStamp) {
                    filterAnnouncementArrayList.add(arrayListBroadcast.get(i));
                }
            }
        }
        for (int i = 0; i < filterAnnouncementArrayList.size(); i++) {
            arrayListMsg.add("Message : " + arrayListBroadcast.get(i).getMsg());
        }
        arrayAdapter = new ArrayAdapter(announcementadmin.this, android.R.layout.simple_list_item_1, arrayListMsg);
        listViewAnnouncement.setAdapter(arrayAdapter);
    }*/
}
