package com.mygdx.claninvasion.model.map;


import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.claninvasion.model.entity.Entity;
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
    private TiledMapTileLayer Layer2;

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
    public void setLayer2(TiledMapTileLayer Layer2) {
        this.Layer2 = Layer2;
    }
    public TiledMapTileLayer getLayer2() {
       return  this.Layer2 ;
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
    public void mutate(WorldCell c1) {
        Pair<Integer , Integer> Coordinates = c1.getMapPosition();
        Timer.schedule(new Timer.Task() {
            int i = 7;
            @Override

            public void run() {
                TiledMapTileLayer.Cell cell = Layer2.getCell(Coordinates.getValue0(), Coordinates.getValue1());
                Layer2.setCell(i, 4, null);
                Layer2.setCell(i + 1, 4, cell);
                i++;


            }
        }, 2, 1, 20);

    }
}
