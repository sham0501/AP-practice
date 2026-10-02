package main;

import main.panels.MainMenuPanel;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class GameManager {
    private MyFrame frame;
    private JPanel currentPanel;
    private boolean canAime, playMusic, doSave;
    private String colorOfBall;
    private Clip clip;
    private ArrayList<String> names;
    private ArrayList<Integer> points;
    private ArrayList<String> dates;

    public GameManager() {
        names = new ArrayList<>();
        points = new ArrayList<>();
        dates = new ArrayList<>();
        getFile();
        frame = new MyFrame();
        currentPanel = new MainMenuPanel(this);
        frame.add(currentPanel);
        getBooleans();
        setPayMusic();
    }

    private void setPayMusic() {
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (playMusic && clip == null) {
                    try {
                        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                                new File("bara.wav").getAbsoluteFile());
                        clip = AudioSystem.getClip();
                        clip.open(audioInputStream);
                        clip.loop(Clip.LOOP_CONTINUOUSLY);
                        clip.start();
                    } catch (Exception ex) {
                        System.out.println("Error with playing sound.");
                        ex.printStackTrace();
                    }
                } else if (!playMusic && clip != null) {
                    clip.stop();
                    clip = null;
                }
            }
        });
        timer.start();
    }

    public void saveRecord(String name, int point) {
        names.add(name);
        points.add(point);
        String date = new Date().toString();
        dates.add(date);
        setFile();
    }

    private void setFile() {
        File file = new File("history.txt");
        try {
            PrintWriter printWriter = new PrintWriter(file);
            for (int i = 0; i < names.size(); i++) {
                printWriter.println(names.get(i) + " " + points.get(i) + " " + dates.get(i));
            }
            printWriter.flush();
            printWriter.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void getFile() {
        File file = new File("history.txt");
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                if (!sc.hasNextInt()) names.add(sc.next());
                else names.add(" ");
                points.add(sc.nextInt());
                String date = sc.nextLine();
                dates.add(date.substring(1));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> getNames() {
        return names;
    }

    public ArrayList<Integer> getPoints() {
        return points;
    }

    public ArrayList<String> getDates() {
        return dates;
    }

    public void setColorOfBall(String colorOfBall) {
        this.colorOfBall = colorOfBall;
    }

    private void getBooleans() {
        try {
            Scanner sc = new Scanner(new File("setting.txt"));
            if (sc.nextBoolean()) canAime = true;
            else canAime = false;
            if (sc.nextBoolean()) playMusic = true;
            else playMusic = false;
            if (sc.nextBoolean()) doSave = true;
            else doSave = false;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public MyFrame getFrame() {
        return frame;
    }

    public JPanel getCurrentPanel() {
        return currentPanel;
    }

    public void setCurrentPanel(JPanel currentPanel) {
        this.currentPanel = currentPanel;
    }

    public boolean isCanAime() {
        return canAime;
    }

    public void setCanAime(boolean canAime) {
        this.canAime = canAime;
    }

    public boolean isPlayMusic() {
        return playMusic;
    }

    public void setPlayMusic(boolean playMusic) {
        this.playMusic = playMusic;
    }

    public boolean isDoSave() {
        return doSave;
    }

    public void setDoSave(boolean doSave) {
        this.doSave = doSave;
    }
}