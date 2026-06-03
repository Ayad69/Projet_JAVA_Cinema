package cinema;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import cinema.BO.Utilisateur;

// Classe utilitaire : elle garde des infos "globales" pendant que l'appli tourne.
// Ici : l'utilisateur connecté + une autre façon de se connecter à PostgreSQL (en plus de DBManager).
public class Session {

    // L'utilisateur actuellement connecté (null si personne n'est connecté).
    private static Utilisateur utilisateurConnecte;

    // Pour enregistrer qui est connecté après un login réussi.
    public static void setUtilisateur(Utilisateur utilisateur) {
        utilisateurConnecte = utilisateur;
    }

    // Récupère l'utilisateur connecté (pour savoir qui est "moi" dans l'appli).
    public static Utilisateur getUtilisateur() {
        return utilisateurConnecte;
    }

    // Déconnecte l'utilisateur (on remet tout à zéro).
    public static void clear() {
        utilisateurConnecte = null;
    }

    // Paramètres pour une connexion JDBC directe (base "fsi" — peut être différente de DBManager).
    private static final String URL = "jdbc:postgresql://localhost:5432/fsi";
    private static final String USER = "fsi_usr";
    private static final String PASSWORD = "fsi_pwd";
    private static Connection connection;

    // Ouvre la connexion une seule fois, puis on la réutilise (singleton simple).
    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connexion à la base réussie.");
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Échec de la connexion à la base de données.");
            }
        }
        return connection;
    }

    // Ferme la connexion quand on n'en a plus besoin (évite de laisser la base "ouverte").
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("Connexion à la base fermée.");
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Erreur lors de la fermeture de la connexion.");
            }
        }
    }
}
