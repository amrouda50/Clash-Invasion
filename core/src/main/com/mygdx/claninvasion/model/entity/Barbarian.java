package com.mygdx.claninvasion.model.entity;

import org.javatuples.Pair;

/**
 * Barbarian Soldier
 */
public class Barbarian extends Soldier {
    /**
     * @param entitySymbol - sprite type (location, name etc.)
     * @param position - position in the cells array
     * @param mapsize - size of the map, helps identifying if entity is not creatable
     */
    public Barbarian(EntitySymbol entitySymbol, Pair<Integer, Integer> position, int mapsize) {
        super(entitySymbol, position, mapsize);
        initHealth = initHealth - 300;
        health.set(initHealth);
    }

    @Override
    public int getCost() {
        return level.current().getCreationCost();
    }

}
