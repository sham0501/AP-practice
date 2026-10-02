package view;

import controller.Constant;

import javax.swing.*;

public class MyFrame extends JFrame {
    public MyFrame() {
        super();
        setVisible(true);
        setLayout(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(Constant.FRAME_SIZE);
        setLocation(Constant.FRAME_LOCATION);
        setResizable(false);
    }
}