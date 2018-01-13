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



public class reqleaveadmin extends AppCompatActivity {
    ArrayList<Message> messageArrayList, filterMessageArrayList;
    ArrayList<String> messageList, spinnerArrayList;
    ListView listView;
    ArrayAdapter arrayAdapter, arrayAdapterSpinner;
    DatabaseReference databaseReference;
    Spinner spinnerFilter;

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
        setContentView(R.layout.activity_reqleaveadmin2);

        main_initiate();

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Request Leave");
        //  toolbar.setNavigationIcon(R.drawable.homeclassbar);
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
                messageArrayList = new ArrayList<>();
                for(DataSnapshot ds:dataSnapshot.getChildren()) {
                    messageArrayList.add(ds.getValue(Message.class));
                }
                sort(messageArrayList);
                filter(spinnerFilter.getSelectedItemPosition());
                arrayAdapter=new ArrayAdapter(reqleaveadmin.this,android.R.layout.simple_list_item_1,messageList);
                listView.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    }

    private void main_initiate() {
        listView = (ListView) findViewById(R.id.ListofLeaves);
        spinnerFilter = (Spinner) findViewById(R.id.spinnerFilter);

        messageArrayList = new ArrayList<>();
        messageList= new ArrayList<>();

        spinnerArrayList = new ArrayList<>();
        spinnerArrayList.add("None");
        spinnerArrayList.add("5 Days");
        spinnerArrayList.add("10 Days");
        spinnerArrayList.add("15 Days");
        arrayAdapterSpinner = new ArrayAdapter(reqleaveadmin.this, android.R.layout.simple_spinner_dropdown_item, spinnerArrayList);
        spinnerFilter.setAdapter(arrayAdapterSpinner);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Leave_request");

    }

    void sort(ArrayList<Message> messageArrayList){

        for (int i = 0; i < messageArrayList.size(); i++) {
            for (int j = 0; j < messageArrayList.size() - 1; j++) {
                if (messageArrayList.get(j).getTimestamp() < messageArrayList.get(j + 1).getTimestamp()) {
                    Message temp = messageArrayList.get(j);
                    messageArrayList.set(j, messageArrayList.get(j + 1));
                    messageArrayList.set(j + 1, temp);
                }
            }
        }
        messageList = new ArrayList<>();
        for(int i = 0; i < messageArrayList.size(); i++) {
            messageList.add("Sender: " + messageArrayList.get(i).getSender() + "\n"
                    + "Message: A request for leave from: " + messageArrayList.get(i).getFromdate() + " to: "
                    + messageArrayList.get(i).getTodate() + " for the Reason : " + messageArrayList.get(i).getReason());
        }
    }

    void filter(int position) {
        filterMessageArrayList = new ArrayList<>();
        messageList = new ArrayList<>();

        if (position == 0) {
            filterMessageArrayList = messageArrayList;
        } else {
            int day = position * 5;
            long currentTimeStamp = System.currentTimeMillis();
            long filterTimeStamp = day * 86400000;
            for (int i = 0; i < messageArrayList.size(); i++) {
                Log.d("something", (currentTimeStamp - messageArrayList.get(i).getTimestamp()) + "");
                if (currentTimeStamp - messageArrayList.get(i).getTimestamp() < filterTimeStamp) {
                    filterMessageArrayList.add(messageArrayList.get(i));
                }
            }
        }
        for (int i = 0; i < filterMessageArrayList.size(); i++) {
            messageList.add("Sender: " + filterMessageArrayList.get(i).getSender() + "\n"
                    + "Message: A request for leave from: " + filterMessageArrayList.get(i).getFromdate() + " to: "
                    + filterMessageArrayList.get(i).getTodate() + " for the Reason : " + filterMessageArrayList.get(i).getReason());
        }
        arrayAdapter = new ArrayAdapter(reqleaveadmin.this, android.R.layout.simple_list_item_1, messageList);
        listView.setAdapter(arrayAdapter);
    }
}

