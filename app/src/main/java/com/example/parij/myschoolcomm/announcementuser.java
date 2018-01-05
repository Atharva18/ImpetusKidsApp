package com.example.parij.myschoolcomm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class announcementuser extends AppCompatActivity {
    ArrayList<Broadcast> arrayListBroadcast;
    ArrayList<String> arrayListMsg;
    ListView listView;
    ArrayAdapter arrayAdapter;
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

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    arrayListBroadcast.add(ds.getValue(Broadcast.class));
                }
                sort(arrayListBroadcast);
                arrayAdapter = new ArrayAdapter(announcementuser.this, android.R.layout.simple_list_item_1, arrayListMsg);
                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    void init()
    {
        listView = (ListView) findViewById(R.id.listViewAnnouncementUser);
        arrayListBroadcast = new ArrayList<>();
        arrayListMsg = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("broadcast");
    }

    void sort(ArrayList<Broadcast> arrayListBroadcast)
    {
        for(int i = 0;i < arrayListBroadcast.size();i++)
        {
            for(int j = 0; j < arrayListBroadcast.size() - 1;j++)
            {
                if(arrayListBroadcast.get(j).getTimestamp() > arrayListBroadcast.get(j + 1).getTimestamp())
                {
                    Broadcast temp =  arrayListBroadcast.get(j);
                    arrayListBroadcast.set(j, arrayListBroadcast.get(j + 1 ));
                    arrayListBroadcast.set(j + 1, temp);
                }
            }
        }
        for(int i = 0;i < arrayListBroadcast.size();i++)
        {
            arrayListMsg.add("Msg : " + arrayListBroadcast.get(i).getMsg());
        }
    }
}
