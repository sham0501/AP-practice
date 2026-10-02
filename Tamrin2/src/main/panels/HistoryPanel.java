package main.panels;

import main.GameManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HistoryPanel extends MyPanel {
    JButton back;
    JTable table;
    JScrollPane scrollPane;
    String[][] history;
    String[] title;


    public HistoryPanel(GameManager game) {
        super(game);
        game.getFrame().setTitle("History");
        setContent();
    }

    @Override
    protected void setContent() {
        setBackButton();
        setHistory();
        setScrollList();
    }

    private void setHistory() {
        title = new String[]{"Name", "Point", "Date"};
        history = new String[game.getNames().size()][3];
        for (int i = 0; i < game.getNames().size(); i++) {
            history[i][0] = game.getNames().get(i);
            history[i][1] = game.getPoints().get(i) + "";
            history[i][2] = game.getDates().get(i);
        }
    }

    private void setScrollList() {
        table = new JTable(history, title);
        table.setBackground(Color.WHITE);
        table.setFont(new Font("", Font.PLAIN, 15));
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 150, 900, 800);
        add(scrollPane);
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