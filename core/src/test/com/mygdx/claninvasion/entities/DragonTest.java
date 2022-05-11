package com.mygdx.claninvasion.entities;

import com.mygdx.claninvasion.model.entity.Dragon;
import com.mygdx.claninvasion.model.entity.EntitySymbol;
import com.mygdx.claninvasion.model.entity.Soldier;
import com.mygdx.claninvasion.model.level.Levels;
import org.javatuples.Pair;
import org.junit.Assert;
import org.junit.Test;

public class DragonTest {
    @Test
    public void testCost() {
        Soldier soldier = new Dragon(EntitySymbol.BARBARIAN, new Pair<>(10, 10), 32);
        soldier.setLevel(Levels.createDragonLevelIterator());
        Assert.assertEquals(Levels.createDragonLevelIterator().current().getCreationCost(), soldier.getCost());
    }
}
