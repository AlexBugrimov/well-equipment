package dev.bug.persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static java.util.stream.Collectors.joining;

public abstract class Database {

    private static final Logger LOG = LogManager.getLogger(Database.class);

    protected abstract Connection connection();

    public <R> R execute(SqlExecuteQuery<R> executeQuery) {
        try (var connection = connection()) {
            return executeQuery.accept(connection);
        } catch (SQLException ex) {
            LOG.error("Failed to execute SQL query", ex);
            throw new IllegalStateException("Failed to execute SQL query", ex);
        }
    }

    // TODO: Add migration with version. https://github.com/AlexBugrimov/well-equipment/issues/9
    public void migrate(boolean isApply, String... filePaths) {
        if (isApply) {
            try (var connection = connection();
                 var statement = connection.createStatement()) {
                for (var filePath : filePaths) {
                    LOG.debug("Start migration: {}", filePath);
                    executeSqlScript(statement, filePath);
                }
                LOG.debug("Migrations have been successfully completed");
            } catch (SQLException ex) {
                LOG.error("Database connection setup error", ex);
                throw new IllegalStateException("Database connection setup error", ex);
            }
        }
    }

    private void executeSqlScript(Statement statement, String filePath) throws SQLException {
        try (var fileReader = new FileReader(filePath);
            var bufferedReader = new BufferedReader(fileReader)) {
            var sqlScript = bufferedReader
                    .lines()
                    .collect(joining("\n"));
            statement.execute(sqlScript);
        } catch (IOException ex) {
            LOG.error("Failed to execute SQL scripts", ex);
            throw new UncheckedIOException("Failed to execute SQL scripts", ex);
        }
    }
}
