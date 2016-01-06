package Pomozni;

/**
 * Created by Nino on 06-Jan-16.
 */
public class SideMenuItem {

    private int ime;
    private int ikona;


    public SideMenuItem(){

    }

    public SideMenuItem(int ime, int ikona) {
        this.ime = ime;
        this.ikona = ikona;
    }

    public int getIme() {
        return ime;
    }

    public void setIme(int ime) {
        this.ime = ime;
    }

    public int getIkona() {
        return ikona;
    }

    public void setIkona(int ikona) {
        this.ikona = ikona;
    }

}
