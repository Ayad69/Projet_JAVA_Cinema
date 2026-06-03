package cinema.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cinema.BO.Franchise;

// Tout ce qui touche la table franchise (SQL dedans).
public class FranchiseDAO extends DAO<Franchise> {

    private void consigner(String action, Franchise obj, Integer idPourLog, String detail) {
        new ActiviteLogDAO().insertPourUtilisateurConnecte(action, "FRANCHISE", idPourLog, detail);
    }

    @Override
    // Ajoute une franchise.
    public boolean create(Franchise obj) {
        boolean controle = false;
        try {
            String a = "INSERT INTO franchise(nom_franchise, siege_social, id_gerant) values (?,?,?);";
            PreparedStatement statement = this.connect.prepareStatement(a);
            statement.setString(1, obj.getNomFranchise());
            statement.setString(2, obj.getSiegeSocial());
            statement.setInt(3, obj.getIdGerant());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                controle = true;
                Integer idLog = obj.getIdFranchise() > 0 ? obj.getIdFranchise() : null;
                consigner("AJOUT", obj, idLog,
                        "nom_franchise=" + obj.getNomFranchise() + ", siege=" + obj.getSiegeSocial()
                                + ", id_gerant=" + obj.getIdGerant());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return controle;
    }

    // Compte combien de franchises ont ce gérant (utilisé dans l'écran liste cinéma pour la suppression).
    public Integer getNbFranchiseByIdGerant(int idGerant) {
        int result = 0;
        try {
            String sql = "SELECT COUNT(*) FROM franchise WHERE id_gerant = ?";
            PreparedStatement ps = this.connect.prepareStatement(sql);
            ps.setInt(1, idGerant);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    // Supprime une franchise.
    public boolean delete(Franchise obj) {
        boolean controle = false;
        try {
            String sql = "DELETE FROM franchise WHERE id_franchise = ?;";
            PreparedStatement statement = this.connect.prepareStatement(sql);
            statement.setInt(1, obj.getIdFranchise());

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                controle = true;
                consigner("SUPPRESSION", obj, obj.getIdFranchise(),
                        "nom_franchise=" + obj.getNomFranchise());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return controle;
    }

    @Override
    // Modifie une franchise.
    public boolean update(Franchise obj) {
        boolean controle = false;
        try {
            String query = "UPDATE franchise SET nom_franchise = ?, siege_social = ?, id_gerant = ? WHERE id_franchise = ?";
            PreparedStatement statement = this.connect.prepareStatement(query);
            statement.setString(1, obj.getNomFranchise());
            statement.setString(2, obj.getSiegeSocial());
            statement.setInt(3, obj.getIdGerant());
            statement.setInt(4, obj.getIdFranchise());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                controle = true;
                consigner("MODIFICATION", obj, obj.getIdFranchise(),
                        "nom_franchise=" + obj.getNomFranchise() + ", siege=" + obj.getSiegeSocial()
                                + ", id_gerant=" + obj.getIdGerant());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return controle;
    }

    @Override
    // Trouve une franchise par id.
    public Franchise find(int id) {
        Franchise franchise = null;
        String query = "SELECT * FROM franchise WHERE id_franchise = ?;";
        try {
            PreparedStatement ps = this.connect.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                franchise = hydrate(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return franchise;
    }

    @Override
    // Liste toutes les franchises (attention : peut renvoyer null si erreur SQL).
    public List<Franchise> findAll() {
        List<Franchise> mesFranchises = new ArrayList<>();
        Franchise franchise;

        try {
            String b = "SELECT * FROM franchise ORDER BY id_franchise";
            Statement ps = this.connect.createStatement();
            ResultSet rs = ps.executeQuery(b);
            while (rs.next()) {
                franchise = hydrate(rs);
                mesFranchises.add(franchise);
            }

        } catch (SQLException e) {
            return null;
        }
        return mesFranchises;
    }

    // Franchises d'un seul gérant (id utilisateur).
    public List<Franchise> getAllByGerant(int idSection) {
        List<Franchise> mesFranchises = new ArrayList<>();
        Franchise franchise;
        try {
            String sql = "SELECT * FROM franchise WHERE id_gerant = ?";
            PreparedStatement ps = this.connect.prepareStatement(sql);
            ps.setInt(1, idSection);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                franchise = hydrate(rs);
                mesFranchises.add(franchise);
            }
        } catch (SQLException e) {
            return null;
        }
        return mesFranchises;
    }

    // Transforme une ligne SQL (ResultSet) en objet Franchise Java.
    private Franchise hydrate(ResultSet resultSet) throws SQLException {
        return new Franchise(resultSet.getInt("id_franchise"),
                resultSet.getString("nom_franchise"),
                resultSet.getString("siege_social"),
                resultSet.getInt("id_gerant"));
    }
}