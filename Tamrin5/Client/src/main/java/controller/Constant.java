package controller;

import java.awt.*;

public class Constant {
    private static final int SCREEN_WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    private static final int SCREEN_HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    public static Dimension FRAME_SIZE = new Dimension(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);
    public static Point FRAME_LOCATION = new Point(
            (int) (SCREEN_WIDTH / 2 - FRAME_SIZE.getWidth() / 2),
            (int) (SCREEN_HEIGHT / 2 - FRAME_SIZE.getHeight() / 2));
    public static final int THREAD_PART_SIZE = 10_000_000;
    public static final int SUB_BYTES_SIZE = 6500;
}