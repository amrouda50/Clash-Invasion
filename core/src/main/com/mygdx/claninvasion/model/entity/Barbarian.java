package com.mygdx.claninvasion.model.entity;

import org.javatuples.Pair;

/**
 * Barbarian Soldier
 * TODO: Logic part is missing
 */
public class Barbarian extends Soldier {
    public Barbarian(EntitySymbol entitySymbol, Pair<Integer, Integer> position) {
        super(entitySymbol, position);
        initHealth = initHealth - 300;
        health.set(initHealth);
    }

    @Override
    public int getCost() {
        return level.current().getCreationCost();
    }

}
