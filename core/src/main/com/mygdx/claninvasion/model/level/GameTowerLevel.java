package com.mygdx.claninvasion.model.level;

import org.javatuples.Ennead;
import org.javatuples.Septet;
import org.javatuples.Sextet;

/**
 * This class is responsible for determining the
 * tower level in the game
 * @author andreicristea
 * @author omarashour
 * @author Dinari
 * TODO: Logic part is missing
 */
public class GameTowerLevel extends Level{
    protected int hitsPointBonus;

    public GameTowerLevel(Septet<Integer, Integer, Integer, Integer, Integer, Integer, Integer> values, int hitsPointBonus) {
        super(values);
        this.hitsPointBonus = hitsPointBonus;
    }

    public int getHitsPointBonus() {
        return hitsPointBonus;
    }
}
