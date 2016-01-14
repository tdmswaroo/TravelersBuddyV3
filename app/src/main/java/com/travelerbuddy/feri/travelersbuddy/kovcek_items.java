package com.travelerbuddy.feri.travelersbuddy;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import Notes.Kovcek;
import Notes.KovcekItem;
import Pomozni.DogodekItem;

public class kovcek_items extends AppCompatActivity {

    private static final int DIALOG_ALERT = 10;

    private Toolbar toolbar;
    private ListView listView;
    ArrayList<DogodekItem> seznam = new ArrayList<DogodekItem>();
    private Context context = this;

    Button alertDialog;
    EditText input = null;
    private String dialog_Text = "";

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

        getSupportActionBar().setTitle("Add items to: " + kovcek.getNaziv());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        final ArrayList<KovcekItem> kovcekItemsLista = new ArrayList<>();
        Cursor c1 = myDBNotes.getAllItemsZaKovcek(Integer.parseInt(getIntent().getStringExtra("id")));

        if (c1 .moveToFirst()) {
            while (c1.isAfterLast() == false) {
                KovcekItem item = new KovcekItem();
                item.setId(Integer.parseInt(c1.getString(c1.getColumnIndex("IDITEM"))));
                item.setVsebina(c1.getString(c1.getColumnIndex("vsebina")));
                item.setChecked(c1.getString(c1.getColumnIndex("checked")));
                item.setIdKovcka(Integer.parseInt(c1.getString(c1.getColumnIndex("kovcek"))));

                kovcekItemsLista.add(item);
                System.out.println(item);

                Log.d("Naziv kovčka: " + kovcek, "GET CHECK");

                c.moveToNext();
            }
        }
        myDBNotes.dbConnector.closeConnection();
        try {
            listView = (ListView) findViewById(R.id.listViewItems);
            ArrayAdapter<KovcekItem> adapter = new ArrayAdapter<KovcekItem>(this, android.R.layout.simple_list_item_1, kovcekItemsLista);
            listView.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
        }

        alertDialog = (Button)findViewById(R.id.dodajNoviItemButton);
        alertDialog.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DIALOG_ALERT);
            }
        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_ALERT:
                Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(true);
                builder.setTitle("Insert content of the item");

                input = new EditText(this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("Insert", new OkOnClickListener());
                builder.setNegativeButton("Cancel", new CancelOnClickListener());
                AlertDialog dialog = builder.create();
                dialog.show();
        }
        return super.onCreateDialog(id);
    }

    private final class CancelOnClickListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            Snackbar.make(findViewById(android.R.id.content), "Insert canceled.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    private final class OkOnClickListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {

            String dialog_Text = input.getText().toString();

            DBHandlerNotes db = new DBHandlerNotes();
            boolean res = false;
            try {
                res = db.insertNovItem("NEKAJ", "false", Integer.parseInt(getIntent().getStringExtra("id")));
                if(res == true) {
                    Snackbar.make(findViewById(android.R.id.content), "Item inserted.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else{
                    Snackbar.make(findViewById(android.R.id.content), "Sory something went wrong.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            } catch (Exception e) {
                Log.d("NEKAJ JE NAROBE", "CHECK");
            }finally {
                //dbConnector.closeConnection();
            }
        }
    }



}
