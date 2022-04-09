package com.mygdx.claninvasion.model.map;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Map modal of the application
 * Should represent all the map manipulations and connected to the view libgdx tilemap
 * @version 0.01
 * TODO Logic implementation required
 */
public class WorldMap {
    private final ArrayList<WorldCell> worldCells;

    /**
     * @param worldCells - array of worldCells
     */
    public WorldMap(WorldCell[] worldCells) {
        this.worldCells = new ArrayList<>();
        this.worldCells.addAll(Arrays.asList(worldCells));
    }

    public WorldMap() {
        this.worldCells = new ArrayList<>();
    }

    public void addCell(WorldCell worldCell) {
        this.worldCells.add(worldCell);
    }

    public void clear() {
        this.worldCells.clear();
    }

    public int getSize() {
        return this.worldCells.size();
    }

    public WorldCell getCell(int i) {
        return worldCells.get(i);
    }

    public ArrayList<WorldCell> getCells() {
        return worldCells;
    }

    /**
     * Change the containment of the c1 cell with c2
     * @param c1 - cell to replace the entity
     * @param c2 - cell to replace the entity
     */
    public void mutate(WorldCell c1, WorldCell c2) {}

    /**
     * Change the containment of the c1 (possible decease
     * of the entity which populated it)
     * @param c1 - cell to remove the entity
     */
    public void mutate(WorldCell c1) {}
}
