package cinema;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import cinema.BO.Utilisateur;

public class Session {

    // --- Gestion de l'utilisateur connecté ---
    private static Utilisateur utilisateurConnecte;

    public static void setUtilisateur(Utilisateur utilisateur) {
        utilisateurConnecte = utilisateur;
    }

    public static Utilisateur getUtilisateur() {
        return utilisateurConnecte;
    }

    public static void clear() {
        utilisateurConnecte = null;
    }

    // --- Connexion centralisée à la base de données ---
    private static final String URL = "jdbc:postgresql://localhost:5432/fsi";
    private static final String USER = "fsi_usr";
    private static final String PASSWORD = "fsi_pwd";
    private static Connection connection;

    /**
     * Retourne la connexion unique à la base de données.
     * 
     * @return la connexion active
     */
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

    /**
     * Ferme proprement la connexion à la base de données.
     */
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
