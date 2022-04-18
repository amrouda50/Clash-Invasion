package com.mygdx.claninvasion.model.entity;

import org.javatuples.Pair;

/**
 * Dragon Soldier
 * TODO: Logic part is missing
 */
public class Dragon extends Soldier {
    public static int COST = 240;
    public Dragon(EntitySymbol entitySymbol, Pair<Integer, Integer> position) {
        super(entitySymbol, position);
    }
}
