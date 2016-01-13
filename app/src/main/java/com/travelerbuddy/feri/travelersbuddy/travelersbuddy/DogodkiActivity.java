package com.travelerbuddy.feri.travelersbuddy.travelersbuddy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.travelerbuddy.feri.travelersbuddy.R;

public class DogodkiActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dogodki);
        toolbar = (Toolbar) findViewById(R.id.toolbarD);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Seznam dogodkov");



    }
}
