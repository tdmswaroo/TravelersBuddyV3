package com.travelerbuddy.feri.travelersbuddy;


import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;

import Notes.Kovcek;
import Notes.Potovanje;

public class dialog extends DialogFragment {

    private Spinner sp;
    private Potovanje potovanje = null;
    final DBHandlerPotovanja myDBpotovanja = new DBHandlerPotovanja();
    final static DBHandlerNotes myDBnotes = new DBHandlerNotes();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_dialog, container, false);
        myDBpotovanja.setContext(rootView.getContext());

        getDialog().setTitle("Adding a new suitcase");

        sp = (Spinner) rootView.findViewById(R.id.potovanja);

        final ArrayList<Potovanje> kovcekLista = new ArrayList<>();

        Cursor c = myDBpotovanja.getAllPotovanja();

        if (c .moveToFirst()) {
            while (c.isAfterLast() == false) {
                Potovanje p = new Potovanje();
                p.setId(Integer.parseInt(c.getString(c.getColumnIndex("ID"))));
                p.setPotovanjeOD(c.getString(c.getColumnIndex("potovanjeOd")));
                p.setPotovanjeDO(c.getString(c.getColumnIndex("potovanjeDo")));
                p.setDatumOdhoda(c.getString(c.getColumnIndex("datumOdhoda")));
                p.setCasPotovanja(c.getString(c.getColumnIndex("casPotovanja")));
                p.setTipPrevoza(c.getString(c.getColumnIndex("tipPrevoza")));

                kovcekLista.add(p);
                Log.d("Naziv kovčka: " + p, "GET CHECK");

                c.moveToNext();
            }
        }
        myDBpotovanja.dbConnector.closeConnection();

        ArrayAdapter<Potovanje> dataAdapter = new ArrayAdapter<Potovanje>(rootView.getContext(),
                android.R.layout.simple_spinner_item, kovcekLista);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp.setAdapter(dataAdapter);

        Button insert = (Button) rootView.findViewById(R.id.insert_suitcase);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myDBnotes.setContext(v.getContext());

                Object item1 = sp.getSelectedItem();
                System.out.println(item1);

                int id = Integer.parseInt(item1.toString().substring(0,1));

                Cursor c1 = myDBpotovanja.getTripById(id);
                Potovanje p = new Potovanje();
                if (c1 .moveToFirst()) {
                    while (c1.isAfterLast() == false) {
                        p.setId(Integer.parseInt(c1.getString(c1.getColumnIndex("ID"))));
                        p.setPotovanjeOD(c1.getString(c1.getColumnIndex("potovanjeOd")));
                        p.setPotovanjeDO(c1.getString(c1.getColumnIndex("potovanjeDo")));
                        p.setDatumOdhoda(c1.getString(c1.getColumnIndex("datumOdhoda")));
                        p.setCasPotovanja(c1.getString(c1.getColumnIndex("casPotovanja")));
                        p.setTipPrevoza(c1.getString(c1.getColumnIndex("tipPrevoza")));

                        kovcekLista.add(p);
                        Log.d("Naziv kovčka: " + p, "GET CHECK");

                        c1.moveToNext();
                    }
                }
                myDBpotovanja.dbConnector.closeConnection();

                EditText input = (EditText) rootView.findViewById(R.id.typeNameOfSuitcase);

                Kovcek kovcek = new Kovcek();
                kovcek.setNaziv(input.getText().toString());

                Calendar c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                kovcek.setCreatedOn(day + "." + (month + 1) + "." + year);
                kovcek.setIdPotovanja(p.getId());
                Log.d("NEKAJ JE NAROBE", kovcek.toString());

                boolean worked = false;
                Log.d("Check", kovcek.getNaziv() + " " + kovcek.getCreatedOn() + " " + kovcek.getIdPotovanja());
                try {
                    worked = insert(kovcek.getNaziv(),kovcek.getCreatedOn(), kovcek.getIdPotovanja());
                } catch (Exception e) {
                    Log.d("NEKAJ JE NAROBE", "CHECK");
                }

                if (worked == true) {
                    Snackbar.make(v, "Suitcase added.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    //KovcekFragment.this.refresh(new KovcekFragment());
                }
                dismiss();

            }
        });

        Button dismiss = (Button) rootView.findViewById(R.id.dismiss);
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return rootView;
    }

    private static boolean insert(String naziv, String created, int idPotovanja){
        boolean worked = false;
        try {
            worked = myDBnotes.insertNovKovcek(naziv, created, idPotovanja);
        } catch (Exception e) {
            Log.d("NEKAJ JE NAROBE", "CHECK");
        }
        return worked;
    }

}
