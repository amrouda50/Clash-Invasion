package com.mygdx.claninvasion.model.entity;

import org.javatuples.Pair;

/**
 * Basic Entity of application, will be used in the view part for the Sprites
 * @author Dinari
 * @version 0.01
 */
public class Entity {
    protected final EntitySymbol entitySymbol;
    protected Pair<Integer , Integer> position;

    /** Creates new Entity. */
    public Entity() {
        entitySymbol = EntitySymbol.TREE;
    }

    public Entity(EntitySymbol entitySymbol, Pair<Integer, Integer> position) {
        this.entitySymbol = entitySymbol;
        this.position = position;
    }

    /** Returns map symbol
     * @return - symbol synced with map representation
     * @see EntitySymbol
     */
    public EntitySymbol getSymbol() {
        return entitySymbol;
    }

    /**
     * Returns map symbol
     * @return - position on the map Of x
     *
     */
    public Integer getPositionX() {
        return position.getValue0();
    }
    /**
     * Returns map symbol
     * @return - position on the map of Y
     *
     */
    public Integer getPositionY() {
        return position.getValue1();
    }
}
