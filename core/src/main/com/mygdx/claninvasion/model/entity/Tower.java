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
    public static int healthValue;
    public AtomicInteger health;
    public static int minHealth;
    public static GameTowerLevel gameTowerLevel;

    public Tower(EntitySymbol entitySymbol, Pair<Integer, Integer> position) {
        super(entitySymbol, position);
        health = new AtomicInteger();
        health.set(healthValue);
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
        Tower.healthValue = gameTowerLevel.getMaxHealth();
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
            System.out.println("The tower is being created for " + creationTime + " seconds");
        }
    });

    @Override
    public void damage(int amount) {
        super.damage(amount);
    }

    @Override
    public void heal() {
        super.heal();
    }


    @Override
    public CompletableFuture<Boolean> attack(ArtificialEntity artificialEntity, Fireable fire) {
        float distance = getVec2Position().dst(
                artificialEntity.getVec2Position().x,
                artificialEntity.getVec2Position().y
        );

        if (distance > 2) {
            return CompletableFuture.supplyAsync(() -> false);
        }

        if (!artificialEntity.isAlive()) {
            return CompletableFuture.supplyAsync(() -> false);
        }

        CompletableFuture<Boolean> future = CompletableFuture
                .supplyAsync(() -> fire.fire(position, artificialEntity.position).join());
        future
                .orTimeout(3, SECONDS)
                .thenAccept(a -> artificialEntity.damage(100))
                .thenAccept(a -> System.out.println("Attack by tower was completed"))
                .completeExceptionally(new RuntimeException("Could not finish the defend method"));
        return future;
    }
}
