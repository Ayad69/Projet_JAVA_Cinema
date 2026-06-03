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

    private static boolean looksLikeBcryptHash(String stored) {
        return stored != null
                && (stored.startsWith("$2a$") || stored.startsWith("$2b$") || stored.startsWith("$2y$"));
    }

    /** Mot de passe à enregistrer : hash BCrypt si ce n’est pas déjà un hash. */
    private String hashForStorage(String plainOrHash) {
        if (plainOrHash == null) {
            return null;
        }
        return looksLikeBcryptHash(plainOrHash) ? plainOrHash : password_hash(plainOrHash);
    }

    @Override
    // Crée un utilisateur (nom, prénom, login, mdp hashé BCrypt).
    public boolean create(Utilisateur obj) {
        boolean result = false;
        try {
            String sql = "INSERT INTO utilisateur (nom, prenom, login, mdp) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = this.connect.prepareStatement(sql);
            ps.setString(1, obj.getNom());
            ps.setString(2, obj.getPrenom());
            ps.setString(3, obj.getLogin());
            ps.setString(4, hashForStorage(obj.getMdp()));
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
            String sql = "UPDATE utilisateur SET login = ?, mdp = ? WHERE id_utilisateur = ?";
            PreparedStatement ps = this.connect.prepareStatement(sql);
            ps.setString(1, obj.getLogin());
            ps.setString(2, hashForStorage(obj.getMdp()));
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

    // Vérifie login + mot de passe : comparaison avec le hash BCrypt en base (rétrocompat mot de passe en clair).
    public Utilisateur authenticate(String login, String password) {
        if (login == null || password == null) {
            return null;
        }
        try {
            String sql = "SELECT * FROM utilisateur WHERE login = ?";
            PreparedStatement ps = this.connect.prepareStatement(sql);
            ps.setString(1, login.trim());
            ResultSet result = ps.executeQuery();
            if (!result.next()) {
                return null;
            }
            String stored = result.getString("mdp");
            boolean ok;
            if (looksLikeBcryptHash(stored)) {
                try {
                    ok = checkPassword(password, stored);
                } catch (IllegalArgumentException ex) {
                    ok = false;
                }
            } else {
                ok = password.equals(stored);
            }
            if (!ok) {
                return null;
            }
            return hydrate(result);
        } catch (SQLException e) {
            return null;
        }
    }
}
