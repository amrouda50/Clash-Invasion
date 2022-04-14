package com.mygdx.claninvasion.model.entity;

import com.mygdx.claninvasion.model.helpers.Direction;
import com.mygdx.claninvasion.model.level.GameSoldierLevelIterator;
import com.mygdx.claninvasion.model.level.Levels;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

/**
 * Soldier class implementation
 * @version 0.01
 */
public class Soldier extends ArtificialEntity {
    private static final int ATTACK = 100;
    private static final int STEP = 1;
    private AtomicBoolean hasTrained = new AtomicBoolean(false);
    public Soldier() {
        super();
        level = Levels.createSoldierLevelIterator();
    }

    /**
     * Attack castle method implementation
     * @param castle - opponents castle
     * @see Castle
     */
    public boolean attackCastle(Castle castle) {
        float distance = getVec2Position().dst(castle.getVec2Position().x, castle.getVec2Position().y);

        GameSoldierLevelIterator level = (GameSoldierLevelIterator)this.level;
        if (distance < level.current().getVisibleArea()) {
            castle.damage(ATTACK + level.current().getAttackIncrease());
            return true;
        }

        return false;
    }

    /**
     * Make a step inside map
     */
    public void step(Direction direction) {
        if (Direction.equals(Direction.DOWN, direction)) {
            position = position.setAt1(position.getValue1() + STEP);
        } else if (Direction.equals(Direction.UP, direction)) {
            position = position.setAt1(position.getValue1() - STEP);
        } else if (Direction.equals(Direction.LEFT, direction)) {
            position = position.setAt0(position.getValue0() - STEP);
        } else if (Direction.equals(Direction.RIGHT, direction)) {
            position = position.setAt0(position.getValue0() + STEP);
        } else {
            throw new IllegalArgumentException(
                    "Such direction not found. Expected \"up\", \"down\", \"left\", or \"right\", got" + direction.alias
            );
        }
    }

    private boolean trainCall() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return hasTrained.getAndSet(true);
    }

    /**
     * Train soldier algorithm
     * @return - boolean promise
     * @see CompletableFuture
     */
    public CompletableFuture<Boolean> train() {
        return CompletableFuture.supplyAsync(this::trainCall);
    }


    public CompletableFuture<Boolean> train(ExecutorService service) {
        return CompletableFuture.supplyAsync(this::trainCall, service);
    }
}
