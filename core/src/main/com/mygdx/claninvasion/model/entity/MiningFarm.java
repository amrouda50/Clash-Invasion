package com.mygdx.claninvasion.model.entity;

import com.mygdx.claninvasion.model.entity.attacktype.Attacks;
import com.mygdx.claninvasion.model.level.*;
import org.javatuples.Pair;
import java.util.concurrent.*;

/**
 * Mining farm
 * @version 0.01
 */
public class MiningFarm extends ArtificialEntity implements Runnable, Mineable {
    private BlockingQueue<Integer> coins;
    private final int healthDecreaseRate;
    private static final int HP_OFFSET_X = 20;

    public MiningFarm(EntitySymbol entitySymbol, Pair<Integer, Integer> position, BlockingQueue<Integer> queue) {
       //Change later
        super(entitySymbol, position);
        coins = queue;
        level = Levels.createMiningLevelIterator();
        healthDecreaseRate = level.current().getHealHealthIncrease();
        super.setHealth(level.current().getMaxHealth());
    }

    @Override
    public void changePosition(Pair<Integer, Integer> position) {
        throw new RuntimeException("Can not change goldmine position after initialization");
    }

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
