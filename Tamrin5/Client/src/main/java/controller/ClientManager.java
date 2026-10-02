package controller;

import model.MyFile;
import model.User;
import view.LoginPanel;
import view.MainPanel;
import view.MyFrame;
import view.MyPanel;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

public class ClientManager {
    private Socket socket;
    private Scanner scanner;
    private PrintWriter printWriter;
    private MyFrame myFrame;
    private MyPanel myPanel;
    private User user;

    public ClientManager() {
        setGraphicView();
        makeSocket();
        waitForServerMessage();
    }

    private void setGraphicView() {
        myFrame = new MyFrame();
        myPanel = new LoginPanel(this);
        myFrame.add(myPanel);
        myPanel.repaint();
    }

    private void makeSocket() {
        try {
            socket = new Socket("localhost", 9000);
            scanner = new Scanner(socket.getInputStream());
            printWriter = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void waitForServerMessage() {
        while (true) {
            if (scanner.hasNextLine()) {
                String type = scanner.nextLine();
                processMessage(type);
            }
        }
    }

    private void processMessage(String type) {
        switch (type) {
            case "correct login" -> {
                user = new User(scanner.nextLine(), scanner.nextLine());
                receiveFilesDetails();
                goToMainPanel();
                System.out.println("you logged in");
            }
            case "incorrect login" -> System.out.println("wrong password or you don't have account");
            case "correct signup" -> {
                user = new User(scanner.nextLine(), scanner.nextLine());
                goToMainPanel();
                System.out.println("you signed up");
            }
            case "incorrect signup" -> System.out.println("this username already taken");
        }
    }

    public void logIn(String username, String password) {
        printWriter.println("login");
        printWriter.println(username);
        printWriter.println(password);
        printWriter.flush();
    }

    public void signUp(String username, String password) {
        printWriter.println("signup");
        printWriter.println(username);
        printWriter.println(password);
        printWriter.flush();
    }

    public void logout() {
        myPanel.setVisible(false);
        myPanel = new LoginPanel(this);
        myFrame.add(myPanel);
        this.user = null;
    }

    public void upload(File file) {
        printWriter.println("upload");
        printWriter.flush();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        FileSender.sendFile(file);
        user.addFile(new MyFile(file.getName(), file.length() + "", new Date().toString()));
        myPanel.setVisible(false);
        myPanel = new MainPanel(this);
        myFrame.add(myPanel);
        JOptionPane.showMessageDialog(myPanel, file.getName() + " uploaded");
    }

    public void download(String fileName, String fileLocation) {
        printWriter.println("download");
        printWriter.println(fileName);
        printWriter.flush();
        File file = FileReceiver.getFile(fileLocation);
        JOptionPane.showMessageDialog(myPanel, file.getName() + " downloaded");
    }

    private void goToMainPanel() {
        myPanel.setVisible(false);
        myPanel = new MainPanel(this);
        myFrame.add(myPanel);
        myPanel.repaint();
    }

    private void receiveFilesDetails() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        int size = scanner.nextInt();
        scanner.nextLine();
        while (size > 0) {
            size --;
            String name = scanner.nextLine();
            String volume = scanner.nextLine();
            String date = scanner.nextLine();
            user.addFile(new MyFile(name, volume, date));
        }
    }

    public User getUser() {
        return user;
    }
}