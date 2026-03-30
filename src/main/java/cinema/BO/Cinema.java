package cinema.BO;

public class Cinema {

    private int idCinema;
    private String denomination;
    private String adresse;
    private String ville;
    private int idFranchise;

    public Cinema(int idCinema, String denomination, String adresse, String ville, int idFranchise) {
        this.idCinema = idCinema;
        this.denomination = denomination;
        this.adresse = adresse;
        this.ville = ville;
        this.idFranchise = idFranchise;
    }

    public int getIdCinema() {
        return idCinema;
    }

    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public int getIdFranchise() {
        return idFranchise;
    }

    public void setIdFranchise(int idFranchise) {
        this.idFranchise = idFranchise;
    }

}
