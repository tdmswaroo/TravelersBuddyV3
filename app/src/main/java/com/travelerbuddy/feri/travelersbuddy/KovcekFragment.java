package com.travelerbuddy.feri.travelersbuddy;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import Notes.Kovcek;
import Notes.KovcekAdapter;
import Notes.KovcekPotovanje;


public class KovcekFragment extends Fragment {

    Button dodajKovcek;
    TextView text;
    EditText textAdd;
    ListView listView;
    ArrayAdapter<Kovcek> adapter;

    View celotni_view;
    DBHandlerNotes mybdNotes = null;
    private static final int DIALOG_ALERT = 10;

    private ArrayList<KovcekPotovanje> kovcekLista = null;
    DBConnector connect = new DBConnector(this.getContext());
    final DBHandlerNotes myDBNotes = new DBHandlerNotes();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View view = inflater.inflate(R.layout.fragment_kovcek, container, false);
        celotni_view = view;

        myDBNotes.setContext(this.getContext());
        mybdNotes = myDBNotes;

        //myDBNotes.deleteAllKovcekAndItems();

        kovcekLista = new ArrayList<>();
        Cursor c = myDBNotes.getAllNotes();

        if (c .moveToFirst()) {
            while (c.isAfterLast() == false) {
                KovcekPotovanje kovcek = new KovcekPotovanje();
                kovcek.setId(Integer.parseInt(c.getString(c.getColumnIndex("IDKOVCEK"))));
                kovcek.setNaziv(c.getString(c.getColumnIndex("naziv")));
                kovcek.setCreatedOn(c.getString(c.getColumnIndex("createdOn")));
                kovcek.setIdPotovanja(Integer.parseInt(c.getString(c.getColumnIndex("potovanje"))));
                kovcek.setPotovanjeOD(c.getString(c.getColumnIndex("potovanjeOd")));
                kovcek.setPotovanjeDO(c.getString(c.getColumnIndex("potovanjeDo")));
                kovcek.setDatumOdhoda(c.getString(c.getColumnIndex("datumOdhoda")));

                kovcekLista.add(kovcek);

                Log.d("Naziv kovčka: " + kovcek.getNaziv() + ", " + kovcek.getIdPotovanja() + ", " + kovcek.getPotovanjeOD()+ " " + kovcek.getPotovanjeDO(), "GET CHECK");

                c.moveToNext();
            }
        }

        listView = (ListView)view.findViewById(R.id.listaKovckov);

        listView.setAdapter(new KovcekAdapter(view.getContext(), kovcekLista));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int idKovcka = position + 1;
                Cursor c = myDBNotes.getNoteById(idKovcka);
                Kovcek kovcek = new Kovcek();;
                if (c .moveToFirst()) {
                    while (c.isAfterLast() == false) {
                        kovcek.setId(Integer.parseInt(c.getString(c.getColumnIndex("IDKOVCEK"))));
                        kovcek.setNaziv(c.getString(c.getColumnIndex("naziv")));
                        kovcek.setCreatedOn(c.getString(c.getColumnIndex("createdOn")));
                        kovcek.setIdPotovanja(Integer.parseInt(c.getString(c.getColumnIndex("potovanje"))));

                        Log.d("Naziv kovčka za id: " + kovcek, "GET CHECK");
                        break;
                    }
                }
                myDBNotes.dbConnector.closeConnection();
                Intent i = new Intent(getActivity().getApplicationContext(),kovcek_items.class);
                i.putExtra("id",String.valueOf(idKovcka));
                i.putExtra("nazivKovcka", kovcek.getNaziv());
                startActivity(i);
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_itemdetail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.action_add:
                Log.d("Sem v on clicku", "CHECK");

                FragmentManager fm = getFragmentManager();
                dialog dialogFragment = new dialog ();
                dialogFragment.show(fm, "Sample Fragment");


                Snackbar.make(celotni_view, "True on menu item!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //KovcekFragment.this.refresh(new KovcekFragment());
                return true;
            default:

                return super.onOptionsItemSelected(item);
        }

    }

    public void refresh(Fragment refr){
        getFragmentManager().beginTransaction().replace(R.id.content_frame, refr).commit();
        System.out.println("Refreshan fragment");
    }

}
