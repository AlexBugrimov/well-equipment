package dev.bug.persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class WellRepository implements Repository<Integer, Well> {

    private static final Logger LOG = LogManager.getLogger(WellRepository.class);

    private final Database database;

    public WellRepository(Database database) {
        this.database = database;
    }

    @Override
    public Well save(Well well) {
        return database.execute(connection -> {
            try (PreparedStatement statement = connection.prepareStatement("""
                    INSERT INTO well (name) VALUES (?)
                """, Statement.RETURN_GENERATED_KEYS)) {
                connection.setAutoCommit(false);
                statement.setString(1, well.name());
                statement.executeUpdate();
                try (var generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        connection.commit();
                        return new Well(
                                generatedKeys.getInt(1),
                                well.name()
                        );
                    }
                    throw new SQLException("Creating well failed");
                }
            } catch (SQLException ex) {
                connection.rollback();
                LOG.error("An error occurred while executing a request to save well", ex);
                throw ex;
            }
        });
    }

    @Override
    public List<Well> findAll() {
        return database.execute(connection -> {
            try (PreparedStatement statement = connection.prepareStatement("""
                            SELECT id AS well_id, name AS well_name FROM well
                     """)) {
                var resultSet = statement.executeQuery();
                var wells = new LinkedList<Well>();
                while (resultSet.next()) {
                    var well = new Well(
                            resultSet.getInt("well_id"),
                            resultSet.getString("well_name")
                    );
                    wells.add(well);
                }
                return wells;
            }
        });
    }

    @Override
    public Well findById(Integer wellId) {
        return database.execute(connection -> {
            try (PreparedStatement statement = connection.prepareStatement("""
                            SELECT id AS well_id, name AS well_name
                            FROM well
                            WHERE id = ?
                            """)) {
                statement.setInt(1, wellId);
                var resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return new Well(
                            resultSet.getInt("well_id"),
                            resultSet.getString("well_name")
                    );
                }
                LOG.debug("Failed to find well with id {}", wellId);
                throw new SQLException("Failed to find well");
            }
        });
    }
}
