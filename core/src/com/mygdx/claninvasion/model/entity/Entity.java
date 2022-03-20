package com.mygdx.claninvasion.model.entity;

import java.awt.Point;

enum Symbol {
    T,
    O,
    C
}


public class Entity {

    private Point position;
    Symbol EntitySymbol;


    public Symbol getEntitySymbol() {
        return EntitySymbol;
    }

    public void setEntitySymbol(Symbol entitySymbol) {
        EntitySymbol = entitySymbol;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}
