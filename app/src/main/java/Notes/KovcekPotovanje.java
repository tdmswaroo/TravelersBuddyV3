package Notes;

/**
 * Created by Asura on 19-Jan-16.
 */
public class KovcekPotovanje {

    public int id;
    public String naziv;
    public String createdOn;
    public int idPotovanja;
    private String potovanjeOD;
    private String potovanjeDO;
    private String datumOdhoda;

    public KovcekPotovanje(){}
    public KovcekPotovanje(int id, String naziv, String createdOn, int idPotovanja, String potovanjeOD,String potovanjeDO,String datumOdhoda){
        this.id = id;
        this.naziv = naziv;
        this.createdOn = createdOn;
        this.idPotovanja = idPotovanja;
        this.potovanjeOD=potovanjeOD;
        this.potovanjeDO=potovanjeDO;
        this.datumOdhoda=datumOdhoda;
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

    public String getPotovanjeOD() {
        return potovanjeOD;
    }

    public void setPotovanjeOD(String potovanjeOD) {
        this.potovanjeOD = potovanjeOD;
    }

    public String getPotovanjeDO() {
        return potovanjeDO;
    }

    public void setPotovanjeDO(String potovanjeDO) {
        this.potovanjeDO = potovanjeDO;
    }

    public String getDatumOdhoda() {
        return datumOdhoda;
    }

    public void setDatumOdhoda(String datumOdhoda) {
        this.datumOdhoda = datumOdhoda;
    }

    public String toString (){
        return this.getPotovanjeOD()+" - "+this.getPotovanjeDO();
    }
}
