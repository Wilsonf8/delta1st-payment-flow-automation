package db.wait;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Wait {

    public Map<String, Object> waitForNewRow(Connection conn, String tableName, long lastKnownId, int pollIntervalMs, int timeoutSeconds) throws Exception {
        long endTime = System.currentTimeMillis() + (timeoutSeconds * 1000L);

        while (System.currentTimeMillis() < endTime) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT first_name FROM " + tableName + " WHERE id > ? AND status = 'active' ORDER BY id ASC LIMIT 1")) {
                stmt.setLong(1, lastKnownId);
                var rs = stmt.executeQuery();

                if (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    int columnCount = rs.getMetaData().getColumnCount();
                    for (int i = 1; i <= columnCount; i++) {
                        row.put(rs.getMetaData().getColumnName(i), rs.getObject(i));
                    }
                    return row; // Found a new row → return it
                }
            }
            Thread.sleep(pollIntervalMs); // Wait before next check
        }
        return null; // Timeout
    }

    public static int getLastRowId(Connection conn, String tableName) throws SQLException {
        String sql = "SELECT id FROM " + tableName + " WHERE status = 'active' ORDER BY id DESC LIMIT 1";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("id");
            }
            return 0;
        }
    }

}
