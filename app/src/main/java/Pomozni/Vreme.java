package Pomozni;

/**
 * Created by Nino on 22-Jan-16.
 */
public class Vreme {

    private String temp;
    private String ikona;
    private String maxTemp;
    private String minTemp;
    private String opis;
    private String vlaga;
    private String hitrostVetra;
    private String smerVetra;
    private long datum;

    public Vreme(){}

    public Vreme(String temp, long datum, String ikona, String maxTemp, String minTemp, String opis, String vlaga, String hitrostVetra, String smerVetra) {
        this.temp = temp;
        this.datum = datum;
        this.ikona = ikona;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.opis = opis;
        this.vlaga = vlaga;
        this.hitrostVetra = hitrostVetra;
        this.smerVetra = smerVetra;
    }

    public long getDatum() {
        return datum;
    }

    public void setDatum(long datum) {
        this.datum = datum;
    }

    public String getSmerVetra() {
        return smerVetra;
    }

    public void setSmerVetra(String smerVetra) {
        this.smerVetra = smerVetra;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getIkona() {
        return ikona;
    }

    public void setIkona(String ikona) {
        this.ikona = ikona;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        this.maxTemp = maxTemp;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(String minTemp) {
        this.minTemp = minTemp;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getVlaga() {
        return vlaga;
    }

    public void setVlaga(String vlaga) {
        this.vlaga = vlaga;
    }

    public String getHitrostVetra() {
        return hitrostVetra;
    }

    public void setHitrostVetra(String hitrostVetra) {
        this.hitrostVetra = hitrostVetra;
    }
}
