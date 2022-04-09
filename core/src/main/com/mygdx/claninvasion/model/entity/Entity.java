package com.mygdx.claninvasion.model.entity;

import com.badlogic.gdx.math.Vector2;

/**
 * Basic Entity of application, will be used in the view part for the Sprites
 * @author Dinari
 * @version 0.01
 */
public class Entity {
    private final EntitySymbol entitySymbol;
    private Vector2 position;

    /**
     * Creates new Entity
     */
    Entity() {
        entitySymbol = EntitySymbol.TREE;
    }

    public Entity(EntitySymbol entitySymbol, Vector2 position) {
        this.entitySymbol = entitySymbol;
        this.position = position;
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
     * @see Vector2
     */
    public Vector2 getPosition() {
        return position;
    }
}
