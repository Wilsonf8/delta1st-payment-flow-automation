package Frame;

import javax.swing.*;

public class MainFrame extends JFrame {

    private GridLabelFrame gridPanel;

    public MainFrame() {
        super("4x4 JLabel Grid");

        gridPanel = new GridLabelFrame();
        add(gridPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null); // Center on screen
        setVisible(true);
    }

    // Forward method to update a label from outside
    public void updateGridLabel(int row, int col, String text) {
        gridPanel.updateLabel(row, col, text);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame());
    }
}


