package cinema.BO;

// Objet "cinéma" : ce qu'on manipule dans le code avant de le sauver en base de données.
// Les champs correspondent aux colonnes de la table cinema (à peu près).
public class Cinema {

    private int idCinema;
    private String denomination;
    private String adresse;
    private String ville;
    private int idFranchise;

    // Constructeur : on donne toutes les valeurs d'un coup (souvent id=0 quand c'est un nouvel objet avant insertion SQL).
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

    // Texte affiché si on fait System.out.println(cinema) par exemple.
    public String toString(){
        return "Dénomination "+this.denomination+" Franchise "+this.idFranchise;
    }


}
