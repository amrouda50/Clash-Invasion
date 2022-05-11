package com.mygdx.claninvasion.model.entity;

import com.mygdx.claninvasion.model.level.Levels;
import com.mygdx.claninvasion.model.player.Player;
import org.javatuples.Pair;

/**
 * Type of tower
 * @version 0.01
 * TODO: Logic part is missing
 */
public class StrategicTower extends Tower {
    public StrategicTower(EntitySymbol entitySymbol, Pair<Integer, Integer> position , int mapsize) {
        super(entitySymbol, position , mapsize);
        level = Levels.createStrategicTowerIterator();
    }

    @Override
    public int getDecreaseRate() {
        return 250;
    }

    @Override
    public int getRadius() {
        return 1;
    }

    @Override
    public void setLevel(Player player) {
        level = player.getGameStrategicTowerIterator();
    }

    @Override
    public String getProjectileSource() {
        return "BuildingBlocks/fire.png";
    }
}
