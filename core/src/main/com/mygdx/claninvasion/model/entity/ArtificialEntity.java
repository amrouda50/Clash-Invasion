package com.mygdx.claninvasion.model.entity;

import com.mygdx.claninvasion.model.level.LevelIterator;

import java.awt.*;

/**
 * Artificial entities are all entities except unmoving ones
 * @author Dinari
 * @version 0.01
 * TODO Logic part is missing
 */
public class ArtificialEntity extends Entity {
    protected int health;
    private LevelIterator level;
    private Point position;

    /**
     * Should heal the entity
     */
    public void heal() {}

    /**
     * Damage
     * @param amount - percent of injury
     * TODO Logic part is missing
     */
    public void damage(int amount) {}

    /**
     * Position change function
     * TODO Logic part is missing
     */
    public void changePosition() {}

    /**
     * Health getter
     */
    public int getHealth() {
        return health;
    }

    /**
     * Level getter
     */
    public LevelIterator getLevel() {
        return level;
    }

    /**
     * Change level
     */
    public void setLevel(LevelIterator level) {
        this.level = level;
    }
}
