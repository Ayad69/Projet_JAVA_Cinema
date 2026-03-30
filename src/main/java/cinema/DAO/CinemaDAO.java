package cinema.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cinema.BO.Cinema;

public class CinemaDAO extends DAO<Cinema> {

    @Override
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean delete(Cinema obj) {
        boolean result = false;
        String query = "DELETE FROM cinema WHERE id_cinema = ?;";

        try (PreparedStatement preparedStatement = this.connect.prepareStatement(query)) {
            preparedStatement.setInt(1, obj.getIdCinema());
            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
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
