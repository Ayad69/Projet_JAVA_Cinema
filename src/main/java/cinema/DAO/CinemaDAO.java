package cinema.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cinema.BO.Cinema;

// Toutes les requêtes SQL pour la table "cinema" (ajouter, supprimer, modifier, lire...).
public class CinemaDAO extends DAO<Cinema> {

    private void consigner(String action, Cinema obj, Integer idPourLog, String detail) {
        new ActiviteLogDAO().insertPourUtilisateurConnecte(action, "CINEMA", idPourLog, detail);
    }

    @Override
    // Ajoute un cinéma dans la base.
    public boolean create(Cinema obj) {
        boolean result = false;
        try {
            String query = "INSERT INTO cinema (denomination, adresse, ville, id_franchise) VALUES (?,?,?,?);";
            PreparedStatement preparedStatement = this.connect.prepareStatement(query);
            preparedStatement.setString(1, obj.getDenomination());
            preparedStatement.setString(2, obj.getAdresse());
            preparedStatement.setString(3, obj.getVille());
            preparedStatement.setInt(4, obj.getIdFranchise());
            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                result = true;
                Integer idLog = obj.getIdCinema() > 0 ? obj.getIdCinema() : null;
                consigner("AJOUT", obj, idLog,
                        "denomination=" + obj.getDenomination() + ", ville=" + obj.getVille()
                                + ", id_franchise=" + obj.getIdFranchise());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    // Supprime un cinéma avec son id.
    public boolean delete(Cinema obj) {
        boolean result = false;
        String query = "DELETE FROM cinema WHERE id_cinema = ?;";

        try (PreparedStatement preparedStatement = this.connect.prepareStatement(query)) {
            preparedStatement.setInt(1, obj.getIdCinema());
            result = preparedStatement.executeUpdate() > 0;
            if (result) {
                consigner("SUPPRESSION", obj, obj.getIdCinema(), "denomination=" + obj.getDenomination());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    // Met à jour les infos d'un cinéma déjà existant.
    public boolean update(Cinema obj) {
        boolean result = false;
        String query = "UPDATE cinema SET denomination = ?, adresse = ?, ville = ?, id_franchise = ? WHERE id_cinema = ?;";
        try {
            PreparedStatement preparedStatement = this.connect.prepareStatement(query);
            preparedStatement.setString(1, obj.getDenomination());
            preparedStatement.setString(2, obj.getAdresse());
            preparedStatement.setString(3, obj.getVille());
            preparedStatement.setInt(4, obj.getIdFranchise());
            preparedStatement.setInt(5, obj.getIdCinema());
            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                result = true;
                consigner("MODIFICATION", obj, obj.getIdCinema(),
                        "denomination=" + obj.getDenomination() + ", adresse=" + obj.getAdresse()
                                + ", ville=" + obj.getVille() + ", id_franchise=" + obj.getIdFranchise());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    // Cherche UN cinéma par son id_cinema.
    public Cinema find(int id) {
        Cinema cinema = null;
        String query = "SELECT * FROM cinema WHERE id_cinema = ?;";
        try {
            PreparedStatement preparedStatement = this.connect.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                cinema = new Cinema(
                        resultSet.getInt("id_cinema"),
                        resultSet.getString("denomination"),
                        resultSet.getString("adresse"),
                        resultSet.getString("ville"),
                        resultSet.getInt("id_franchise"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cinema;
    }

    @Override
    // Récupère tous les cinémas (pour remplir un tableau par exemple).
    public List<Cinema> findAll() {
        List<Cinema> cinemas = new ArrayList<Cinema>();
        String query = "SELECT * FROM cinema;";

        try (PreparedStatement preparedStatement = this.connect.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Cinema cinema = new Cinema(
                        resultSet.getInt("id_cinema"),
                        resultSet.getString("denomination"),
                        resultSet.getString("adresse"),
                        resultSet.getString("ville"),
                        resultSet.getInt("id_franchise"));
                cinemas.add(cinema);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cinemas;
    }

}
