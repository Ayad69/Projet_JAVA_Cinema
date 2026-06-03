package cinema.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

import cinema.Session;

// Écrit une ligne dans activite_log : quel utilisateur a fait quelle action sur quoi.
public class ActiviteLogDAO {

    private final Connection connect = DBManager.getInstance();

    // Insertion complète (tu peux passer id_utilisateur à null si inconnu).
    public void insert(Integer idUtilisateur, String typeAction, String typeEntite, Integer idEntite,
            String detail) {
        String sql = "INSERT INTO activite_log (id_utilisateur, type_action, type_entite, id_entite, detail, date_heure) "
                + "VALUES (?,?,?,?,?,?);";
        try (PreparedStatement ps = connect.prepareStatement(sql)) {
            if (idUtilisateur != null) {
                ps.setInt(1, idUtilisateur);
            } else {
                ps.setObject(1, null);
            }
            ps.setString(2, typeAction);
            ps.setString(3, typeEntite);
            if (idEntite != null) {
                ps.setInt(4, idEntite);
            } else {
                ps.setObject(4, null);
            }
            ps.setString(5, detail != null ? detail : "");
            ps.setTimestamp(6, Timestamp.from(Instant.now()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Version pratique : lit l'utilisateur connecté dans Session (rempli au login).
    public void insertPourUtilisateurConnecte(String typeAction, String typeEntite, Integer idEntite, String detail) {
        Integer idU = null;
        if (Session.getUtilisateur() != null) {
            idU = Session.getUtilisateur().getIdUtilisateur();
        }
        insert(idU, typeAction, typeEntite, idEntite, detail);
    }
}
