package com.mygdx.claninvasion.model.entity;

import com.mygdx.claninvasion.model.level.Levels;
import com.mygdx.claninvasion.model.player.Player;
import org.javatuples.Pair;

/**
 * Roman fort tower
 * @version 0.01
 */
public class RomanFort extends Tower {
    public RomanFort(EntitySymbol entitySymbol, Pair<Integer, Integer> position , int mapsize) {
        super(entitySymbol, position , mapsize);
        level = Levels.createRomanFortTowerIterator();
    }

    @Override
    public int getDecreaseRate() {
        return 85;
    }

    @Override
    public int getRadius() {
        return 4;
    }

    @Override
    public void setLevel(Player player) {
        level = player.getGameRomanFortIterator();
    }
}
