package com.mygdx.claninvasion.model.entity;

import com.badlogic.gdx.Game;
import com.mygdx.claninvasion.model.level.GameTowerLevel;
import com.mygdx.claninvasion.model.level.Level;
import com.mygdx.claninvasion.model.level.LevelIterator;
import org.javatuples.Pair;

import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Tower extends ArtificialEntity implements Defensible {
    public static int COST = 200;
    public static int creationTime;
    public static int maxHealth;
    public static int healGoalPoint;

    public int currentHealth;

    public static GameTowerLevel gameTowerLevel;

    public Tower(EntitySymbol entitySymbol, Pair<Integer, Integer> position) {
        super(entitySymbol, position);

        currentHealth = maxHealth;
        health = new AtomicInteger();
        health.set(currentHealth);
        super.setHealth(health);
        healGoalPoint = getPercentage().intValue();

        changeLevel();
        try {
            createTower.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Tower creation did not work");
        }
    }

    public static void changeLevel() {
        Tower.creationTime = gameTowerLevel.getCreationTime();
        Tower.COST = gameTowerLevel.getCreationCost();
        Tower.maxHealth = gameTowerLevel.getMaxHealth();
        //(int) (( (float) gameTowerLevel.getHealGoalPoint()/ 100.0 ) * (float) Tower.maxHealth);
    }

    Tower(LevelIterator<Level> levelIterator) {
        super(levelIterator);
    }

    CompletableFuture<Void> createTower = CompletableFuture.runAsync(new Runnable() {
        @Override
        public void run() {
            try {
                MILLISECONDS.sleep(creationTime);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            System.out.println("The tower is created successfully");
        }
    });

    CompletableFuture<Void> reactionTime = CompletableFuture.runAsync(new Runnable() {
        @Override
        public void run() {
            try {
                MILLISECONDS.sleep(getReactionTime().intValue());
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            System.out.println("The tower is preparing to attack");
        }
    });

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


    public static int RADIUS = 4;

    public boolean canFire(ArtificialEntity entity) {
        float distance = getVec2Position().dst(
                entity.getVec2Position().x,
                entity.getVec2Position().y
        );

        return distance <= RADIUS;
    }

    @Override
    public void attack(ArtificialEntity artificialEntity) {
        try {
            reactionTime.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Tower reaction time did not work as expected");
        }

        if (!artificialEntity.isAlive()) {
            return;
        }
        artificialEntity.setDecreaseHealth(85);
        heal(); // Not tested yet
        System.out.println("Decreasing.. Current is" + artificialEntity.getHealth() + ", Entity " + artificialEntity);
    }
}

