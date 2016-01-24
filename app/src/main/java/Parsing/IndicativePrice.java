package Parsing;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Klemen on 21.1.2016.
 */
public class IndicativePrice  implements Parcelable {
    private float price;
    private String currency;
    private boolean isFreeTransfer;

    public IndicativePrice(float price, String currency, boolean isFreeTransfer) {
        this.price = price;
        this.currency = currency;
        this.isFreeTransfer = isFreeTransfer;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public boolean isFreeTransfer() {
        return isFreeTransfer;
    }

    public void setIsFreeTransfer(boolean isFreeTransfer) {
        this.isFreeTransfer = isFreeTransfer;
    }

    @Override
    public String toString() {
        return "IndicativePrice{" +
                "price=" + price +
                ", currency='" + currency + '\'' +
                ", isFreeTransfer=" + isFreeTransfer +
                '}';
    }

    protected IndicativePrice(Parcel in) {
        price = in.readFloat();
        currency = in.readString();
        isFreeTransfer = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(price);
        dest.writeString(currency);
        dest.writeByte((byte) (isFreeTransfer ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<IndicativePrice> CREATOR = new Parcelable.Creator<IndicativePrice>() {
        @Override
        public IndicativePrice createFromParcel(Parcel in) {
            return new IndicativePrice(in);
        }

        @Override
        public IndicativePrice[] newArray(int size) {
            return new IndicativePrice[size];
        }
    };
}