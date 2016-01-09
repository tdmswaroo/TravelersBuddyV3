package com.travelerbuddy.feri.travelersbuddy;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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


        View view = inflater.inflate(R.layout.fragment_kovcek, container, false);

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
                    dodajKovcek.setText("Ok");
                } else if ((!textAdd.getText().toString().equals("")) && (text.getVisibility() == View.INVISIBLE)) {

                    Calendar c = Calendar.getInstance();
                    int day = c.get(Calendar.DAY_OF_MONTH);
                    int month = c.get(Calendar.MONTH);
                    int year = c.get(Calendar.YEAR);
                    boolean worked = true;

                    try {
                        worked = myDBNotes.insertNovKovcek(textAdd.getText().toString(), day + "." + (month+1) + "." + year, 1);
                    } catch (Exception e) {
                        Log.d("NEKAJ JE NAROBE", "CHECK");
                    }

                    text.setVisibility(View.VISIBLE);
                    textAdd.setVisibility(View.INVISIBLE);
                    dodajKovcek.setText("+");

                    if (worked == true) {
                        Toast.makeText(getContext(), "Kovček dodan", Toast.LENGTH_LONG).show();
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

        String[] stringi = new String[kovcekLista.size()];
        for(int i = 0; i<kovcekLista.size();i++){
            stringi[i]= kovcekLista.get(i).getId()+". "+kovcekLista.get(i).getNaziv()+" "+kovcekLista.get(i).getCreatedOn();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this.getContext(),
                android.R.layout.simple_list_item_1,
                stringi);

        listView.setAdapter(adapter);
        ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();

        return view;
    }


}
