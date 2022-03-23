package com.mygdx.claninvasion.model.entity;

import java.awt.Point;

public class Entity {
    private EntitySymbol entitySymbol;
    private Point position;

    Entity() {
        entitySymbol = EntitySymbol.C;
    }

    public EntitySymbol getEntitySymbol() {
        return entitySymbol;
    }

    public Point getPosition() {
        return position;
    }
}
