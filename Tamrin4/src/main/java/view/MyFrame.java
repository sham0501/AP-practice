package view;

import javax.swing.*;

import java.awt.*;

import static controller.Parameter.*;

public class MyFrame extends JFrame {
    public MyFrame() {
        super();
        setLayout(null);
        setBounds(FRAME_X, FRAME_Y, FRAME_WIDTH, FRAME_HEIGHT);
        setUndecorated(true);
        getRootPane().setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.BLACK));
        setVisible(true);
    }
}