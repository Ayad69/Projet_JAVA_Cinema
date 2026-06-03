package cinema.DAO;

import java.sql.Connection;
import java.util.List;

// Classe abstraite = modèle pour tous les DAO (cinéma, franchise, salle...).
// Le "<T>" veut dire : "on ne sait pas encore quel type exact, chaque DAO le précisera".
public abstract class DAO<T> {

    // La connexion SQL partagée (une seule pour toute l'appli, via DBManager).
    protected Connection connect = DBManager.getInstance();

    // Insérer une nouvelle ligne dans la table.
    public abstract boolean create(T obj);

    // Supprimer une ligne.
    public abstract boolean delete(T obj);

    // Modifier une ligne déjà existante.
    public abstract boolean update(T obj);

    // Chercher une ligne avec son id.
    public abstract T find(int id);

    // Lire toutes les lignes de la table.
    public abstract List<T> findAll();
}
