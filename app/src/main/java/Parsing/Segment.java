package Parsing;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Klemen on 21.1.2016.
 */
public class Segment implements Parcelable {
    private String kind;
    private float duration;
    private String sName;
    private String tName;
    private IndicativePrice indicativePrice;
    private String url;

    public Segment(String kind, float duration, String sName, String tName, IndicativePrice indicativePrice, String url) {
        this.kind = kind;
        this.duration = duration;
        this.sName = sName;
        this.tName = tName;
        this.indicativePrice = indicativePrice;
        this.url = url;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public IndicativePrice getIndicativePrice() {
        return indicativePrice;
    }

    public void setIndicativePrice(IndicativePrice indicativePrice) {
        this.indicativePrice = indicativePrice;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    @Override
    public String toString() {
        return "Segment{" +
                "kind='" + kind + '\'' +
                ", duration=" + duration +
                ", sName='" + sName + '\'' +
                ", tName='" + tName + '\'' +
                ", indicativePrice=" + indicativePrice +
                ", url='" + url + '\'' +
                '}';
    }

    protected Segment(Parcel in) {
        kind = in.readString();
        duration = in.readFloat();
        sName = in.readString();
        tName = in.readString();
        indicativePrice = (IndicativePrice) in.readValue(IndicativePrice.class.getClassLoader());
        url = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(kind);
        dest.writeFloat(duration);
        dest.writeString(sName);
        dest.writeString(tName);
        dest.writeValue(indicativePrice);
        dest.writeString(url);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Segment> CREATOR = new Parcelable.Creator<Segment>() {
        @Override
        public Segment createFromParcel(Parcel in) {
            return new Segment(in);
        }

        @Override
        public Segment[] newArray(int size) {
            return new Segment[size];
        }
    };
}
