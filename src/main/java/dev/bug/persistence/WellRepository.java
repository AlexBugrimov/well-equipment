package dev.bug.persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class WellRepository implements Repository<Integer, WellEntity> {

    private static final Logger LOG = LogManager.getLogger(WellRepository.class);

    private final Database database;

    public WellRepository(Database database) {
        this.database = database;
    }

    @Override
    public WellEntity save(WellEntity wellEntity) {
        return database.execute(connection -> {
            try (PreparedStatement statement = connection.prepareStatement("""
                    INSERT INTO well (name) VALUES (?)
                """, Statement.RETURN_GENERATED_KEYS)) {
                connection.setAutoCommit(false);
                statement.setString(1, wellEntity.name());
                statement.executeUpdate();
                try (var generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        connection.commit();
                        return new WellEntity(
                                generatedKeys.getInt(1),
                                wellEntity.name()
                        );
                    }
                    throw new SQLException("Creating wellEntity failed");
                }
            } catch (SQLException ex) {
                connection.rollback();
                LOG.error("An error occurred while executing a request to save wellEntity", ex);
                throw ex;
            }
        });
    }

    @Override
    public List<WellEntity> findAll() {
        return database.execute(connection -> {
            try (PreparedStatement statement = connection.prepareStatement("""
                            SELECT id AS well_id, name AS well_name FROM well
                     """)) {
                var resultSet = statement.executeQuery();
                var wells = new LinkedList<WellEntity>();
                while (resultSet.next()) {
                    var wellEntity = new WellEntity(
                            resultSet.getInt("well_id"),
                            resultSet.getString("well_name")
                    );
                    wells.add(wellEntity);
                }
                return wells;
            }
        });
    }

    @Override
    public WellEntity findById(Integer wellId) {
        return database.execute(connection -> {
            try (PreparedStatement statement = connection.prepareStatement("""
                            SELECT id AS well_id, name AS well_name
                            FROM well
                            WHERE id = ?
                            """)) {
                statement.setInt(1, wellId);
                var resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return new WellEntity(
                            resultSet.getInt("well_id"),
                            resultSet.getString("well_name")
                    );
                }
                LOG.debug("Failed to find wellEntity with id {}", wellId);
                throw new SQLException("Failed to find wellEntity");
            }
        });
    }
}
