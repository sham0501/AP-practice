package main.elements;

public class Brick {
    private double x, y;
    private int number, width, height;
    private final int pointNumber;
    private String color;
    private boolean exist;

    public Brick(double x, double y, int number, String color) {
        this.x = x;
        this.y = y;
        this.number = number;
        pointNumber = number;
        this.color = color;
        width = 100;
        height = 50;
        exist = true;
    }

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
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

    public int getPointNumber() {
        return pointNumber;
    }
}