package dev.bug.persistence;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface SqlExecute {

    void accept(Connection connection) throws SQLException;
}
