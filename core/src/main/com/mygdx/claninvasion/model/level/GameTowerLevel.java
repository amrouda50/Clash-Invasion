package com.mygdx.claninvasion.model.level;

import org.javatuples.Septet;

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
    public int level;

    public GameTowerLevel(Septet<Integer, Integer, Integer, Integer, Integer, Integer, Integer> values, int hitsPointBonus) {
        super(values);
        this.level = level;
        this.hitsPointBonus = hitsPointBonus;
    }

    public void setHitsPointBonus(int hitsPointBonus) {
        this.hitsPointBonus = hitsPointBonus;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getHitsPointBonus() {
        return hitsPointBonus;
    }
}
