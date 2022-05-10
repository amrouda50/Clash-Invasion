package com.mygdx.claninvasion.model.entity;

import com.mygdx.claninvasion.model.level.Levels;
import org.javatuples.Pair;
import java.util.concurrent.atomic.AtomicInteger;

public class Tower extends ArtificialEntity implements Defensible {
    private final int radius = 4;
    /**
     * @param entitySymbol - sprite type (location, name etc.)
     * @param position - position in the cells array
     * @param mapsize - size of the map, helps identifying if entity is not creatable
     */
    public Tower(EntitySymbol entitySymbol, Pair<Integer, Integer> position, int mapsize) {
        super(entitySymbol, position, mapsize);
        level = Levels.createTowerLevelIterator();
        health = new AtomicInteger();
        super.setHealth(level.current().getMaxHealth());
    }

    @Override
    public void damage(int amount) {
        if (isAlive()) {
            super.damage(amount);
        }
    }

    /**
     * @param entity - entity to fire at
     * @return if firing is allowed
     */
    public boolean canFire(ArtificialEntity entity) {
        float distance = getVec2Position().dst(
                entity.getVec2Position().x,
                entity.getVec2Position().y
        );
        return distance <= radius;
    }

    /**
     * Attacking the entity in a radius
     * @param artificialEntity - entity which was being attacked
     */
    @Override
    public void attack(ArtificialEntity artificialEntity) {
        if (!artificialEntity.isAlive()) {
            return;
        }
        artificialEntity.setDecreaseHealth(getDescreaseRate());
    }

    public int getDescreaseRate() {
        return 85;
    }

    public int getRadius() {
        return radius;
    }
}

