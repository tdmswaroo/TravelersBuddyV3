package com.travelerbuddy.feri.travelersbuddy;


import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Pomozni.Vreme;


public class VremeFragment extends Fragment {

    private ArrayList<Vreme> seznam = new ArrayList<Vreme>();

    private TextView prikazStopinj;
    private ImageView prikazSlikeVremena;
    private TextView prikazMaxTemp;
    private TextView prikazMinTemp;
    private TextView prikazOpisVremena;
    private TextView prikazVlage;
    private TextView prikazHitrostiVetra;
    private TextView prikazSmerVetra;
    private ImageView ikonaVlaga;
    private ImageView ikonaSmerV;
    private String city;
    private ImageView ikonaHitrostV;
    private Context contex = getActivity();
    private TextView temp1;
    private TextView temp2;
    private TextView temp3;
    private TextView temp4;
    private TextView day1;
    private TextView day2;
    private TextView day3;
    private TextView day4;
    private ImageView napoved1;
    private ImageView napoved2;
    private ImageView napoved3;
    private ImageView napoved4;


    public VremeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vreme, container, false);

        SharedPreferences sharedPreferences = getActivity().getPreferences(getActivity().MODE_PRIVATE);
        city = sharedPreferences.getString("cityWeather", "City").replaceAll(" ", "");


        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(Character.toUpperCase(city.charAt(0)) + city.substring(1));

        temp1 = (TextView) view.findViewById(R.id.temp1);
        temp2 = (TextView) view.findViewById(R.id.temp2);
        temp3 = (TextView) view.findViewById(R.id.temp3);
        temp4 = (TextView) view.findViewById(R.id.temp4);

        day1 = (TextView) view.findViewById(R.id.day1);
        day2 = (TextView) view.findViewById(R.id.day2);
        day3 = (TextView) view.findViewById(R.id.day3);
        day4 = (TextView) view.findViewById(R.id.day4);

        napoved1 = (ImageView) view.findViewById(R.id.napoved1);
        napoved2 = (ImageView) view.findViewById(R.id.napoved2);
        napoved3 = (ImageView) view.findViewById(R.id.napoved3);
        napoved4 = (ImageView) view.findViewById(R.id.napoved4);


        prikazHitrostiVetra = (TextView) view.findViewById(R.id.hitrostVetraText);
        prikazVlage = (TextView) view.findViewById(R.id.vlagaText);
        prikazSmerVetra = (TextView) view.findViewById(R.id.smerVetraText);;

        prikazSlikeVremena = (ImageView) view.findViewById(R.id.slikaVremena);
        prikazStopinj = (TextView) view.findViewById(R.id.textViewStopinje);

        prikazMaxTemp = (TextView) view.findViewById(R.id.maxStopinj);
        prikazMinTemp = (TextView) view.findViewById(R.id.minStopinj);
        prikazOpisVremena = (TextView) view.findViewById(R.id.vremeOpisText);

        ikonaSmerV = (ImageView) view.findViewById(R.id.smerVetraIkona);
        ikonaVlaga = (ImageView) view.findViewById(R.id.vlagaIkona);
        ikonaHitrostV = (ImageView) view.findViewById(R.id.hitrostVetraIkona);

        if (city == "City") {

            FragmentManager fm = getFragmentManager();
            DialogSearchCityWeather d = new DialogSearchCityWeather();
            d.show(fm, "Sample Fragment");

        } else {


                String url = "http://api.openweathermap.org/data/2.5/forecast/daily?q="+city+"&mode=json&units=metric&cnt=5&appid=66ac2b011ad65b8193da78b4a82d5f5e";
                new PrikazJSON().execute(new String[]{url});

        }

        return view;
    }

    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_vreme, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.search_city:

                FragmentManager fm = getFragmentManager();
                DialogSearchCityWeather d = new DialogSearchCityWeather();
                d.show(fm, "Sample Fragment");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }


    private class PrikazJSON extends AsyncTask<String, Void, ArrayList<Vreme>> {

        @Override
        protected ArrayList<Vreme> doInBackground(String... urlStr) {
            StringBuilder sb = new StringBuilder();

            try {

                URL url = new URL(urlStr[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                if (conn.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : "
                            + conn.getResponseCode());
                }

                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));

                String output;

                while ((output = br.readLine()) != null) {
                    sb.append(output);
                }

                conn.disconnect();

            } catch (MalformedURLException e) {

                e.printStackTrace();

            } catch (IOException e) {

                e.printStackTrace();

            }

            try {

                JSONObject jsonObject = new JSONObject(sb.toString());


                JSONArray list = (JSONArray) jsonObject.get("list");

                long datum = 0;
                String ikona = "";
                String opis = "";
                for (int i = 0; i < list.length(); i++) {

                    datum = list.getJSONObject(i).getLong("dt");
                    int vlaga = list.getJSONObject(i).getInt("humidity");

                    double hitrostVetra = list.getJSONObject(i).getInt("speed");
                    String hitrostV = Math.round(hitrostVetra) + "";

                    int smerVetra = list.getJSONObject(i).getInt("deg");
                    String smerV = izracunajSmer(smerVetra);

                    JSONObject temp = list.getJSONObject(i).getJSONObject("temp");
                    double t = temp.getDouble("day");
                    String temperatura = Math.round(t) + "°";

                    double maxT = temp.getDouble("max");
                    String maxTmperatura = Math.round(maxT) + "°";

                    double minT = temp.getDouble("min");
                    String minTemperatura = Math.round(minT) + "°";

                    JSONArray a = list.getJSONObject(i).getJSONArray("weather");
                    for (int j = 0; j < a.length(); j++) {
                        ikona = a.getJSONObject(j).getString("icon");

                        opis = a.getJSONObject(j).getString("description");

                    }

                    seznam.add(new Vreme(temperatura,datum,ikona, maxTmperatura, minTemperatura, opis, "" + vlaga, hitrostV, smerV));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return seznam;
        }

        @Override
        protected void onPostExecute(ArrayList<Vreme> vremes) {

                Vreme v = seznam.get(0);

                prikazStopinj.setText(v.getTemp());
                prikazSlikeVremena.setImageResource(dodajIkono(v.getIkona()));

                prikazMinTemp.setText(v.getMinTemp());
                prikazMaxTemp.setText(v.getMaxTemp());
                prikazOpisVremena.setText(v.getOpis());

                ikonaVlaga.setImageResource(R.mipmap.raindrop2);
                prikazVlage.setText(v.getVlaga() + "%");
                ikonaHitrostV.setImageResource(R.mipmap.winds4);
                prikazHitrostiVetra.setText(v.getHitrostVetra() + "MPS");
                ikonaSmerV.setImageResource(R.mipmap.compass49);
                prikazSmerVetra.setText(v.getSmerVetra());

                temp1.setText(seznam.get(1).getTemp());
                temp2.setText(seznam.get(2).getTemp());
                temp3.setText(seznam.get(3).getTemp());
                temp4.setText(seznam.get(4).getTemp());

                Calendar c = Calendar.getInstance();
                Date date = new Date(seznam.get(1).getDatum() * 1000);
                c.setTime(date);
                SimpleDateFormat sdf = new SimpleDateFormat("EE");

                day1.setText(prevedi(sdf.format(date)));

                date = new Date(seznam.get(2).getDatum() * 1000);
                c.setTime(date);
                day2.setText(prevedi(sdf.format(date)));

                date = new Date(seznam.get(3).getDatum() * 1000);
                c.setTime(date);
                day3.setText(prevedi(sdf.format(date)));

                date = new Date(seznam.get(4).getDatum() * 1000);
                c.setTime(date);
                day4.setText(prevedi(sdf.format(date)));

                napoved1.setImageResource(dodajIkonoNapoved(seznam.get(1).getIkona()));
                napoved2.setImageResource(dodajIkonoNapoved(seznam.get(2).getIkona()));
                napoved3.setImageResource(dodajIkonoNapoved(seznam.get(3).getIkona()));
                napoved4.setImageResource(dodajIkonoNapoved(seznam.get(4).getIkona()));


        }
    }

    public int dodajIkono(String ikona){

        if(ikona.matches("01d")){
            return R.mipmap.ic_weather_sunny_white_36dp;
        }else if(ikona.matches("01n")){
            return R.mipmap.ic_weather_night_white_48dp;
        }else if(ikona.matches("02d") || ikona.matches("02n")){
            return R.mipmap.ic_weather_partlycloudy_white_48dp;
        }else if(ikona.matches("03d") || ikona.matches("03n") || ikona.matches("04d") || ikona.matches("04n")){
            return R.mipmap.ic_weather_cloudy_white_48dp;
        }else if(ikona.matches("09d") || ikona.matches("09n")){
            return R.mipmap.ic_weather_pouring_white_48dp;
        }else if(ikona.matches("10d") || ikona.matches("10n")){
            return R.mipmap.ic_weather_rainy_white_48dp;
        }else if(ikona.matches("11d") || ikona.matches("11n")){
            return R.mipmap.ic_weather_lightning_white_48dp;
        }else if(ikona.matches("13d") || ikona.matches("13n")){
            return R.mipmap.ic_weather_snowy_white_48dp;
        }else{
            return R.mipmap.ic_weather_fog_white_48dp;
        }

    }

    public int dodajIkonoNapoved(String ikona){

        if(ikona.matches("01d")){
            return R.mipmap.sonce;
        }else if(ikona.matches("01n")){
            return R.mipmap.sonce;
        }else if(ikona.matches("02d") || ikona.matches("02n")){
            return R.mipmap.partlycoudcolor;
        }else if(ikona.matches("03d") || ikona.matches("03n") || ikona.matches("04d") || ikona.matches("04n")){
            return R.mipmap.oblaki;
        }else if(ikona.matches("09d") || ikona.matches("09n")){
            return R.mipmap.dezz;
        }else if(ikona.matches("10d") || ikona.matches("10n")){
            return R.mipmap.dez;
        }else if(ikona.matches("11d") || ikona.matches("11n")){
            return R.mipmap.strela;
        }else if(ikona.matches("13d") || ikona.matches("13n")){
            return R.mipmap.sneg;
        }else{
            return R.mipmap.oblaki;
        }

    }

    public String izracunajSmer(int smerVetra) {

        if(smerVetra == 0 || smerVetra == 360){
            return "North";
        }else if(smerVetra == 180){
            return "South";
        }else if(smerVetra == 90){
            return "East";
        }else if(smerVetra == 270){
            return "West";
        }else if(smerVetra > 0 && smerVetra < 95){
            return "Northeast";
        }else if(smerVetra > 90 && smerVetra < 180){
            return "Southeast";
        }else if(smerVetra > 180 && smerVetra < 270){
            return "Southwest";
        }else if(smerVetra > 270 && smerVetra < 360){
            return "Northwest";
        }

        return "";
    }

    public String prevedi(String kratica){
        switch (kratica) {

            case "pon.":
                return "Mon";
            case "tor.":
                return "Tue";
            case "sre.":
                return "Wed";
            case "čet.":
                return "Thu";
            case "pet.":
                return "Fri";
            case "sob.":
                return "Sat";
            case "ned.":
                return "Sun";
            default:
                return kratica;

        }
    }

/*
    private class TestirajPovezavo extends AsyncTask<Void, Void, Boolean> {
        boolean b = false;

        @Override
        protected Boolean doInBackground(Void... params) {
            povezava = hasActiveInternetConnection(contex);
            if(povezava == true)
                b = true;

            return povezava;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            Log.d("BOOL",""+b);
            povezava = b;

        }
    }

        public boolean hasActiveInternetConnection(Context context) {
        if (isNetworkAvailable()) {
            try {
                HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
                urlc.setRequestProperty("User-Agent", "Test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500);
                urlc.connect();
                return (urlc.getResponseCode() == 200);
            } catch (IOException e) {
            }
        } else {
        }
        return false;
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }*/
}