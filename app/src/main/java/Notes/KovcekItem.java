package Notes;

/**
 * Created by Asura on 14-Jan-16.
 */
public class KovcekItem {

    private int id;
    private String vsebina;
    private boolean selected = false;
    private int idKovcka;

    public KovcekItem(){}
    public KovcekItem(int id, String vsebina, boolean selected, int idKovcka){
        super();
        this.id = id;
        this.vsebina = vsebina;
        this.selected = selected;
        this.idKovcka = idKovcka;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id=id;
    }

    public String getVsebina() {
        return vsebina;
    }
    public void setVsebina(String vsebina) {
        this.vsebina = vsebina;
    }

    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getIdKovcka() {
        return idKovcka;
    }

    public void setIdKovcka(int idKovcka) {
        this.idKovcka = idKovcka;
    }

}
