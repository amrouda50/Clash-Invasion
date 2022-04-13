package com.mygdx.claninvasion.model.entity;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import com.mygdx.claninvasion.model.helpers.Direction;
import com.mygdx.claninvasion.model.level.LevelIterator;
import org.javatuples.Pair;

/**
 * Artificial entities are all entities except unmoving ones
 * @author Dinari
 * @version 0.01
 * TODO Some requirements will be needed
 */
public class ArtificialEntity extends Entity {
    protected AtomicInteger health;
    protected LevelIterator level;
    protected AtomicInteger reactionTime;
    protected Direction direction;
    protected static int MAX_HEALTH = 10000;
    protected static int MIN_HEALTH = 0;
    protected static int STANDARD_REACTION_TIME = 1000;
    protected static int HEAL_HEALTH_INCREASE = 10;

    ArtificialEntity() {
        super();
        health = new AtomicInteger(MAX_HEALTH);
        reactionTime = new AtomicInteger(STANDARD_REACTION_TIME);
        direction = Direction.DOWN;
    }

    /**
     * Should heal the entity
     */
    public void heal() {
        if (!isAlive()) {
            return;
        }

         Thread thread = new Thread(() -> {
            while (getPercentage().get() < 30) {
                try {
                    health.set(HEAL_HEALTH_INCREASE);
                    Thread.sleep(STANDARD_REACTION_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    /**
     * Damage
     * @param amount - percent of injury
     */
    public void damage(int amount) {
        health.set(getHealth() - amount);
    }

    /**
     * Position change function
     */
    public void changePosition(Pair<Integer, Integer> position) {
       this.position = position;
    }

    /**
     * Health getter
     */
    public int getHealth() {
        return health.get();
    }

    public float getHealthPercentage() {
        return (health.get() / (float)MAX_HEALTH) * 100;
    }

    public AtomicLong getPercentage() {
        return new AtomicLong((health.get() / (long)MAX_HEALTH) * 100);
    }

    public boolean isAlive() {
        return health.get() > MIN_HEALTH;
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
