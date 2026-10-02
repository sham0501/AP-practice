package controller;

import model.DataBase;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerManager {
    private final ServerSocket serverSocket;
    private final ArrayList<ClientHandler> clientHandlers;
    private final DataBase dataBase;

    public ServerManager() {
        dataBase = new DataBase();
        clientHandlers = new ArrayList<>();
        try {
            serverSocket = new ServerSocket(9000);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        waitForClient();
    }

    private void waitForClient() {
        while (true) {
            try {
                //this sleep is waiting for authenticating process
                Thread.sleep(100);
                deleteAdditionalClientHandler();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket, dataBase);
                clientHandlers.add(clientHandler);
                clientHandler.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void deleteAdditionalClientHandler() {
        for (int i = 0; i < clientHandlers.size(); i++) {
            if (!clientHandlers.get(i).isAlive()) {
                clientHandlers.remove(i);
                i--;
            }
        }
    }
}