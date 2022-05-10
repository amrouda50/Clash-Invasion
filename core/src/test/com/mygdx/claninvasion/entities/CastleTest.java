package com.mygdx.claninvasion.entities;

import com.mygdx.claninvasion.model.entity.Castle;
import com.mygdx.claninvasion.model.entity.EntitySymbol;
import com.mygdx.claninvasion.model.level.GameSoldierLevelIterator;
import com.mygdx.claninvasion.model.level.Levels;
import com.mygdx.claninvasion.model.player.Player;
import org.javatuples.Pair;
import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class CastleTest {

    @Test
    public void castleTrainSoldiers() {
        Player player = mock(Player.class);
        Castle castle = new Castle(EntitySymbol.CASTEL, new Pair<>(10, 10), player, 21);

        GameSoldierLevelIterator iterator = Levels.createBarbarianLevelIterator();

        castle
                .trainSoldiers(EntitySymbol.BARBARIAN, (cost) -> {
                    int expected = iterator.current().getCreationCost();
                    Assert.assertEquals(expected * Castle.AMOUNT_OF_SOLDIERS, cost.intValue());
                    Assert.assertEquals(Castle.AMOUNT_OF_SOLDIERS, castle.getSoldiers().size());
                    return false;
                });
    }


    @Test(expected = RuntimeException.class)
    public void noMovingOfMining() {
        Player player = mock(Player.class);
        Castle castle = new Castle(EntitySymbol.CASTEL, new Pair<>(10, 10), player, 21);

        castle.changePosition(new Pair<>(23, 23));
    }
}
