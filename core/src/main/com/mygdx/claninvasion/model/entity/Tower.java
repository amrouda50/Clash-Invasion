package com.mygdx.claninvasion.model.entity;

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
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Tower extends ArtificialEntity implements Defensible {
    GameTowerLevelIterator towerLevelIterator = null;
    Timer timer = new Timer();

    public static int COST = 200;
    public static int creationTime;
    public static int healthValue;
    public AtomicInteger health;
    public static int minHealth;

    public Tower(EntitySymbol entitySymbol, Pair<Integer, Integer> position) {
        super(entitySymbol, position);
        System.out.println("It will sleep for " + creationTime);
        health = new AtomicInteger();
        if(creationTime == 0) {
            upgradeCreationTime();
        }
        health.set(healthValue);
        super.setHealth(health);
        try {
            MILLISECONDS.sleep(creationTime);
        } catch (InterruptedException e) {
            System.out.println("This did not work");
        }
    }

    private void upgradeCreationTime() {
        creationTime = createTowerLevelIterator().current().getCreationTime();
        healthValue = createTowerLevelIterator().current().getMaxHealth();
        health.set(healthValue);
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
