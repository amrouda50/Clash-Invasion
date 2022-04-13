package com.mygdx.claninvasion.model.helpers;

public enum Direction {
    ROTATE_0(0, 0, "down"),
    ROTATE_90(1, 90, "left"),
    ROTATE_180(2, 180, "up"),
    ROTATE_270(3, 270, "right"),
    DOWN(0, 0, "down"),
    LEFT(1, 90, "left"),
    UP(2, 180, "up"),
    RIGHT(3, 270, "right");

    public final int index;
    public final int rotation;
    public final String alias;

    Direction(int index, int rotation, String alias) {
        this.index = index;
        this.rotation = rotation;
        this.alias = alias;
    }


    public Boolean equals(Direction direction1, Direction direction2) {
        return direction1.index == direction2.index;
    }
}
