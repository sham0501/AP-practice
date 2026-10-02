package controller;

import controller.enums.Direction;
import controller.enums.Mode;
import model.Board;
import model.Piece;
import view.CliGame;
import view.GraphicGame;

import javax.swing.*;
import java.util.Random;

public class GameManager {
    private Board board;
    private Mode gameMode;
    private final LoadConfig config;
    private final int boardWidth, boardHeight;
    private boolean canMoveDiagonal;

    public GameManager() {
        config = LoadConfig.getInstance(this);
        config.load();
        boardWidth = config.getWidth();
        boardHeight = config.getHeight();
        chooseGameStructure();
    }

    private void chooseGameStructure() {
        String[] options = {"CLI", "Graphic"};
        int answer = JOptionPane.showOptionDialog(
                null,
                "Choose Game Structure",
                "Puzzle",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                null
        );
        if (answer == 0) gameMode = Mode.CLI;
        else gameMode = Mode.Graphic;
        startGame();
    }

    private void startGame() {
        switch (gameMode) {
            case Mode.Graphic:
                new GraphicGame(this);
                break;
            case Mode.CLI:
                new CliGame((this));
                break;
        }
    }

    public void movePiece(Direction direction) {
        int missingPieceIndex = board.getMissingPiece();
        switch (direction) {
            case Direction.UP:
                if (missingPieceIndex < boardWidth) return;
                board.swapPieces(missingPieceIndex, missingPieceIndex - boardWidth);
                board.setMissingPiece(missingPieceIndex - boardWidth);
                break;
            case Direction.DOWN:
                if (missingPieceIndex >= boardWidth * (boardHeight - 1)) return;
                board.swapPieces(missingPieceIndex, missingPieceIndex + boardWidth);
                board.setMissingPiece(missingPieceIndex + boardWidth);
                break;
            case Direction.RIGHT:
                if (missingPieceIndex % boardWidth == boardWidth - 1) return;
                board.swapPieces(missingPieceIndex, missingPieceIndex + 1);
                board.setMissingPiece(missingPieceIndex + 1);
                break;
            case Direction.LEFT:
                if (missingPieceIndex % boardWidth == 0) return;
                board.swapPieces(missingPieceIndex, missingPieceIndex - 1);
                board.setMissingPiece(missingPieceIndex - 1);
                break;
            case Direction.UP_RIGHT:
                if (missingPieceIndex < boardWidth) return;
                if (missingPieceIndex % boardWidth == boardWidth - 1) return;
                board.swapPieces(missingPieceIndex, missingPieceIndex - boardWidth + 1);
                board.setMissingPiece(missingPieceIndex - boardWidth + 1);
                break;
            case Direction.DOWN_RIGHT:
                if (missingPieceIndex >= boardWidth * (boardHeight - 1)) return;
                if (missingPieceIndex % boardWidth == boardWidth - 1) return;
                board.swapPieces(missingPieceIndex, missingPieceIndex + boardWidth + 1);
                board.setMissingPiece(missingPieceIndex + boardWidth + 1);
                break;
            case Direction.UP_LEFT:
                if (missingPieceIndex < boardWidth) return;
                if (missingPieceIndex % boardWidth == 0) return;
                board.swapPieces(missingPieceIndex, missingPieceIndex - boardWidth - 1);
                board.setMissingPiece(missingPieceIndex - boardWidth - 1);
                break;
            case Direction.DOWN_LEFT:
                if (missingPieceIndex >= boardWidth * (boardHeight - 1)) return;
                if (missingPieceIndex % boardWidth == 0) return;
                board.swapPieces(missingPieceIndex, missingPieceIndex + boardWidth - 1);
                board.setMissingPiece(missingPieceIndex + boardWidth - 1);
                break;

        }
        if (gameFinished()) gameEnd();
    }

    private void gameEnd() {
        if (gameMode == Mode.Graphic) GraphicGame.finishedGameMessage();
        else if (gameMode == Mode.CLI) CliGame.finishedGameMessage();
        shuffleBoard();
    }

    private void shuffleBoard() {
        Random rnd = new Random();
        for (int i = board.getPieces().size() - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            Piece copy = board.getPieces().get(index).getClone();
            board.getPieces().get(index).setImage(board.getPieces().get(i).getImg());
            board.getPieces().get(index).setPieceNumber(board.getPieces().get(i).getPieceNumber());
            board.getPieces().get(i).setImage(copy.getImg());
            board.getPieces().get(i).setPieceNumber(copy.getPieceNumber());
        }
        if (!canMoveDiagonal && !isSolvable(config.getHeight(), config.getWidth())) {
            shuffleBoard();
        }
        for (int i = 0; i < board.getPieces().size(); i++) {
            if (board.getPieces().get(i).getPieceNumber() == 0) board.setMissingPiece(i);
        }
    }

    private boolean isSolvable(int n, int m) {
        int inversions = getInversions();
        int blankRow = findBlankRow(n, m);
        if (m % 2 != 0) {
            return inversions % 2 == 0;
        } else {
            if (blankRow % 2 == 0) {
                return inversions % 2 != 0;
            } else {
                return inversions % 2 == 0;
            }
        }
    }

    private int getInversions() {
        int inversions = 0;
        for (int i = 0; i < board.getPieces().size(); i++) {
            for (int j = i + 1; j < board.getPieces().size(); j++) {
                if (board.getPieces().get(i).getPieceNumber() > board.getPieces().get(j).getPieceNumber() &&
                        board.getPieces().get(i).getPieceNumber() != 0 &&
                        board.getPieces().get(j).getPieceNumber() != 0) {
                    inversions++;
                }
            }
        }
        return inversions;
    }

    private int findBlankRow(int n, int m) {
        int blankIndex = -1;
        for (int i = 0; i < board.getPieces().size(); i++) {
            if (board.getPieces().get(i).getPieceNumber() == 0) {
                blankIndex = i;
                break;
            }
        }
        return n - (blankIndex / m);
    }

    public boolean gameFinished() {
        for (int i = 0; i < board.getPieces().size() - 1; i++) {
            int pieceIdentifier = board.getPieces().get(i).getPieceNumber();
            if (pieceIdentifier != i + 1) {
                return false;
            }
        }
        return true;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setCanMoveDiagonal(boolean canMoveDiagonal) {
        this.canMoveDiagonal = canMoveDiagonal;
    }

    public boolean isCanMoveDiagonal() {
        return canMoveDiagonal;
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public int getBoardHeight() {
        return boardHeight;
    }
}