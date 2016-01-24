package Parsing;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Klemen on 21.1.2016.
 */
public class Route implements Parcelable {
    private String name;
    private float duration;
    private IndicativePrice indicativePrice;
    private ArrayList<Segment> segments;

    public Route(String name, float duration, IndicativePrice indicativePrice, ArrayList<Segment> segments) {
        this.name = name;
        this.duration = duration;
        this.indicativePrice = indicativePrice;
        this.segments = segments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public IndicativePrice getIndicativePrice() {
        return indicativePrice;
    }

    public void setIndicativePrice(IndicativePrice indicativePrice) {
        this.indicativePrice = indicativePrice;
    }

    public ArrayList<Segment> getSegments() {
        return segments;
    }

    public void setSegments(ArrayList<Segment> segments) {
        this.segments = segments;
    }

    @Override
    public String toString() {
        return "Route{" +
                "name='" + name + '\'' +
                ", duration=" + duration +
                ", indicativePrice=" + indicativePrice +
                ", segments=" + segments +
                '}';
    }

    protected Route(Parcel in) {
        name = in.readString();
        duration = in.readFloat();
        indicativePrice = (IndicativePrice) in.readValue(IndicativePrice.class.getClassLoader());
        if (in.readByte() == 0x01) {
            segments = new ArrayList<Segment>();
            in.readList(segments, Segment.class.getClassLoader());
        } else {
            segments = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeFloat(duration);
        dest.writeValue(indicativePrice);
        if (segments == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(segments);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Route> CREATOR = new Parcelable.Creator<Route>() {
        @Override
        public Route createFromParcel(Parcel in) {
            return new Route(in);
        }

        @Override
        public Route[] newArray(int size) {
            return new Route[size];
        }
    };
}