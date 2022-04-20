package com.mygdx.claninvasion.model.entity;

import com.mygdx.claninvasion.model.level.Level;
import com.mygdx.claninvasion.model.level.LevelIterator;
import org.javatuples.Pair;

import java.util.concurrent.CompletableFuture;

public class Tower extends ArtificialEntity implements Defensible {
    public static int COST = 200;
    public Tower(EntitySymbol entitySymbol, Pair<Integer, Integer> position) {
        super(entitySymbol, position);

    }

    Tower(LevelIterator<Level> levelIterator) {
        super(levelIterator);
    }

    @Override
    public void damage(int amount) {
        super.damage(amount);
    }

    @Override
    public void heal() {
        super.heal();
    }

    public static int RADIUS = 2;

    public boolean canFire(ArtificialEntity entity) {
        float distance = getVec2Position().dst(
                entity.getVec2Position().x,
                entity.getVec2Position().y
        );

        return distance <= RADIUS;
    }


    @Override
    public void attack(ArtificialEntity artificialEntity) {
        if (!artificialEntity.isAlive()) {
            return;
        }
        artificialEntity.setDecreaseHealth(50);
        System.out.println("Descresing.. Current is" + artificialEntity.getHealth() + ", Entity " + artificialEntity);
    }
}
