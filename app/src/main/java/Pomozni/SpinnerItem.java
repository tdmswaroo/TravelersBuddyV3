package Pomozni;

/**
 * Created by Nino on 08-Jan-16.
 */
public class SpinnerItem {

    private String ime;
    private int ikona;


    public SpinnerItem(){

    }

    public SpinnerItem(String ime, int ikona) {
        this.ime = ime;
        this.ikona = ikona;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public int getIkona() {
        return ikona;
    }

    public void setIkona(int ikona) {
        this.ikona = ikona;
    }

}
