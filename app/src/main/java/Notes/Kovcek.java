package Notes;

/**
 * Created by Asura on 09-Jan-16.
 */
public class Kovcek {

    public int id;
    public String naziv;
    public String createdOn;
    public int idPotovanja;

    public Kovcek(){}
    public Kovcek(int id, String naziv, String createdOn, int idPotovanja){
        this.id = id;
        this.naziv = naziv;
        this.createdOn = createdOn;
        this.idPotovanja = idPotovanja;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public int getIdPotovanja() {
        return idPotovanja;
    }

    public void setIdPotovanja(int idPotovanja) {
        this.idPotovanja = idPotovanja;
    }

    public String toString(){
        return id + ", " + naziv + ", " + createdOn + ", " + idPotovanja;
    }
}
