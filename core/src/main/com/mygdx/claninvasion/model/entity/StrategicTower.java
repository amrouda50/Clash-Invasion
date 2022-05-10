package com.mygdx.claninvasion.model.entity;

import org.javatuples.Pair;

/**
 * Type of tower
 * @version 0.01
 * TODO: Logic part is missing
 */
public class StrategicTower extends Tower {
    StrategicTower(EntitySymbol entitySymbol, Pair<Integer, Integer> position , int mapsize) {
        super(entitySymbol, position , mapsize);
    }
}
