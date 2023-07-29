package dev.bug;

import dev.bug.persistence.*;

import java.util.List;

/**
 * Main class
 */
public class Application {

    private static final String DB_MIGRATION_V1_20230730 = "db/migration/V1_20230730/";

    public static void main(final String[] args) {
        var database = new SQLiteDatabase("test.db");

        database.applyMigrations(false,
                DB_MIGRATION_V1_20230730 + "create_well_table.sql",
                DB_MIGRATION_V1_20230730 + "load_well_data.sql",
                DB_MIGRATION_V1_20230730 + "create_equipment_table.sql",
                DB_MIGRATION_V1_20230730 + "load_equipment_data.sql"
        );

        new WellRepository(database);
        new EquipmentRepository(database);
    }
}
