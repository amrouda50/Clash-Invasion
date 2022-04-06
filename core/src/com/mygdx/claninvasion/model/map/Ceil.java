package com.mygdx.claninvasion.model.map;

import com.mygdx.claninvasion.model.entity.Entity;

public class Ceil {
    Point position;
    Entity occupier;


    public Ceil(Entity occupier) {
        this.occupier = occupier;
        position = new Point(0, 0);
    }

    public Ceil(int x, int y) {
        this.position.setX(x);
        this.position.setY(y);
    }


    public Ceil(Point position, Entity occupier) {
        this.position = position;
        this.occupier = occupier;
    }

    public void changeOccupier(Entity newOccupier) {
        this.occupier = newOccupier;
    }
}
