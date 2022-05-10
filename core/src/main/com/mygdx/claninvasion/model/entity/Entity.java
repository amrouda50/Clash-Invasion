package com.mygdx.claninvasion.model.entity;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.claninvasion.exceptions.EntityOutsideOfBoundsException;
import org.javatuples.Pair;

import java.util.Random;

/**
 * Basic Entity of application, will be used in the view part for the Sprites
 * @author Dinari
 * @version 0.01
 */
public class Entity {
    /**
     * symbol this represents type of sprite use
     */
    protected final EntitySymbol entitySymbol;

    /**
     * map position of the entity (not x,y its more the position considering 2d array index)
     * helps to detect current entities cell and its isometric position through Map class
     * manipulations
     */
    protected Pair<Integer , Integer> position;

    /**
     * size of the map
     */
    protected final int mapSize;

    protected Boolean isNotInsideMap(Pair<Integer, Integer> position) {
        return  position.getValue0() < 0
                || position.getValue1() < 0
                || position.getValue0() > mapSize
                ||  position.getValue1() > mapSize;
    }

    /**
     * @param entitySymbol - sprite type (location, name etc.)
     * @param position - position in the cells array
     * @param worldMapSize - size of the map, helps identifying if entity is not creatable
     * @throws IndexOutOfBoundsException - is thrown when entity is not inside map cells
     */
    public Entity(EntitySymbol entitySymbol, Pair<Integer, Integer> position , int worldMapSize) throws EntityOutsideOfBoundsException {
        if (worldMapSize < 1) {
            throw new IllegalArgumentException("Map size can not be less than 1");
        }
        mapSize = worldMapSize;

        if (isNotInsideMap(position)) {
            throw new EntityOutsideOfBoundsException(position);
        }

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
     * Returns map x position (row position)
     * @return - position on the map of x
     *
     */
    public Integer getPositionX() {
        return position.getValue0();
    }

    /**
     * @return - position pair
     */
    public Pair<Integer, Integer> getPosition() {
        return position;
    }

    /**
     * Returns map y position (column position)
     * @return - position on the map of y
     *
     */
    public Integer getPositionY() {
        return position.getValue1();
    }

    /**
     * @return make vector2 class of position
     */
    public Vector2 getVec2Position() {
        return new Vector2(position.getValue0(), position.getValue1());
    }

    public int getMapSize() {
        return mapSize;
    }
}
