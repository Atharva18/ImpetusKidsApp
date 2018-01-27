package com.example.parij.myschoolcomm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.parij.myschoolcomm.Models.LeaveRequest;
import com.example.parij.myschoolcomm.Models.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class reqleaveadmin extends AppCompatActivity {
    //ArrayList<Message> messageArrayList, filterMessageArrayList;
    ArrayList<LeaveRequest> messageArrayList, filterMessageArrayList;
    ArrayList<Student> students;
    HashMap<String, Student> hashMapStudent;
    ArrayList<String> messageList, spinnerArrayList;
    ListView listView;
    ArrayAdapter arrayAdapter, arrayAdapterSpinner;
    DatabaseReference databaseReference, databaseReferenceStudents;
    Spinner spinnerFilter;
    EditText editTextSearch;


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
        databaseReferenceStudents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    students.add(data.getValue(Student.class));
                }
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        messageArrayList = new ArrayList<>();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            LeaveRequest leaveRequest = ds.getValue(LeaveRequest.class);
                            messageArrayList.add(leaveRequest);
                            hashMapStudent.put(leaveRequest.getUsername(), getStudent(leaveRequest.getUsername()));

                        }
                        sort(messageArrayList);
                        filter(spinnerFilter.getSelectedItemPosition());
                        arrayAdapter = new ArrayAdapter(reqleaveadmin.this, android.R.layout.simple_list_item_1, messageList);
                        listView.setAdapter(arrayAdapter);

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

    private void main_initiate() {
        listView = (ListView) findViewById(R.id.ListofLeaves);
        spinnerFilter = (Spinner) findViewById(R.id.spinnerFilter);

        editTextSearch = (EditText) findViewById(R.id.editTextLeaveRequestSearch);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterListByText(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        hashMapStudent = new HashMap<>();
        messageArrayList = new ArrayList<>();
        messageList= new ArrayList<>();
        students = new ArrayList<>();
        spinnerArrayList = new ArrayList<>();
        spinnerArrayList.add("All");
        spinnerArrayList.add("5 Days");
        spinnerArrayList.add("10 Days");
        spinnerArrayList.add("15 Days");
        arrayAdapterSpinner = new ArrayAdapter(reqleaveadmin.this, android.R.layout.simple_spinner_dropdown_item, spinnerArrayList);
        spinnerFilter.setAdapter(arrayAdapterSpinner);
        spinnerFilter.setSelection(1);
        databaseReference = FirebaseDatabase.getInstance().getReference(Constants.FBDB).child("leaveRequests");
        databaseReferenceStudents = FirebaseDatabase.getInstance().getReference(Constants.FBDB).child("students");
    }

    void sort(ArrayList<LeaveRequest> messageArrayList) {

        for (int i = 0; i < messageArrayList.size(); i++) {
            for (int j = 0; j < messageArrayList.size() - 1; j++) {
                if (messageArrayList.get(j).getTimestamp() < messageArrayList.get(j + 1).getTimestamp()) {
                    LeaveRequest temp = messageArrayList.get(j);
                    messageArrayList.set(j, messageArrayList.get(j + 1));
                    messageArrayList.set(j + 1, temp);
                }
            }
        }
        messageList = new ArrayList<>();
        for(int i = 0; i < messageArrayList.size(); i++) {
            messageList.add("Sender : " + hashMapStudent.get(messageArrayList.get(i).getUsername()).getName() + "\n"
                    + "Reason : " + messageArrayList.get(i).getReason() + "\n"
                    + "From : " + messageArrayList.get(i).getFromDate() + "\t To : " + messageArrayList.get(i).getToDate());
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
            messageList.add("Sender : " + hashMapStudent.get(messageArrayList.get(i).getUsername()).getName() + "\n"
                    + "Reason : " + messageArrayList.get(i).getReason() + "\n"
                    + "From : " + messageArrayList.get(i).getFromDate() + "\t To : " + messageArrayList.get(i).getToDate());
        }
        if (editTextSearch.getText().toString() != null)
            filterListByText(editTextSearch.getText().toString());
        else
            filterListByText("");
    }

    void filterListByText(String input) {
        Log.d("inputText", input);
        ArrayList<String> arrayListDisplay = new ArrayList<>();
        for (int i = 0; i < filterMessageArrayList.size(); i++) {
            if (hashMapStudent.get(filterMessageArrayList.get(i).getUsername()).getName().toLowerCase().contains(input.toLowerCase())) {
                arrayListDisplay.add("Sender : " + hashMapStudent.get(messageArrayList.get(i).getUsername()).getName() + "\n"
                        + "Reason : " + messageArrayList.get(i).getReason() + "\n"
                        + "From : " + messageArrayList.get(i).getFromDate() + "\t To : " + messageArrayList.get(i).getToDate());
            }
        }
        arrayAdapter = new ArrayAdapter(reqleaveadmin.this, android.R.layout.simple_list_item_1, arrayListDisplay);
        listView.setAdapter(arrayAdapter);
    }

    Student getStudent(String username) {
        Student student = new Student();
        for (Student s : students) {
            if (s.getUsername().equals(username)) {
                student = s;
            }
        }
        return student;
    }
}

