package com.mygdx.claninvasion.model.entity;

import com.mygdx.claninvasion.model.level.Level;
import com.mygdx.claninvasion.model.level.LevelIterator;
import org.javatuples.Pair;

/**
 * Roman fort tower
 * @version 0.01
 * TODO: Logic part is missing
 */
public class RomanFort extends Tower {
    RomanFort(EntitySymbol entitySymbol, Pair<Integer, Integer> position) {
        super(entitySymbol, position);
    }

    RomanFort(LevelIterator<Level> levelIterator) {
        super(levelIterator);
    }
}
