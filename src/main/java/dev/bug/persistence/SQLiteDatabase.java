package dev.bug.persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

import java.sql.*;

public class SQLiteDatabase extends Database {

    private final static Logger LOG = LogManager.getLogger(SQLiteDatabase.class);

    private final SQLiteConfig config;

    private final String url;

    public SQLiteDatabase(SQLiteConfig config, String url) {
        this.config = config;
        this.url = url;
    }

    public SQLiteDatabase(String url) {
        this(new SQLiteConfig(), url);
    }

    public synchronized Connection connection() {
        try {
            var dataSource = new SQLiteDataSource(config);
            dataSource.setUrl("jdbc:sqlite:%s".formatted(url));
            return dataSource.getConnection();
        } catch (SQLException ex) {
            LOG.error("Database '%s' connection error".formatted(url), ex);
            throw new IllegalStateException("Database connection error ", ex);
        }
    }
}
