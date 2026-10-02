package view;

import controller.enums.Direction;
import controller.GameManager;
import model.Board;

import java.util.Scanner;

public class CliGame {
    private final GameManager game;
    private final Board board;

    public CliGame(GameManager game) {
        this.game = game;
        board = game.getBoard();
        printDescription();
        printBoard();
        startListener();
    }

    private void startListener() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            String input = sc.next();
            switch (input) {
                case "w":
                    game.movePiece(Direction.UP);
                    break;
                case "s":
                    game.movePiece(Direction.DOWN);
                    break;
                case "d":
                    game.movePiece(Direction.RIGHT);
                    break;
                case "a":
                    game.movePiece(Direction.LEFT);
                    break;
                case "e":
                    game.movePiece(Direction.UP_RIGHT);
                    break;
                case "c":
                    game.movePiece(Direction.DOWN_RIGHT);
                    break;
                case "q":
                    game.movePiece(Direction.UP_LEFT);
                    break;
                case "z":
                    game.movePiece(Direction.DOWN_LEFT);
                    break;
            }
            printBoard();
        }
    }

    private void printDescription() {
        System.out.println("\u001B[34m" + "for swapping type and enter one of this:" + "\u001B[0m");
        System.out.println("\u001B[34m" + "up -> w\tdown -> s\tright -> d\tleft -> a" + "\u001B[0m");
        if (game.isCanMoveDiagonal())
            System.out.print("\u001B[34m" + "up-right -> e\tdown-right -> c" +
                    "\tup-left -> q\tdown-left -> z" + "\u001B[0m");
    }

    private void printBoard() {
        for (int i = 0; i < game.getBoard().getPieces().size(); i++) {
            if (i % game.getBoardWidth() == 0) System.out.println();
            if (board.getPieces().get(i).getPieceNumber() == 0)
                System.out.print("\u001B[31m" + 0 + "\u001B[0m ");
            else
                System.out.print(board.getPieces().get(i).getPieceNumber() + " ");
        }
        System.out.println();
    }

    public static void finishedGameMessage() {
        System.out.println("\u001B[32m" + "You finished the game, congratulation" + "\u001B[0m");
        System.out.println("\n");
        System.out.println("\u001B[34m" + "new game:" + "\u001B[0m");
    }
}