package controller;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

public class FileSender {
    private static ArrayList<SenderThread> threads;

    public static void sendFile(File file) {
        threads = new ArrayList<>();

        byte[] fileBytes;
        try {
            fileBytes = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int numThreads = fileBytes.length / Constant.THREAD_PART_SIZE;
        int chunkSize = Constant.THREAD_PART_SIZE;

        sendFileLength(fileBytes.length);

        for (int i = 0; i <= numThreads; i++) {
            int startPos = i * chunkSize;
            int endPos = (i == numThreads) ? fileBytes.length : (i + 1) * chunkSize;
            threads.add(new SenderThread(fileBytes, startPos, endPos, i + 3000));
        }

        //wait for receivers
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        for (SenderThread thread : threads) {
            thread.start();
        }

        for (SenderThread thread : threads) {
            while (thread.isAlive()) {
            }
        }

        System.out.println("threads number : " + threads.size());
    }

    private static void sendFileLength(int length) {
        try {
            Thread.sleep(50);
            Socket socket = new Socket("localhost", 7777);
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println(length);
            printWriter.flush();
            socket.close();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class SenderThread extends Thread {
    private final byte[] chunkBytes;
    private final int port;
    private final DatagramSocket datagramSocket;

    public SenderThread(byte[] fileBytes, int startPos, int endPos, int port) {
        this.port = port;
        this.chunkBytes = Arrays.copyOfRange(fileBytes, startPos, endPos);
        try {
            datagramSocket = new DatagramSocket();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        int numPacket = chunkBytes.length / Constant.SUB_BYTES_SIZE;
        for (int i = 0; i <= numPacket; i++) {
            int start = i * Constant.SUB_BYTES_SIZE;
            int end = (i == numPacket)
                    ? chunkBytes.length
                    : (i + 1) * Constant.SUB_BYTES_SIZE;
            byte[] sub = Arrays.copyOfRange(chunkBytes, start, end);
            try {
                DatagramPacket packet = new DatagramPacket(sub, sub.length, InetAddress.getLocalHost(), port);
                datagramSocket.send(packet);
                //waiting to receiver get ready
                Thread.sleep(5);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        datagramSocket.close();
    }
}