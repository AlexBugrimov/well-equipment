package dev.bug.persistence.repository;

import dev.bug.domain.Count;
import dev.bug.domain.WellName;
import dev.bug.persistence.Database;
import dev.bug.persistence.model.EquipmentEntity;
import dev.bug.persistence.model.WellEntity;
import dev.bug.usecase.access.EquipmentPersistence;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class EquipmentRepository implements Repository<Integer, EquipmentEntity>, EquipmentPersistence {

    private static final Logger LOG = LogManager.getLogger(EquipmentRepository.class);

    private final Database database;

    public EquipmentRepository(Database database) {
        this.database = database;
    }

    @Override
    public EquipmentEntity save(EquipmentEntity equipmentEntity) {
        return database.execute(connection -> {
            try (PreparedStatement statement = connection.prepareStatement("""
                    INSERT INTO equipment (name, well_id) VALUES (?, ?)
                """, Statement.RETURN_GENERATED_KEYS)) {
                connection.setAutoCommit(false);
                statement.setString(1, equipmentEntity.name());
                statement.setInt(2, equipmentEntity.wellEntity().id());
                statement.executeUpdate();
                try (var generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        connection.commit();
                        return new EquipmentEntity(
                                generatedKeys.getInt(1),
                                equipmentEntity.name(),
                                equipmentEntity.wellEntity());
                    }
                    throw new SQLException("Creating equipmentEntity failed");
                }
            } catch (SQLException ex) {
                connection.rollback();
                LOG.error("An error occurred while executing a request to save equipmentEntity", ex);
                throw ex;
            }
        });
    }

    @Override
    public List<EquipmentEntity> findAll() {
        return database.execute(connection -> {
            try (PreparedStatement statement = connection.prepareStatement("""
                            SELECT
                                we.id AS well_id,
                                we.name AS well_name,
                                eq.id AS eq_id,
                                eq.name AS eq_name
                            FROM equipment eq
                            LEFT JOIN well we on we.id = eq.well_id
                            """)) {
                var resultSet = statement.executeQuery();
                var equipments = new LinkedList<EquipmentEntity>();
                while (resultSet.next()) {
                    var wellId = resultSet.getInt("well_id");
                    var wellName = resultSet.getString("well_name");
                    var equipmentId = resultSet.getInt("eq_id");
                    var equipmentName = resultSet.getString("eq_name");

                    var wellEntity = new WellEntity(wellId, wellName);
                    var equipmentEntity = new EquipmentEntity(equipmentId, equipmentName, wellEntity);
                    equipments.add(equipmentEntity);
                }
                return equipments;
            }
        });
    }

    @Override
    public EquipmentEntity findById(Integer equipmentId) {
        return database.execute(connection -> {
            try (PreparedStatement statement = connection.prepareStatement("""
                            SELECT
                                eq.id AS eq_id,
                                eq.name AS eq_name,
                                we.id AS well_id,
                                we.name AS well_name
                            FROM equipment eq
                            LEFT JOIN well we ON we.id = eq.well_id
                            WHERE eq.id = ?
                            """)) {
                statement.setInt(1, equipmentId);
                var resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    var wellEntity = new WellEntity(
                            resultSet.getInt("well_id"),
                            resultSet.getString("well_name")
                    );
                    return new EquipmentEntity(
                            resultSet.getInt("eq_id"),
                            resultSet.getString("eq_name"),
                            wellEntity
                    );
                }
                LOG.debug("Failed to find equipmentEntity with id {}", equipmentId);
                throw new SQLException("Failed to find equipmentEntity");
            }
        });
    }

    @Override
    public void setupEquipment(Count count, WellName wellName) {


    }
}
