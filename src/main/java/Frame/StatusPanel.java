package Frame;

import javax.swing.*;
import java.awt.*;

public class StatusPanel extends JPanel {
    private JLabel statusLabel;

    public StatusPanel() {
        setLayout(new BorderLayout());
        statusLabel = new JLabel("Status: N/A", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(statusLabel, BorderLayout.CENTER);
    }

    public void setStatus(String status) {
        statusLabel.setText("Status: " + status);
    }

    public String getStatus() {
        return statusLabel.getText();
    }
}

