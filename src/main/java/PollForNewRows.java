import Tunnel.SSHTunnel;

import java.sql.*;
import java.time.Duration;
import java.util.concurrent.*;

public class PollForNewRows {
    private static final String URL  = "jdbc:mysql://localhost:3307/ds-service-sadvanced";
    private static final String USER = "trainee_readonly";
    private static final String PASS = "k21jBncdIP7iWIk7Dqti";

    private static volatile long lastSeenId = 0;          // remember the highest id we've seen

    public static void main(String[] args) throws Exception {
        SSHTunnel.openTunnel();
        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();

        exec.scheduleAtFixedRate(() -> {
            try (Connection c  = DriverManager.getConnection(URL, USER, PASS);
                 PreparedStatement ps = c.prepareStatement(
                         "SELECT * FROM payments WHERE id > ? ORDER BY id ASC")) {

                ps.setLong(1, lastSeenId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        lastSeenId = rs.getLong("id");   // advance the watermark
                        handleRow(rs);                   // your business logic
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, 0, Duration.ofSeconds(5).toMillis(), TimeUnit.MILLISECONDS);
    }

    private static void handleRow(ResultSet rs) throws SQLException {
        System.out.println("New row → id=" + rs.getLong("id") +
                ", col=" + rs.getString("id"));
        // do something useful…
    }
}

