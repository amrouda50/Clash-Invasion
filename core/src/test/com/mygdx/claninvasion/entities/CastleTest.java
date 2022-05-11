package com.mygdx.claninvasion.entities;

import com.mygdx.claninvasion.model.entity.Castle;
import com.mygdx.claninvasion.model.entity.EntitySymbol;
import com.mygdx.claninvasion.model.entity.attacktype.AttackType;
import com.mygdx.claninvasion.model.level.GameSoldierLevelIterator;
import com.mygdx.claninvasion.model.level.Levels;
import com.mygdx.claninvasion.model.player.Player;
import com.mygdx.claninvasion.view.actors.HealthBar;
import org.javatuples.Pair;
import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class CastleTest {

    @Test
    public void castleTrainSoldiers() {
        Player player = mock(Player.class);
        Castle castle = new Castle(EntitySymbol.CASTEL, new Pair<>(10, 10), player, 21, mock(HealthBar.class));

        GameSoldierLevelIterator iterator = Levels.createBarbarianLevelIterator();

        castle
                .trainSoldiers(EntitySymbol.BARBARIAN, (cost) -> {
                    int expected = iterator.current().getCreationCost();
                    Assert.assertEquals(expected * Castle.AMOUNT_OF_SOLDIERS, cost.intValue());
                    Assert.assertEquals(Castle.AMOUNT_OF_SOLDIERS, castle.getSoldiers().size());
                    return false;
                }, mock(AttackType.class));
    }


    @Test(expected = RuntimeException.class)
    public void noMovingOfMining() {
        Player player = mock(Player.class);
        Castle castle = new Castle(EntitySymbol.CASTEL, new Pair<>(10, 10), player, 21, mock(HealthBar.class));

        castle.changePosition(new Pair<>(23, 23));
    }
}
