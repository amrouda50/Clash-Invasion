package com.mygdx.claninvasion.model.entity;

import com.badlogic.gdx.Game;
import com.mygdx.claninvasion.model.level.GameTowerLevel;
import com.mygdx.claninvasion.model.level.GameTowerLevelIterator;
import com.mygdx.claninvasion.model.level.Level;
import com.mygdx.claninvasion.model.level.LevelIterator;
import org.javatuples.Pair;

import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;

import static com.mygdx.claninvasion.model.level.Levels.createTowerLevelIterator;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import java.util.Timer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.javatuples.Septet;

public class Tower extends ArtificialEntity implements Defensible {
    GameTowerLevelIterator towerLevelIterator = null;
    Timer timer = new Timer();

    public static int COST = 200;
    public static int creationTime;
    public static int maxHealth;

    public int currentHealth;

    public static GameTowerLevel gameTowerLevel;

    public Tower(EntitySymbol entitySymbol, Pair<Integer, Integer> position) {
        super(entitySymbol, position);

        currentHealth = maxHealth;
        health = new AtomicInteger();
        health.set(currentHealth);
        super.setHealth(health);

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
                MILLISECONDS.sleep(gameTowerLevel.getReactionTime());
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
        System.out.println("Descresing.. Current is" + artificialEntity.getHealth() + ", Entity " + artificialEntity);
    }
}

