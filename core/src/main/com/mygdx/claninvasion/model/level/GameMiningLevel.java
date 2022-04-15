package com.mygdx.claninvasion.model.level;

import org.javatuples.Septet;
import org.javatuples.Sextet;

/**
 * This class is responsible for determining the
 * mining level in the game
 * @author andreicristea
 * @author omarashour
 * @author Dinari
 * TODO: Logic part is missing
 */
public class GameMiningLevel extends Level {
    private int goldBonus;

    public GameMiningLevel(Septet<Integer, Integer, Integer, Integer, Integer, Integer, Integer> values, int goldBonus) {
        super(values);
        this.goldBonus = goldBonus;
    }

    public int getGoldBonus() {
        return goldBonus;
    }
}
