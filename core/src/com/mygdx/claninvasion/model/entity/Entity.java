package com.mygdx.claninvasion.model.entity;

import java.awt.Point;

/**
 * Basic Entity of application, will be used in the view part for the Sprites
 * @author Dinari
 * @version 0.01
 */
public class Entity {
    private EntitySymbol entitySymbol;
    private Point position;

    /**
     * Creates new Entity
     */
    Entity() {
        entitySymbol = EntitySymbol.C;
    }

    /**
     * Returns map symbol
     * @return - symbol synced with map representation
     * @see EntitySymbol
     */
    public EntitySymbol getEntitySymbol() {
        return entitySymbol;
    }

    /**
     * Returns map symbol
     * @return - position on the map
     * @see Point
     */
    public Point getPosition() {
        return position;
    }
}
