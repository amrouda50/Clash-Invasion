package com.mygdx.claninvasion.model.entity;

import com.mygdx.claninvasion.model.level.Levels;
import org.javatuples.Pair;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Tower extends ArtificialEntity implements Defensible {
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
        } else {
            remove();
        }
    }

    @Override
    public AtomicLong getPercentage() {
        return super.getPercentage();
    }

    private void remove() {
    }

    @Override
    public void heal() {
        super.heal();
    }

    public boolean canFire(ArtificialEntity entity) {
        float distance = getVec2Position().dst(
                entity.getVec2Position().x,
                entity.getVec2Position().y
        );

        int radius = 4;
        return distance <= radius;
    }

    @Override
    public void attack(ArtificialEntity artificialEntity) {
        reactionTime.get();

        if (!artificialEntity.isAlive()) {
            return;
        }

        artificialEntity.setDecreaseHealth(85);
    }
}

