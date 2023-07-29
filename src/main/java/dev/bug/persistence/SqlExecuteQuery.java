package dev.bug.persistence;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface SqlExecuteQuery<R> {

    R accept(Connection connection) throws SQLException;
}
