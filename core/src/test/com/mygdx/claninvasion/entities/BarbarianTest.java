package com.mygdx.claninvasion.entities;

import com.mygdx.claninvasion.model.entity.Barbarian;
import com.mygdx.claninvasion.model.entity.EntitySymbol;
import com.mygdx.claninvasion.model.entity.Soldier;
import com.mygdx.claninvasion.model.level.Levels;
import org.javatuples.Pair;
import org.junit.Assert;
import org.junit.Test;

public class BarbarianTest {
    @Test
    public void testCost() {
        Soldier soldier = new Barbarian(EntitySymbol.BARBARIAN, new Pair<>(10, 10), 32);
        soldier.setLevel(Levels.createBarbarianLevelIterator());
        Assert.assertEquals(Levels.createBarbarianLevelIterator().current().getCreationCost(), soldier.getCost());
    }
}
