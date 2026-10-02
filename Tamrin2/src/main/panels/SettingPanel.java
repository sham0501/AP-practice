package main.panels;

import main.GameManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class SettingPanel extends MyPanel {
    JComboBox aime, music, save;
    JLabel aimeText, musicText, saveText;
    JButton back;

    public SettingPanel(GameManager game) {
        super(game);
        game.getFrame().setTitle("Setting");
        setContent();
    }

    @Override
    protected void setContent() {
        setAimeBox();
        setMusicBox();
        setSaveBox();
        setBackButton();
    }

    private void setAimeBox() {
        aime = new JComboBox<>(new String[]{"Off", "On"});
        aime.setBounds(350, 180, 380, 150);
        if (game.isCanAime()) aime.setSelectedItem("On");
        add(aime);
        aimeText = new JLabel("Aime");
        aimeText.setForeground(Color.WHITE);
        aimeText.setBounds(200, 180, 300, 150);
        aimeText.setFont(new Font("", Font.BOLD, 35));
        add(aimeText);
    }

    private void setMusicBox() {
        music = new JComboBox<>(new String[]{"Off", "On"});
        music.setBounds(350, 380, 380, 150);
        if (game.isPlayMusic()) music.setSelectedItem("On");
        add(music);
        musicText = new JLabel("Music");
        musicText.setForeground(Color.WHITE);
        musicText.setBounds(200, 380, 200, 150);
        musicText.setFont(new Font("", Font.BOLD, 35));
        add(musicText);
    }

    private void setSaveBox() {
        save = new JComboBox<>(new String[]{"Off", "On"});
        save.setBounds(350, 580, 380, 150);
        if (game.isDoSave()) save.setSelectedItem("On");
        add(save);
        saveText = new JLabel("Save");
        saveText.setForeground(Color.WHITE);
        saveText.setBounds(200, 580, 200, 150);
        saveText.setFont(new Font("", Font.BOLD, 35));
        add(saveText);
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
                setBooleans();
                game.getCurrentPanel().setVisible(false);
                game.setCurrentPanel(new MainMenuPanel(game));
            }
        };
    }

    private void setBooleans() {
        try {
            PrintWriter printWriter = new PrintWriter(new File("setting.txt"));
            if (aime.getSelectedItem().equals("On")) {
                printWriter.print("true ");
                game.setCanAime(true);
            }
            else {
                printWriter.print("false ");
                game.setCanAime(false);
            }
            if (music.getSelectedItem().equals("On")) {
                printWriter.print("true ");
                game.setPlayMusic(true);
            }
            else {
                printWriter.print("false ");
                game.setPlayMusic(false);
            }
            if (save.getSelectedItem().equals("On")) {
                printWriter.print("true ");
                game.setDoSave(true);
            }
            else {
                printWriter.print("false ");
                game.setDoSave(false);
            }
            printWriter.flush();
            printWriter.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}