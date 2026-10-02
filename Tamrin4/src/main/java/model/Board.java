package model;

import java.util.ArrayList;

public class Board {
    private final ArrayList<Piece> pieces;
    private int missingPiece;

    public Board(ArrayList<Piece> pieces) {
        this.pieces = pieces;
        missingPiece = 7;
    }

    public void swapPieces(int i, int j) {
        Piece copy = pieces.get(i).getClone();
        pieces.get(i).setImage(pieces.get(j).getImg());
        pieces.get(i).setPieceNumber(pieces.get(j).getPieceNumber());
        pieces.get(j).setImage(copy.getImg());
        pieces.get(j).setPieceNumber(copy.getPieceNumber());
    }

    public ArrayList<Piece> getPieces() {
        return pieces;
    }

    public int getMissingPiece() {
        return missingPiece;
    }

    public void setMissingPiece(int missingPiece) {
        this.missingPiece = missingPiece;
    }
}