package cinema.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cinema.BO.Franchise;

public class FranchiseDAO extends DAO<Franchise> {

    @Override
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
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return controle;
    }

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
    public boolean delete(Franchise obj) {
        boolean controle = false;
        try {
            String sql = "DELETE FROM franchise WHERE id_franchise = ?;";
            PreparedStatement statement = this.connect.prepareStatement(sql);
            statement.setInt(1, obj.getIdFranchise());

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                controle = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return controle;
    }

    @Override
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
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return controle;
    }

    @Override
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

    private Franchise hydrate(ResultSet resultSet) throws SQLException {
        return new Franchise(resultSet.getInt("id_franchise"),
                resultSet.getString("nom_franchise"),
                resultSet.getString("siege_social"),
                resultSet.getInt("id_gerant"));
    }
}