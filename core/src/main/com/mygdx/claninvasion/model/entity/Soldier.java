package com.mygdx.claninvasion.model.entity;

import com.mygdx.claninvasion.model.entity.attacktype.AttackType;
import com.mygdx.claninvasion.model.entity.attacktype.AttackTypeSoldier;
import com.mygdx.claninvasion.model.entity.attacktype.AttackTypeSword;
import com.mygdx.claninvasion.model.entity.attacktype.Attacks;
import com.mygdx.claninvasion.model.helpers.Direction;
import com.mygdx.claninvasion.model.level.GameSoldierLevelIterator;
import org.javatuples.Pair;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Soldier class implementation
 * @version 0.01
 */
public abstract class Soldier extends ArtificialEntity {
    private static final int ATTACK = 5;
    private static final int STEP = 1;
    private final AtomicBoolean hasTrained = new AtomicBoolean(false);
    private AttackType attackType;

    public abstract int getCost();

    public Soldier(EntitySymbol entitySymbol, Pair<Integer, Integer> position) {
        super(entitySymbol, position);
        attackType = new AttackTypeSoldier();
    }


    public void setAttackType(Attacks attackType) {
        if(attackType == Attacks.SWORD) {
            this.attackType = new AttackTypeSword();
        }
    }

    /**
     * Attack castle method implementation
     * @param castle - opponents castle
     * @see Castle
     */
    public void attackCastle(Castle castle) {
        float distance = getVec2Position().dst(castle.getVec2Position().x, castle.getVec2Position().y);

        GameSoldierLevelIterator level = (GameSoldierLevelIterator) this.level;
        try {
        if (distance < (level.current().getVisibleArea() + getAttackType().getVisibleArea())) {
            castle.damage(ATTACK + level.current().getAttackIncrease());
        } }
        catch (Exception e) {
            System.out.println("Attack Type not set");
        }
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

    public AttackType getAttackType() {
        return attackType;
    }

}
