package com.example.parij.myschoolcomm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by parij on 23/12/2017.
 */

public class tuesday extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_tuesday);
        //this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_close);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

       // getSupportActionBar().setTitle("Authorized to Collect");
        //toolbar.setNavigationIcon(R.drawable.parentbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
