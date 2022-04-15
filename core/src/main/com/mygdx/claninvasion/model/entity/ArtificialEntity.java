package com.mygdx.claninvasion.model.entity;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import com.mygdx.claninvasion.model.helpers.Direction;
import com.mygdx.claninvasion.model.level.Level;
import com.mygdx.claninvasion.model.level.LevelIterator;
import com.mygdx.claninvasion.model.level.Levels;
import org.javatuples.Pair;

/**
 * Artificial entities are all entities except unmoving ones
 * @author Dinari
 * @version 0.01
 * TODO Some requirements will be needed
 */
public class ArtificialEntity extends Entity {
    protected AtomicInteger health;
    protected LevelIterator<? extends Level> level;
    protected AtomicInteger reactionTime;
    protected Direction direction;

    ArtificialEntity(EntitySymbol entitySymbol, Pair<Integer, Integer> position) {
        super(entitySymbol, position);
        level = Levels.createLevelIterator();
        init();
    }

    ArtificialEntity(LevelIterator<Level> levelIterator) {
        super();
        level = levelIterator;
        init();
    }

    private void init() {
        health = new AtomicInteger(level.current().getMaxHealth());
        reactionTime = new AtomicInteger(level.current().getReactionTime());
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
            while (getPercentage().get() < level.current().getHealGoalPoint()) {
                try {
                    health.set(level.current().getHealHealthIncrease() + health.get());
                    Thread.sleep(level.current().getReactionTime());
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
        return (health.get() / (float) level.current().getMaxHealth()) * 100;
    }

    public AtomicLong getPercentage() {
        return new AtomicLong((health.get() / (long) level.current().getMaxHealth()) * 100);
    }

    public boolean isAlive() {
        return health.get() > level.current().getMinHealth();
    }

    /**
     * Level getter
     */
    public LevelIterator<? extends Level> getLevel() {
        return level;
    }

    /**
     * Change level
     */
    public void setLevel(LevelIterator<Level> level) {
        this.level = level;
    }
}
