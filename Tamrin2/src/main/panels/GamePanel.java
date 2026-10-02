package main.panels;

import main.elements.*;
import main.GameManager;
import main.elements.items.normal.*;
import main.elements.items.special.*;


import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class GamePanel extends MyPanel {
    Timer gameTimer, aimeTimer, bricksAndItemsTimer, elementsTimer;
    ArrayList<Timer> ballTimer;
    JLabel time;
    JButton pause;
    int minute, second, point;
    ArrayList<Ball> balls;
    ArrayList<Brick> bricks;
    ArrayList<NormalItem> normalItems;
    String name, colorOfBall, level;
    double xFirstBall;
    boolean isFirstBall, isConfused, isDancing;
    boolean extraLife, useExtraLife;
    int xMouse, yMouse;
    int[] chances;
    int numberOfBrick, brickIncreaseNumber;
    double brickSpeedMove;

    public GamePanel(GameManager game) {
        super(game);
        game.getFrame().setTitle("Game");
        setLayout(null);
    }

    public GamePanel(GameManager game, String name, String colorOfBall, String level) {
        this(game);
        this.name = name;
        this.colorOfBall = colorOfBall;
        this.level = level;
        bricks = new ArrayList<>();
        balls = new ArrayList<>();
        balls.add(new Ball(490, 900, colorOfBall));
        normalItems = new ArrayList<>();
        setContent();
    }

    @Override
    public void setContent() {
        setChances(level);
        setElementsTimer();
        setBricksAndItemsMoveTimer();
        setGameTimer();
        setBallTimer();
        setAimeTimer();
        setMouseListener();
        setMouseMotionListener();
        setPauseButton();
    }

    private void setChances(String level) {
        chances = new int[100];
        if (level.equals("easy")) {
            brickSpeedMove = 0.5;
            brickIncreaseNumber = 1;
            for (int i = 0; i < 30; i++) {
                chances[i] = 1;
            }
            for (int i = 30; i < 33; i++) {
                chances[i] = 2;
            }
            for (int i = 33; i < 35; i++) {
                chances[i] = 3;
            }
            for (int i = 35; i < 37; i++) {
                chances[i] = 4;
            }
            for (int i = 37; i < 88; i++) {
                chances[i] = 5;
            }
            for (int i = 88; i < 90; i++) {
                chances[i] = 6;
            }
            for (int i = 90; i < 94; i++) {
                chances[i] = 7;
            }
            for (int i = 94; i < 96; i++) {
                chances[i] = 8;
            }
            for (int i = 96; i < 98; i++) {
                chances[i] = 9;
            }
            for (int i = 98; i < 100; i++) {
                chances[i] = 10;
            }
        } else if (level.equals("normal")) {
            brickSpeedMove = 1;
            brickIncreaseNumber = 2;
            for (int i = 0; i < 30; i++) {
                chances[i] = 1;
            }
            for (int i = 30; i < 32; i++) {
                chances[i] = 2;
            }
            for (int i = 32; i < 34; i++) {
                chances[i] = 3;
            }
            for (int i = 34; i < 36; i++) {
                chances[i] = 4;
            }
            for (int i = 36; i < 86; i++) {
                chances[i] = 5;
            }
            for (int i = 86; i < 88; i++) {
                chances[i] = 6;
            }
            for (int i = 88; i < 93; i++) {
                chances[i] = 7;
            }
            for (int i = 93; i < 96; i++) {
                chances[i] = 8;
            }
            for (int i = 96; i < 98; i++) {
                chances[i] = 9;
            }
            for (int i = 98; i < 100; i++) {
                chances[i] = 10;
            }
        } else if (level.equals("hard")) {
            brickSpeedMove = 2;
            brickIncreaseNumber = 3;
            for (int i = 0; i < 40; i++) {
                chances[i] = 1;
            }
            for (int i = 40; i < 41; i++) {
                chances[i] = 2;
            }
            for (int i = 41; i < 43; i++) {
                chances[i] = 3;
            }
            for (int i = 43; i < 45; i++) {
                chances[i] = 4;
            }
            for (int i = 45; i < 88; i++) {
                chances[i] = 5;
            }
            for (int i = 88; i < 90; i++) {
                chances[i] = 6;
            }
            for (int i = 90; i < 93; i++) {
                chances[i] = 7;
            }
            for (int i = 93; i < 97; i++) {
                chances[i] = 8;
            }
            for (int i = 97; i < 98; i++) {
                chances[i] = 9;
            }
            for (int i = 98; i < 100; i++) {
                chances[i] = 10;
            }
        }
    }

    private void setElementsTimer() {
        elementsTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (canAddElements()) {
                    numberOfBrick += brickIncreaseNumber;
                    setElements();
                }
            }
        });
        elementsTimer.start();
    }

    private boolean canAddElements() {
        for (int i = 0; i < bricks.size(); i++) {
            if (bricks.get(i).isExist() && bricks.get(i).getY() < 170) return false;
        }
        for (int i = 0; i < normalItems.size(); i++) {
            if (normalItems.get(i).getY() < 170) return false;
        }
        return true;
    }

    private void setElements() {
        if (numberOfBrick == 60 && !useExtraLife) {
            normalItems.add(new ExtraLife(110 * 3 + 45, 120));
            useExtraLife = true;
        } else {
            for (int i = 0; i < 9; i++) {
                Random random = new Random();
                int index = chances[random.nextInt(100)];
                if (index == 1) {
                    bricks.add(new Brick(110 * i + 10, 110, numberOfBrick, "pink"));
                } else if (index == 2) {
                    bricks.add(new Bomb(110 * i + 10, 110, numberOfBrick, "bomb"));
                } else if (index == 3) {
                    bricks.add(new DancingLight(110 * i + 10, 110, numberOfBrick, "dancing"));
                } else if (index == 4) {
                    bricks.add(new Seism(110 * i + 10, 110, numberOfBrick, "seism"));
                } else if (index == 5) {

                } else if (index == 6) {
                    normalItems.add(new SpeedUp(110 * i + 45, 120));
                } else if (index == 7) {
                    normalItems.add(new AdditionBall(110 * i + 45, 120));
                } else if (index == 8) {
                    normalItems.add(new AimeConfused(110 * i + 45, 120));
                } else if (index == 9) {
                    normalItems.add(new Reverse(110 * i + 45, 120));
                } else if (index == 10) {
                    normalItems.add(new PowerUp(110 * i + 45, 120));
                }
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(Color.WHITE);
        drawLine(g2D);
        drawBall(g2D);
        drawBrick(g2D);
        drawNormalItem(g2D);
        drawPoint(g2D);
        drawAime(g2D);
    }

    private void drawLine(Graphics2D g2D) {
        g2D.drawLine(0, 100, 1000, 100);
        g2D.drawLine(0, 900, 1000, 900);
    }

    private void drawBall(Graphics2D g2D) {
        if (!isDancing) {
            for (int i = 0; i < balls.size(); i++) {
                g2D.drawImage(new ImageIcon(balls.get(i).getColor() + "Ball.png").getImage(),
                        (int) balls.get(i).getX(),
                        (int) balls.get(i).getY(),
                        20,
                        20,
                        null);
            }
        }
    }

    private void drawBrick(Graphics2D g2D) {
        if (!isDancing) {
            for (int i = 0; i < bricks.size(); i++) {
                if (bricks.get(i).getY() > 100) {
                    if (bricks.get(i).isExist()) {
                        g2D.drawImage(new ImageIcon(bricks.get(i).getColor() + "Brick.png").getImage(),
                                (int) bricks.get(i).getX(),
                                (int) bricks.get(i).getY(),
                                bricks.get(i).getWidth(),
                                bricks.get(i).getHeight(),
                                null);
                        g2D.setColor(Color.BLACK);
                        g2D.setFont(new Font("", Font.BOLD, 25 * bricks.get(i).getWidth() / 100));
                        if (bricks.get(i).getNumber() / 10 == 0) {
                            g2D.drawString(bricks.get(i).getNumber() + "",
                                    (int) bricks.get(i).getX() + 42 * bricks.get(i).getWidth() / 100,
                                    (int) bricks.get(i).getY() + 34 * bricks.get(i).getHeight() / 50);
                        } else if (bricks.get(i).getNumber() / 100 == 0) {
                            g2D.drawString(bricks.get(i).getNumber() + "",
                                    (int) bricks.get(i).getX() + 34 * bricks.get(i).getWidth() / 100,
                                    (int) bricks.get(i).getY() + 34 * bricks.get(i).getHeight() / 50);
                        } else {
                            g2D.drawString(bricks.get(i).getNumber() + "",
                                    (int) bricks.get(i).getX() + 26 * bricks.get(i).getWidth() / 100,
                                    (int) bricks.get(i).getY() + 34 * bricks.get(i).getHeight() / 50);
                        }
                    }
                }
            }
        }
    }

    private void drawNormalItem(Graphics2D g2D) {
        if (!isDancing) {
            for (int i = 0; i < normalItems.size(); i++) {
                if (normalItems.get(i).getY() > 110) {
                    if (normalItems.get(i) instanceof AdditionBall) {
                        g2D.drawImage(new ImageIcon(colorOfBall + "Ball.png").getImage(),
                                normalItems.get(i).getX(),
                                normalItems.get(i).getY(),
                                30,
                                30,
                                null);
                        g2D.setFont(new Font("", Font.BOLD, 30));
                        g2D.setColor(Color.BLACK);
                        g2D.drawString("+", normalItems.get(i).getX() + 3, normalItems.get(i).getY() + 24);
                    } else if (normalItems.get(i) instanceof SpeedUp) {
                        g2D.setFont(new Font("", Font.BOLD, 30));
                        g2D.setColor(Color.WHITE);
                        g2D.drawString("x2", normalItems.get(i).getX(), normalItems.get(i).getY() + 25);
                    } else if (normalItems.get(i) instanceof PowerUp) {
                        g2D.setFont(new Font("", Font.PLAIN, 30));
                        g2D.drawString("💪", normalItems.get(i).getX() + 4, normalItems.get(i).getY() + 22);
                    } else if (normalItems.get(i) instanceof AimeConfused) {
                        g2D.setFont(new Font("", Font.PLAIN, 30));
                        g2D.setColor(Color.WHITE);
                        g2D.drawString("!?", normalItems.get(i).getX() + 4, normalItems.get(i).getY() + 22);
                    } else if (normalItems.get(i) instanceof Reverse) {
                        g2D.setFont(new Font("", Font.PLAIN, 40));
                        g2D.setColor(Color.WHITE);
                        g2D.drawString("⇡", normalItems.get(i).getX() + 2, normalItems.get(i).getY() + 30);
                    } else if (normalItems.get(i) instanceof ExtraLife) {
                        g2D.setFont(new Font("", Font.PLAIN, 30));
                        g2D.setColor(Color.WHITE);
                        g2D.drawString("🤍", normalItems.get(i).getX(), normalItems.get(i).getY() + 26);
                    }
                }
            }
        }
    }

    private void drawPoint(Graphics2D g2D) {
        g2D.setColor(Color.GREEN);
        g2D.setFont(new Font("", Font.BOLD, 20));
        g2D.drawString(point + "", 490, 55);
    }

    private void drawAime(Graphics2D g2D) {
        if (game.isCanAime() && canMove()) {
            g2D.setColor(Color.CYAN);
            g2D.drawLine((int) balls.getFirst().getX() + 10,
                    (int) balls.getFirst().getY() + 10,
                    (int) ((xMouse - balls.getFirst().getX() - 10) * 800 / (balls.getFirst().getY() + 10 - yMouse)
                            + balls.getFirst().getX()) + 10,
                    100);
        }
    }

    private void setGameTimer() {
        time = new JLabel(minute + " : " + second);
        time.setForeground(Color.YELLOW);
        time.setBounds(900, 40, 100, 20);
        time.setFont(new Font("", Font.BOLD, 20));
        add(time);
        gameTimer = new Timer(1000, gameTimerActionListener());
        gameTimer.start();
    }

    private ActionListener gameTimerActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (second == 59) {
                    second = 0;
                    minute++;
                } else {
                    second++;
                }
                time.setText(minute + " : " + second);
            }
        };
    }

    private void setBricksAndItemsMoveTimer() {
        bricksAndItemsTimer = new Timer(100, bricksAndItemsMoveTimerActionListener());
        bricksAndItemsTimer.start();
    }

    private ActionListener bricksAndItemsMoveTimerActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < bricks.size(); i++) {
                    bricks.get(i).setY(bricks.get(i).getY() + 1);
                    if (bricks.get(i).isExist() && bricks.get(i).getY() + 50 > 900) {
                        bricks.get(i).setExist(false);
                        if (!extraLife) {
                            gameTimer.stop();
                            aimeTimer.stop();
                            bricksAndItemsTimer.stop();
                            stopBall();
                            JOptionPane.showMessageDialog(getParent(),
                                    "GAME OVER\npoint: " + point,
                                    "gema over",
                                    JOptionPane.PLAIN_MESSAGE);
                            setVisible(false);
                            if (game.isDoSave()) game.saveRecord(name, point);
                            game.setCurrentPanel(new MainMenuPanel(game));
                        }
                        extraLife = false;
                    }
                }
                for (int i = 0; i < normalItems.size(); i++) {
                    normalItems.get(i).setY(normalItems.get(i).getY() + 1);
                }
            }
        };
    }

    private void setAimeTimer() {
        aimeTimer = new Timer(20, aimeTimerActionListener());
        aimeTimer.start();
    }

    private ActionListener aimeTimerActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!bricksAndItemsTimer.isRunning() && canMove()) {
                    setBricksAndItemsTurnMove();
                    balls.add(new Ball(balls.getFirst().getX(), balls.getFirst().getY(), colorOfBall));
                    ballTimer.add(new Timer(10, moveBallActionListener(balls.size() - 1)));
                    bricksAndItemsTimer.start();
                }
                revalidate();
                repaint();
            }
        };
    }

    private void setBricksAndItemsTurnMove() {
        Timer moved = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < bricks.size(); i++) {
                    bricks.get(i).setY(bricks.get(i).getY() + 1);
                }
                for (int i = 0; i < normalItems.size(); i++) {
                    normalItems.get(i).setY(normalItems.get(i).getY() + 1);
                }
            }
        });
        moved.start();
        Timer stop = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moved.stop();
            }
        });
        stop.setRepeats(false);
        stop.start();
    }

    private void setMouseMotionListener() {
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if (!isConfused) {
                    xMouse = e.getX();
                    yMouse = e.getY();
                    if (yMouse > 900) yMouse = 880;
                } else {
                    xMouse = (int) (game.getFrame().getWidth() * Math.random());
                    yMouse = (int) ((game.getFrame().getHeight() - 100) * Math.random());
                }
            }
        });
    }

    private void setMouseListener() {
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (canMove()) {
                    bricksAndItemsTimer.stop();
                    isFirstBall = true;
                    isConfused = false;
                    setMoveDirection();
                    moveBall();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    private void setMoveDirection() {
        for (int i = 0; i < balls.size(); i++) {
            double xBall = balls.get(i).getX();
            double yBall = balls.get(i).getY();
            double x = xMouse - 10 - xBall;
            double y = yMouse - 10 - yBall;
            double d = Math.hypot(x, y);
            balls.get(i).setXMove(Ball.BALL_SPEED * x / d);
            balls.get(i).setYMove(Ball.BALL_SPEED * y / d);
        }
    }

    private boolean canMove() {
        for (int i = 0; i < ballTimer.size(); i++) {
            if (ballTimer.get(i).isRunning()) return false;
        }
        return true;
    }

    private void setBallTimer() {
        ballTimer = new ArrayList<>();
        for (int i = 0; i < balls.size(); i++) {
            ballTimer.add(new Timer(10, moveBallActionListener(i)));
        }
    }

    private ActionListener moveBallActionListener(int i) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (balls.get(i).getX() < 0) {
                    balls.get(i).setX(0);
                    balls.get(i).hitX();
                }
                if (balls.get(i).getX() + 20 > 1000) {
                    balls.get(i).setX(980);
                    balls.get(i).hitX();
                }
                if (balls.get(i).getY() < 100) {
                    balls.get(i).setY(100);
                    balls.get(i).hitY();
                }
                hitBrickUpDown(i);
                hitBrickRightLeft(i);
                balls.get(i).moveX();
                balls.get(i).moveY();
                hitNormalItem(i);
                if (balls.get(i).getY() > 900) {
                    ballTimer.get(i).stop();
                    if (isFirstBall) {
                        xFirstBall = balls.get(i).getX();
                        isFirstBall = false;
                    }
                    balls.get(i).setX(xFirstBall);
                    balls.get(i).setY(900);
                }
                revalidate();
                repaint();
            }
        };
    }

    private void hitBrickUpDown(int i) {
        double xBall = balls.get(i).getX();
        double yBall = balls.get(i).getY();
        for (int j = 0; j < bricks.size(); j++) {
            if (bricks.get(j).isExist()) {
                double xBrick = bricks.get(j).getX();
                double yBrick = bricks.get(j).getY();
                double widthBrick = bricks.get(j).getWidth();
                double heightBrick = bricks.get(j).getHeight();
                if (xBall + 20 > xBrick && xBall < xBrick + widthBrick) {
                    if (yBall + 20 > yBrick && yBall < yBrick) {
                        bricks.get(j).setNumber(bricks.get(j).getNumber() - Ball.BALL_POWER);
                        balls.get(i).setY(yBrick - 20);
                        balls.get(i).hitY();
                        if (bricks.get(j).getNumber() <= 0) {
                            hitSpecialItem(j);
                            point += bricks.get(j).getPointNumber();
                            bricks.get(j).setExist(false);
                        }
                        return;
                    } else if (yBall + 20 > yBrick + heightBrick && yBall < yBrick + heightBrick) {
                        bricks.get(j).setNumber(bricks.get(j).getNumber() - Ball.BALL_POWER);
                        balls.get(i).setY(yBrick + heightBrick);
                        balls.get(i).hitY();
                        if (bricks.get(j).getNumber() <= 0) {
                            hitSpecialItem(j);
                            point += bricks.get(j).getPointNumber();
                            bricks.get(j).setExist(false);
                        }
                        return;
                    }
                }
            }
        }
    }

    private void hitBrickRightLeft(int i) {
        double xBall = balls.get(i).getX();
        double yBall = balls.get(i).getY();
        for (int j = 0; j < bricks.size(); j++) {
            if (bricks.get(j).isExist()) {
                double xBrick = bricks.get(j).getX();
                double yBrick = bricks.get(j).getY();
                double widthBrick = bricks.get(j).getWidth();
                double heightBrick = bricks.get(j).getHeight();
                if (yBall + 20 > yBrick && yBall < yBrick + heightBrick) {
                    if (xBall + 20 > xBrick + widthBrick && xBall < xBrick + widthBrick) {
                        bricks.get(j).setNumber(bricks.get(j).getNumber() - Ball.BALL_POWER);
                        balls.get(i).setX(xBrick + widthBrick);
                        balls.get(i).hitX();
                        if (bricks.get(j).getNumber() <= 0) {
                            hitSpecialItem(j);
                            point += bricks.get(j).getPointNumber();
                            bricks.get(j).setExist(false);
                        }
                        return;
                    } else if (xBall + 20 > xBrick && xBall < xBrick) {
                        bricks.get(j).setNumber(bricks.get(j).getNumber() - Ball.BALL_POWER);
                        balls.get(i).setX(xBrick - 20);
                        balls.get(i).hitX();
                        if (bricks.get(j).getNumber() <= 0) {
                            hitSpecialItem(j);
                            point += bricks.get(j).getPointNumber();
                            bricks.get(j).setExist(false);
                        }
                        return;
                    }
                }
            }
        }
    }

    private void hitSpecialItem(int j) {
        if (bricks.get(j) instanceof DancingLight) {
            dancingLightAction();
        } else if (bricks.get(j) instanceof Seism) {
            seismAction();
        } else if (bricks.get(j) instanceof Bomb) {
            bombAction(j);
        }
    }

    private void dancingLightAction() {
        String[] colors = {"red", "blue", "green", "yellow"};
        Random random = new Random();
        Timer dancing = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setBackground(new Color((int) (Math.random() * 0x1000000)));
                for (int i = 0; i < bricks.size(); i++) {
                    if (!(bricks.get(i) instanceof DancingLight) &&
                            !(bricks.get(i) instanceof Seism) &&
                            !(bricks.get(i) instanceof Bomb)) {
                        bricks.get(i).setColor(colors[random.nextInt(4)]);
                    }
                }
                for (int i = 0; i < balls.size(); i++) {
                    balls.get(i).setColor(colors[random.nextInt(4)]);
                }
            }
        });
        dancing.start();
        Timer dancing2 = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isDancing = !isDancing;
            }
        });
        dancing2.start();
        Timer stop = new Timer(10000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dancing.stop();
                dancing2.stop();
                setBackground(Color.BLACK);
                for (int i = 0; i < bricks.size(); i++) {
                    if (!(bricks.get(i) instanceof DancingLight) &&
                            !(bricks.get(i) instanceof Seism) &&
                            !(bricks.get(i) instanceof Bomb)) {
                        bricks.get(i).setColor("pink");
                    }
                }
                for (int i = 0; i < balls.size(); i++) {
                    balls.get(i).setColor(colorOfBall);
                }
                isDancing = false;
            }
        });
        stop.setRepeats(false);
        stop.start();
    }

    private void seismAction() {
        Timer seism = new Timer(70, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < bricks.size(); i++) {
                    bricks.get(i).setWidth(bricks.get(i).getWidth() + 2 * Seism.SIZE_CHANGE);
                    bricks.get(i).setHeight(bricks.get(i).getHeight() + Seism.SIZE_CHANGE);
                }
                if (bricks.getFirst().getWidth() < 70 || bricks.getFirst().getWidth() > 100) Seism.SIZE_CHANGE *= -1;
            }
        });
        seism.start();
        Timer stop = new Timer(10000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seism.stop();
                for (int i = 0; i < bricks.size(); i++) {
                    bricks.get(i).setWidth(100);
                    bricks.get(i).setHeight(50);
                }
            }
        });
        stop.setRepeats(false);
        stop.start();
    }

    private void bombAction(int j) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                    new File("bomb.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
        double x = bricks.get(j).getX();
        double y = bricks.get(j).getY();
        for (int i = 0; i < bricks.size(); i++) {
            if (i == j) continue;
            double xBrick = bricks.get(i).getX();
            double yBrick = bricks.get(i).getY();
            if (xBrick > x - 120 && xBrick < x + 120 && yBrick > y - 120 && yBrick < y + 120 &&
            bricks.get(i).isExist()) {
                bricks.get(i).setNumber(bricks.get(i).getNumber() - 50);
                if (bricks.get(i).getNumber() <= 0) {
                    point += bricks.get(i).getPointNumber();
                    bricks.get(i).setExist(false);
                    hitSpecialItem(i);
                }
            }
        }
    }

    private void hitNormalItem(int i) {
        for (int j = 0; j < normalItems.size(); j++) {
            if (balls.get(i).getX() + 20 > normalItems.get(j).getX() && balls.get(i).getX() < normalItems.get(j).getX() + 30 &&
                    balls.get(i).getY() + 20 > normalItems.get(j).getY() && balls.get(i).getY() < normalItems.get(j).getY() + 30) {
                if (normalItems.get(j) instanceof AdditionBall) {
                    additionBallAction(j);
                } else if (normalItems.get(j) instanceof SpeedUp) {
                    speedUpAction();
                } else if (normalItems.get(j) instanceof PowerUp) {
                    powerUpAction();
                } else if (normalItems.get(j) instanceof AimeConfused) {
                    aimeConfusedAction();
                } else if (normalItems.get(j) instanceof Reverse) {
                    reverseAction();
                } else if (normalItems.get(j) instanceof ExtraLife) {
                    extraLifeAction();
                }
                normalItems.remove(j);
                return;
            }
        }
    }

    private void additionBallAction(int j) {
        if (isFirstBall) {
            isFirstBall = false;
            xFirstBall = normalItems.get(j).getX();
        }
        balls.add(new Ball(xFirstBall, 900, colorOfBall));
        ballTimer.add(new Timer(10, moveBallActionListener(balls.size() - 1)));
    }

    private void speedUpAction() {
        Ball.BALL_SPEED *= 2;
        for (int k = 0; k < balls.size(); k++) {
            balls.get(k).setXMove(balls.get(k).getXMove() * 2);
            balls.get(k).setYMove(balls.get(k).getYMove() * 2);
        }
        Timer speed = new Timer(15000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ball.BALL_SPEED /= 2;
                for (int k = 0; k < balls.size(); k++) {
                    balls.get(k).setXMove(balls.get(k).getXMove() / 2);
                    balls.get(k).setYMove(balls.get(k).getYMove() / 2);
                }
            }
        });
        speed.setRepeats(false);
        speed.start();
    }

    private void powerUpAction() {
        Ball.BALL_POWER = 2;
        Timer power = new Timer(15000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ball.BALL_POWER = 1;
            }
        });
        power.setRepeats(false);
        power.start();
    }

    private void aimeConfusedAction() {
        isConfused = true;
    }

    private void reverseAction() {
        Timer moved = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < bricks.size(); i++) {
                    bricks.get(i).setY(bricks.get(i).getY() - 1);
                }
                for (int i = 0; i < normalItems.size(); i++) {
                    normalItems.get(i).setY(normalItems.get(i).getY() - 1);
                }
            }
        });
        moved.start();
        Timer stop = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moved.stop();
            }
        });
        stop.setRepeats(false);
        stop.start();
    }

    private void extraLifeAction() {
        extraLife = true;
    }

    private void moveBall() {
        for (int i = 0; i < ballTimer.size(); i++) {
            ballTimer.get(i).setInitialDelay(i * 100);
            ballTimer.get(i).start();
        }
    }

    private boolean stopBall() {
        boolean isRunning = false;
        for (int i = 0; i < ballTimer.size(); i++) {
            if (ballTimer.get(i).isRunning()) {
                ballTimer.get(i).stop();
                isRunning = true;
            }
        }
        return isRunning;
    }

    private void setPauseButton() {
        pause = new JButton("Pause");
        pause.setFont(new Font("", Font.BOLD, 25));
        pause.setBounds(40, 30, 150, 60);
        pause.addActionListener(pauseActionListener());
        add(pause);
    }

    private ActionListener pauseActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameTimer.stop();
                aimeTimer.stop();
                bricksAndItemsTimer.stop();
                boolean isRunning = stopBall();
                String[] option = {"Resume", "Restart", "Preparation Page", "Main Menu"};
                int answer = JOptionPane.showOptionDialog(
                        game.getFrame(),
                        "choose one",
                        "Pause",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        option,
                        0);
                if (answer == 0 || answer == -1) {
                    gameTimer.start();
                    aimeTimer.start();
                    bricksAndItemsTimer.start();
                    if (isRunning) {
                        for (int i = 0; i < ballTimer.size(); i++) {
                            ballTimer.get(i).setInitialDelay(0);
                            ballTimer.get(i).start();
                        }
                    }
                } else if (answer == 1) {
                    setVisible(false);
                    game.setCurrentPanel(new GamePanel(game, name, colorOfBall, level));
                } else if (answer == 2) {
                    setVisible(false);
                    game.setCurrentPanel(new PreparationPagePanel(game));
                } else if (answer == 3) {
                    setVisible(false);
                    game.setCurrentPanel(new MainMenuPanel(game));
                }
            }
        };
    }
}