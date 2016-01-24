package com.travelerbuddy.feri.travelersbuddy;

import android.util.JsonReader;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import Parsing.IndicativePrice;
import Parsing.Place;
import Parsing.Response;
import Parsing.Route;
import Parsing.Segment;

/**
 * Created by Klemen on 26.11.2015.
 */
public class BeriJSON {

    final String TAG = "JsonParser.java";

    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";

    public Response getJSONFromUrl(String url) throws IOException {

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
            return  readStreamWithJsonReader(in);

        }finally {
            in.close();
        }


    }
    /*
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
    */
    public Response readStreamWithJsonReader(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readObject(reader);

        }catch(Exception e ) {
            e.printStackTrace();
            return null;
        }finally{
            reader.close();
        }

    }

    private Response readObject(JsonReader reader) throws IOException {
        ArrayList<Place> places = new ArrayList<>();
        ArrayList<Route> routes = new ArrayList<>();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("places")) {
                places = readPlaces(reader);
            } else if(name.equals("routes")){
                routes = readRoutes(reader);
            }else {
                reader.skipValue();
            }
        }
        reader.endObject();

        //returnaj response objekt
        return new Response(places, routes);
    }

    private ArrayList<Route> readRoutes(JsonReader reader) throws IOException {
        ArrayList<Route> routes  = new ArrayList();

        reader.beginArray();
        while (reader.hasNext()) {
            routes.add(readRoute(reader));
        }
        reader.endArray();
        return routes;



    }

    private Route readRoute(JsonReader reader) throws IOException {
        String name = null;
        float duration = 0;
        IndicativePrice indicativePrice = null;
        ArrayList<Segment> segments = new ArrayList<>();

        reader.beginObject();
        while (reader.hasNext()) {
            String tag = reader.nextName();
            if (tag.equals("name")) {
                name = reader.nextString();
            } else if (tag.equals("duration")) {
                duration =   Float.parseFloat(reader.nextString());
            } else if (tag.equals("indicativePrice")) {
                indicativePrice = readIndicativePrice(reader);
            } else if (tag.equals("segments")) {
                segments = readSegments(reader);
            } else{
                reader.skipValue();
            }
        }
        reader.endObject();
        return new Route(name, duration, indicativePrice, segments);
    }

    private ArrayList<Segment> readSegments(JsonReader reader) throws IOException {
        ArrayList<Segment> segments = new ArrayList();

        reader.beginArray();
        while (reader.hasNext()) {
            segments.add(readSegment(reader));
        }
        reader.endArray();

        return segments;

    }

    private Segment readSegment(JsonReader reader) throws IOException {
        String kind = null;
        float duration = 0;
        String sName = null;
        String tName = null;
        IndicativePrice indicativePrice= null;
        String url = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String tag = reader.nextName();
            if (tag.equals("kind")) {
                kind = reader.nextString();
            } else if (tag.equals("duration")) {
                duration = Float.parseFloat(reader.nextString());
            } else if (tag.equals("sName")) {
                sName = reader.nextString();
            } else if (tag.equals("tName")) {
                tName = reader.nextString();
            } else if (tag.equals("indicativePrice")) {
                indicativePrice = readIndicativePrice(reader);
            }else if (tag.equals("itineraries")) {
                url = readItinearies(reader);
            }else{
                reader.skipValue();
            }
        }
        reader.endObject();

        return new Segment(kind,duration, sName, tName, indicativePrice, url);
    }

    private String readItinearies(JsonReader reader) throws IOException {
        String url = null;

        reader.beginArray();
        while (reader.hasNext()) {
               url = readLegsArray(reader);
        }
        reader.endArray();
        return url;

    }

    private String readLegsArray(JsonReader reader) throws IOException {
        String url = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String tag = reader.nextName();
            if (tag.equals("legs")) {
                url = readLegs(reader);
            } else {
                reader.skipValue();
                //return null;
            }
        }
        reader.endObject();
        return url;
    }

    private String readLegs(JsonReader reader) throws IOException {

        String url = null;

        reader.beginArray();
        while (reader.hasNext()) {
            url = readUrl(reader);
        }
        reader.endArray();
        return url;


    }

    private String readUrl(JsonReader reader) throws IOException {
        String url = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String tag = reader.nextName();
            if (tag.equals("url")) {
                url = reader.nextString();
            }else{
                reader.skipValue();
            }
        }
        reader.endObject();
        return url;
    }

    private IndicativePrice readIndicativePrice(JsonReader reader) throws IOException {
        float price = 0;
        String currency = null;
        boolean isFreeTransfer = false;

        reader.beginObject();
        while (reader.hasNext()) {
            String tag = reader.nextName();
            if (tag.equals("price")) {
                price = Float.parseFloat(reader.nextString());
            } else if (tag.equals("currency")) {
                currency =   reader.nextString();
            } else if (tag.equals("isFreeTransfer")) {
                String bool = reader.nextString();
                if(Integer.parseInt(bool) == 1 )
                    isFreeTransfer = true;
                else
                    isFreeTransfer = false;
            } else{
                reader.skipValue();
            }
        }
        reader.endObject();
        return new IndicativePrice(price, currency, isFreeTransfer);
    }


    public  ArrayList<Place> readPlaces(JsonReader reader) throws IOException {
        ArrayList<Place> places = new ArrayList();

        reader.beginArray();
        while (reader.hasNext()) {
            places.add(readPlace(reader));
        }
        reader.endArray();
        return places;
    }

    public Place readPlace(JsonReader reader) throws IOException {

        String kind = null;
        String name = null;
        String longName = null;
        String pos = null;
        String countryCode = null;
        String regionCode = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String tag = reader.nextName();
            if (tag.equals("kind")) {
                kind = reader.nextString();
            } else if (tag.equals("name")) {
                name = reader.nextString();
            } else if (tag.equals("longName")) {
                longName = reader.nextString();
            } else if (tag.equals("pos")) {
                pos = reader.nextString();
            } else if (tag.equals("countryCode")) {
                countryCode = reader.nextString();
            }else if (tag.equals("regionCode")) {
                regionCode = reader.nextString();
            }else{
                reader.skipValue();
            }
        }
        reader.endObject();
        return new Place(kind, name, longName, pos, countryCode, regionCode);
    }


}
