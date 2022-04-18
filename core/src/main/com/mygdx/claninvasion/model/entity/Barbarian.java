package com.mygdx.claninvasion.model.entity;

import org.javatuples.Pair;

/**
 * Barbarian Soldier
 * TODO: Logic part is missing
 */
public class Barbarian extends Soldier {
    public static int COST = 100;
    public Barbarian(EntitySymbol entitySymbol, Pair<Integer, Integer> position) {
        super(entitySymbol, position);
    }
}
