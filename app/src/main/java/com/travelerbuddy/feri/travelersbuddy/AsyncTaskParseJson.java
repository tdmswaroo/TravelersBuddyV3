package com.travelerbuddy.feri.travelersbuddy;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Klemen on 28.11.2015.
 */
public class AsyncTaskParseJson extends AsyncTask<String, String, JSONArray> {
    public interface JsonResponse {
        void jsonProcessFinished(JSONArray output);
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
        yourJsonStringUrl = "http://free.rome2rio.com/api/1.2/json/Search?key=eTflvQZt&oName="+from+"&dName="+to;

    }

    @Override
    protected JSONArray doInBackground(String... arg0) {
        JSONArray data = null;
        try {

            // instantiate our json parser
            BeriJSON jParser = new BeriJSON();

            // get json string from url


            JSONObject json = jParser.getJSONFromUrl(yourJsonStringUrl);
            data = json.getJSONArray("places");
            System.out.println("data: "+data.toString());



            // get the array of current weather

        } catch (JSONException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }

        return data;
    }



    @Override
    protected void onPostExecute(JSONArray result) {
        callback.jsonProcessFinished(result);

        //adapter = new RecyclerViewAdapter(context, feedslist);
        //mRecyclerView.setAdapter(adapter);
    }



    public void parseData(){

        feedslist = new ArrayList();
feedslist.add("nekakj");

        }




        }
