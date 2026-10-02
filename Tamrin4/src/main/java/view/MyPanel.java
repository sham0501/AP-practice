package view;

import javax.swing.*;

import java.awt.*;

import static controller.Parameter.FRAME_HEIGHT;
import static controller.Parameter.FRAME_WIDTH;

public class MyPanel extends JPanel {
    public MyPanel() {
        super();
        setLayout(null);
        setBackground(Color.BLACK);
        setBounds(0,0, FRAME_WIDTH, FRAME_HEIGHT);
        setVisible(true);
    }
}