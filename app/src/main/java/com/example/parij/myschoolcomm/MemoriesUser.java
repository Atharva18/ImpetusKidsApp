package com.example.parij.myschoolcomm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.parij.myschoolcomm.Models.Memory;
import com.example.parij.myschoolcomm.Models.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import jp.shts.android.storiesprogressview.StoriesProgressView;

public class MemoriesUser extends AppCompatActivity implements StoriesProgressView.StoriesListener {
    StoriesProgressView storiesProgressView;
    DatabaseReference databaseReference;
    ImageView imageView;
    View reverse, skip;
    String username;
    int counter = 0;
    int progressCount;
    ArrayList<Memory> imageLinks;
    ArrayList<String> imageUrls;
    long pressTime = 0L;
    long limit = 500L;
    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    pressTime = System.currentTimeMillis();
                    storiesProgressView.pause();
                    return false;
                case MotionEvent.ACTION_UP:
                    long now = System.currentTimeMillis();
                    storiesProgressView.resume();
                    return limit < now - pressTime;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memories_user);
        init();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Student s;
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    s = data.getValue(Student.class);
                    if (s.getUsername().equals(username)) {
                        imageLinks = new ArrayList<>(s.getMemoryImageLinks());
                        break;
                    }
                }
                for (Memory m : imageLinks) {
                    imageUrls.add(m.getUrl());
                    Log.d("memories", m.getUrl());
                }
                progressCount = imageUrls.size();
                check();
                startMemories();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        reverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storiesProgressView.reverse();
            }
        });
        reverse.setOnTouchListener(onTouchListener);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storiesProgressView.skip();
            }
        });
        skip.setOnTouchListener(onTouchListener);
    }

    void check() {
        if (imageLinks.size() == 0) {
            Toast.makeText(MemoriesUser.this, "No memories avilable", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    void init() {
        imageView = (ImageView) findViewById(R.id.imageViewMemories);
        SessionManagement.retrieveSharedPreferences(this);
        username = SessionManagement.username;
        imageUrls = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.FBDB).child("students");

        storiesProgressView = (StoriesProgressView) findViewById(R.id.stories);
        reverse = findViewById(R.id.reverse);
        skip = findViewById(R.id.skip);

    }

    void startMemories() {
        storiesProgressView.setStoriesCount(progressCount);
        storiesProgressView.setStoryDuration(3000L);
        storiesProgressView.setStoriesListener(this);
        storiesProgressView.startStories();
        Glide.with(this).load(imageUrls.get(counter)).into(imageView);
    }

    @Override
    public void onNext() {
        if (counter + 1 == imageUrls.size())
            return;
        Glide.with(this).load(imageUrls.get(++counter)).into(imageView);
    }

    @Override
    public void onPrev() {
        if (counter - 1 == 0)
            return;
        Glide.with(this).load(imageUrls.get(--counter)).into(imageView);
    }

    @Override
    public void onComplete() {
        finish();
    }

    @Override
    protected void onDestroy() {
        // Very important !
        storiesProgressView.destroy();
        super.onDestroy();
    }
}
