package com.example.parij.myschoolcomm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class announcementadmin extends AppCompatActivity {
    ListView listViewAnnouncement;
    Spinner spinnerFilter;
    ArrayAdapter arrayAdapter, arrayAdapterSpinner;
    EditText announcement;
    Button sendbtn;
    Broadcast broadcast;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList<Broadcast> arrayListBroadcast, filterAnnouncementArrayList;
    ArrayList<String> arrayListMsg, spinnerArrayList;

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

        spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filter(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg;
                if(announcement.getText() != null)
                {
                    broadcast =  new Broadcast(announcement.getText().toString(), System.currentTimeMillis());
                    databaseReference.push().setValue(broadcast);
                    Toast.makeText(announcementadmin.this,"Announcement Sent!",Toast.LENGTH_LONG).show();

                }
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    arrayListBroadcast.add(ds.getValue(Broadcast.class));
                }
                sort(arrayListBroadcast);
                filter(spinnerFilter.getSelectedItemPosition());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void main_initiate() {
        listViewAnnouncement = (ListView) findViewById(R.id.listViewAnnouncementAdmin);
        announcement=(EditText)findViewById(R.id.announcemnttext);
        sendbtn=(Button)findViewById(R.id.sendbtn);
        firebaseDatabase = FirebaseDatabase.getInstance();
        spinnerFilter = (Spinner) findViewById(R.id.spinnerFilterAnnouncementAdmin);
        arrayListBroadcast = new ArrayList<>();
        arrayListMsg = new ArrayList<>();
        spinnerArrayList = new ArrayList<>();
        spinnerArrayList.add("None");
        spinnerArrayList.add("5 Days");
        spinnerArrayList.add("10 Days");
        spinnerArrayList.add("15 Days");
        arrayAdapterSpinner = new ArrayAdapter(announcementadmin.this, android.R.layout.simple_spinner_dropdown_item, spinnerArrayList);
        spinnerFilter.setAdapter(arrayAdapterSpinner);

        databaseReference = firebaseDatabase.getReference().child("broadcast");
    }

    void sort(ArrayList<Broadcast> arrayListBroadcast) {
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
    }
}
