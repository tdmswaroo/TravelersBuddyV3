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
    private String opisDogodka;
    private String ime_prizorisca;
    private int ikonaDatum;
    private int ikonaLokacija;

    public DogodekItem(){}

    public DogodekItem(String naziv, String lokacija, String datum, int slikaDogodka, String opisDogodka, String ime_prizorisca, int ikonaDatum, int ikonaLokacija) {
        this.naziv = naziv;
        this.lokacija = lokacija;
        this.datum = datum;
        this.slikaDogodka = slikaDogodka;
        this.opisDogodka = opisDogodka;
        this.ime_prizorisca = ime_prizorisca;
        this.ikonaDatum = ikonaDatum;
        this.ikonaLokacija = ikonaLokacija;
    }

    public int getSlikaDogodka() {
        return slikaDogodka;
    }

    public void setSlikaDogodka(int slikaDogodka) {
        this.slikaDogodka = slikaDogodka;
    }

    public String getOpisDogodka() {
        return opisDogodka;
    }

    public void setOpisDogodka(String opisDogodka) {
        this.opisDogodka = opisDogodka;
    }

    public String getIme_prizorisca() {
        return ime_prizorisca;
    }

    public void setIme_prizorisca(String ime_prizorisca) {
        this.ime_prizorisca = ime_prizorisca;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getIkonaLokacija() {
        return ikonaLokacija;
    }

    public void setIkonaLokacija(int ikonaLokacija) {
        this.ikonaLokacija = ikonaLokacija;
    }

    public int getIkonaDatum() {
        return ikonaDatum;
    }

    public void setIkonaDatum(int ikonaDatum) {
        this.ikonaDatum = ikonaDatum;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getLokacija() {
        return lokacija;
    }

    public void setLokacija(String lokacija) {
        this.lokacija = lokacija;
    }
}
