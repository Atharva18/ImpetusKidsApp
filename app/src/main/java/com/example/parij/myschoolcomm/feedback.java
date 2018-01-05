package com.example.parij.myschoolcomm;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class feedback extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    RadioButton op1,op2;
    EditText ans2;
    Bundle bundle;
    String username="";
    String response="";
    DatabaseReference rootref,demoref;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public feedback() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View rootView= inflater.inflate(R.layout.fragment_feedback, container, false);

       bundle=getActivity().getIntent().getExtras();
       username=bundle.getString("username");


       op1=(RadioButton) rootView.findViewById(R.id.op1);
       op2=(RadioButton)rootView.findViewById(R.id.op2);
       ans2=(EditText)rootView.findViewById(R.id.ans);


       rootref= FirebaseDatabase.getInstance().getReference();

       demoref=rootref.child("feedback");

       return rootView;
    }




    @Override
    public void onClick(View v) {
        if(op1.isChecked())
        {
            response=op1.getText().toString().trim();
            demoref.child("response").setValue(response);
            //save response with username in Database
        }
        else if(op2.isChecked())
        {
            response=op2.getText().toString().trim();
            demoref.child("response").setValue(response);
            //save response with username in database
        }
        else {
            Toast.makeText(getContext(), "Please select any one option", Toast.LENGTH_LONG).show();
        }

        demoref.child("suggestions").setValue(ans2);



    }

}

class frag_feedback{

    String ans1;
    String ans2;

    public frag_feedback(){

    }
    public frag_feedback(String ans2){
        this.ans2=ans2;
    }

    public String getAns2() {
        return ans2;
    }

    public void setAns2(String ans2) {
        this.ans2 = ans2;
    }
}
