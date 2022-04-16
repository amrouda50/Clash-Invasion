package com.mygdx.claninvasion.model.entity;

/**
 * TileMap Symbol bindings
 * @author Dinari
 * @version 0.01
 */
public enum EntitySymbol {
    BARBARIAN("barbarian", 'B', "Solider", 17),
    CASTEL("castel", 'C', "castel", 9),
    CASTEL_REVERSED("castel-reverse", 'C', "castel reverse", 3),
    STONE("Stone", 'S', "stone", 19),
    DRAGON("Dragon", 'D', "dragon", 15),
    TREE("tree", 'T', "Tree", 2) ,
    TOWER("tower", 't', "tower", 20),
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


    @Override
    public String toString() {
        return this.sourcePart;
    }
}
