package com.mygdx.claninvasion.model.map;

import com.mygdx.claninvasion.model.entity.Entity;
/**
 * Cell is the most basic structure inside a map
 */
public class Cell {
    Point point;
    Entity occupier;

    /**
     * @param occupier - entity which will be at this place
     */
    public Cell(Entity occupier) {
        this.occupier = occupier;
        point = new Point(0, 0);
    }

    /**
     * @param x - position x
     * @param y - position y
     */
    public Cell(int x, int y) {
        this.point.setX(x);
        this.point.setY(y);
    }

    /**
     * @param occupier - entity which will be at this place
     * @param point - Point where the cell is located
     */
    public Cell(Point point, Entity occupier) {
        this.point = point;
        this.occupier = occupier;
    }

    /**
     * Replaces an entity with another entity
     * @param newOccupier - entity which will be at this place
     */
    public void changeOccupier(Entity newOccupier) {
        this.occupier = newOccupier;
    }
}
