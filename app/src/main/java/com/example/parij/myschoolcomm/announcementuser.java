package com.example.parij.myschoolcomm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class announcementuser extends AppCompatActivity {
    ArrayList<Broadcast> arrayListBroadcast, filterAnnouncementArrayList;
    ArrayList<String> arrayListMsg, spinnerArrayList;
    Spinner spinnerFilter;
    ListView listView;
    ArrayAdapter arrayAdapter, arrayAdapterSpinner;
    DatabaseReference databaseReference;

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
        setContentView(R.layout.activity_announcementuser);
        init();

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

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
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
    void init()
    {
        listView = (ListView) findViewById(R.id.listViewAnnouncementUser);
        spinnerFilter = (Spinner) findViewById(R.id.spinnerFilterAnnouncementUser); 
        arrayListBroadcast = new ArrayList<>();
        arrayListMsg = new ArrayList<>();
        spinnerArrayList = new ArrayList<>();
        spinnerArrayList.add("None");
        spinnerArrayList.add("5 Days");
        spinnerArrayList.add("10 Days");
        spinnerArrayList.add("15 Days");
        arrayAdapterSpinner = new ArrayAdapter(announcementuser.this, android.R.layout.simple_spinner_dropdown_item, spinnerArrayList);
        spinnerFilter.setAdapter(arrayAdapterSpinner);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("broadcast");
    }

    void sort(ArrayList<Broadcast> arrayListBroadcast)
    {
        for(int i = 0;i < arrayListBroadcast.size();i++)
        {
            for(int j = 0; j < arrayListBroadcast.size() - 1;j++)
            {
                if (arrayListBroadcast.get(j).getTimestamp() < arrayListBroadcast.get(j + 1).getTimestamp())
                {
                    Broadcast temp =  arrayListBroadcast.get(j);
                    arrayListBroadcast.set(j, arrayListBroadcast.get(j + 1 ));
                    arrayListBroadcast.set(j + 1, temp);
                }
            }
        }
        for(int i = 0;i < arrayListBroadcast.size();i++)
        {
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
        arrayAdapter = new ArrayAdapter(announcementuser.this, android.R.layout.simple_list_item_1, arrayListMsg);
        listView.setAdapter(arrayAdapter);
    }
}
