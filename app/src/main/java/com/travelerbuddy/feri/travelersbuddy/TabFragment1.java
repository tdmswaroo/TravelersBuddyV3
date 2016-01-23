package com.travelerbuddy.feri.travelersbuddy;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import Notes.Potovanje;
import Notes.PotovanjeAdapter;


public class TabFragment1 extends Fragment {


    ListView listViewF;
    ArrayList<Potovanje> potovanjaLista = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab_fragment1, container, false);

        listViewF = (ListView) view.findViewById(R.id.listViewFutureTrips);

        DBHandlerPotovanja potovanja = new DBHandlerPotovanja();
        potovanja.setContext(getContext());

        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);

        potovanjaLista = new ArrayList<>();

        Cursor c = potovanja.getAllPotovanja();
        Potovanje p = null;
        if (c .moveToFirst()) {
            while (c.isAfterLast() == false) {
                p = new Potovanje();

                String string = c.getString(c.getColumnIndex("datumOdhoda"));
                String today = day +"."+ month +"."+ year;
                DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                Date date = null;
                Date today_date = null;
                try {
                    date = format.parse(string);
                    today_date = format.parse(today);
                }catch(Exception e){
                    e.printStackTrace();
                }
                System.out.println(date);
                System.out.println(today_date);

                //int res = date.compareTo(today_date);
                if(date.after(today_date)) {//morem zrihtat še da se pravilno izpisujejo glede na datum
                    p.setId(Integer.parseInt(c.getString(c.getColumnIndex("ID"))));
                    p.setPotovanjeOD(c.getString(c.getColumnIndex("potovanjeOd")));
                    p.setPotovanjeDO(c.getString(c.getColumnIndex("potovanjeDo")));
                    p.setDatumOdhoda(c.getString(c.getColumnIndex("datumOdhoda")));
                    p.setCasPotovanja(c.getString(c.getColumnIndex("casPotovanja")));
                    p.setTipPrevoza(c.getString(c.getColumnIndex("tipPrevoza")));
                    potovanjaLista.add(p);
                }

                Log.d("Potovanje: " + p, "GET CHECK");

                c.moveToNext();
            }
        }
        potovanja.dbConnector.closeConnection();

        listViewF.setAdapter(new PotovanjeAdapter(view.getContext(), potovanjaLista));



        return view;
    }


}
