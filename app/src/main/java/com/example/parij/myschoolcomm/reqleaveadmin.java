package com.example.parij.myschoolcomm;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



public class reqleaveadmin extends AppCompatActivity {
    ArrayList<Message> messageArrayList;
    ArrayList<String> messageList;
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
        setContentView(R.layout.activity_reqleaveadmin2);

        main_initiate();

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Request Leave");
        //  toolbar.setNavigationIcon(R.drawable.homeclassbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()) {
                    messageArrayList.add(ds.getValue(Message.class));
                }

                sort(messageArrayList);
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
        messageArrayList = new ArrayList<>();
        messageList= new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Leave_request");

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    void sort(ArrayList<Message> messageArrayList){

        for(int i = 0;i < messageArrayList.size();i++)
        {
            for(int j = 0; j < messageArrayList.size() - 1;j++)
            {
                if(messageArrayList.get(j).getTimestamp() > messageArrayList.get(j + 1).getTimestamp())
                {
                    Message temp =  messageArrayList.get(j);
                    messageArrayList.set(j, messageArrayList.get(j + 1 ));
                    messageArrayList.set(j + 1, temp);
                }
            }
        }
        for(int i = 0;i < messageArrayList.size();i++)
        {
            messageList.add("Sender: "+messageArrayList.get(i).getSender()+System.lineSeparator()+" Message: A request for leave from: "+messageArrayList.get(i).getFromdate()+" to: "+messageArrayList.get(i).getTodate()+"for the Reason : " + messageArrayList.get(i).getReason());
        }
    }
}

