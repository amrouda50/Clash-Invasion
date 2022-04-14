package com.mygdx.claninvasion.model.entity;

/**
 * TileMap Symbol bindings
 * @author Dinari
 * @version 0.01
 */
public enum EntitySymbol {
    BARBARIAN("barbarian", 'B'),
    CASTEL("castel", 'C'),
    STONE("Stone", 'S'),
    DRAGON("Dragon", 'D'),
    TREE("tree", 'T') ,
    Tower("tower" , 't');

    public final String sourcePart;
    public final char letter;

    EntitySymbol(String sourcePart, char letter) {
        this.sourcePart = sourcePart;
        this.letter = letter;
    }
}
