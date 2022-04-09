package com.mygdx.claninvasion.model.map;

/**
 * Map modal of the application
 * Should represent all the map manipulations and connected to the view libgdx tilemap
 * @version 0.01
 * TODO Logic implementation required
 */
public class Map {
    private Cell[] cells;

    /**
     * @param cells - array of cells
     */
    public Map(Cell[] cells) {
        this.cells = cells;
    }

    /**
     * Change the containment of the c1 cell with c2
     * @param c1 - cell to replace the entity
     * @param c2 - cell to replace the entity
     */
    public void mutate(Cell c1, Cell c2) {}

    /**
     * Change the containment of the c1 (possible decease
     * of the entity which populated it)
     * @param c1 - cell to remove the entity
     */
    public void mutate(Cell c1) {}
}
