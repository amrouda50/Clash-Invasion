package com.mygdx.claninvasion.model.entity;

import org.javatuples.Pair;

/**
 * Natural entity formations
 * @version 0.01
 * TODO: Logic part is missing
 */
public class NaturalEntity extends Entity {
    private final boolean isPassed;

    public NaturalEntity(EntitySymbol entitySymbol, Pair<Integer, Integer> position, int mapsize) {
        super(entitySymbol, position , mapsize);
        this.isPassed = false;
    }

    /**
     * Getter for passed state
     * @return - if the entities can pass the objects
     */
    public boolean isPassed() {
        return isPassed;
    }
}
