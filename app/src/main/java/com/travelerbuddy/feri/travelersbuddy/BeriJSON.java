package com.travelerbuddy.feri.travelersbuddy;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Klemen on 26.11.2015.
 */
public class BeriJSON {

    final String TAG = "JsonParser.java";

    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";

    public JSONObject getJSONFromUrl(String url) throws IOException {

        URL u = null;
        try {
            u = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) u.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStream in = new BufferedInputStream(urlConnection.getInputStream());

        try {
            return  readStream(in);

        }finally {
            in.close();
        }


    }

    private JSONObject readStream(InputStream in) {

        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            in.close();
            json = sb.toString();

        } catch (Exception e) {
            Log.e(TAG, "Error converting result " + e.toString());
        }

        // try parse the string to a JSON object
       try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e(TAG, "Error parsing data " + e.toString());
        }

        // return JSON String
        return jObj;
    }


}
