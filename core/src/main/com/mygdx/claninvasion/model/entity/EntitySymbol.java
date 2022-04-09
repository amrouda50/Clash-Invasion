package com.mygdx.claninvasion.model.entity;

/**
 * TileMap Symbol bindings
 * @author Dinari
 * @version 0.01
 */
public enum EntitySymbol {
    BARBARIAN("barbarian"),
    CASTEL("castel"),
    STONE("Stone"),
    DRAGON("Dragon"),
    TREE("tree");

    public final String sourcePart;

    EntitySymbol(String sourcePart) {
        this.sourcePart = sourcePart;
    }
}
