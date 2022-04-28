package com.mygdx.claninvasion.model.entity;

import com.mygdx.claninvasion.model.level.*;
import org.javatuples.Pair;

import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Mining farm
 * @version 0.01
 */
public class MiningFarm extends ArtificialEntity implements Runnable, Mineable {
    public static int COST = 300;
    private BlockingQueue<Integer> coins;
    private final int healthDecreaseRate = 10;
    private static final int HP_OFFSET_X = 20;

    public static int creationTime;
    public static GameMiningLevel gameMiningLevel;

    public MiningFarm(EntitySymbol entitySymbol, Pair<Integer, Integer> position, BlockingQueue<Integer> queue) {
        super(entitySymbol, position);
        coins = queue;
        level = Levels.createMiningLevelIterator();

        try {
            createMiningFarm.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Mine Farm creation did not work");
        }
    }

    public static void changeLevel() {
        MiningFarm.creationTime = gameMiningLevel.getCreationTime();
        System.out.println("The value of creation time is " + gameMiningLevel.getCreationTime());
        //Tower.COST = gameTowerLevel.getCreationCost();
        //Tower.maxHealth = gameTowerLevel.getMaxHealth();
    }

    @Override
    public void changePosition(Pair<Integer, Integer> position) {
        throw new RuntimeException("Can not change goldmine position after initialization");
    }

    CompletableFuture<Void> createMiningFarm = CompletableFuture.runAsync(new Runnable() {
        @Override
        public void run() {
            try {
                MILLISECONDS.sleep(gameMiningLevel.getCreationTime());
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            System.out.println("Mining Farm is created successfully");
        }
    });


    public void setCoins(BlockingQueue<Integer> queue) {
        coins = queue;
    }

    @Override
    public void run() {
        startMining();
    }

    @Override
    public Pair<Float, Float> getHealthBarOffset() {
        return new Pair<>(super.getHealthBarOffset().getValue0() + HP_OFFSET_X, super.getHealthBarOffset().getValue1());
    }

    @Override
    public void startMining() {
        while (isAlive()) {
            if (coins == null) {
                throw new RuntimeException("Coins blocking queue is not initialized");
            }

            try {
                int reaction = level.current().getReactionTime();
                int boundedRandomValue = ThreadLocalRandom.current().nextInt(reaction / 2, reaction);
                int gold = ((GameMiningLevelIterator)level).current().getGoldBonus();
                setDecreaseHealth(healthDecreaseRate);

                Thread.sleep(boundedRandomValue);
                coins.put(gold);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
