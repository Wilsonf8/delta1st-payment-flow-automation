package Frame;

import javax.swing.*;
import java.awt.*;

public class PaymentFlowFrame extends JFrame {

    private final JLabel[][] dataCells = new JLabel[4][4];

    public PaymentFlowFrame() {
        super("Payment Flow Grid");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 300);
        setLocationRelativeTo(null);

        JPanel grid = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        // Ensure grid expands with window
        gbc.weightx = 1;
        gbc.weighty = 1;

        // Empty top-left cell
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        grid.add(createCenteredLabel(""), gbc);

        // Header: Deltalst (spans 2 columns)
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        grid.add(createCenteredLabel("Delta1st"), gbc);

        // Header: CYBS (spans 2 columns)
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        grid.add(createCenteredLabel("CYBS"), gbc);

        // Subheaders
        String[] subHeaders = {"Auth", "Settlement", "Auth", "Settlement"};
        gbc.gridwidth = 1;
        for (int i = 0; i < subHeaders.length; i++) {
            gbc.gridx = i + 1;
            gbc.gridy = 1;
            grid.add(createCenteredLabel(subHeaders[i]), gbc);
        }

        // Data rows
        for (int row = 0; row < 4; row++) {
            // Row label
            gbc.gridx = 0;
            gbc.gridy = row + 2;
            grid.add(createCenteredLabel("Row " + (row + 1)), gbc);

            // Data cells
            for (int col = 0; col < 4; col++) {
                gbc.gridx = col + 1;
                gbc.gridy = row + 2;
                JLabel cell = createCenteredLabel("");
                cell.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                dataCells[row][col] = cell;
                grid.add(cell, gbc);
            }
        }

        add(grid);
        setLayout(new BorderLayout());
        add(grid, BorderLayout.CENTER);
        setVisible(true);
    }

    private JLabel createCenteredLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        return label;
    }

    public void setCellText(int row, int col, String text) {
        if (row >= 0 && row < dataCells.length && col >= 0 && col < dataCells[0].length) {
            dataCells[row][col].setText(text);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PaymentFlowFrame::new);
    }
}


