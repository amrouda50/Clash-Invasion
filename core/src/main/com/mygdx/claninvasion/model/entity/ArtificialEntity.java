package com.mygdx.claninvasion.model.entity;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import com.mygdx.claninvasion.model.helpers.Direction;
import com.mygdx.claninvasion.model.level.Level;
import com.mygdx.claninvasion.model.level.LevelIterator;
import com.mygdx.claninvasion.model.level.Levels;
import com.mygdx.claninvasion.view.actors.HealthBar;
import org.javatuples.Pair;

import com.mygdx.claninvasion.exceptions.EntityOutsideOfBoundsException;

/**
 * Artificial entities are all entities except unmoving ones
 * @author Dinari
 * @version 0.01
 */
public abstract class ArtificialEntity extends Entity {
    /**
     * health of the entity
     */
    protected AtomicInteger health;

    /**
     * initial health, should be unchangeable
     */
    protected int initHealth;
    /**
     * view health bar, linked for initialization purposes
     */
    protected HealthBar hpBar;
    /**
     * level iterator of the current entity,
     * from it depend  a lot of entity properties
     * such as healing factor, max health or damage amount
     */
    protected LevelIterator<? extends Level> level;
    /**
     * direction where entity is looking at
     */
    protected Direction direction;

    /**
     * id of the entity
     */
    private UUID id;


    /**
     * @param entitySymbol - sprite type (location, name etc.)
     * @param position - position in the cells array
     * @param mapsize - size of the map, helps identifying if entity is not creatable
     */
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
        direction = Direction.DOWN;
        id = UUID.randomUUID();
    }

    /**
     * @param bar - the healthbar reference for change
     */
    public void setHealthBar(HealthBar bar) {
        bar.setDimensions(getHealthBarSizes());
        bar.setPositionOffset(getHealthBarOffset());
        hpBar = bar;
    }

    /**
     * @return current health bar instance
     */
    public HealthBar getHealthBar() {
        return hpBar;
    }

    /**
     * @return offset of the health bar (how far from entity)
     */
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
           CountDownLatch latch = new CountDownLatch(1);
            while (getPercentage().get() < level.current().getHealGoalPoint()) {
                try {
                    setIncreaseHealth();
                    latch.await(level.current().getReactionTime(), TimeUnit.MILLISECONDS);
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
        if (hpBar != null) {
            hpBar.substStamina(percent);
        }
    }

    protected void setIncreaseHealth() {
        float percent = level.current().getHealGoalPoint() / (float)health.get();
        health.set(level.current().getHealGoalPoint() + health.get());
        if (hpBar != null) {
            hpBar.addStamina(percent);
        }
    }

    /**
     * @return health percentage
     */
    public float getHealthPercentage() {
        return health.get() <= 0
                ? 0 :
                (health.get() / (float) initHealth) * 100;
    }


    /**
     * @return thread safe percentage
     */
    public AtomicLong getPercentage() {
        return new AtomicLong(health.get() <= 0
                ? 0 :
                (long) ((health.get() / (float) initHealth) * 100));
    }


    /**
     * @return for checking if object is alive
     */
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
     * Iterates level to next one
     * @return whether level was changed
     */
    public boolean changeLevel() {
        if (level.hasNext()) {
            level.next();
            return true;
        }

        return false;
    }

    /**
     * set new level iterator
     */
    public <T extends Level> void setLevel(LevelIterator<T> level) {
        this.level = level;
    }

    /**
     * @return id of entity
     */
    public UUID getId() {
        return id;
    }

    /**
     * @return levels reaction time
     */
    public AtomicInteger getReactionTime() {
        return new AtomicInteger(level.current().getReactionTime());
    }
}
