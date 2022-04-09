package com.mygdx.claninvasion.model.map;


import org.javatuples.Pair;

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

    public WorldCell getCell(Pair<Integer, Integer> cellPlace) {
        return (WorldCell) worldCells.stream().filter(cell -> cell.getMapPosition().equals(cellPlace)).toArray()[0];
    }

    public int maxCellRowPosition() {
        return worldCells
                .stream()
                .max((c1, c2) -> c1.getMapPosition().getValue0() >= c2.getMapPosition().getValue0() ? 1 : -1)
                .get()
                .getMapPosition()
                .getValue0();
    }

    public int minCellRowPosition() {
        return worldCells
                .stream()
                .min((c1, c2) -> c1.getMapPosition().getValue0() >= c2.getMapPosition().getValue0() ? 1 : -1)
                .get()
                .getMapPosition()
                .getValue0();
    }

    public int maxCellColumnPosition() {
        return worldCells
                .stream()
                .max((c1, c2) -> c1.getMapPosition().getValue1() >= c2.getMapPosition().getValue1() ? 1 : -1)
                .get()
                .getMapPosition()
                .getValue1();
    }

    public int minCellColumnPosition() {
        return worldCells
                .stream()
                .min((c1, c2) -> c1.getMapPosition().getValue1() >= c2.getMapPosition().getValue1() ? 1 : -1)
                .get()
                .getMapPosition()
                .getValue1();
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
