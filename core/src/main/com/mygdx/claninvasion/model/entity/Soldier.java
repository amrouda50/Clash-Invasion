package com.mygdx.claninvasion.model.entity;

import com.mygdx.claninvasion.model.entity.attacktype.AttackType;
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
    private AttackType attackType;

    public abstract int getCost();

    public Soldier(EntitySymbol entitySymbol, Pair<Integer, Integer> position) {
        super(entitySymbol, position);
    }


    public void setAttackType(AttackType attackType) {
        this.attackType = attackType;
    }

    /**
     * Attack castle method implementation
     * @param castle - opponents castle
     * @see Castle
     */
    public void attackCastle(Castle castle) {
        float distance = getVec2Position().dst(castle.getVec2Position().x, castle.getVec2Position().y);

        GameSoldierLevelIterator level = (GameSoldierLevelIterator) this.level;
        // without attack type soldier is weak, like in dark souls to hit only with palm
        if (attackType == null &&  (distance < (level.current().getVisibleArea()))) {
            castle.setDecreaseHealth(level.current().getAttackIncrease());
        } else if (attackType != null) {
            attackType.attack(castle, distance, level.current());
        }
    }

    /*
    * Thread sleeps for the training of soldier
    * @return - integer COST
    * */
    private int trainCall() {
        try {
            Thread.sleep(level.current().getCreationTime());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        hasTrained.getAndSet(true);
        return getCost();
    }

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
     * @return - boolean promise
     * @see CompletableFuture
     */
    public CompletableFuture<Integer> train() {
        return CompletableFuture.supplyAsync(this::trainCall);
    }

    public CompletableFuture<Integer> train(ExecutorService service) {
        return CompletableFuture.supplyAsync(this::trainCall, service);
    }
}
