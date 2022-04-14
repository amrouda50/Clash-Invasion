package com.mygdx.claninvasion.model.entity;

import com.mygdx.claninvasion.model.level.Level;
import com.mygdx.claninvasion.model.level.LevelIterator;
import org.javatuples.Pair;

/**
 * Mining farm
 * @version 0.01
 * TODO: Logic part is missing
 */
public class MiningFarm extends ArtificialEntity implements Mineable {
    MiningFarm(EntitySymbol entitySymbol, Pair<Integer, Integer> position) {
        super(entitySymbol, position);
    }

    MiningFarm(LevelIterator<Level> levelIterator) {
        super(levelIterator);
    }
}
