package com.example.parij.myschoolcomm;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.darsh.multipleimageselect.activities.AlbumSelectActivity;
import com.darsh.multipleimageselect.models.Image;
import com.example.parij.myschoolcomm.Models.Memory;
import com.example.parij.myschoolcomm.Models.Programs;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;


public class memories_admin extends AppCompatActivity {


    RadioButton radioButtonAll, radioButtonSeeding, radioButtonBudding, radioButtonBlossoming, radioButtonFlourishing, radioButtonDayCare;
    Button button;
    AlertDialog dialog = null;
    View view;
    ArrayList<Programs> programsArrayList = new ArrayList<>();
    StorageReference storageReference;
    Bundle bundle = new Bundle();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memories_admin);
        AlertDialog.Builder build = new AlertDialog.Builder(memories_admin.this);
        view = getLayoutInflater().inflate(R.layout.dialog_choose_program, null);
        initialise();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("newDb").child("Programs");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Programs programs;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    programs = ds.getValue(Programs.class);
                    programsArrayList.add(programs);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (radioButtonSeeding.isChecked()) {
                    select_photo((Constants.getProgramName(4)));
                    select_photo((Constants.getProgramName(5)));
                    select_photo((Constants.getProgramName(6)));
                    select_photo((Constants.getProgramName(7)));
                    select_photo((Constants.getProgramName(8)));
                    dialog.cancel();
                } else if (radioButtonSeeding.isChecked()) {
                    select_photo((Constants.getProgramName(5)));
                    dialog.cancel();
                } else if (radioButtonBudding.isChecked()) {
                    select_photo((Constants.getProgramName(6)));
                    dialog.cancel();
                } else if (radioButtonBlossoming.isChecked()) {
                    select_photo((Constants.getProgramName(7)));
                    dialog.cancel();
                } else if (radioButtonFlourishing.isChecked()) {
                    select_photo((Constants.getProgramName(8)));
                    dialog.cancel();
                } else if (radioButtonDayCare.isChecked()) {
                    select_photo((Constants.getProgramName(4)));
                    dialog.cancel();
                }

            }
        });
        build.setView(view);
        dialog = build.create();
        dialog.show();
    }


    void select_photo(String program) {

        bundle.putString("Program", program);
        bundle.putString(com.darsh.multipleimageselect.helpers.Constants.INTENT_EXTRA_LIMIT, String.valueOf(10));

        for (String key : bundle.keySet()) {
            Log.d("myApplication", key + " is a key in the bundle");
        }
        Intent intent = new Intent(memories_admin.this, AlbumSelectActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, com.darsh.multipleimageselect.helpers.Constants.REQUEST_CODE);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == com.darsh.multipleimageselect.helpers.Constants.REQUEST_CODE && resultCode == RESULT_OK && data != null) {

            ArrayList<Image> images = new ArrayList<>();

            images = data.getParcelableArrayListExtra(com.darsh.multipleimageselect.helpers.Constants.INTENT_EXTRA_IMAGES);
            Log.e("Images",images.get(0).path);
            for (String key : bundle.keySet()) {
                Log.d("myApplication", key + " is a key in the bundle");
            }
            String program = bundle.getString("Program");
            upload_photo(images, program);
        }
    }

    void upload_photo(ArrayList<Image> images, String program) {

        Programs programs = new Programs();

        if (programsArrayList.size() != 0) {
            for (Programs prog : programsArrayList) {
                if (prog.getName().equals(program)) {
                    programs = prog;
                }
            }
        } else {
            programs.setName(program);
            programs.setMemoryImageLinks(null);
        }

        final ArrayList<Memory> arrayListMemories = new ArrayList<>();
        programs.getMemoryImageLinks();
        final int lowestTimestampIndex = lowestTimestamp(arrayListMemories);
        final Programs progobj = programs;
        storageReference = FirebaseStorage.getInstance().getReference().child("Memories").child(program).child(lowestTimestampIndex + "");

        final ProgressDialog progressDialog = new ProgressDialog(memories_admin.this);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Uploading image...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();

        for (int i = 0; i < images.size(); i++) {

            File file = new File(String.valueOf(images.get(i).path));
            Uri uri = Uri.fromFile(file);
            UploadTask uploadTask = storageReference.putFile(Uri.parse(uri.toString()));
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(memories_admin.this, "Image upload failed!", Toast.LENGTH_SHORT).show();
                }
            }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    progressDialog.dismiss();
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.FBDB).child("students").child("Programs");
                    if (arrayListMemories.size() < 9)
                        arrayListMemories.add(new Memory(System.currentTimeMillis(), task.getResult().getDownloadUrl().toString()));
                    else
                        arrayListMemories.set(lowestTimestampIndex, new Memory(System.currentTimeMillis(), task.getResult().getDownloadUrl().toString()));

                    progobj.setMemoryImageLinks(arrayListMemories);

                    databaseReference.push().setValue(progobj);

                    Toast.makeText(memories_admin.this, "Upload successful", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(memories_admin.this, admindashboard.class));
                    finish();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.setMax((int) taskSnapshot.getTotalByteCount());
                    progressDialog.setProgress((int) taskSnapshot.getBytesTransferred());
                    progressDialog.setSecondaryProgress((int) taskSnapshot.getBytesTransferred());
                    Log.e("Task Progress", "bytesT: " + taskSnapshot.getBytesTransferred() + "bytesTot: " + taskSnapshot.getTotalByteCount());
                }
            });

        }
    }

    int lowestTimestamp(ArrayList<Memory> arrayList) {
        long minTS = Long.MAX_VALUE;
        int index = 0;
        if (arrayList.size() >= 9) {
            for (int i = 0; i < arrayList.size(); i++) {
                Log.e("TS: ", "ts: " + minTS + " index: " + i + " size: " + arrayList.size());
                if (arrayList.get(i).getTimestamp() < minTS) {
                    minTS = arrayList.get(i).getTimestamp();
                    index = i;
                }
            }
        } else {
            index = arrayList.size();
        }
        return index;
    }

    void initialise() {
        radioButtonAll = view.findViewById(R.id.radioButtonAllPrograms);
        radioButtonSeeding = view.findViewById(R.id.radioButtonSeeding);
        radioButtonBudding = view.findViewById(R.id.radioButtonBudding);
        radioButtonBlossoming = view.findViewById(R.id.radioButtonBlossoming);
        radioButtonFlourishing = view.findViewById(R.id.radioButtonFlourishing);
        radioButtonDayCare = view.findViewById(R.id.radioButtonDayCare);
        button = view.findViewById(R.id.buttonTypeOk);
    }

}
