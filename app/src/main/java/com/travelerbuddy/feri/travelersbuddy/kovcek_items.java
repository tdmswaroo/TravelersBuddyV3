package com.travelerbuddy.feri.travelersbuddy;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import Notes.Kovcek;
import Pomozni.DogodekItem;

public class kovcek_items extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listView;
    ArrayList<DogodekItem> seznam = new ArrayList<DogodekItem>();
    private Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kovcek_items);
        toolbar = (Toolbar) findViewById(R.id.toolbarD);
        setSupportActionBar(toolbar);

        final DBHandlerNotes myDBNotes = new DBHandlerNotes();
        myDBNotes.setContext(this);

        String idKovcka = getIntent().getStringExtra("id");
        System.out.println(idKovcka);
        Cursor c = myDBNotes.getNoteById(Integer.parseInt(idKovcka));
        Kovcek kovcek = null;
        if (c .moveToFirst()) {
            while (c.isAfterLast() == false) {
                kovcek = new Kovcek();
                kovcek.setId(Integer.parseInt(c.getString(c.getColumnIndex("IDKOVCEK"))));
                kovcek.setNaziv(c.getString(c.getColumnIndex("naziv")));
                kovcek.setCreatedOn(c.getString(c.getColumnIndex("createdOn")));
                kovcek.setIdPotovanja(Integer.parseInt(c.getString(c.getColumnIndex("potovanje"))));

                Log.d("Naziv kovčka: " + kovcek, "GET CHECK");
                c.moveToNext();
            }
        }

        getSupportActionBar().setTitle("Opened suitcase: "+kovcek.getNaziv()+"");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
