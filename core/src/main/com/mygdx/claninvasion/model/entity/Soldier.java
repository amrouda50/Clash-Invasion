package com.mygdx.claninvasion.model.entity;

import com.mygdx.claninvasion.model.level.GameSoldierLevelIterator;
import org.javatuples.Pair;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Soldier class implementation
 * @version 0.01
 */
public abstract class Soldier extends ArtificialEntity {
    private final AtomicBoolean hasTrained = new AtomicBoolean(false);

    public Soldier(EntitySymbol entitySymbol, Pair<Integer, Integer> position, int mapsize) {
        super(entitySymbol, position, mapsize);
    }

    /**
     * Attack castle method implementation
     * @param castle - opponents castle
     * @see Castle
     */
    public void attackCastle(Castle castle) {
        float distance = getVec2Position().dst(castle.getVec2Position().x, castle.getVec2Position().y);

        GameSoldierLevelIterator level = (GameSoldierLevelIterator) this.level;
        if (distance < level.current().getVisibleArea()) {
            int attack = 5;
            castle.damage(attack + level.current().getAttackIncrease());
        }
    }

    private int trainCall() {
        try {
            Thread.sleep(level.current().getCreationTime());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        hasTrained.getAndSet(true);
        return getCost();
    }

    public abstract int getCost();

    @Override
    public Pair<Float, Float> getHealthBarOffset() {
        return new Pair<>(-22f , 20f);
    }

    @Override
    public Pair<Float, Float> getHealthBarSizes() {
        return new Pair<>(14f, 5f);
    }


    /**
     * Train soldier algorithm
     * @param service - who will execute future
     * @return - boolean promise
     * @see CompletableFuture
     */
    public CompletableFuture<Integer> train(ExecutorService service) {
        return CompletableFuture.supplyAsync(this::trainCall, service);
    }
}
