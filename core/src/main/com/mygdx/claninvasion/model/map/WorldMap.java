package com.mygdx.claninvasion.model.map;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.claninvasion.model.Globals;
import com.mygdx.claninvasion.model.entity.Entity;
import com.mygdx.claninvasion.model.entity.EntitySymbol;
import com.mygdx.claninvasion.model.entity.Soldier;
import com.mygdx.claninvasion.model.entity.Tower;
import org.javatuples.Pair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Map modal of the application
 * Should represent all the map manipulations and connected to the view libgdx tilemap
 * @version 0.01
 * TODO Logic implementation required
 */
public class WorldMap {
    private final List<WorldCell> worldCells;
    private TiledMapTileLayer entitiesLayer;
    private Graph G;
    private TiledMapTileSets tilesets;
    private CountDownLatch latch = new CountDownLatch(1);

    public WorldMap() {
        this.worldCells = Collections.synchronizedList(new CopyOnWriteArrayList<>());
    }

    public void restart() {
        latch = new CountDownLatch(1);
        worldCells.clear();
    }

    public void finish() {
        latch.countDown();
    }

    public void addCell(WorldCell worldCell) {
        this.worldCells.add(worldCell);
    }

    public void setTileset(TiledMapTileSets tilesets) {
        this.tilesets = tilesets;
    }

    public Entity createMapEntity(EntitySymbol symbol, WorldCell worldCell, Object obj) {
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();

        TiledMapTileSet tileSet = tilesets.getTileSet(symbol.tsx);
        cell.setTile(tileSet.getTile(symbol.id));

        Entity entity = EntitiesCreators.createEntity(symbol, worldCell.getMapPosition(), obj);
        occupyPosition(worldCell, entity, cell);
        return entity;
    }

    public boolean removeMapEntity(Entity entity) {
        List<WorldCell> cells = getCells(entity.getPosition());
        if (cells == null || cells.size() == 0) return false;
        cells.forEach(WorldCell::removeEntity);
        cells.forEach(cell -> getLayer2().setCell(
                cell.getMapPosition().getValue1(),
                cell.getMapPosition().getValue0(),
                null
        ));
        return true;
    }

    public Entity createMapEntity(EntitySymbol symbol, Pair<Integer, Integer> position, Object obj) {
        WorldCell cell = getCell(position);
        return createMapEntity(symbol, cell, obj);
    }

    public void occupyPosition(WorldCell worldCell, Entity occupier, TiledMapTileLayer.Cell cell) {
        getLayer2().setCell(
                worldCell.getMapPosition().getValue1(),
                worldCell.getMapPosition().getValue0(),
                cell
        );
        worldCell.setOccupier(occupier);
        System.out.println(worldCell.getMapPosition().toString());
        System.out.println(worldCell.getOccupier().getPosition());
    }

    public void clear() {
        this.worldCells.clear();
    }

    public int getSize() {
        return this.worldCells.size();
    }

    public WorldCell getCell(int index) {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return worldCells.get(index);
    }

    public int transformMapPositionToIndex(Pair<Integer, Integer> cellPlace) {
        WorldCell cell = getCell(cellPlace);
        return worldCells.indexOf(cell);
    }

    public Pair<Integer, Integer> transformMapIndexToPosition(int index) {
        return getCell(index).getMapPosition();
    }

    public Vector2 tranformMapPositionToIso(Pair<Integer, Integer> cellPlace) {
        WorldCell cell = getCell(cellPlace);
        return cell.getworldIsoPoint();
    }

    public WorldCell getCell(Pair<Integer, Integer> cellPlace) {
        try {
            return (WorldCell) worldCells.stream().filter(cell -> cell.getMapPosition().equals(cellPlace)).toArray()[0];
        } catch (Exception e) {
            return null;
        }
    }

