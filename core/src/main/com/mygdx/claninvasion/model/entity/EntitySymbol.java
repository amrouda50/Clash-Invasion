package com.mygdx.claninvasion.model.entity;

/**
 * TileMap Symbol bindings
 * @author Dinari
 * @version 0.01
 */
public enum EntitySymbol {
    BARBARIAN("barbarian", 'B', "Solider", 17),
    CASTEL("blue-castel", 'C', "blue-castel", 23),
    CASTEL_REVERSED("red-castel", 'C', "red-Castel", 24),
    STONE("Stone", 'S', "stone", 19),
    DRAGON("Dragon", 'D', "dragon", 15),
    TREE("tree", 'T', "Tree", 2) ,
    HILL_TOWER("tower", 'H', "tower", 20),
    ROMAN_FORT("splashtower", 'F', "tower", 25),
    // TDDO: change later for appropriate tile
    STRATEGIC_TOWER("splashtower", 'F', "tower", 25),
    MINING("goldmine", 'G', "goldmine", 22);

    public final String sourcePart;
    public final char letter;
    public final String tsx;
    public final int id;

    EntitySymbol(String sourcePart, char letter, String tsx, int id) {
        this.sourcePart = sourcePart;
        this.letter = letter;
        this.tsx = tsx;
        this.id = id;
    }

    public static Boolean isTower(EntitySymbol entitySymbol) {
        return entitySymbol == EntitySymbol.ROMAN_FORT
                || entitySymbol == EntitySymbol.HILL_TOWER
                || entitySymbol == EntitySymbol.STRATEGIC_TOWER;
    }


    @Override
    public String toString() {
        return this.sourcePart;
    }
}
