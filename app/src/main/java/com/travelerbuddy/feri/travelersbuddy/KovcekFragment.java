package com.travelerbuddy.feri.travelersbuddy;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import Notes.Kovcek;


public class KovcekFragment extends Fragment {

    Button dodajKovcek;
    TextView text;
    EditText textAdd;
    ListView listView;
    ArrayAdapter<Kovcek> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View view = inflater.inflate(R.layout.fragment_kovcek, container, false);


        DBConnector connect = new DBConnector(this.getContext());
        final DBHandlerNotes myDBNotes = new DBHandlerNotes();
        myDBNotes.setContext(this.getContext());

        //final DBHandlerPotovanja myDBPotovanja = new DBHandlerPotovanja(this.getContext());
        //myDBPotovanja.setContext(this.getContext());

        text = (TextView)view.findViewById(R.id.dodajNoviKovcekText);
        textAdd = (EditText)view.findViewById(R.id.dodajKovcekEdit);

        dodajKovcek = (Button)view.findViewById(R.id.dodajNoviKovcekButton);
        dodajKovcek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Sem v on clicku", "CHECK");
                if (text.getVisibility() == View.VISIBLE) {
                    text.setVisibility(View.INVISIBLE);
                    textAdd.setVisibility(View.VISIBLE);
                    dodajKovcek.setText("Add");
                    dodajKovcek.setTextSize(16);
                } else if ((!textAdd.getText().toString().equals("")) && (text.getVisibility() == View.INVISIBLE)) {

                    Calendar c = Calendar.getInstance();
                    int day = c.get(Calendar.DAY_OF_MONTH);
                    int month = c.get(Calendar.MONTH);
                    int year = c.get(Calendar.YEAR);
                    boolean worked = false;
                    try {
                        worked = myDBNotes.insertNovKovcek(textAdd.getText().toString(), day + "." + (month+1) + "." + year, 1);
                    } catch (Exception e) {
                        Log.d("NEKAJ JE NAROBE", "CHECK");
                    }

                    text.setVisibility(View.VISIBLE);
                    textAdd.setVisibility(View.INVISIBLE);
                    dodajKovcek.setText("+");

                    if (worked == true) {
                        Snackbar.make(view, "Suitcase added.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        KovcekFragment.this.refresh(new KovcekFragment());
                    }
                }

            }
        });

        final ArrayList<Kovcek> kovcekLista = new ArrayList<>();
        Cursor c = myDBNotes.getAllNotes();

        if (c .moveToFirst()) {
            while (c.isAfterLast() == false) {
                Kovcek kovcek = new Kovcek();
                kovcek.setId(Integer.parseInt(c.getString(c.getColumnIndex("IDKOVCEK"))));
                kovcek.setNaziv(c.getString(c.getColumnIndex("naziv")));
                kovcek.setCreatedOn(c.getString(c.getColumnIndex("createdOn")));
                kovcek.setIdPotovanja(Integer.parseInt(c.getString(c.getColumnIndex("potovanje"))));

                kovcekLista.add(kovcek);

                Log.d("Naziv kovčka: " + kovcek, "GET CHECK");

                c.moveToNext();
            }
        }
        myDBNotes.dbConnector.closeConnection();

        listView = (ListView)view.findViewById(R.id.listaKovckov);

        ArrayAdapter<Kovcek> adapter = new ArrayAdapter<Kovcek>(this.getContext(),android.R.layout.simple_list_item_1,kovcekLista);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int idKovcka = position + 1;
                Intent i = new Intent(getActivity().getApplicationContext(),kovcek_items.class);
                i.putExtra("id",String.valueOf(idKovcka));
                startActivity(i);
            }
        });

        return view;
    }

    public void refresh(Fragment refr){
        getFragmentManager().beginTransaction().replace(R.id.content_frame, refr).commit();
        System.out.println("Refreshan fragment");
    }

}
