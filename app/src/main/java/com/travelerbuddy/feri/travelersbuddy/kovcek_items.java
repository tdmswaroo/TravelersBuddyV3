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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import Notes.Kovcek;
import Notes.KovcekItem;
import Notes.KovcekItemAdapter;
import Pomozni.DogodekItem;

public class kovcek_items extends AppCompatActivity {//implements android.widget.CompoundButton.OnCheckedChangeListener{

    private static final int DIALOG_ALERT = 10;

    private Toolbar toolbar;
    private ListView listView;
    ArrayList<DogodekItem> seznam = new ArrayList<DogodekItem>();
    private Context context = this;

    Button alertDialog;
    EditText input = null;
    private String dialog_Text = "";

    ArrayAdapter<KovcekItem> adapter = null;

    DBConnector connect = new DBConnector(this);
    final DBHandlerNotes myDBNotes = new DBHandlerNotes();

    KovcekItemAdapter dataAdapter = null;
    ArrayList<KovcekItem> kovcekItemsLista = null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kovcek_items);

        toolbar = (Toolbar) findViewById(R.id.toolbarD);
        setSupportActionBar(toolbar);

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
                break;
            }
        }

        getSupportActionBar().setTitle("Add items to: " + kovcek.getNaziv());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);



        //*******************************************************************************************************



        Cursor c1 = myDBNotes.getAllItemsZaKovcek(Integer.parseInt(getIntent().getStringExtra("id")));

        this.displayListView(c1);

        myDBNotes.dbConnector.closeConnection();

        alertDialog = (Button)findViewById(R.id.dodajNoviItemButton);
        alertDialog.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DIALOG_ALERT);
            }
        });

    }

    private void displayListView(Cursor c){
        kovcekItemsLista = new ArrayList<KovcekItem>();
        KovcekItem item = null;
        if (c.moveToFirst()) {
            while (c.isAfterLast() == false) {
                item = new KovcekItem();
                item.setId(Integer.parseInt(c.getString(c.getColumnIndex("IDITEM"))));
                item.setVsebina(c.getString(c.getColumnIndex("vsebina")));
                item.setSelected(Boolean.valueOf(c.getString(c.getColumnIndex("checked"))));
                item.setIdKovcka(Integer.parseInt(c.getString(c.getColumnIndex("kovcek"))));

                kovcekItemsLista.add(item);
                Log.d("Naziv itema: " + item, "GET CHECK");
                c.moveToNext();
            }
        }
        dataAdapter = new KovcekItemAdapter(this,R.layout.item_vrstica, kovcekItemsLista);
        ListView listView = (ListView) findViewById(R.id.listViewItems);
        listView.setAdapter(dataAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                KovcekItem country = (KovcekItem) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),
                        "Clicked on Row: " + country.getVsebina(),
                        Toast.LENGTH_LONG).show();
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

            boolean res = false;
            try {
                res = myDBNotes.insertNovItem(dialog_Text, "false", Integer.parseInt(getIntent().getStringExtra("id")));
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
                myDBNotes.dbConnector.closeConnection();
            }
        }
    }



}

