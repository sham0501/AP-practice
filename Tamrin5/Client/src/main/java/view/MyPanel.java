package view;

import controller.ClientManager;
import controller.Constant;

import javax.swing.*;
import java.awt.*;

public abstract class MyPanel extends JPanel {
    protected ClientManager clientManager;

    public MyPanel(ClientManager clientManager) {
        this.clientManager = clientManager;
        setLayout(null);
        setVisible(true);
        setLocation(0, 0);
        setSize(Constant.FRAME_SIZE);
        setBackground(Color.BLACK);
        setContent();
    }

    protected abstract void setContent();

}