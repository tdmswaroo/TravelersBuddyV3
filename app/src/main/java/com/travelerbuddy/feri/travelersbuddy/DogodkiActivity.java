package com.travelerbuddy.feri.travelersbuddy;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import Pomozni.DogodekItem;
import Pomozni.DogodkiAdapter;

public class DogodkiActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listView;
    ArrayList<DogodekItem> seznam = new ArrayList<DogodekItem>();
    private Context context = this;
int slika = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dogodki);
        toolbar = (Toolbar) findViewById(R.id.toolbarD);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Event list");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        String lokacija = getIntent().getStringExtra("lokacija");
        String mesec = getIntent().getStringExtra("mesec");
        String kategorija = getIntent().getStringExtra("kat");

        String url = "http://api.eventful.com/json/events/search?app_key=kp7d9fDLcD4FC495&location" +
                "=" + lokacija + "&date=" + mesec + "&category=" + kategorija + "&image_sizes=large&page_size=20";

        new PrikazDogodkov().execute(url, kategorija);
        listView = (ListView) findViewById(R.id.dogodkiListView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(context,DogodekPodrobno.class);
                DogodekItem d =(DogodekItem)parent.getItemAtPosition(position);
                Bundle extras = new Bundle();
                extras.putSerializable("dogodek",d);
                i.putExtras(extras);
                startActivity(i);
            }
        });
    }

    private class PrikazDogodkov extends AsyncTask<String, Void, ArrayList<DogodekItem>> {

        @Override
        protected ArrayList<DogodekItem> doInBackground(String... urlStr) {

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

            } catch (Exception e) {

                e.printStackTrace();

            }

            try {
                JSONObject jsonObject = new JSONObject(sb.toString());
                JSONObject events = (JSONObject) jsonObject.get("events");
                JSONArray listDogodkov = (JSONArray) events.get("event");

                String kat = urlStr[1];

                dolociSliko(kat);

                for(int i=0; i<listDogodkov.length(); i++){



                    seznam.add(new DogodekItem(
                            listDogodkov.getJSONObject(i).getString("title"),
                            listDogodkov.getJSONObject(i).get("venue_name").toString()+", "+listDogodkov.getJSONObject(i).getString("city_name"),
                            listDogodkov.getJSONObject(i).getString("start_time"),
                            slika,
                            listDogodkov.getJSONObject(i).get("description").toString(),
                            listDogodkov.getJSONObject(i).get("venue_address").toString(),
                            R.mipmap.koledar,
                            R.mipmap.lokac
                    ));
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return seznam;
        }


        protected void onPostExecute(ArrayList<DogodekItem> seznam) {

            listView.setAdapter(new DogodkiAdapter(context, seznam));

        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }
    public int dolociSliko(String kat) {
        switch (kat) {
            case "music":
                    slika = R.mipmap.music;
                break;
            case "conference":
                slika = R.mipmap.conference;
                break;
            case  "comedy":
                slika = R.mipmap.comedy;
                break;
            case  "family_fun_kids":
                slika = R.mipmap.fam;
                break;
            case  "movies_film":
                slika = R.mipmap.movie;
                break;
            case  "attractions":
                slika = R.mipmap.atractions;
                break;
            case  "sports":
                slika = R.mipmap.sports;
                break;
            case  "technology":
                slika = R.mipmap.tech;
                break;
            case  "other":
                slika = R.mipmap.koledar;
                break;
            case "":
                slika = R.mipmap.koledar;
                break;
            default:
                slika = R.mipmap.koledar;
                break;
        }
        return 0;
    }
}
