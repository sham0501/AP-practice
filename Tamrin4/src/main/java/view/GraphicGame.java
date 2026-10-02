package view;

import controller.enums.Direction;
import controller.GameManager;
import controller.Parameter;
import model.Board;
import model.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

public class GraphicGame extends MyPanel {
    private final MyFrame frame;
    private final GameManager game;
    private final Board board;

    public GraphicGame(GameManager game) {
        this.game = game;
        board = game.getBoard();
        frame = new MyFrame();
        frame.add(this);
        setVisible(true);
        frame.addKeyListener(new MyKeyListener());
        startUpdate();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Piece piece : board.getPieces()) {
            g.drawImage(piece.getImg(),
                    piece.getLocation().getX(),
                    piece.getLocation().getY(),
                    Parameter.PIECE_IMAGE_SIZE - 20,
                    Parameter.PIECE_IMAGE_SIZE - 20,
                    null);
        }
    }

    public void startUpdate() {
        Timer update = new Timer();
        update.schedule(new TimerTask() {
            @Override
            public void run() {
                repaint();
                revalidate();
            }
        }, 0, 100);
    }

    public static void finishedGameMessage() {
        JOptionPane.showMessageDialog
                (null,
                        "You finished the game, congratulation",
                        "Game Finished",
                        JOptionPane.INFORMATION_MESSAGE);
    }

    private class MyKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent keyEvent) {
        }

        @Override
        public void keyPressed(KeyEvent keyEvent) {
            switch (keyEvent.getKeyCode()) {
                case KeyEvent.VK_W:
                    game.movePiece(Direction.UP);
                    break;
                case KeyEvent.VK_S:
                    game.movePiece(Direction.DOWN);
                    break;
                case KeyEvent.VK_D:
                    game.movePiece(Direction.RIGHT);
                    break;
                case KeyEvent.VK_A:
                    game.movePiece(Direction.LEFT);
                    break;
                case KeyEvent.VK_E:
                    game.movePiece(Direction.UP_RIGHT);
                    break;
                case KeyEvent.VK_C:
                    game.movePiece(Direction.DOWN_RIGHT);
                    break;
                case KeyEvent.VK_Q:
                    game.movePiece(Direction.UP_LEFT);
                    break;
                case KeyEvent.VK_Z:
                    game.movePiece(Direction.DOWN_LEFT);
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent keyEvent) {

        }
    }
}