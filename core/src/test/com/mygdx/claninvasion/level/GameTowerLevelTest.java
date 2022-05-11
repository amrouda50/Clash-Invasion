package com.mygdx.claninvasion.level;

import com.mygdx.claninvasion.model.level.GameTowerLevelIterator;
import org.junit.Assert;
import org.junit.Test;

import static com.mygdx.claninvasion.model.level.Levels.*;

public class GameTowerLevelTest {
    @Test
    public void StrategicTowerIteratorTest() {
        GameTowerLevelIterator gameStrategicTowerIterator = createStrategicTowerIterator();
        Assert.assertEquals(gameStrategicTowerIterator.current().getHitsPointBonus(),200);
        Assert.assertEquals(gameStrategicTowerIterator.current().getRadiusBonus(),0);
        Assert.assertEquals(gameStrategicTowerIterator.current().getAttackBonus(),0);
        Assert.assertEquals(gameStrategicTowerIterator.current().getMaxHealth(),1000);
        Assert.assertEquals(gameStrategicTowerIterator.current().getCreationCost(),540);
        Assert.assertEquals(gameStrategicTowerIterator.current().getCreationTime(),700);
        Assert.assertEquals(gameStrategicTowerIterator.current().getMinHealth(),0);
        Assert.assertEquals(gameStrategicTowerIterator.current().getReactionTime(),400);
        Assert.assertEquals(gameStrategicTowerIterator.current().getHealHealthIncrease(),20);
        Assert.assertEquals(gameStrategicTowerIterator.current().getHealGoalPoint(),10);

        Assert.assertTrue(gameStrategicTowerIterator.hasNext());
        Assert.assertEquals(gameStrategicTowerIterator.next().getCreationTime(),850);

    }

    @Test
    public void RomanFortTowerIteratorTest() {
        GameTowerLevelIterator gameRomanFortIterator = createRomanFortTowerIterator();

        Assert.assertEquals(gameRomanFortIterator.current().getHitsPointBonus(),200);
        Assert.assertEquals(gameRomanFortIterator.current().getRadiusBonus(),0);
        Assert.assertEquals(gameRomanFortIterator.current().getAttackBonus(),0);
        Assert.assertEquals(gameRomanFortIterator.current().getMaxHealth(),1000);
        Assert.assertEquals(gameRomanFortIterator.current().getCreationCost(),200);
        Assert.assertEquals(gameRomanFortIterator.current().getCreationTime(),600);
        Assert.assertEquals(gameRomanFortIterator.current().getMinHealth(),0);
        Assert.assertEquals(gameRomanFortIterator.current().getReactionTime(),600);
        Assert.assertEquals(gameRomanFortIterator.current().getHealHealthIncrease(),10);
        Assert.assertEquals(gameRomanFortIterator.current().getHealGoalPoint(),30);

        Assert.assertTrue(gameRomanFortIterator.hasNext());
        Assert.assertEquals(gameRomanFortIterator.next().getCreationTime(),500);
    }

    @Test
    public void hillTowerIteratorTest() {
        GameTowerLevelIterator gameHillTowerIterator = createHillTowerIterator();

        Assert.assertEquals(gameHillTowerIterator.current().getHitsPointBonus(),200);
        Assert.assertEquals(gameHillTowerIterator.current().getRadiusBonus(),0);
        Assert.assertEquals(gameHillTowerIterator.current().getAttackBonus(),0);
        Assert.assertEquals(gameHillTowerIterator.current().getMaxHealth(),2000);
        Assert.assertEquals(gameHillTowerIterator.current().getCreationCost(),500);
        Assert.assertEquals(gameHillTowerIterator.current().getCreationTime(),1000);
        Assert.assertEquals(gameHillTowerIterator.current().getMinHealth(),0);
        Assert.assertEquals(gameHillTowerIterator.current().getReactionTime(),3500);
        Assert.assertEquals(gameHillTowerIterator.current().getHealHealthIncrease(),20);
        Assert.assertEquals(gameHillTowerIterator.current().getHealGoalPoint(),10);

        Assert.assertTrue(gameHillTowerIterator.hasNext());
        Assert.assertEquals(gameHillTowerIterator.next().getCreationTime(),950);
    }
}

