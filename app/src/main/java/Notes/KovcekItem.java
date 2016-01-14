package Notes;

/**
 * Created by Asura on 14-Jan-16.
 */
public class KovcekItem {

    private int id;
    private String vsebina;
    private String checked;
    private int idKovcka;

    public KovcekItem(){}
    public KovcekItem(int id, String vsebina, String checked, int idKovcka){
        this.id = id;
        this.vsebina = vsebina;
        this.checked = checked;
        this.idKovcka = idKovcka;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVsebina() {
        return vsebina;
    }

    public void setVsebina(String vsebina) {
        this.vsebina = vsebina;
    }

    public String isChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public int getIdKovcka() {
        return idKovcka;
    }

    public void setIdKovcka(int idKovcka) {
        this.idKovcka = idKovcka;
    }

    public String toString(){
        return id + ", " + vsebina + ", " + checked + ", " + idKovcka;
    }

}
