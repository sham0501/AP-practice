package main.panels;

import main.GameManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuPanel extends MyPanel {
    JButton newGame, history, setting, exit;
    JLabel record;

    public MainMenuPanel(GameManager game) {
        super(game);
        game.getFrame().setTitle("Main Menu");
        setContent();
    }

    @Override
    protected void setContent() {
        setNewGameButton();
        setHistoryButton();
        setSettingButton();
        setExitButton();
        setRecordLabel();
    }

    private void setNewGameButton() {
        newGame = new JButton("New Game");
        newGame.setBounds(250, 400, 500, 200);
        newGame.setFont(new Font("", Font.BOLD, 50));
        newGame.addActionListener(newGameActionListener());
        add(newGame);
    }

    private ActionListener newGameActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.getCurrentPanel().setVisible(false);
                game.setCurrentPanel(new PreparationPagePanel(game));
            }
        };
    }

    private void setSettingButton() {
        setting = new JButton("Setting");
        setting.setBounds(200, 650, 290, 80);
        setting.setFont(new Font("", Font.BOLD, 35));
        setting.addActionListener(settingActionListener());
        add(setting);
    }

    private ActionListener settingActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.getCurrentPanel().setVisible(false);
                game.setCurrentPanel(new SettingPanel(game));
            }
        };
    }

    private void setHistoryButton() {
        history = new JButton("History");
        history.setBounds(510, 650, 290, 80);
        history.setFont(new Font("", Font.BOLD, 35));
        history.addActionListener(historyActionListener());
        add(history);
    }

    private ActionListener historyActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.getCurrentPanel().setVisible(false);
                game.setCurrentPanel(new HistoryPanel(game));
            }
        };
    }

    private void setExitButton() {
        exit = new JButton("Exit");
        exit.setBounds(40, 40, 150, 80);
        exit.setFont(new Font("", Font.BOLD, 25));
        exit.addActionListener(exitActionListener());
        add(exit);
    }

    private ActionListener exitActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };
    }

    private void setRecordLabel() {
        int best = 0;
        for (int i = 0; i < game.getPoints().size(); i++) {
            if (game.getPoints().get(i) > best) best = game.getPoints().get(i);
        }
        record = new JLabel("Best Record:   " + best);
        record.setBounds(320, 250, 500, 100);
        record.setForeground(Color.GREEN);
        record.setFont(new Font("", Font.BOLD, 30));
        add(record);
    }
}