package db.dbmanager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseManager {

    private static HikariDataSource dataSource;

    public static void initializeDataSource() {
        HikariConfig config = new HikariConfig();
        // Configure HikariCP properties (JDBC URL, username, password, pool size, etc.)
        config.setJdbcUrl(System.getenv("URL"));
        config.setUsername(System.getenv("DB_USERNAME"));
        config.setPassword(System.getenv("DB_PASSWORD"));
        config.setMaximumPoolSize(10); // Example pool size
        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection(); // Get a connection from the pool
    }
    public static void close() {
        if (dataSource != null) dataSource.close();
    }
}
