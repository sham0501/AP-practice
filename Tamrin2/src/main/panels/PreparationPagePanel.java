package main.panels;

import main.GameManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PreparationPagePanel extends MyPanel {

    JComboBox level, colorOffBalls;
    JLabel levelText, colorText, nameText;
    JTextField name;
    JButton start, back;

    public PreparationPagePanel(GameManager game) {
        super(game);
        game.getFrame().setTitle("Preparation Page");
        setContent();
    }

    @Override
    protected void setContent() {
        setLevelBox();
        setColorOfBallsBox();
        setNameField();
        setStartButton();
        setBackButton();
    }

    private void setLevelBox() {
        level = new JComboBox<>(new String[]{"easy", "normal", "hard"});
        level.setBounds(350, 180, 380, 150);
        add(level);
        levelText = new JLabel("Level");
        levelText.setForeground(Color.WHITE);
        levelText.setBounds(200, 180, 300, 150);
        levelText.setFont(new Font("", Font.BOLD, 35));
        add(levelText);
    }

    private void setColorOfBallsBox() {
        colorOffBalls = new JComboBox<>(new String[]{"white", "red", "blue", "green", "yellow"});
        colorOffBalls.setBounds(350, 380, 380, 150);
        add(colorOffBalls);
        colorText = new JLabel("Color");
        colorText.setForeground(Color.WHITE);
        colorText.setBounds(200, 380, 200, 150);
        colorText.setFont(new Font("", Font.BOLD, 35));
        add(colorText);
    }

    private void setNameField() {
        name = new JTextField();
        name.setBounds(350, 630, 380, 50);
        add(name);
        nameText = new JLabel("Name");
        nameText.setForeground(Color.WHITE);
        nameText.setBounds(200, 580, 200, 150);
        nameText.setFont(new Font("", Font.BOLD, 35));
        add(nameText);
    }

    private void setStartButton() {
        start = new JButton("Start");
        start.setBounds(700, 800, 250, 100);
        start.setFont(new Font("", Font.BOLD, 40));
        start.addActionListener(startActionlistener());
        add(start);
    }

    private ActionListener startActionlistener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.getCurrentPanel().setVisible(false);
                game.setColorOfBall((String) colorOffBalls.getSelectedItem());
                game.setCurrentPanel(new GamePanel(game, name.getText(),
                        (String)colorOffBalls.getSelectedItem(),
                        (String)level.getSelectedItem()));
            }
        };
    }

    private void setBackButton() {
        back = new JButton("Back");
        back.setFont(new Font("", Font.BOLD, 25));
        back.setBounds(40, 40, 150, 80);
        back.addActionListener(backActionListener());
        add(back);
    }

    private ActionListener backActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.getCurrentPanel().setVisible(false);
                game.setCurrentPanel(new MainMenuPanel(game));
            }
        };
    }
}