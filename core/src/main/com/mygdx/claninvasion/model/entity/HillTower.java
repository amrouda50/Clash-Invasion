package com.mygdx.claninvasion.model.entity;

import com.mygdx.claninvasion.model.level.Levels;
import com.mygdx.claninvasion.model.player.Player;
import org.javatuples.Pair;

/**
 * Hill tower
 * @version 0.01
 * TODO: Logic part is missing
 */
public class HillTower extends Tower {
    public HillTower(EntitySymbol entitySymbol, Pair<Integer, Integer> position , int mapsize) {
        super(entitySymbol, position , mapsize);
        level = Levels.createHillTowerIterator();
    }

    @Override
    public int getDecreaseRate() {
        return 30;
    }

    @Override
    public int getRadius() {
        return 10;
    }

    @Override
    public void setLevel(Player player) {
        level = player.getGameHillTowerIterator();
    }
}
