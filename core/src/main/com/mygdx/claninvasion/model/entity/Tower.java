package com.mygdx.claninvasion.model.entity;

import com.mygdx.claninvasion.model.level.Level;
import com.mygdx.claninvasion.model.level.LevelIterator;
import com.mygdx.claninvasion.model.level.Levels;
import com.mygdx.claninvasion.model.player.Player;
import org.javatuples.Pair;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Tower extends ArtificialEntity implements Defensible {
    private Soldier targetedSolider = null;
    /**
     * @param entitySymbol - sprite type (location, name etc.)
     * @param position - position in the cells array
     * @param mapsize - size of the map, helps identifying if entity is not creatable
     */
    public Tower(EntitySymbol entitySymbol, Pair<Integer, Integer> position, int mapsize) {
        super(entitySymbol, position, mapsize);
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
        return distance <= getRadius();
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
        artificialEntity.setDecreaseHealth(getDecreaseRate());
    }

    public abstract int getDecreaseRate();
    public abstract int getRadius();
    public abstract void setLevel(Player player);

    public void setTargetedSolider(Soldier targetedSolider) {
        this.targetedSolider = targetedSolider;
    }

    public Soldier getTargetedSolider() {
        return targetedSolider;
    }
}

