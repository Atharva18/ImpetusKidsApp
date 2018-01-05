package com.example.parij.myschoolcomm;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class adminauthorized extends AppCompatActivity {

    TextView name,relation,contactNo,date1,date2;
    FirebaseDatabase database;
    //Button submit;
    Bundle bundle;
    ImageView personphoto;
    //Button upload;
    Button call;
    final int ImgReq=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminauthorized);

        // Intent intent = getIntent();

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Authorized to Collect");
        //toolbar.setNavigationIcon(R.drawable.parentbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);

        initialise();

        bundle=getIntent().getExtras();
        final String username = bundle.getString("username");

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String number = contactNo.getText().toString();

                if(!TextUtils.isEmpty(number)) {
                    String dial = "tel:" + number;
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
                }else {
                    Toast.makeText(adminauthorized.this, "Enter a phone number", Toast.LENGTH_SHORT).show();
                }



            }
        });






  /*      upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,ImgReq);

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mname= name.getText().toString();
                String mcontactNo=contactNo.getText().toString();
                String mrelation= relation.getText().toString();
                String datestart = date1.getText().toString();
                String dateend= date2.getText().toString();

                int flag=0;

                if(TextUtils.isEmpty(mname))
                {
                    name.setError("Please enter the name");
                    flag++;
                }
                if(!TextUtils.isEmpty(mname) && flag==0)
                {
                    String regex = "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(mname);

                    if(!matcher.matches())
                    {
                        flag++;
                        name.setError("Please enter a valid name!");
                    }

                }
                if(TextUtils.isEmpty(mcontactNo))
                {
                    contactNo.setError("Please enter the contact number");
                    flag++;
                }
                if(!TextUtils.isEmpty(mcontactNo)&& flag==0)
                {
                    String regex = "[0-9*#+() -]*";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(mcontactNo);

                    if (!matcher.matches()) {
                        flag++;
                        contactNo.setError("Enter a valid number!");
                        // Toast.makeText(getApplicationContext(),"Please enter a valid contact no!",Toast.LENGTH_LONG).show();
                    }

                }
                if(TextUtils.isEmpty(mrelation))
                {
                    relation.setError("Please enter the relation");
                    flag++;
                }
                if(!TextUtils.isEmpty(mrelation)&& flag==0)
                {
                    String regex = "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(mname);

                    if(!matcher.matches())
                    {
                        flag++;
                        name.setError("Invalid!");
                    }



                }
                if(TextUtils.isEmpty(datestart))
                {
                    flag++;
                    date1.setError("Please enter the starting date");
                }
                if(flag==0 && !TextUtils.isEmpty(datestart))
                {
                    String regex = "^(([1-9])|([0][1-9])|([1-2][0-9])|([3][0-1]))\\-(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\-\\d{4}$";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(datestart);

                    if(!matcher.matches())
                    {
                        flag++;
                        date1.setError("Invalid date format!");
                    }


                }

                if(TextUtils.isEmpty(dateend))
                {
                    flag++;
                    date2.setError("Please enter the ending date");
                }
                if(flag==0 && !TextUtils.isEmpty(dateend))
                {
                    String regex = "^(([1-9])|([0][1-9])|([1-2][0-9])|([3][0-1]))\\-(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\-\\d{4}$";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(dateend);

                    if(!matcher.matches())
                    {
                        flag++;
                        date2.setError("Invalid date format!");
                    }

                }


                if(flag==0)
                {

                    database=FirebaseDatabase.getInstance();
                    DatabaseReference reference= database.getReference("authorizeToCollect");


                    authorizeclass authorize=new authorizeclass(mname,mcontactNo,mrelation,datestart,dateend);


                    reference.child(username).setValue(authorize);

                    Toast.makeText(getApplicationContext(),"Response submitted",Toast.LENGTH_LONG).show();



                }



            }
        });
*/

    }

    @Override
    protected void onStart()
    {
        super.onStart();


        bundle=getIntent().getExtras();
        final String username = bundle.getString("username");
        database=FirebaseDatabase.getInstance();
         DatabaseReference databaseReference= database.getReference("authorizeToCollect");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {

                for(DataSnapshot data : dataSnapshot.getChildren())
                {
                    if(username.equals(data.getKey()))
                    {
                        // contactNo.setText((CharSequence) data.child("mcontact"));
                        contactNo.setText((CharSequence) data.child("mcontact").getValue());
                        name.setText((CharSequence)data.child("mname").getValue());
                        relation.setText((CharSequence)data.child("relation").getValue());
                        date1.setText((CharSequence)data.child("startdate").getValue());
                        date2.setText((CharSequence)data.child("enddate").getValue());

                    }


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

                Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_LONG).show();

            }
        });

        databaseReference = database.getReference("Images").child("Authorized_Person");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot data : dataSnapshot.getChildren())
                {
                    if(username.equals(data.getKey()))
                    {

                        String url = data.getValue().toString();
                        if(url!=null)
                            Glide.with(getApplicationContext()).load(url).into(personphoto);
                        else
                            Toast.makeText(getApplicationContext(),"Something went wrong!Please try again!",Toast.LENGTH_SHORT).show();
                    }
                }




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if(requestCode == ImgReq && requestCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            personphoto.setImageURI(selectedImage);
        }
    }

    public void initialise()
    {
        //upload=(Button)findViewById(R.id.upload);
        personphoto=(ImageView)findViewById(R.id.personphoto);
       // submit=(Button)findViewById(R.id.submit);
        name=(TextView)findViewById(R.id.name);
        contactNo=(TextView)findViewById(R.id.contactno);
        relation=(TextView)findViewById(R.id.relation);
        date1=(TextView)findViewById(R.id.date1);
        date2=(TextView)findViewById(R.id.date2);
        call=(Button)findViewById(R.id.call);

    }

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
}
