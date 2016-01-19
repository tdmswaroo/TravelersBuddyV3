package Notes;

/**
 * Created by Asura on 19-Jan-16.
 */
public class Potovanje {

    private int id;
    private String potovanjeOD;
    private String potovanjeDO;
    private String datumOdhoda;
    private String casPotovanja;
    private String tipPrevoza;

    public Potovanje(){}
    public Potovanje(int id,String potovanjeOD,String potovanjeDO,String datumOdhoda,String casPotovanja,String tipPrevoza){
        this.id=id;
        this.potovanjeOD=potovanjeOD;
        this.potovanjeDO=potovanjeDO;
        this.datumOdhoda=datumOdhoda;
        this.casPotovanja=casPotovanja;
        this.tipPrevoza=tipPrevoza;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCasPotovanja() {
        return casPotovanja;
    }

    public void setCasPotovanja(String casPotovanja) {
        this.casPotovanja = casPotovanja;
    }

    public String getTipPrevoza() {
        return tipPrevoza;
    }

    public void setTipPrevoza(String tipPrevoza) {
        this.tipPrevoza = tipPrevoza;
    }
}
