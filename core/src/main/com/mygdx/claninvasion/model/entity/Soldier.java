package com.mygdx.claninvasion.model.entity;

import com.mygdx.claninvasion.model.entity.attacktype.AttackType;
import com.mygdx.claninvasion.model.entity.attacktype.AttackTypeDefault;
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

    public Soldier(EntitySymbol entitySymbol, Pair<Integer, Integer> position, int mapsize) {
        super(entitySymbol, position, mapsize);
        attackType = new AttackTypeDefault();
    }


    public void setAttackType(AttackType attackType) {
        if (attackType == null) {
            throw new NullPointerException("Attack type can not be null");
        }
        this.attackType = attackType;
    }

    /**
     * Attack castle method implementation
     * @param castle - opponents castle
     * @see Castle
     */
    public boolean attackCastle(Castle castle) {
        float distance = getVec2Position().dst(castle.getVec2Position().x, castle.getVec2Position().y);

        GameSoldierLevelIterator level = (GameSoldierLevelIterator) this.level;
        // without attack type soldier is weak, like in dark souls to hit only with palm
        return attackType.attack(castle, distance, level.current());
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
