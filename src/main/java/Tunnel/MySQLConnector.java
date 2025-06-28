package Tunnel;

import java.sql.*;
import java.time.Duration;
import java.util.concurrent.*;

public class MySQLConnector {
    public static void main(String[] args) {
        // Start SSH tunnel
        SSHTunnel.openTunnel();

        // JDBC connection
        String url = "jdbc:mysql://localhost:3307/ds-service-sadvanced";
        String user = "trainee_readonly";
        String password = "k21jBncdIP7iWIk7Dqti";  // Replace with the actual password

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("Database connected successfully.");
            // Do queries here
//          String query = "SELECT * FROM payments LIMIT 10";
            String query = "SELECT * FROM payments ORDER BY id DESC LIMIT 1";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Print column values
            ResultSetMetaData meta = rs.getMetaData();
            int columnCount = meta.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(meta.getColumnName(i) + ": " + rs.getString(i) + "  ");
                }
                System.out.println();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

