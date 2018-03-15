package com.example.parij.myschoolcomm;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.parij.myschoolcomm.Models.Announcement;
import com.example.parij.myschoolcomm.Models.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;

public class announcementuser extends AppCompatActivity {
    ArrayList<Broadcast> arrayListBroadcast, filterAnnouncementArrayList;
    ArrayList<String> arrayListMsg, spinnerArrayList;
    Spinner spinnerFilter;
    ListView listView;
    ArrayAdapter arrayAdapter, arrayAdapterSpinner;
    DatabaseReference databaseReference, databaseReferenceUser;
    FoldingCell foldingCell1, foldingCell2, foldingCell3;
    Button buttonViewAll;
    ArrayList<Announcement> arrayListAnnouncement;
    TextView textViewTitle1, textViewDetail1, textViewTitle2, textViewDetail2, textViewTitle3, textViewDetail3, textViewUnfoldedTitle1, textViewUnfoldedTitle2, textViewUnfoldedTitle3;
    int program;
    String username;
    Boolean isAdmin;
    AlertDialog.Builder dialog;
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
        SessionManagement.retrieveSharedPreferences(announcementuser.this);
        username = SessionManagement.username;
        isAdmin = SessionManagement.isAdmin;
        init();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Announcements");
        //  toolbar.setNavigationIcon(R.drawable.childprofbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);

