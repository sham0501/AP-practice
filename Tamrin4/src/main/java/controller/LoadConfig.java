package controller;

import model.Board;
import model.Location;
import model.Piece;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import static controller.Parameter.*;

public class LoadConfig {
    private static LoadConfig instance;
    private GameManager game;
    private int width, height;
    private Scanner sc;

    private LoadConfig(GameManager game) {
        this.game = game;
    }

    public static LoadConfig getInstance() {
        return instance;
    }

    public static LoadConfig getInstance(GameManager game) {
        return instance = new LoadConfig(game);
    }

    public void load() {
        readFile();
        setParameter();
    }

    private void readFile() {
        try {
            File file = new File("src/main/resources/Config.txt");
            sc = new Scanner(file);
            readWidthAndHeight();
            readDiagonalMovement();
            ArrayList<Integer> pieceNumbers = readPieceNumbers();
            ArrayList<Image> pieceImages = readPieceImages();
            ArrayList<Location> pieceLocations = setPieceLocations();
            setGameBoard(pieceImages, pieceLocations, pieceNumbers);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void readWidthAndHeight() {
        sc.next();
        sc.next();
        width = sc.nextInt();
        sc.next();
        sc.next();
        height = sc.nextInt();
    }

    private void readDiagonalMovement() {
        sc.next();
        sc.next();
        game.setCanMoveDiagonal(sc.nextBoolean());
    }

    private ArrayList<Integer> readPieceNumbers() {
        sc.next();
        sc.next();
        ArrayList<Integer> pieceNumbers = new ArrayList<>();
        for (int i = 0; i < width * height; i++) {
            pieceNumbers.add(sc.nextInt());
            sc.next();
        }
        return pieceNumbers;
    }

    private ArrayList<Image> readPieceImages() {
        sc.next();
        sc.next();
        ArrayList<Image> pieceImages = new ArrayList<>();
        for (int i = 0; i < width * height; i++) {
            pieceImages.add(new ImageIcon("src/main/resources/" + sc.next()).getImage());
            sc.next();
        }
        return pieceImages;
    }

    private ArrayList<Location> setPieceLocations() {
        ArrayList<Location> pieceLocations = new ArrayList<>();
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                pieceLocations.add(new Location(i * PIECE_IMAGE_SIZE, j * PIECE_IMAGE_SIZE));
            }
        }
        return pieceLocations;
    }

    private void setGameBoard(ArrayList<Image> pieceImages,
                              ArrayList<Location> pieceLocations,
                              ArrayList<Integer> pieceNumbers) {
        ArrayList<Piece> pieces = new ArrayList<>();
        for (int i = 0; i < width * height; i++) {
            pieces.add(new Piece(pieceImages.get(i), pieceLocations.get(i), pieceNumbers.get(i)));
        }
        game.setBoard(new Board(pieces));
    }

    private void setParameter() {
        setGraphicParameter();
    }

    private void setFrameParameter() {
        FRAME_WIDTH = width * PIECE_IMAGE_SIZE;
        FRAME_HEIGHT = height * PIECE_IMAGE_SIZE;
        FRAME_X = SCREEN_WIDTH / 2 - FRAME_WIDTH / 2;
        FRAME_Y = SCREEN_HEIGHT / 2 - FRAME_HEIGHT / 2;
    }

    private void setGraphicParameter() {
        setFrameParameter();
    }

    public void save() {

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}