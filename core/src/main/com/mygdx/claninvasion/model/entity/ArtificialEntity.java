package com.mygdx.claninvasion.model.entity;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import com.mygdx.claninvasion.exceptions.EntityOutsideOfBoundsException;
import com.mygdx.claninvasion.model.helpers.Direction;
import com.mygdx.claninvasion.model.level.Level;
import com.mygdx.claninvasion.model.level.LevelIterator;
import com.mygdx.claninvasion.model.level.Levels;
import com.mygdx.claninvasion.view.actors.HealthBar;
import org.javatuples.Pair;

/**
 * Artificial entities are all entities except unmoving ones
 * @author Dinari
 * @version 0.01
 */
public abstract class ArtificialEntity extends Entity {
    protected AtomicInteger health;
    protected int initHealth;
    protected HealthBar hpBar;
    protected LevelIterator<? extends Level> level;
    protected AtomicInteger reactionTime;
    protected Direction direction;
    private UUID id;

    public ArtificialEntity(EntitySymbol entitySymbol, Pair<Integer, Integer> position, int mapsize) {
        super(entitySymbol, position, mapsize);
        level = Levels.createLevelIterator();
        init();
        initHealth = health.get();
    }

    protected void setHealth(int newHealth) {
        this.health.set(newHealth);
    }

    private void init() {
        health = new AtomicInteger(level.current().getMaxHealth());
        reactionTime = new AtomicInteger(level.current().getReactionTime());
        direction = Direction.DOWN;
        id = UUID.randomUUID();
    }

    public void setHealthBar(HealthBar bar) {
        bar.setDimensions(getHealthBarSizes());
        bar.setPositionOffset(getHealthBarOffset());
        hpBar = bar;
    }

    public HealthBar getHealthBar() {
        return hpBar;
    }

    public Pair<Float, Float> getHealthBarOffset() {
        return new Pair<>(-22f , 43f);
    }

    public Pair<Float, Float> getHealthBarSizes() {
        return new Pair<>(14f, 5f);
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
                    setIncreaseHealth();
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
     * @param amount - amount of injury
     */
    public void damage(int amount) {
        setDecreaseHealth(amount);
    }

    /**
     * Position change function
     */
    public void changePosition(Pair<Integer, Integer> position) {
       if (isNotInsideMap(position)) {
           throw new EntityOutsideOfBoundsException(position);
       }
       this.position = position;
    }

    /**
     * Health getter
     */
    public int getHealth() {
        return health.get();
    }

    protected void setDecreaseHealth(int amount) {
        if (hpBar == null) {
            throw new NullPointerException("Instantiate health bar before");
        }
        if (amount > health.get()) {
            health.set(level.current().getMinHealth());
            return;
        }

        float percent = amount / (float)health.get();
        health.set(health.get() - amount);
        hpBar.substStamina(percent);
    }

    protected void setIncreaseHealth() {
        float percent = level.current().getHealGoalPoint() / (float)health.get();
        health.set(level.current().getHealGoalPoint() + health.get());
        hpBar.addStamina(percent);
    }

    public float getHealthPercentage() {
        return health.get() <= 0
                ? 0 :
                (health.get() / (float) initHealth) * 100;
    }

    public AtomicLong getPercentage() {
        return new AtomicLong(health.get() <= 0
                ? 0 :
                (long) ((health.get() / (float) initHealth) * 100));
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

    public boolean changeLevel() {
        if (level.hasNext()) {
            level.next();
            return true;
        }

        return false;
    }

    /**
     * Change level
     */
    public <T extends Level>void setLevel(LevelIterator<T> level) {
        this.level = level;
    }

    public UUID getId() {
        return id;
    }

    public AtomicInteger getReactionTime() {
        return reactionTime;
    }
}
