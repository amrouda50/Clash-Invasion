package com.mygdx.claninvasion.model.entity;

import com.badlogic.gdx.math.Vector2;
import org.javatuples.Pair;

/**
 * Basic Entity of application, will be used in the view part for the Sprites
 * @author Dinari
 * @version 0.01
 */
public class Entity {
    private final EntitySymbol entitySymbol;
    private Pair<Integer , Integer> position;

    /**
     * Creates new Entity
     */
    Entity() {
        entitySymbol = EntitySymbol.TREE;
    }

    public Entity(EntitySymbol entitySymbol, Pair<Integer,Integer> position) {
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
     * @return - position on the map Of x
     *
     */
    public Integer getValueX(){
        return position.getValue0();
    }
    /**
     * Returns map symbol
     * @return - position on the map of Y
     *
     */
    public Integer getValueY(){
        return position.getValue1();
    }
}