    public List<WorldCell> getCells(Pair<Integer, Integer> cellPlace) {
        try {
            return worldCells.stream().filter(cell -> cell.getMapPosition().equals(cellPlace)).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setEntitiesLayer(TiledMapTileLayer entitiesLayer) {
        this.entitiesLayer = entitiesLayer;
    }
    public TiledMapTileLayer getLayer2() {
       return  this.entitiesLayer ;
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

    public List<WorldCell> getCells() {
        return worldCells;
    }

    public void printEntitiesLayer() {
        for (int i = minCellRowPosition(); i <= maxCellRowPosition(); i++) {
            System.out.print("{");
            for (int j = minCellColumnPosition(); j <= maxCellColumnPosition(); j++) {
                WorldCell cell = getCell(new Pair<>(i, j));
                if (cell.getOccupier() != null) {
                    System.out.print(" " + cell.getOccupier().getSymbol().letter + ", ");
                } else {
                    System.out.print(" -, ");
                }
            }
            System.out.print("}\n");
        }
    }

    public ArrayList<Tower> getTowers() {
        ArrayList<Tower> towers = new ArrayList<>();
        for (WorldCell cell : worldCells) {
            if (cell.hasOccupier() && cell.getOccupier() instanceof Tower) {
                towers.add((Tower) cell.getOccupier());
            }
        }

        return towers;
    }

    public ArrayList<Soldier> getSoldiers() {
        ArrayList<Soldier> soldiers = new ArrayList<>();
        for (WorldCell cell : worldCells) {
            if (cell.hasOccupier() && cell.getOccupier() instanceof Soldier) {
                soldiers.add((Soldier) cell.getOccupier());
            }
        }

        return soldiers;
    }

    public void mutate(WorldCell cell1, WorldCell cell2) {
        Pair<Integer, Integer> coordinates = new Pair<>(cell1.getMapPosition().getValue1(), cell1.getMapPosition().getValue0());
        Pair<Integer, Integer> coordinatedDist = new Pair<>(cell2.getMapPosition().getValue1(), cell2.getMapPosition().getValue0());
        TiledMapTileLayer.Cell cell = entitiesLayer.getCell(coordinates.getValue0(), coordinates.getValue1());
        entitiesLayer.setCell(coordinates.getValue0(), coordinates.getValue1(), null);
        entitiesLayer.setCell(coordinatedDist.getValue0(), coordinatedDist.getValue1(), cell);
    }

    public void mutate(int index1, int index2) {
        try {
            WorldCell cell1 = getCell(index1);
            WorldCell cell2 = getCell(index2);

            mutate(cell1, cell2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Graph getGraph() {
        return this.G;
    }

    public void setGraph(int size, List<WorldCell> worldCells) {
        System.out.println(size);
        this.G = new Graph(size, worldCells);
    }

    //    /** Change the containment of the c1 (possible decease
    //     * of the entity which populated it)
    //     * @param cell - cell to remove the entity
    //     */
   /* public void mutate(WorldCell cell) {
        AtomicReference<Pair<Integer, Integer>> coordinates = new AtomicReference<>(cell.getMapPosition());
        Timer.schedule(new Timer.Task() {
            int i = 7;
            int count = 0;
            @Override
            public void run() {
                TiledMapTileLayer.Cell cell = entitiesLayer.getCell(coordinates.get().getValue0() + count, coordinates.get().getValue1());
                entitiesLayer.setCell(i + count, 4, null);
                entitiesLayer.setCell(i + 1 + count, 4, cell);
                count++;
            }
        }, 2, 1, 20);

    }*/

        public List<Pair<Integer,Integer>> getNeighborsOfPoint(int x, int y) {
            List<Pair<Integer,Integer>> neighbors = new ArrayList<>();
            for (int xx = -1; xx <= 1; xx++) {
                for (int yy = -1; yy <= 1; yy++) {
                    if (xx == 0 && yy == 0) {
                        continue; // You are not neighbor to yourself
                    }
                    if (Math.abs(xx) + Math.abs(yy) > 1) {
                        continue;
                    }
                    if (isOnMap(x + xx, y + yy)) {
                        neighbors.add(new Pair<>(x + xx, y + yy));
                    }
                }
            }
            return neighbors;
        }

    public boolean isOnMap(int x, int y) {
        return x >= 0 && y >= 0 && x < Globals.V_WIDTH && y < Globals.V_HEIGHT;
    }

}
