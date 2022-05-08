package com.mygdx.claninvasion.model.entity;

import com.mygdx.claninvasion.model.level.Level;
import com.mygdx.claninvasion.model.level.LevelIterator;
import com.mygdx.claninvasion.model.level.Levels;
import org.javatuples.Pair;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Tower extends ArtificialEntity implements Defensible {
    public static int COST = 250;
    public static int RADIUS = 4;

    public Tower(EntitySymbol entitySymbol, Pair<Integer, Integer> position) {
        super(entitySymbol, position);

        level = Levels.createTowerLevelIterator();
        health = new AtomicInteger();
        super.setHealth(level.current().getMaxHealth());
    }

    Tower(LevelIterator<Level> levelIterator) {
        super(levelIterator);
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

        return distance <= RADIUS;
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