        foldingCell1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foldingCell1.toggle(false);
            }
        });
        foldingCell2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foldingCell2.toggle(false);
            }
        });
        foldingCell3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foldingCell3.toggle(false);
            }
        });
        /*spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filter(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
        buttonViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //listView.setVisibility(View.VISIBLE);
                //buttonViewAll.setVisibility(View.INVISIBLE);
                showAnnouncementListDialog();
            }
        });

        databaseReferenceUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (username.equals(data.getValue(Student.class).getUsername()))
                        program = data.getValue(Student.class).getProgram();
                }
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Announcement announcement;
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            //arrayListBroadcast.add(ds.getValue(Broadcast.class));
                            announcement = ds.getValue(Announcement.class);
                            Log.d("announcement class ", program + "" + isAdmin);

                            if (isAdmin) {
                                arrayListAnnouncement.add(announcement);
                            }
                            else {
                                if (program == announcement.getProgram() || announcement.getProgram() == Constants.ALLPROGRAMS) {
                                    arrayListAnnouncement.add(announcement);
                                    Log.d("announcement user ", program + "");
                                }
                            }

                        }
                        arrayListAnnouncement = sort(arrayListAnnouncement);
                        setView(arrayListAnnouncement);
                        //sort(arrayListBroadcast);
                        //filter(spinnerFilter.getSelectedItemPosition());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    void showAnnouncementListDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View convertView = inflater.inflate(R.layout.dialog_anouncement_list, null);
        dialog.setView(convertView);
        dialog.setTitle("Announcement List :");
        listView = convertView.findViewById(R.id.listViewAnnouncementUser);
        arrayAdapter = new ArrayAdapter(announcementuser.this, android.R.layout.simple_list_item_1, arrayListMsg);
        listView.setAdapter(arrayAdapter);
        dialog.show();
    }

    void init()
    {
        //listView = (ListView) findViewById(R.id.listViewAnnouncementUser);
        dialog = new AlertDialog.Builder(announcementuser.this);
        foldingCell1 = findViewById(R.id.foldindcell1);
        foldingCell2 = findViewById(R.id.foldindcell2);
        foldingCell3 = findViewById(R.id.foldindcell3);
        buttonViewAll = findViewById(R.id.buttonViewAllAnnouncements);
        textViewDetail1 = findViewById(R.id.textViewAnnouncementDetail1);
        textViewDetail2 = findViewById(R.id.textViewAnnouncementDetail2);
        textViewDetail3 = findViewById(R.id.textViewAnnouncementDetail3);
        textViewTitle1 = findViewById(R.id.textViewAnnouncementTitle1);
        textViewTitle2 = findViewById(R.id.textViewAnnouncementTitle2);
        textViewTitle3 = findViewById(R.id.textViewAnnouncementTitle3);
        textViewUnfoldedTitle1 = findViewById(R.id.textViewUnfoldedAnnouncementTitle1);
        textViewUnfoldedTitle2 = findViewById(R.id.textViewUnfoldedAnnouncementTitle2);
        textViewUnfoldedTitle3 = findViewById(R.id.textViewUnfoldedAnnouncementTitle3);
        arrayListAnnouncement = new ArrayList<>();
        arrayListMsg = new ArrayList<>();
        /*spinnerFilter = (Spinner) findViewById(R.id.spinnerFilterAnnouncementUser);
        arrayListBroadcast = new ArrayList<>();
        spinnerArrayList = new ArrayList<>();
        spinnerArrayList.add("All");
        spinnerArrayList.add("5 Days");
        spinnerArrayList.add("10 Days");
        spinnerArrayList.add("15 Days");
        arrayAdapterSpinner = new ArrayAdapter(announcementuser.this, android.R.layout.simple_spinner_dropdown_item, spinnerArrayList);
        spinnerFilter.setAdapter(arrayAdapterSpinner);
        */
        databaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.FBDB).child("announcements");
        databaseReferenceUser = FirebaseDatabase.getInstance().getReference().child(Constants.FBDB).child("students");
    }

    ArrayList<Announcement> sort(ArrayList<Announcement> arrayListAnnouncement) {
        for (int i = 0; i < arrayListAnnouncement.size(); i++) {
            for (int j = 0; j < arrayListAnnouncement.size() - 1; j++) {
                if (arrayListAnnouncement.get(j).getTimestamp() < arrayListAnnouncement.get(j + 1).getTimestamp()) {
                    Announcement temp = arrayListAnnouncement.get(j);
                    arrayListAnnouncement.set(j, arrayListAnnouncement.get(j + 1));
                    arrayListAnnouncement.set(j + 1, temp);
                }
            }
        }
        for (int i = 3; i < arrayListAnnouncement.size(); i++) {
            arrayListMsg.add("Title : " + arrayListAnnouncement.get(i).getTitle() + "\nMessage : " + arrayListAnnouncement.get(i).getDetail());
            Log.d("announcement", "Title : " + arrayListAnnouncement.get(i).getTitle() + "\nMessage : " + arrayListAnnouncement.get(i).getDetail());
        }
        return arrayListAnnouncement;
    }

    void setView(ArrayList<Announcement> arrayListAnnouncement) {
        int n = arrayListAnnouncement.size() > 3 ? 3 : arrayListAnnouncement.size();
        Log.d("size", "why? " + arrayListAnnouncement.size());
        switch (n) {
            case 0:
                return;
            case 3:
                foldingCell3.setVisibility(View.VISIBLE);
                textViewUnfoldedTitle3.setText(arrayListAnnouncement.get(2).getTitle());
                textViewTitle3.setText(arrayListAnnouncement.get(2).getTitle());
                textViewDetail3.setText(arrayListAnnouncement.get(2).getDetail());
            case 2:
                foldingCell2.setVisibility(View.VISIBLE);
                textViewUnfoldedTitle2.setText(arrayListAnnouncement.get(1).getTitle());
                textViewTitle2.setText(arrayListAnnouncement.get(1).getTitle());
                textViewDetail2.setText(arrayListAnnouncement.get(1).getDetail());
            case 1:
                foldingCell1.setVisibility(View.VISIBLE);
                textViewUnfoldedTitle1.setText(arrayListAnnouncement.get(0).getTitle());
                textViewTitle1.setText(arrayListAnnouncement.get(0).getTitle());
                textViewDetail1.setText(arrayListAnnouncement.get(0).getDetail());


        }
        if (arrayListAnnouncement.size() > 3) {
            buttonViewAll.setVisibility(View.VISIBLE);
        }
    }
    /*void sort(ArrayList<Broadcast> arrayListBroadcast) {
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
    }*/
}
