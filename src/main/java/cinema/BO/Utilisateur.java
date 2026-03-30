package cinema.BO;

public class Utilisateur {

    private int idUtilisateur;
    private String nom;
    private String prenom;
    private String login;
    private String mdp;

    public Utilisateur() {
    }

    public Utilisateur(int idUtilisateur, String nom, String prenom, String login, String mdp) {
        this.nom = nom;
        this.prenom = prenom;
        this.mdp = mdp;
        this.login = login;
        this.idUtilisateur = idUtilisateur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
