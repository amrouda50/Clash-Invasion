package com.mygdx.claninvasion.model.level;

import org.javatuples.Septet;

/**
 * This class is responsible for determining the
 * tower level in the game
 * @author andreicristea
 * @author omarashour
 * @author Dinari
 */
public class GameTowerLevel extends Level {
    protected final int hitsPointBonus;
    protected final int radiusBonus;
    protected final int attackBonus;


    public GameTowerLevel(
            Septet<Integer, Integer, Integer, Integer, Integer, Integer, Integer> values,
            int hitsPointBonus,
            int radiusBonus,
            int attackBonus
    ) {
        super(values);
        this.hitsPointBonus = hitsPointBonus;
        this.radiusBonus = radiusBonus;
        this.attackBonus = attackBonus;
    }

    public int getHitsPointBonus() {
        return hitsPointBonus;
    }

    public int getRadiusBonus() {
        return radiusBonus;
    }

    public int getAttackBonus() {
        return attackBonus;
    }
}
