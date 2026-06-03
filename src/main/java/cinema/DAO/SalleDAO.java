package cinema.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cinema.BO.Salle;

// Requêtes SQL pour les salles (liées à un cinéma).
public class SalleDAO extends DAO<Salle> {

    @Override
    // Insère une nouvelle salle.
    public boolean create(Salle obj) {
        boolean result = false;
        try {
            String query = "INSERT INTO salle (numero, description, nb_places, id_cinema) VALUES (?,?,?,?);";
            PreparedStatement ps = this.connect.prepareStatement(query);
            ps.setInt(1, obj.getNumero());
            ps.setString(2, obj.getDescription());
            ps.setInt(3, obj.getNbPlaces());
            ps.setInt(4, obj.getIdCinema());
            result = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    // Supprime une salle.
    public boolean delete(Salle obj) {
        String query = "DELETE FROM salle WHERE id_salle = ?;";
        try (PreparedStatement ps = this.connect.prepareStatement(query)) {
            ps.setInt(1, obj.getIdSalle());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    // Met à jour une salle.
    public boolean update(Salle obj) {
        String query = "UPDATE salle SET numero = ?, description = ?, nb_places = ?, id_cinema = ? WHERE id_salle = ?;";
        try (PreparedStatement ps = this.connect.prepareStatement(query)) {
            ps.setInt(1, obj.getNumero());
            ps.setString(2, obj.getDescription());
            ps.setInt(3, obj.getNbPlaces());
            ps.setInt(4, obj.getIdCinema());
            ps.setInt(5, obj.getIdSalle());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    // Trouve une salle par id.
    public Salle find(int id) {
        String query = "SELECT * FROM salle WHERE id_salle = ?;";
        try (PreparedStatement ps = this.connect.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    // Toutes les salles, triées par id.
    public List<Salle> findAll() {
        List<Salle> list = new ArrayList<>();
        String query = "SELECT * FROM salle ORDER BY id_salle;";
        try (PreparedStatement ps = this.connect.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Convertit une ligne SQL en objet Salle.
    private static Salle mapRow(ResultSet rs) throws SQLException {
        return new Salle(
                rs.getInt("id_salle"),
                rs.getInt("numero"),
                rs.getString("description"),
                rs.getInt("nb_places"),
                rs.getInt("id_cinema"));
    }
}
