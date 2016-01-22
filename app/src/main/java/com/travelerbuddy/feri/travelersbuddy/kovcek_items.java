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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import Notes.KovcekItem;
import Notes.KovcekItemAdapter;
import Pomozni.DogodekItem;

public class kovcek_items extends AppCompatActivity {//implements android.widget.CompoundButton.OnCheckedChangeListener{

    private static final int DIALOG_ALERT = 10;
    private static final int DIALOG_ALERT1 = 20;

    private Toolbar toolbar;
    private ListView listView;
    ArrayList<DogodekItem> seznam = new ArrayList<DogodekItem>();
    private Context context = this;

    Button alertDialog;
    EditText input = null;
    EditText input1 = null;

    private String dialog_Text = "";

    ArrayAdapter<KovcekItem> adapter = null;

    DBConnector connect = new DBConnector(this);
    final DBHandlerNotes myDBNotes = new DBHandlerNotes();

    KovcekItemAdapter dataAdapter = null;
    ArrayList<KovcekItem> kovcekItemsLista = null;

    private String vsebina;
    private int ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kovcek_items);

        toolbar = (Toolbar) findViewById(R.id.toolbarD);
        setSupportActionBar(toolbar);

        myDBNotes.setContext(this);

        String idKovcka = getIntent().getStringExtra("id");
        String naziv = getIntent().getStringExtra("nazivKovcka");
        System.out.println(idKovcka);


        getSupportActionBar().setTitle(naziv);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        //*******************************************************************************************************



        Cursor c1 = myDBNotes.getAllItemsZaKovcek(Integer.parseInt(getIntent().getStringExtra("id")));

        this.displayListView(c1);

        myDBNotes.dbConnector.closeConnection();
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

                ID = country.getId();
                vsebina = country.getVsebina();

                showDialog(DIALOG_ALERT1);

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
                break;
            case DIALOG_ALERT1:
                Builder builder1 = new AlertDialog.Builder(this);
                builder1.setCancelable(true);
                builder1.setTitle("Update content of the item");

                input1 = new EditText(this);
                input1.setInputType(InputType.TYPE_CLASS_TEXT);
                input1.setText(vsebina);
                builder1.setView(input1);

                builder1.setPositiveButton("Update", new UpdateOnClickListener());
                builder1.setNegativeButton("Cancel", new CancelOnClickListener());
                AlertDialog dialog1 = builder1.create();
                dialog1.show();
        }
        return super.onCreateDialog(id);
    }

    private final class UpdateOnClickListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {

            String dialog_Text = input1.getText().toString();
            System.out.print(dialog_Text);
            if (dialog_Text == null || dialog_Text.equals("")) {
                Snackbar.make(findViewById(android.R.id.content), "Please insert some content before you try to update an item.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } else {
                long res = 0;
                try {
                    res = myDBNotes.updateItem(ID, dialog_Text);
                    if (res == 1) {
                        Snackbar.make(findViewById(android.R.id.content), "Item updated.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else {
                        Snackbar.make(findViewById(android.R.id.content), "Sory something went wrong.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                } catch (Exception e) {
                    Log.d("NEKAJ JE NAROBE", "CHECK");
                } finally {
                    Cursor c1 = myDBNotes.getAllItemsZaKovcek(Integer.parseInt(getIntent().getStringExtra("id")));
                    displayListView(c1);
                    myDBNotes.dbConnector.closeConnection();
                }
            }
        }
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
            System.out.print(dialog_Text);
            if (dialog_Text == null || dialog_Text.equals("")) {
                Snackbar.make(findViewById(android.R.id.content), "Please insert some content before you try to insert an item.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } else {
                boolean res = false;
                try {
                    res = myDBNotes.insertNovItem(dialog_Text, "false", Integer.parseInt(getIntent().getStringExtra("id")));
                    if (res == true) {
                        Snackbar.make(findViewById(android.R.id.content), "Item inserted.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else {
                        Snackbar.make(findViewById(android.R.id.content), "Sory something went wrong.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                } catch (Exception e) {
                    Log.d("NEKAJ JE NAROBE", "CHECK");
                } finally {
                    Cursor c1 = myDBNotes.getAllItemsZaKovcek(Integer.parseInt(getIntent().getStringExtra("id")));
                    displayListView(c1);
                    myDBNotes.dbConnector.closeConnection();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_itemdetail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }else if(id == R.id.action_add){
            showDialog(DIALOG_ALERT);
        }
        return super.onOptionsItemSelected(menuItem);
    }

}

