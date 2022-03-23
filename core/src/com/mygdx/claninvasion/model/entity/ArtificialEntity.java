package com.mygdx.claninvasion.model.entity;

import com.mygdx.claninvasion.model.level.LevelIterator;

import java.awt.*;

public class ArtificialEntity extends Entity {

    protected int health;
    private LevelIterator level;
    private Point position;

    public void heal() {

    }

    public void damage(int amount) {

    }

    public void changePosition() {

    }

    @Override
    public Point getPosition() {
        return position;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public LevelIterator getLevel() {
        return level;
    }

    public void setLevel(LevelIterator level) {
        this.level = level;
    }
}
