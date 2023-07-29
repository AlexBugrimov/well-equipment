package dev.bug.persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class EquipmentRepository implements Repository<Integer, Equipment> {

    private final static Logger LOG = LogManager.getLogger(EquipmentRepository.class);

    private final Database database;

    public EquipmentRepository(Database database) {
        this.database = database;
    }

    @Override
    public Equipment save(Equipment equipment) {
        return database.execute(connection -> {
            try (PreparedStatement statement = connection.prepareStatement("""
                    INSERT INTO equipment (name, well_id) VALUES (?, ?)
                """, Statement.RETURN_GENERATED_KEYS)) {
                connection.setAutoCommit(false);
                statement.setString(1, equipment.name());
                statement.setInt(2, equipment.well().id());
                statement.executeUpdate();
                try (var generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        connection.commit();
                        return new Equipment(
                                generatedKeys.getInt(1),
                                equipment.name(),
                                equipment.well());
                    }
                    connection.rollback();
                    LOG.error("An error occurred while executing a request to save equipment");
                    throw new SQLException("Creating equipment failed");
                }
            }
        });
    }

    @Override
    public List<Equipment> findAll() {
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
                var equipments = new LinkedList<Equipment>();
                while (resultSet.next()) {
                    var wellId = resultSet.getInt("well_id");
                    var wellName = resultSet.getString("well_name");
                    var equipmentId = resultSet.getInt("eq_id");
                    var equipmentName = resultSet.getString("eq_name");

                    var well = new Well(wellId, wellName);
                    var equipment = new Equipment(equipmentId, equipmentName, well);
                    equipments.add(equipment);
                }
                return equipments;
            }
        });
    }

    @Override
    public Equipment findById(Integer equipmentId) {
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
                    var well = new Well(
                            resultSet.getInt("well_id"),
                            resultSet.getString("well_name")
                    );
                    return new Equipment(
                            resultSet.getInt("eq_id"),
                            resultSet.getString("eq_name"),
                            well
                    );
                }
                LOG.debug("Failed to find equipment with id {}", equipmentId);
                throw new SQLException("Failed to find equipment");
            }
        });
    }
}
