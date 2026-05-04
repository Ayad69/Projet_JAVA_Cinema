package cinema.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import cinema.BO.Utilisateur;

// Gère les utilisateurs : login, mot de passe, liste, etc.
// On utilise BCrypt pour ne pas stocker le mot de passe en clair (en théorie — selon ce qu'on enregistre en base).
public class UtilisateurDAO extends DAO<Utilisateur> {

    // Hash = version "cryptée" du mot de passe pour la base de données.
    public String password_hash(String password) {
      return  BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // Compare un mot de passe tapé avec le hash stocké.
    public boolean checkPassword(String password, String hash) {
       return BCrypt.checkpw(password, hash);
    }

    @Override
    // Crée un utilisateur (attention : le code actuel mélange peut-être les colonnes nom/login).
    public boolean create(Utilisateur obj) {
        boolean result = false;
        try {
            String sql = "INSERT INTO utilisateur(nom,prenom,login, mdp) VALUES(?,?,?,?)";
            //obj.getMdp()
            PreparedStatement ps = this.connect.prepareStatement(sql);
            ps.setString(1, obj.getLogin());
            ps.setString(2, password_hash(obj.getMdp()));
            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    // Supprime un utilisateur.
    public boolean delete(Utilisateur obj) {
        boolean result = false;
        try {
            String sql = "DELETE FROM utilisateur WHERE id_utilisateur = ?";
            PreparedStatement ps = this.connect.prepareStatement(sql);
            ps.setInt(1, obj.getIdUtilisateur());

            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    // Met à jour login / mdp.
    public boolean update(Utilisateur obj) {
        boolean result = false;
        try {
            String sql = "UPDATE Utilisateur SET login=?, mdp=? WHERE id_utilisateur = ?";
            PreparedStatement ps = this.connect.prepareStatement(sql);
            ps.setString(1, obj.getLogin());
            ps.setString(2, obj.getMdp());
            ps.setInt(3, obj.getIdUtilisateur());
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Une ligne SQL -> un objet Utilisateur.
    private Utilisateur hydrate(ResultSet resultSet) throws SQLException {
        return new Utilisateur(resultSet.getInt("id_utilisateur"),
                resultSet.getString("nom"),
                resultSet.getString("prenom"),
                resultSet.getString("login"),
                resultSet.getString("mdp"));
    }

    @Override
    // Tous les utilisateurs.
    public List<Utilisateur> findAll() {
        List<Utilisateur> mesUtilisateurs = new ArrayList<>();
        Utilisateur utilisateur;
        try {
            String sql = "SELECT * FROM utilisateur";
            Statement statement = this.connect.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                utilisateur = hydrate(rs);
                mesUtilisateurs.add(utilisateur);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mesUtilisateurs;
    }

    @Override
    // Un utilisateur par id.
    public Utilisateur find(int idUtilisateur) {
        Utilisateur user;
        try {
            String sql = "SELECT * FROM utilisateur WHERE id_utilisateur = ?";
            PreparedStatement ps = this.connect.prepareStatement(sql);
            ps.setInt(1, idUtilisateur);
            ResultSet result = ps.executeQuery();
            if (result.next()) {
                user = hydrate(result);
            } else {
                user = null;
            }

        } catch (SQLException e) {
            return null;
        }
        return user;
    }

    // Vérifie login + mot de passe (comme sur l'écran connexion).
    public Utilisateur authenticate(String login, String password) {
        Utilisateur user = null;
        try {
            String sql = "SELECT * FROM utilisateur WHERE login =? AND mdp=?";
            PreparedStatement ps = this.connect.prepareStatement(sql);
            ps.setString(1, login);
            ps.setString(2, password);
            ResultSet result = ps.executeQuery();
            if (result.next()) {
                user = hydrate(result);
            }
        } catch (SQLException e) {
            return null;
        }
        return user;
    }
}
