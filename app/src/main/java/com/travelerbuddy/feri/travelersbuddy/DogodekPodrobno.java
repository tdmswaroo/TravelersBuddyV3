package com.travelerbuddy.feri.travelersbuddy;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import Pomozni.DogodekItem;


public class DogodekPodrobno extends AppCompatActivity implements OnMapReadyCallback {

    private Toolbar toolbar;
    private TextView naziv;
    private TextView lokacija;
    private TextView datum;
    private TextView opis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dogodek_podrobno);
        toolbar = (Toolbar) findViewById(R.id.toolbarD);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Event details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        DogodekItem d = (DogodekItem) getIntent().getSerializableExtra("dogodek");

        naziv = (TextView) findViewById(R.id.naslovPodrobnosti);
        opis = (TextView) findViewById(R.id.opis);
        lokacija = (TextView) findViewById(R.id.textLokacijaDogodkaPod);
        datum = (TextView) findViewById(R.id.textDatumDogodkaPod);

        naziv.setText(d.getNaziv());
        if (d.getOpisDogodka().equals(null)) {
            opis.setText("No description of event");
        } else {
            opis.setText(d.getOpisDogodka());
        }
        datum.setText(d.getDatum());
        lokacija.setText(d.getLokacija());

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions().position(new LatLng(53.558, 9.927)).title("Marker"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(53.558, 9.927), 15));


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
