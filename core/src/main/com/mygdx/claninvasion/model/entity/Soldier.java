package com.mygdx.claninvasion.model.entity;

import com.mygdx.claninvasion.model.helpers.Direction;
import com.mygdx.claninvasion.model.level.GameSoldierLevel;
import com.mygdx.claninvasion.model.level.GameSoldierLevelIterator;
import com.mygdx.claninvasion.model.level.GameTowerLevel;
import com.mygdx.claninvasion.model.level.Levels;
import com.mygdx.claninvasion.model.map.WorldCell;
import com.mygdx.claninvasion.view.actors.HealthBar;
import org.javatuples.Pair;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Soldier class implementation
 * @version 0.01
 */
public abstract class Soldier extends ArtificialEntity {
    private static final int ATTACK = 5;
    private static final int STEP = 1;
    private final AtomicBoolean hasTrained = new AtomicBoolean(false);

    public static int creationTime;
    public static GameSoldierLevel gameSoldierLevel;
    public static int maxHealth;

    public Soldier(EntitySymbol entitySymbol, Pair<Integer, Integer> position) {
        super(entitySymbol, position);
        level = Levels.createSoldierLevelIterator();

        maxHealth = maxHealth + gameSoldierLevel.getHitsPointBonus();
        setHealth(maxHealth);

        changeLevel();
        try {
            createSoldier.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Soldier creation did not work");
        }
    }

    public static void changeLevel() {
        Soldier.creationTime = gameSoldierLevel.getCreationTime();
        Barbarian.COST = gameSoldierLevel.getCreationCost();
        Dragon.COST = gameSoldierLevel.getCreationCost();
        Soldier.maxHealth = gameSoldierLevel.getMaxHealth();
    }

    /**
     * Attack castle method implementation
     * @param castle - opponents castle
     * @see Castle
     */
    public void attackCastle(Castle castle) {

        float distance = getVec2Position().dst(castle.getVec2Position().x, castle.getVec2Position().y);

        GameSoldierLevelIterator level = (GameSoldierLevelIterator) this.level;
        if (distance < gameSoldierLevel.getVisibleArea()) {
            castle.damage(ATTACK + gameSoldierLevel.getAttackIncrease());
        }
    }

    CompletableFuture<Void> createSoldier = CompletableFuture.runAsync(new Runnable() {
        @Override
        public void run() {
            try {
                MILLISECONDS.sleep(creationTime);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            System.out.println("The soldier is trained successfully after creation time of " + Soldier.creationTime);
        }
    });

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

    private int trainCall() {
        try {
            Thread.sleep(500);
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
