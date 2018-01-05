package com.example.parij.myschoolcomm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class Timetable extends AppCompatActivity {
    private Spinner Day_selection;
    private Button timetable_disp;
    private TextView subject1;
    private TextView subject2;
    private TextView subject3;
    private TextView subject4;
    private ListView listdisp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        intialise();

        timetable_disp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TT_display();
                holidayDisplay();
            }
        });
    }

    void intialise()
    {
       // Day_selection=(Spinner)findViewById(R.id.day);
        //timetable_disp=(Button)findViewById(R.id.btn_select);
        //subject1=(TextView)findViewById(R.id.sub1);
        //subject2=(TextView)findViewById(R.id.sub2);
        //subject3=(TextView)findViewById(R.id.sub3);
       // subject4=(TextView)findViewById(R.id.sub4);
       // listdisp=(ListView)findViewById(R.id.listdisplay);
    }

    void holidayDisplay()
    {
        String holidayJan[]=new String[]{"15-jan:School Function","26-jan:Republic Day","30-jan:Founders day"};
        ArrayList<String> holiday=new ArrayList<String>();
        holiday.addAll(Arrays.asList(holidayJan));
        //holiday=new ArrayAdapter<String>(this,R.layout.simplerow)
    }
    void TT_display()
    {
        String in=Day_selection.getSelectedItem().toString();
        switch(in)
        {
            case "Monday":
                subject1.setText("Maths");
                subject2.setText("English");
                subject3.setText("Drawing");
                subject4.setText("Science");
                break;
            case "Tuesday":
                subject1.setText("English");
                subject2.setText("Maths");
                subject3.setText("Science");
                subject4.setText("Drawing");
                break;
        }
    }
}
