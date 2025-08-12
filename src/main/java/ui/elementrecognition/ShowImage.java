package ui.elementrecognition;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class ShowImage{
    public void showImage(BufferedImage image) {
        JFrame frame = new JFrame("Processed Screenshot");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(image.getWidth(), image.getHeight());

        JLabel label = new JLabel(new ImageIcon(image));
        frame.getContentPane().add(label);
        frame.pack();
        frame.setVisible(true);
    }
}

