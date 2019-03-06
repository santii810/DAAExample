package es.uvigo.esei.daa.dao;

import es.uvigo.esei.daa.entities.Pet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PetsDAO extends DAO {
    private final static Logger LOG = Logger.getLogger(PetsDAO.class.getName());


    public Pet get(int id) throws DAOException, IllegalArgumentException {
        try (final Connection conn = this.getConnection()) {
            final String query = "SELECT * FROM pet WHERE id=?";

            try (final PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setInt(1, id);

                try (final ResultSet result = statement.executeQuery()) {
                    if (result.next()) {
                        return rowToEntity(result);
                    } else {
                        throw new IllegalArgumentException("Invalid id");
                    }
                }
            }
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error getting a person", e);
            throw new DAOException(e);
        }
    }

    private Pet rowToEntity(ResultSet row) throws SQLException {
        return new Pet(
                row.getInt("id"),
                row.getInt("owner"),
                row.getString("name")
        );
    }
}
