package cinema.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Sert à ouvrir UNE SEULE connexion PostgreSQL pour tout le projet (pattern "singleton").
// Comme ça on ne se connecte pas 50 fois à la base sans faire exprès.
public class DBManager {

    private static String url = "jdbc:postgresql://localhost:5432/gestion_cinema";

    private static String user = "cinema_usr";

    private static String pass = "cinema_pwd";

    private static Connection connect;

    // Donne la connexion : si elle n'existe pas encore, on la crée, sinon on renvoie la même.
    public static Connection getInstance() {
        if (connect == null) {
            try {
                Class.forName("org.postgresql.Driver");
                connect = DriverManager.getConnection(url, user, pass);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return connect;
    }
}
