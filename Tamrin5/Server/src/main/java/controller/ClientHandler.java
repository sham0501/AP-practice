package controller;

import model.DataBase;
import model.MyFile;
import model.User;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

public class ClientHandler extends Thread {
    private final Socket socket;
    private final Scanner scanner;
    private final PrintWriter printWriter;
    private final DataBase dataBase;
    private User user;

    public ClientHandler(Socket socket, DataBase dataBase) {
        try {
            this.dataBase = dataBase;
            this.socket = socket;
            this.scanner = new Scanner(socket.getInputStream());
            this.printWriter = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        super.run();
        waitForClientMessage();
    }

    private void waitForClientMessage() {
        while (true) {
            if (scanner.hasNextLine()) {
                String type = scanner.nextLine();
                processMessage(type);
            }
        }
    }

    private void processMessage(String type) {
        switch (type) {
            case "login" -> logInProcess();
            case "signup" -> signUpProcess();
            case "upload" -> uploadProcess();
            case "download" -> downloadProcess();
        }
    }

    private void logInProcess() {
        String username = scanner.nextLine();
        String password = scanner.nextLine();
        User user = Authentication.logInAuthentication(dataBase.getUsers(), username, password);
        if (user == null) {
            sendMessage("incorrect login");
        } else {
            this.user = user;
            sendMessage("correct login");
            sendMessage(username);
            sendMessage(password);
            sendFilesDetails();
        }
    }

    private void sendFilesDetails() {
        printWriter.println(user.getFiles().size());
        printWriter.flush();
        for (MyFile file : user.getFiles()) {
            printWriter.println(file.getFile().getName());
            printWriter.println(file.getFile().length());
            printWriter.println(file.getDateModified());
            printWriter.flush();
        }
    }

    private void signUpProcess() {
        String username = scanner.nextLine();
        String password = scanner.nextLine();
        User user = Authentication.signUpAuthentication(dataBase.getUsers(), username, password);
        if (user == null) {
            sendMessage("incorrect signup");
        } else {
            this.user = user;
            dataBase.addUser(user);
            sendMessage("correct signup");
            sendMessage(username);
            sendMessage(password);
        }
    }

    private void uploadProcess() {
        File file = FileReceiver.getFile();
        user.addFile(new MyFile(file, new Date().toString()));
        System.out.println("Done in server");
    }

    private void downloadProcess() {
        String fileName = scanner.nextLine();
        for (MyFile file : user.getFiles()) {
            //" " : nemidoonam chera avvalesh space miad!!
            if (file.getFile().getName().equals(" " + fileName)) {
                System.out.println(file.getFile().getName() + 11111);
                FileSender.sendFile(file.getFile());
                break;
            }
        }
    }

    private void sendMessage(String message) {
        printWriter.println(message);
        printWriter.flush();
    }
}