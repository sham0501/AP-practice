package main;

import javax.swing.*;

public class MyFrame extends JFrame {
    public MyFrame() {
        setVisible(true);
        setLayout(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1000, 1000);
        setLocation(400, 0);
        setResizable(false);
        setIconImage(new ImageIcon("icon.jpg").getImage());
    }
}