package com.mygdx.claninvasion.entities;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.claninvasion.model.entity.Castle;
import com.mygdx.claninvasion.model.entity.EntitySymbol;
import com.mygdx.claninvasion.model.entity.Soldier;
import com.mygdx.claninvasion.model.level.GameSoldierLevel;
import com.mygdx.claninvasion.model.level.Levels;
import org.javatuples.Pair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.concurrent.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SoldierTest {
    public Castle castle;
    private final int mapSize = 32;

    @Before
    public void init() {
        castle = mock(Castle.class);
    }


    @Test
    public void testCastleAttack() {
        Pair<Integer, Integer> position = new Pair<>(20, 20);
        Soldier soldier = new Soldier(EntitySymbol.DRAGON, position, mapSize) {
            @Override
            public int getCost() {
                return 0;
            }
        };
        soldier.setLevel(Levels.createBarbarianLevelIterator());

        when(castle.getVec2Position()).thenReturn(new Vector2(5, 5));

        boolean attacked = soldier.attackCastle(castle);
        Assert.assertFalse(attacked);

        when(castle.getVec2Position()).thenReturn(new Vector2(21, 21));
        attacked = soldier.attackCastle(castle);
        Assert.assertTrue(attacked);

        when(castle.getVec2Position()).thenReturn(new Vector2(
                21 + ((GameSoldierLevel)soldier.getLevel().current()).getVisibleArea(),
                21 + ((GameSoldierLevel)soldier.getLevel().current()).getVisibleArea()
        ));
        attacked = soldier.attackCastle(castle);
        Assert.assertFalse(attacked);
    }

    @Test
    public void soldierTrainTest() throws ExecutionException, InterruptedException {
        Pair<Integer, Integer> position = new Pair<>(20, 20);
        Soldier soldier = new Soldier(EntitySymbol.DRAGON, position, mapSize) {
            @Override
            public int getCost() {
                return 100;
            }
        };
        ExecutorService service = Executors.newFixedThreadPool(1);
        int value = soldier.train(service).get();

        Assert.assertEquals(soldier.getCost(), value);
    }
}
