package com.mygdx.claninvasion.level;

import com.mygdx.claninvasion.model.level.*;
import org.junit.Assert;
import org.junit.Test;

import static com.mygdx.claninvasion.model.level.Levels.*;

public class LevelIteratorTest {

    @Test
    public void defaultLevelIteratorTest() {
        DefaultGameLevelIterator gameLevelIterator = createLevelIterator();
        Assert.assertTrue(gameLevelIterator.hasNext());
        gameLevelIterator.next();
        Assert.assertTrue(gameLevelIterator.hasNext());
        gameLevelIterator.next();
        Assert.assertTrue(gameLevelIterator.hasNext());
        gameLevelIterator.next();
        Assert.assertFalse(gameLevelIterator.hasNext());
    }

    @Test
    public void miningGoldLevelIteratorTest() {
        GameMiningLevelIterator gameMiningLevelIterator = createMiningLevelIterator();
        Assert.assertEquals(gameMiningLevelIterator.current().getGoldBonus(),10);
        Assert.assertEquals(gameMiningLevelIterator.current().getMaxHealth(),1000);
        Assert.assertEquals(gameMiningLevelIterator.current().getCreationCost(),200);
        Assert.assertEquals(gameMiningLevelIterator.current().getCreationTime(),600);
        Assert.assertEquals(gameMiningLevelIterator.current().getMinHealth(),0);
        Assert.assertEquals(gameMiningLevelIterator.current().getReactionTime(),600);
        Assert.assertEquals(gameMiningLevelIterator.current().getHealHealthIncrease(),10);
        Assert.assertEquals(gameMiningLevelIterator.current().getHealGoalPoint(),30);

        Assert.assertTrue(gameMiningLevelIterator.hasNext());
        Assert.assertEquals(gameMiningLevelIterator.next().getCreationTime(),500);
    }

    @Test
    public void barbarianLevelIteratorTest() {
        GameSoldierLevelIterator gameBarbarianLevelIterator = createBarbarianLevelIterator();

        Assert.assertEquals(gameBarbarianLevelIterator.current().getAttackIncrease(),1);
        Assert.assertEquals(gameBarbarianLevelIterator.current().getVisibleArea(),2);
        Assert.assertEquals(gameBarbarianLevelIterator.current().getMovementSpeed(),600);
        Assert.assertEquals(gameBarbarianLevelIterator.current().getMaxHealth(),1000);
        Assert.assertEquals(gameBarbarianLevelIterator.current().getCreationCost(),200);
        Assert.assertEquals(gameBarbarianLevelIterator.current().getCreationTime(),600);
        Assert.assertEquals(gameBarbarianLevelIterator.current().getMinHealth(),0);
        Assert.assertEquals(gameBarbarianLevelIterator.current().getReactionTime(),600);
        Assert.assertEquals(gameBarbarianLevelIterator.current().getHealHealthIncrease(),10);
        Assert.assertEquals(gameBarbarianLevelIterator.current().getHealGoalPoint(),30);

        Assert.assertTrue(gameBarbarianLevelIterator.hasNext());
        Assert.assertEquals(gameBarbarianLevelIterator.next().getCreationTime(),500);
    }

    @Test
    public void dragonLevelIteratorTest() {
        GameSoldierLevelIterator gameDragonLevelIterator = createDragonLevelIterator();

        Assert.assertEquals(gameDragonLevelIterator.current().getAttackIncrease(),4);
        Assert.assertEquals(gameDragonLevelIterator.current().getVisibleArea(),3);
        Assert.assertEquals(gameDragonLevelIterator.current().getMovementSpeed(),300);
        Assert.assertEquals(gameDragonLevelIterator.current().getMaxHealth(),1100);
        Assert.assertEquals(gameDragonLevelIterator.current().getCreationCost(),350);
        Assert.assertEquals(gameDragonLevelIterator.current().getCreationTime(),750);
        Assert.assertEquals(gameDragonLevelIterator.current().getMinHealth(),0);
        Assert.assertEquals(gameDragonLevelIterator.current().getReactionTime(),450);
        Assert.assertEquals(gameDragonLevelIterator.current().getHealHealthIncrease(),10);
        Assert.assertEquals(gameDragonLevelIterator.current().getHealGoalPoint(),30);

        Assert.assertTrue(gameDragonLevelIterator.hasNext());
        Assert.assertEquals(gameDragonLevelIterator.next().getCreationTime(),650);
    }
}

