import Tunnel.SSHTunnel;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.Objects;
import java.util.concurrent.*;

public class LiveDatabaseCellViewer {

    private static String lastKnownValue = null;

    public static void main(String[] args) {
        // GUI setup
        JFrame frame = new JFrame("Live DB Value Viewer");
        JLabel label = new JLabel("Loading...", SwingConstants.CENTER);
        JLabel bottom = new JLabel("Loading...", SwingConstants.LEFT);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(label);
        frame.setSize(400, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Database connection setup (replace with your own values)
        SSHTunnel.openTunnel();
        String url = "jdbc:mysql://localhost:5000/ss-service-avs-service";
        String user = "trainee_readonly";
        String password = "k21jBncdIP7iWIk7Dqti";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);

            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            scheduler.scheduleAtFixedRate(() -> {
                String sql = "SELECT batch_status FROM payments WHERE id = 2042";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        String currentValue = rs.getString("batch_status");
                        if (!Objects.equals(currentValue, lastKnownValue)) {
                            lastKnownValue = currentValue;
                            SwingUtilities.invokeLater(() ->
                                    label.setText("Status: " + currentValue)
                            );
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }, 0, 5, TimeUnit.SECONDS);

        } catch (SQLException e) {
            e.printStackTrace();
            label.setText("Database error!");
        }
    }
}

