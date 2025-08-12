package logger;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Logger {
    private static JPanel contentPanel;
    private static JTextArea logArea;


    public static void init() {
        JFrame frame = new JFrame("POS Test Logger");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);

        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        logArea = new JTextArea(10, 70);
        logArea.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(logArea);

        // ✅ Add "Close" button
        JButton closeButton = new JButton("❌ Close Logger");
        closeButton.addActionListener(e -> frame.dispose()); // closes only the JFrame

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(logScrollPane, BorderLayout.CENTER);
        bottomPanel.add(closeButton, BorderLayout.SOUTH);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(new JScrollPane(contentPanel), BorderLayout.CENTER);
        wrapper.add(bottomPanel, BorderLayout.SOUTH);

        frame.setContentPane(wrapper);
        frame.setVisible(true);

    }

    public static void logHeading(String text) {
        if (contentPanel == null) return;

        SwingUtilities.invokeLater(() -> {
            JLabel label = new JLabel(text);
            label.setFont(new Font("Arial", Font.BOLD, 16));
            contentPanel.add(label);
            contentPanel.revalidate();
            contentPanel.repaint();
        });
    }

    public static void logIndented(String text) {
        if (contentPanel == null) return;

        SwingUtilities.invokeLater(() -> {
            JLabel label = new JLabel(text);
            label.setFont(new Font("Arial", Font.PLAIN, 12));
            label.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0)); // indent 20px
            contentPanel.add(label);
            contentPanel.revalidate();
            contentPanel.repaint();
        });
    }

}


