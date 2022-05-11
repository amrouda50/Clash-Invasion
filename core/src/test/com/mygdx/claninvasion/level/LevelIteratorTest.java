package com.mygdx.claninvasion.level;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.claninvasion.model.entity.ArtificialEntity;
import com.mygdx.claninvasion.model.entity.EntitySymbol;
import com.mygdx.claninvasion.model.entity.Tower;
import com.mygdx.claninvasion.model.level.*;
import com.mygdx.claninvasion.model.player.Player;
import com.mygdx.claninvasion.view.actors.HealthBar;
import org.javatuples.Pair;
import org.junit.Assert;
import org.junit.Test;

import static com.mygdx.claninvasion.model.level.Levels.*;

public class LevelIteratorTest {

    @Test
    public void defaultlevelIteratorTest() {
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
    public void MiningGoldLevelIteratorTest() {
        GameMiningLevelIterator gameMiningLevelIterator = createMiningLevelIterator()
                ;
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
    public void BarbarianLevelIteratorTest() {
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
}

