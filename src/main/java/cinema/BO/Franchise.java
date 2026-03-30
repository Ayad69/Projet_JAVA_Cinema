package cinema.BO;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Franchise {

    private int idFranchise;
    private SimpleStringProperty nomFranchise = new SimpleStringProperty();
    private SimpleStringProperty siegeSocial = new SimpleStringProperty();
    private int idGerant;

    public Franchise() {
    }

    public Franchise(int idFranchise, String nomFranchise, String siegeSocial, int idGerant) {
        this.idFranchise = idFranchise;
        this.nomFranchise.set(nomFranchise);
        this.siegeSocial.set(siegeSocial);
        this.idGerant = idGerant;
    }

    public int getIdFranchise() {
        return idFranchise;
    }

    public int getIdGerant() {
        return idGerant;
    }

    public void setIdGerant(int idGerant) {
        this.idGerant = idGerant;
    }

    public String getNomFranchise() {
        return nomFranchise.get();
    }

    public void setNomFranchise(String nomFranchise) {
        this.nomFranchise.set(nomFranchise);
    }

    public String getSiegeSocial() {
        return siegeSocial.get();
    }

    public void setSiegeSocial(String siegeSocial) {
        this.siegeSocial.set(siegeSocial);
    }

    public StringProperty nomFranchiseProperty() {
        return nomFranchise;
    }

    public StringProperty siegeSocialProperty() {
        return siegeSocial;
    }

    @Override
    public String toString() {
        return "Franchise [idFranchise=" + idFranchise + ", nomFranchise=" + nomFranchise + ", siegeSocial="
                + siegeSocial + ", idGerant=" + idGerant + "]";
    }
}
