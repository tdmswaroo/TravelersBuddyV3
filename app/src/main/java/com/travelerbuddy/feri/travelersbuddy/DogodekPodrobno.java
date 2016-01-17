package com.travelerbuddy.feri.travelersbuddy;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import Pomozni.DogodekItem;


public class DogodekPodrobno extends AppCompatActivity implements OnMapReadyCallback, LocationListener {

    private Toolbar toolbar;
    private TextView naziv;
    private TextView lokacija;
    private TextView datum;
    private TextView povezavaDoDogodka;
    private double latitudeDogodka = 0;
    private double longitudeDogodka = 0;
    private GoogleMap map;
    private MarkerOptions mp;


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
        lokacija = (TextView) findViewById(R.id.textLokacijaDogodkaPod);
        datum = (TextView) findViewById(R.id.textDatumDogodkaPod);
        povezavaDoDogodka = (TextView) findViewById(R.id.povezavaDogodka);

        naziv.setText(d.getNaziv());
        datum.setText(d.getDatum());
        lokacija.setText(d.getLokacija());

        povezavaDoDogodka.setClickable(true);
        povezavaDoDogodka.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "<a href='" + d.getPovezavaDoDogodka() + "'> Link to event </a>";
        povezavaDoDogodka.setText(Html.fromHtml(text));

        latitudeDogodka = Double.parseDouble(d.getLatitude());
        longitudeDogodka = Double.parseDouble(d.getLongitude());


        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

       LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (lm != null) {

            try {
                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

            }catch (SecurityException e) {
            }

        }


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.addMarker(new MarkerOptions().position(new LatLng(latitudeDogodka, longitudeDogodka)).title("Event venue"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitudeDogodka, longitudeDogodka), 10));


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onLocationChanged(Location location) {
        map.clear();
        map.addMarker(new MarkerOptions().position(new LatLng(latitudeDogodka, longitudeDogodka)).title("Event venue"));
        map.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude()))
                .title("Your position")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
