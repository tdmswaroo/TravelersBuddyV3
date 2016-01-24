package Pomozni;

import java.io.Serializable;

/**
 * Created by Nino on 13-Jan-16.
 */
public class DogodekItem implements Serializable{

    private String naziv;
    private String lokacija;
    private String datum;
    private int slikaDogodka;
    private String ime_prizorisca;
    private String povezavaDoDogodka;
    private String latitude;
    private String longitude;
    private int ikonaDatum;
    private int ikonaLokacija;

    public DogodekItem(){}

    public DogodekItem(String naziv, String lokacija, String datum, int slikaDogodka, String povezavaDoDogodka, String latitude, String longitude, int ikonaDatum, int ikonaLokacija) {
        this.naziv = naziv;
        this.lokacija = lokacija;
        this.datum = datum;
        this.slikaDogodka = slikaDogodka;
        this.povezavaDoDogodka = povezavaDoDogodka;
        this.latitude = latitude;
        this.longitude = longitude;
        this.ikonaDatum = ikonaDatum;
        this.ikonaLokacija = ikonaLokacija;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getLokacija() {
        return lokacija;
    }

    public void setLokacija(String lokacija) {
        this.lokacija = lokacija;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public int getSlikaDogodka() {
        return slikaDogodka;
    }

    public void setSlikaDogodka(int slikaDogodka) {
        this.slikaDogodka = slikaDogodka;
    }

    public String getIme_prizorisca() {
        return ime_prizorisca;
    }

    public void setIme_prizorisca(String ime_prizorisca) {
        this.ime_prizorisca = ime_prizorisca;
    }

    public String getPovezavaDoDogodka() {
        return povezavaDoDogodka;
    }

    public void setPovezavaDoDogodka(String povezavaDoDogodka) {
        this.povezavaDoDogodka = povezavaDoDogodka;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public int getIkonaDatum() {
        return ikonaDatum;
    }

    public void setIkonaDatum(int ikonaDatum) {
        this.ikonaDatum = ikonaDatum;
    }

    public int getIkonaLokacija() {
        return ikonaLokacija;
    }

    public void setIkonaLokacija(int ikonaLokacija) {
        this.ikonaLokacija = ikonaLokacija;
    }
}
