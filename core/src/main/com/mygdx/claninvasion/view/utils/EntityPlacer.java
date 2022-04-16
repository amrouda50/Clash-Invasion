package com.mygdx.claninvasion.view.utils;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.claninvasion.model.GameModel;
import com.mygdx.claninvasion.model.Player;
import com.mygdx.claninvasion.model.entity.*;
import com.mygdx.claninvasion.model.map.WorldCell;
import com.mygdx.claninvasion.model.map.WorldMap;
import org.javatuples.Pair;
import org.javatuples.Quartet;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

interface Placer {
    void placeRender(WorldMap map);
    void place(TextureRegion region, Pair<Integer, Integer> position, WorldCell cell);
}

public class EntityPlacer implements Placer {
    private final GameModel model;
    private final ArrayList<Pair<Integer, Integer>> castleReversePositions;
    private final ArrayList<Pair<Integer, Integer>> castlePositions;

    public EntityPlacer(GameModel game) {
        model = game;
        castlePositions = new ArrayList<>();
        castleReversePositions = new ArrayList<>();
    }

    public void handleMultiPositioned(WorldMap map) {
        Quartet<Pair<Integer, Integer>, Pair<Integer, Integer>, Pair<Integer, Integer>, Pair<Integer, Integer>>
                castlePosition = getCastlePositions(castlePositions);
        Quartet<Pair<Integer, Integer>, Pair<Integer, Integer>, Pair<Integer, Integer>, Pair<Integer, Integer>>
                reverseCastlePositions = getCastlePositions(castleReversePositions);

        Castle player1Castle = new Castle(EntitySymbol.CASTEL, castlePosition, model.getPlayerOne());
        Castle player2Castle = new Castle(EntitySymbol.CASTEL_REVERSED, reverseCastlePositions, model.getPlayerTwo());

        setCastleCellOccupier(map, player1Castle);
        setCastleCellOccupier(map, player2Castle);

        model.getPlayerOne().changeCastle(player1Castle);
        model.getPlayerTwo().changeCastle(player2Castle);

        castlePositions.clear();
        castleReversePositions.clear();
    }

    private void setCastleCellOccupier(WorldMap map, Castle castle) {
        WorldCell cell = map.getCell(castle.getPositions().getValue0());
        if (cell == null) {
            return;
        }
        cell.setOccupier(castle);
        cell = map.getCell(castle.getPositions().getValue1());
        if (cell == null) {
            return;
        }
        cell.setOccupier(castle);
        cell = map.getCell(castle.getPositions().getValue2());
        if (cell == null) {
            return;
        }
        cell.setOccupier(castle);
        cell = map.getCell(castle.getPositions().getValue3());
        if (cell == null) {
            return;
        }
        cell.setOccupier(castle);
    }

    private Quartet<Pair<Integer, Integer>, Pair<Integer, Integer>, Pair<Integer, Integer>, Pair<Integer, Integer>>
    getCastlePositions(ArrayList<Pair<Integer, Integer>> castlePositions) {
        Pair<Integer, Integer> bottomRight =  castlePositions.get(5);
        Pair<Integer, Integer> topLeft = castlePositions.get(4);
        Pair<Integer, Integer> bottomLeft = new Pair<>(topLeft.getValue0(), bottomRight.getValue1());
        Pair<Integer, Integer> topRight = new Pair<>( bottomRight.getValue0(), topLeft.getValue1());
        return new Quartet<>(topLeft, topRight, bottomRight, bottomLeft);
    }

    @Override
    public void place(TextureRegion region, Pair<Integer, Integer> position, WorldCell cell) {
        if (cell == null) {
            return;
        }
        String path = region.getTexture().toString();
        String name =  Paths.get(path).getFileName().toString();
        String trimmedEntityName  = name.substring(0, name.lastIndexOf('.'));

        if (EntitySymbol.CASTEL.sourcePart.equals(trimmedEntityName)) {
            castlePositions.add(position);
        } else if (EntitySymbol.CASTEL_REVERSED.sourcePart.equals(trimmedEntityName)){
            castleReversePositions.add(position);
        } else if (EntitySymbol.TREE.sourcePart.equals(trimmedEntityName)) {
            NaturalEntity entity = new NaturalEntity(EntitySymbol.TREE, position);
            cell.setOccupier(entity);
        } else if (EntitySymbol.STONE.sourcePart.equals(trimmedEntityName)) {
            NaturalEntity entity = new NaturalEntity(EntitySymbol.STONE, position);
            cell.setOccupier(entity);
        }
    }

    private <T extends Entity> void setEntities(List<T> entities, WorldMap map) {
        for (Entity soldier : entities) {
            WorldCell cell = map.getCell(soldier.getPosition());
            if (cell != null) {
                cell.setOccupier(soldier);
            }
        }
    }

    @Override
    public void placeRender(WorldMap map) {
        place(map, model.getPlayerOne());
        place(map, model.getPlayerTwo());
    }

    private void place(WorldMap map, Player player) {
        setCastleCellOccupier(map, player.getCastle());

        setEntities(player.getSoldiers(), map);
        setEntities(player.getMiningFarms(), map);
        setEntities(player.getTowers(), map);
    }
}
