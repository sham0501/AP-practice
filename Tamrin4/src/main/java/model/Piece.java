package model;

import java.awt.*;

public class Piece {
    private Image img;
    private int pieceNumber;
    private final Location location;

    public Piece(Image img, Location location, int pieceIdentifier) {
        this.img = img;
        this.location = location;
        this.pieceNumber = pieceIdentifier;
    }

    public void setPieceNumber(int pieceNumber) {
        this.pieceNumber = pieceNumber;
    }

    public void setImage(Image img) {
        this.img = img;
    }

    public Piece getClone() {
        return new Piece(img, location, pieceNumber);
    }

    public Image getImg() {
        return img;
    }

    public int getPieceNumber() {
        return pieceNumber;
    }

    public Location getLocation() {
        return location;
    }
}