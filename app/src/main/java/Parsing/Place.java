package Parsing;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Klemen on 21.1.2016.
 */
public class Place implements Parcelable {



    private String kind;
    private String name;
    private String longName;
    private String pos;
    private String countryCode;
    private String regionCode;

    public Place(String kind, String name, String longName, String pos, String countryCode, String regionCode) {
        this.kind = kind;
        this.name = name;
        this.longName = longName;
        this.pos = pos;
        this.countryCode = countryCode;
        this.regionCode = regionCode;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    @Override
    public String toString() {
        return "Place{" +
                "kind='" + kind + '\'' +
                ", name='" + name + '\'' +
                ", longName='" + longName + '\'' +
                ", pos='" + pos + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", regionCode='" + regionCode + '\'' +
                '}';
    }

    protected Place(Parcel in) {
        kind = in.readString();
        name = in.readString();
        longName = in.readString();
        pos = in.readString();
        countryCode = in.readString();
        regionCode = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(kind);
        dest.writeString(name);
        dest.writeString(longName);
        dest.writeString(pos);
        dest.writeString(countryCode);
        dest.writeString(regionCode);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Place> CREATOR = new Parcelable.Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };
}