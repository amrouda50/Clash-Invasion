package com.mygdx.claninvasion.level;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.claninvasion.model.entity.ArtificialEntity;
import com.mygdx.claninvasion.model.entity.EntitySymbol;
import com.mygdx.claninvasion.model.entity.Tower;
import com.mygdx.claninvasion.model.level.GameTowerLevel;
import com.mygdx.claninvasion.model.level.GameTowerLevelIterator;
import com.mygdx.claninvasion.model.level.Level;
import com.mygdx.claninvasion.model.player.Player;
import com.mygdx.claninvasion.view.actors.HealthBar;
import org.javatuples.Pair;
import org.junit.Assert;
import org.junit.Test;

import static com.mygdx.claninvasion.model.level.Levels.createStrategicTowerIterator;

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
        Assert.assertEquals(gameStrategicTowerIterator.current().getMaxHealth(),1000);
        Assert.assertEquals(gameStrategicTowerIterator.current().getMinHealth(),0);
        Assert.assertEquals(gameStrategicTowerIterator.current().getReactionTime(),400);
        Assert.assertEquals(gameStrategicTowerIterator.current().getHealHealthIncrease(),20);
        Assert.assertEquals(gameStrategicTowerIterator.current().getHealGoalPoint(),10);

        Assert.assertTrue(gameStrategicTowerIterator.hasNext());
        Assert.assertEquals(gameStrategicTowerIterator.next().getCreationTime(),850);

    }
}

