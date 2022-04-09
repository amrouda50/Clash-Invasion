package com.mygdx.claninvasion.model.map;

/**
 * Point simple class for handling the positioning of the Player
 * (later will have all the complex data like isometric x and y and etc.)
 */
public class Point {
    private int x;
    private int y;

    /**
     * @param x - x position
     * @param y - y position
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }


    /**
     * X getter
     * return - x
     */
    public int getX() {
        return x;
    }

    /**
     * X setter
     * @param x - x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Y getter
     * return - y
     */
    public int getY() {
        return y;
    }

    /**
     * Y setter
     * @param y - x
     */
    public void setY(int y) {
        this.y = y;
    }
}
