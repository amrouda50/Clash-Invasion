package com.mygdx.claninvasion.model.entity;

import com.mygdx.claninvasion.model.level.Level;
import com.mygdx.claninvasion.model.level.LevelIterator;
import org.javatuples.Pair;

/**
 * Type of tower
 * @version 0.01
 * TODO: Logic part is missing
 */
public class StrategicTower extends Tower {
    StrategicTower(EntitySymbol entitySymbol, Pair<Integer, Integer> position) {
        super(entitySymbol, position);
    }

    StrategicTower(LevelIterator<Level> levelIterator) {
        super(levelIterator);
    }
}
