package main.elements;

public class Ball {
    private double x, y, xMove, yMove;
    private String color;
    public static int BALL_POWER = 1;
    public static int BALL_SPEED = 8;

    public Ball(double x, double y, String color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void moveX() {
        x += xMove;
    }

    public void moveY() {
        y += yMove;
    }

    public void hitX() {
        xMove *= -1;
    }

    public void hitY() {
        yMove *= -1;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getXMove() {
        return xMove;
    }

    public void setXMove(double xMove) {
        this.xMove = xMove;
    }

    public double getYMove() {
        return yMove;
    }

    public void setYMove(double yMove) {
        this.yMove = yMove;
    }
}