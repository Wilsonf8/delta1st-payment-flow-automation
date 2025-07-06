package Frame;

import javax.swing.*;
import java.awt.*;

public class GridLabelFrame extends JFrame {

    private JLabel[][] labelGrid = new JLabel[4][4];

    public GridLabelFrame() {
        super("4x4 JLabel Grid");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLayout(new GridLayout(4, 4)); // 4 rows, 4 columns

        // Initialize and add labels
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                JLabel label = new JLabel("[" + row + "," + col + "]", SwingConstants.CENTER);
                label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                label.setFont(new Font("Arial", Font.PLAIN, 16));
                labelGrid[row][col] = label;
                add(label);
            }
        }

        setVisible(true);
    }

    // Example method to update a specific label
    public void updateLabel(int row, int col, String text) {
        labelGrid[row][col].setText(text);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GridLabelFrame());
    }
}

