package com.travelerbuddy.feri.travelersbuddy;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import Parsing.Place;
import Parsing.Response;
import Parsing.Route;
import Parsing.Segment;

/**
 * Created by Klemen on 28.11.2015.
 */
public class AsyncTaskParseJson extends AsyncTask<String, String, Response> {
    public interface JsonResponse {
        void jsonProcessFinished(Response output);
    }


    private RecyclerViewAdapter adapter;
    public JsonResponse callback;
    final String TAG = "AsyncTaskParseJson.java";
    private String from;
    private String to;
    private RecyclerView mRecyclerView;
    // set your json string url here
    String yourJsonStringUrl ;
    // contacts JSONArray

    private List feedslist;

    public AsyncTaskParseJson(JsonResponse callback,  String from, String to) {
        this.callback = callback;
        this.from = from;
        this.to = to;
    }

    @Override
    protected void onPreExecute() {

        from = from.replaceAll("\\s+","_");
        to = to.replaceAll("\\s+","_");
        yourJsonStringUrl = "http://free.rome2rio.com/api/1.2/json/Search?key=eTflvQZt&oName="+from+"&dName="+to;

    }

    @Override
    protected Response doInBackground(String... arg0) {
        Response response = null;
        try {

            // instantiate our json parser
            BeriJSON jParser = new BeriJSON();

            // get json string from url


            //JSONObject json = jParser.getJSONFromUrl(yourJsonStringUrl);
            response = jParser.getJSONFromUrl(yourJsonStringUrl);
            //data = json.getJSONArray("places");


            prinData(response);


            // get the array of current weather


        } catch (IOException e) {

            e.printStackTrace();
        }

        return response;
    }

    private void prinData(Response response) {
        System.out.println("___________IZPIS_______________________: "+response.toString());

        System.out.println("PLACES: "+response.toString());

        for(Place p:response.getPlaces()){
            Log.d("places", "CountryCode: " + p.getCountryCode());
            Log.d("places", "Kind: "+p.getKind());
            Log.d("places", "LongName: "+p.getLongName());
            Log.d("places", "Name: "+p.getName());
            Log.d("places", "Pos: "+p.getPos());
            Log.d("places", "RegionCode: "+p.getRegionCode());

        }

        System.out.println("ROUTES: "+response.toString());

        for(Route p:response.getRoutes()){
            Log.d("routes", "Name: "+p.getName());
            Log.d("routes", "Duration: " + p.getDuration());



            Log.d("routes", "SEGMENTS");
            for (Segment s: p.getSegments()) {
                Log.d("routes", s.getIndicativePrice().toString());
                Log.d("routes", "Duration: "+s.getDuration());
                Log.d("routes", "Kind: "+s.getKind());
                Log.d("routes", "sName "+s.getsName());
                Log.d("routes", "tName: "+s.gettName());
                Log.d("routes", "URL"+s.getUrl());


            }




        }
    }


    @Override
    protected void onPostExecute(Response result) {
        callback.jsonProcessFinished(result);


    }









        }
