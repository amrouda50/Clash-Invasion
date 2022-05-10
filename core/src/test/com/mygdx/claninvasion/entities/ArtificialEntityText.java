package com.mygdx.claninvasion.entities;

import com.mygdx.claninvasion.exceptions.EntityOutsideOfBoundsException;
import com.mygdx.claninvasion.model.entity.ArtificialEntity;
import com.mygdx.claninvasion.model.entity.EntitySymbol;
import com.mygdx.claninvasion.model.level.Level;
import com.mygdx.claninvasion.model.level.LevelIterator;
import com.mygdx.claninvasion.model.level.Levels;
import com.mygdx.claninvasion.view.actors.HealthBar;
import org.javatuples.Pair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArtificialEntityText {
    ArtificialEntity entity;
    HealthBar bar;

    @Before
    public void init() {
        initEntity();
        bar = mock(HealthBar.class);
    }

    private void initEntity() {
        entity = new ArtificialEntity(EntitySymbol.TREE, new Pair<>(8, 8), 32) {};
    }

    private void initHealthBar() {
        entity.setHealthBar(bar);
    }

    @Test(expected = NullPointerException.class)
    // damage should be percent of injury
    public void testDamageException() {
        initEntity();
        entity.damage(1);
        Assert.assertEquals(99, entity.getHealth());
    }

    @Test
    public void initializeHealthBar() {
        initHealthBar();
        Assert.assertEquals(entity.getHealthBar(), bar);
    }

    @Test
    public void testMetaData() {
        ArtificialEntity entity1 = entity;
        Assert.assertEquals(entity1.getId(), entity.getId());

        Assert.assertEquals(entity1.getLevel().current().getReactionTime(), entity1.getReactionTime().get());
    }

    @Test
    public void testDamage() {
        initEntity();
        initHealthBar();
        int maxHealth = entity.getLevel().current().getMaxHealth();
        entity.damage(1);
        Assert.assertEquals(maxHealth - 1, entity.getHealth());

        for (int i = maxHealth - 1; i >= 0; i--) {
            entity.damage(1);
        }

        Assert.assertEquals(0, entity.getHealth());
    }

    @Test
    public void testDamageOutOfBounds() {
        initEntity();
        initHealthBar();

        int maxHealth = entity.getLevel().current().getMaxHealth();
        entity.damage(2 * maxHealth);

        entity.damage(1);

        Assert.assertEquals(0, entity.getHealthPercentage(), 0);
        Assert.assertEquals(new AtomicLong(0).get(), entity.getPercentage().get(), 0);
        Assert.assertEquals(0, entity.getHealth(), 0);
        Assert.assertEquals(entity.getLevel().current().getMinHealth(), entity.getHealth());
        Assert.assertFalse(entity.isAlive());
    }


    @Test
    public void testHealthPercentage() {
        initEntity();
        initHealthBar();

        Assert.assertEquals(100, entity.getHealthPercentage(), 0);
        Assert.assertEquals(new AtomicLong(100).get(), entity.getPercentage().get());
        Assert.assertTrue(entity.isAlive());
    }

    @Test
    public void testLevels() {
        var levelIterator = mock(LevelIterator.class);
        when(levelIterator.next()).thenReturn(new Level(Levels.level1));
        when(levelIterator.hasNext()).thenReturn(true);

        ArtificialEntity entity = new ArtificialEntity(EntitySymbol.TREE, new Pair<>(8, 8), 32) {};
        entity.setLevel(levelIterator);

        Assert.assertEquals(levelIterator, entity.getLevel());

        boolean changeLevel = entity.changeLevel();
        when(levelIterator.hasNext()).thenReturn(false);
        Assert.assertTrue(changeLevel);

        entity.changeLevel();
        changeLevel = entity.changeLevel();
        Assert.assertFalse(changeLevel);
    }

    @Test
    public void changePositionTest() {
        Pair<Integer, Integer> position = new Pair<>(10, 12);
        entity.changePosition(position);
    }

    @Test(expected = EntityOutsideOfBoundsException.class)
    public void changePositionNegativeParameterTest() {
        Pair<Integer, Integer> position = new Pair<>(0, -23);
        entity.changePosition(position);
    }

    @Test(expected = EntityOutsideOfBoundsException.class)
    public void changePositionOutOfMapSizeTest() {
        Pair<Integer, Integer> position = new Pair<>(100, 100);
        entity.changePosition(position);
    }

    @Test
    public void healTest() throws InterruptedException {
        initEntity();
        initHealthBar();
        entity.damage(500);
        CountDownLatch latch = new CountDownLatch(1);

        Assert.assertEquals(50, entity.getPercentage().get());

        entity.heal();
        latch.await(entity.getReactionTime().get() * 10, TimeUnit.MILLISECONDS);

        // no change happened cause, healing is available only after health < getHealGoalPoint
        Assert.assertEquals(500, entity.getHealth());

        entity.damage(350);
        // now health percent is smaller than health goal points
        Assert.assertEquals(15, entity.getPercentage().get());

        entity.heal();
        latch.await(entity.getReactionTime().get() * 10, TimeUnit.MILLISECONDS);
        Assert.assertEquals(
                entity.getLevel().current().getHealGoalPoint(),
                entity.getHealthPercentage(),
                0.1
        );

        entity.damage(600);
        // object is already dead no healing
        entity.heal();
        Assert.assertEquals(
                entity.getLevel().current().getMinHealth(),
                entity.getHealth(),
                0.1
        );
    }
}
