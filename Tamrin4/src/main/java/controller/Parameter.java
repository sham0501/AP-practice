package controller;

import java.awt.*;

public class Parameter {
    public static int SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    public static int SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
    public static int FRAME_WIDTH;
    public static int FRAME_HEIGHT;
    public static int FRAME_X;
    public static int FRAME_Y = SCREEN_HEIGHT / 2 - FRAME_HEIGHT / 2;
    public static int PIECE_IMAGE_SIZE = 200;
}