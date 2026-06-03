package cinema.BO;

// Une salle appartient à un cinéma : numéro, description, nombre de places, etc.
// idCinema dit à quel cinéma cette salle est rattachée.
public class Salle {

    private int idSalle;
    private int numero;
    private String description;
    private int nbPlaces;
    private int idCinema;

    public Salle(int idSalle, int numero, String description, int nbPlaces, int idCinema) {
        this.idSalle = idSalle;
        this.numero = numero;
        this.description = description;
        this.nbPlaces = nbPlaces;
        this.idCinema = idCinema;
    }

    public int getIdSalle() {
        return idSalle;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNbPlaces() {
        return nbPlaces;
    }

    public void setNbPlaces(int nbPlaces) {
        this.nbPlaces = nbPlaces;
    }

    public int getIdCinema() {
        return idCinema;
    }

    public void setIdCinema(int idCinema) {
        this.idCinema = idCinema;
    }

    @Override
    public String toString() {
        return "Salle n°" + numero + " — " + description + " (" + nbPlaces + " places)";
    }
}
