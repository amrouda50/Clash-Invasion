package com.mygdx.claninvasion.model.entity;

import com.mygdx.claninvasion.model.level.Level;
import com.mygdx.claninvasion.model.level.LevelIterator;
import org.javatuples.Pair;

/**
 * Hill tower
 * @version 0.01
 * TODO: Logic part is missing
 */
public class HillTower extends Tower {
    HillTower(EntitySymbol entitySymbol, Pair<Integer, Integer> position , int mapsize) {
        super(entitySymbol, position , mapsize);
    }

    HillTower(LevelIterator<Level> levelIterator) {
        super(levelIterator);
    }
}
