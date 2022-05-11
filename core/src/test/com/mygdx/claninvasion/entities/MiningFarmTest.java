package com.mygdx.claninvasion.entities;

import com.mygdx.claninvasion.model.entity.EntitySymbol;
import com.mygdx.claninvasion.model.entity.MiningFarm;
import com.mygdx.claninvasion.view.actors.HealthBar;
import org.javatuples.Pair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.mockito.Mockito.mock;

public class MiningFarmTest {
    private BlockingQueue<Integer> coinProduceQueue;

    @Before
    public void init() {
        coinProduceQueue = new LinkedBlockingDeque<>(3);
    }

    @Test
    public void testMining() throws InterruptedException {
        MiningFarm farm = new MiningFarm(EntitySymbol.MINING, new Pair<>(20, 20), coinProduceQueue, 32);
        farm.setHealthBar(mock(HealthBar.class));
        AtomicInteger wealth = new AtomicInteger(0);
        Thread t1 = new Thread(farm::startMining);
        Thread t2 = new Thread(() -> {
            while (farm.isAlive()) {
                int gold;
                try {
                    gold = coinProduceQueue.take();
                    wealth.addAndGet(gold);
                } catch (InterruptedException ignored) { }
            }
        });
        CountDownLatch latch = new CountDownLatch(1);
        t1.start();
        t2.start();

        latch.await(2000, TimeUnit.MILLISECONDS);
        Assert.assertTrue(wealth.get() > 0);
        t1.interrupt();
        t2.interrupt();
    }

    @Test(expected = RuntimeException.class)
    public void noMovingOfMining() {
        MiningFarm farm = new MiningFarm(EntitySymbol.MINING, new Pair<>(20, 20), coinProduceQueue, 32);
        farm.setHealthBar(mock(HealthBar.class));

        farm.changePosition(new Pair<>(23, 23));
    }
}
