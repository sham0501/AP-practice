package controller;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileReceiver {
    private static ArrayList<ReceiverThread> threads;
    private static byte[] fileBytes;
    private static int size;
    private static String name;

    public static File getFile() {
        threads = new ArrayList<>();

        receiveFileLengthAndName();

        fileBytes = new byte[size];
        System.out.println("size set correctly : " + size);

        int numThreads = size / Constant.THREAD_PART_SIZE;
        int chunkSize = Constant.THREAD_PART_SIZE;

        for (int i = 0; i <= numThreads; i++) {
            chunkSize = (i == numThreads) ? size % chunkSize : chunkSize;
            var receiverThread = new ReceiverThread(chunkSize, i + 1000);
            threads.add(receiverThread);
        }

        for (ReceiverThread thread : threads) {
            thread.start();
        }

        for (ReceiverThread thread : threads) {
            while (thread.isAlive()) {
                //wait for uploading
            }
        }

        appendChunkToFileBytes();

        System.out.println("number of threads : " + threads.size());

        System.out.println(name);
        File file = new File("Server/src/main/resources/ " + name);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(fileBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file;
    }

    private static void appendChunkToFileBytes() {
        int pointer = 0;
        for (ReceiverThread receiverThread : threads) {
            int length = receiverThread.getChunkBytes().length;
            byte[] chunk = receiverThread.getChunkBytes();
            for (int i = 0; i < length; i++) {
                fileBytes[pointer++] = chunk[i];
            }
        }
    }

    private static void receiveFileLengthAndName() {
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            Socket socket = serverSocket.accept();
            Scanner sc = new Scanner(socket.getInputStream());
            size = sc.nextInt();
            sc.nextLine();
            name = sc.nextLine();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

class ReceiverThread extends Thread {
    private final byte[] chunkBytes;
    private int pointer;
    private final DatagramSocket datagramSocket;

    public ReceiverThread(int chunkSize, int port) {
        this.chunkBytes = new byte[chunkSize];
        try {
            datagramSocket = new DatagramSocket(port);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        int numPacket = chunkBytes.length / Constant.SUB_BYTES_SIZE;
        while (numPacket >= 0) {
            try {
                byte[] sub = (numPacket == 0)
                        ? new byte[chunkBytes.length % Constant.SUB_BYTES_SIZE]
                        : new byte[Constant.SUB_BYTES_SIZE];
                DatagramPacket packet = new DatagramPacket(sub, sub.length);
                datagramSocket.receive(packet);
                appendSubToChunk(sub);
                numPacket--;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        datagramSocket.close();
    }

    private void appendSubToChunk(byte[] sub) {
        for (int i = 0; i < sub.length && pointer < chunkBytes.length; i++) {
            chunkBytes[pointer++] = sub[i];
        }
    }

    public byte[] getChunkBytes() {
        return chunkBytes;
    }
}