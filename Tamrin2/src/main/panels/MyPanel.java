package main.panels;

import main.GameManager;

import javax.swing.*;
import java.awt.*;

public abstract class MyPanel extends JPanel {

    GameManager game;
    public MyPanel(GameManager game) {
        this.game = game;
        game.getFrame().add(this);
        setSize(1000, 1000);
        setLocation(0, 0);
        setBackground(Color.BLACK);
        setLayout(null);
    }

    protected abstract void setContent();
}