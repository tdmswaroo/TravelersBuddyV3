package Parsing;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Klemen on 21.1.2016.
 */
public class Response implements Parcelable {
    private ArrayList<Place> places;
    private ArrayList<Route> routes;

    public Response(ArrayList<Place> places, ArrayList<Route> routes ) {
        this.places = places;
        this.routes = routes;
    }

    public ArrayList<Place> getPlaces() {
        return places;
    }

    public void setPlaces(ArrayList<Place> places) {
        this.places = places;
    }

    public ArrayList<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(ArrayList<Route> routes) {
        this.routes = routes;
    }



    protected Response(Parcel in) {
        if (in.readByte() == 0x01) {
            places = new ArrayList<Place>();
            in.readList(places, Place.class.getClassLoader());
        } else {
            places = null;
        }
        if (in.readByte() == 0x01) {
            routes = new ArrayList<Route>();
            in.readList(routes, Route.class.getClassLoader());
        } else {
            routes = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (places == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(places);
        }
        if (routes == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(routes);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Response> CREATOR = new Parcelable.Creator<Response>() {
        @Override
        public Response createFromParcel(Parcel in) {
            return new Response(in);
        }

        @Override
        public Response[] newArray(int size) {
            return new Response[size];
        }
    };
}


