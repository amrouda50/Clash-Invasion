package com.mygdx.claninvasion.view.utils;

import java.nio.file.Paths;

import com.mygdx.claninvasion.model.entity.*;
import com.mygdx.claninvasion.view.exceptions.UnknownTiledMapLayerException;
import org.javatuples.Pair;
import org.javatuples.Quartet;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.BatchTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.claninvasion.model.map.WorldCell;
import com.mygdx.claninvasion.model.map.WorldMap;


public class IsometricTiledMapGameRenderer extends BatchTiledMapRenderer {
    protected Matrix4 isoTransform;
    protected Matrix4 invIsotransform;
    protected Vector3 screenPos = new Vector3();

    protected Vector2 topRight = new Vector2();
    protected Vector2 bottomLeft = new Vector2();
    protected Vector2 topLeft = new Vector2();
    protected Vector2 bottomRight = new Vector2();

    protected static String BACKGROUND_LAYER = "Layer1";
    protected static String ENTITIES_LAYER = "Layer2";
    protected EntityPlacer chooser;
    protected boolean firstRender;

    public IsometricTiledMapGameRenderer(TiledMap map, float unitScale, EntityPlacer chooser) {
        super(map, unitScale);
        this.chooser = chooser;
        init();
    }

    private void init() {
        // create the isometric transform
        isoTransform = new Matrix4();
        isoTransform.idt();

        // isoTransform.translate(0, 32, 0);
        isoTransform.scale((float) (Math.sqrt(2.0) / 2.0), (float) (Math.sqrt(2.0) / 4.0), 1.0f);
        isoTransform.rotate(0.0f, 0.0f, 1.0f, -45);

        // ... and the inverse matrix
        invIsotransform = new Matrix4(isoTransform);
        invIsotransform.inv();
    }

    private Vector3 translateScreenToIso(Vector2 vec) {
        screenPos.set(vec.x, vec.y, 0);
        screenPos.mul(invIsotransform);

        return screenPos;
    }

    public void render(WorldMap worldMap) {
        render(worldMap, false);
    }

    /**
     * Rewritten render with special value supplied
     * @param worldMap - map of the application
     */
    public void render(WorldMap worldMap, boolean firstRender) {
        worldMap.restart();
        beginRender();
        worldMap.setEntitiesLayer((TiledMapTileLayer) map.getLayers().get(ENTITIES_LAYER));
        for (MapLayer layer : map.getLayers()) {
            renderMapLayer(layer, worldMap);
        }

        endRender();
        this.firstRender = firstRender;

        if (firstRender) {
            chooser.handleMultiPositioned(worldMap);
        } else {
            chooser.placeRender(worldMap);
        }
        worldMap.finish();
    }

    /**
     * Method used inside render(mop)
     * @param layer - tile layer
     * @param map - world model map
     */
    protected void renderMapLayer(MapLayer layer, WorldMap map) {
        if (!layer.isVisible()) {
            return;
        }
        if (layer instanceof MapGroupLayer) {
            MapLayers childLayers = ((MapGroupLayer) layer).getLayers();
            for (int i = 0; i < childLayers.size(); i++) {
                MapLayer childLayer = childLayers.get(i);
                if (!childLayer.isVisible()) {
                    continue;
                }
                renderMapLayer(childLayer);
            }
        } else {
            if (layer instanceof TiledMapTileLayer) {
                renderTileLayer((TiledMapTileLayer) layer, map);
            } else if (layer instanceof TiledMapImageLayer) {
                renderImageLayer((TiledMapImageLayer) layer);
            } else {
                renderObjects(layer);
            }
        }
    }

    protected void setScreenPoints(final float layerOffsetX, final float layerOffsetY) {
        // setting up the screen points
        // COL1
        topRight.set(viewBounds.x + viewBounds.width - layerOffsetX, viewBounds.y - layerOffsetY);
        // COL2
        bottomLeft.set(viewBounds.x - layerOffsetX, viewBounds.y + viewBounds.height - layerOffsetY);
        // ROW1
        topLeft.set(viewBounds.x - layerOffsetX, viewBounds.y - layerOffsetY);
        // ROW2
        bottomRight.set(viewBounds.x + viewBounds.width - layerOffsetX, viewBounds.y + viewBounds.height - layerOffsetY);
    }

    protected float getColorForLayer(TiledMapTileLayer layer) {
        final Color batchColor = batch.getColor();
        return Color
                .toFloatBits(
                        batchColor.r,
                        batchColor.g,
                        batchColor.b,
                        batchColor.a * layer.getOpacity()
                );
    }

    protected Pair<Float, Float> getLayerOffset(TiledMapTileLayer layer) {
        final float layerOffsetX = layer.getRenderOffsetX() * unitScale;
        // offset in tiled is y down, so we flip it
        final float layerOffsetY = -layer.getRenderOffsetY() * unitScale;
        return new Pair<>(layerOffsetX, layerOffsetY);
    }

    protected Quartet<Float, Float, Float, Float> getLayerXY(
            Pair<Float, Float> xy, TextureRegion region, TiledMapTile tile, Pair<Float, Float> offset
    ) {
        float x1 = xy.getValue0() + tile.getOffsetX() * unitScale + offset.getValue0();
        float y1 = xy.getValue1() + tile.getOffsetY() * unitScale + offset.getValue1();
        float x2 = x1 + region.getRegionWidth() * unitScale;
        float y2 = y1 + region.getRegionHeight() * unitScale;
        return new Quartet<>(x1, y1, x2, y2);
    }

    protected Quartet<Float, Float, Float, Float> getLayerUV(TextureRegion region) {
        float u1 = region.getU();
        float v1 = region.getV2();
        float u2 = region.getU2();
        float v2 = region.getV();
        return new Quartet<>(u1, v1, u2, v2);
    }

    /**
     * Most of application rendering logic lies here
     * Goes though every cell, gets though magic its coordinates
     * Then creates a vertices array for feeding it up to the draw method
     * Also uploads newly updated cells to the map
     * @param layer - tile layer
     * @param map - world model map
     */
    public void renderTileLayer(TiledMapTileLayer layer, WorldMap map) {
        final float color = getColorForLayer(layer);
        float tileWidth = layer.getTileWidth() * unitScale;
        float tileHeight = layer.getTileHeight() * unitScale;
        Pair<Float, Float> offset = getLayerOffset(layer);

        float halfTileWidth = tileWidth * 0.5f;
        float halfTileHeight = tileHeight * 0.5f;

        this.setScreenPoints(offset.getValue0(), offset.getValue1());

        int toIso = 0;
        // transforming screen coordinates to iso coordinates
        int row1 = (int) (translateScreenToIso(topLeft).y / tileWidth) + toIso;
        int row2 = (int) (translateScreenToIso(bottomRight).y / tileWidth) - toIso ;

        int col1 = (int) (translateScreenToIso(bottomLeft).x / tileWidth) - toIso ;
        int col2 = (int) (translateScreenToIso(topRight).x / tileWidth) + toIso ;

        for (int row = row2; row >= row1; row--) {
            for (int col = col1; col <= col2; col++) {
                float positionX = (col * halfTileWidth) + (row * halfTileWidth);
                float positionY = (row * halfTileHeight) - (col * halfTileHeight);
                renderTileCell(layer, new Pair<>(row, col), new Pair<>(positionX, positionY), offset, color, map);
            }

        }
    }

    protected void renderTileCell(
            TiledMapTileLayer layer,
            Pair<Integer, Integer> position,
            Pair<Float, Float> calculatedXY,
            Pair<Float, Float> offset,
            float color,
            WorldMap map
    ) {
        final TiledMapTileLayer.Cell cell = layer.getCell(position.getValue1(), position.getValue0());
        if (cell == null) {
            return;
        }

        final TiledMapTile tile = cell.getTile();
        if (tile != null) {
            final boolean flipX = cell.getFlipHorizontally();
            final boolean flipY = cell.getFlipVertically();
            final int rotations = cell.getRotation();
            TextureRegion region = tile.getTextureRegion();
            Quartet<Float, Float, Float, Float> xy = getLayerXY(calculatedXY, region, tile, offset);
            Quartet<Float, Float, Float, Float> uv = getLayerUV(region);

            CreateVerticesCommand command = new CreateVerticesCommand(new Pair<>(flipX, flipY), xy, uv, color);

            float[] vertices = command.createVertices(rotations);
            batch.draw(region.getTexture(), vertices, 0, CreateVerticesCommand.NUM_VERTICES);


            if (map != null && layer.getName().equals(BACKGROUND_LAYER)) {
                WorldCell worldCell = new WorldCell(
                        xy,
                        position,
                        region,
                        calculatedXY.getValue0() + "" + calculatedXY.getValue1()
                );
                worldCell.setTileCell(cell);
                map.addCell(worldCell);
            } else if (map != null && layer.getName().equals(ENTITIES_LAYER)) {
                WorldCell worldCell = map.getCell(position);
                chooser.place(region, position, worldCell);
//                if (worldCell != null && entity != null) {
//                    worldCell.addEntity(entity);
//                }
            } else if (map != null) {
                throw new UnknownTiledMapLayerException(layer);
            }
        }
    }

//    /**
//     * This function takes in a cell and return the EntitySymbol of the Entity in the cell , if there is no Entity then it return null
//     * @return EntitySymbol
//     */
//    protected Entity chooseEntitySymbol(TextureRegion region, Pair<Integer, Integer> position) {
//        String path = region.getTexture().toString();
//        String entityName =  Paths.get(path).getFileName().toString();
//        String trimmedEntityName  = entityName.substring(0, entityName.lastIndexOf('.'));
//
//        switch (trimmedEntityName) {
//            case "Stone":
//                return new NaturalEntity(EntitySymbol.STONE, position);
//            case "tree":
//                return new NaturalEntity(EntitySymbol.TREE, position);
//            case "Dragon":
//            case "Dragon-Flipped":
//                return new Soldier(EntitySymbol.DRAGON, position);
//            case "barbarian-fliped":
//            case "barbarian":
//                return new Soldier(EntitySymbol.BARBARIAN, position);
//            case "tower":
//                return new Tower(EntitySymbol.TOWER, position);
//            case EntitySymbol.CASTEL.sourcePart:
//
//                return new Castle(EntitySymbol.CASTEL, position);
//            default:
//                return null;
//        }
//    }

    /**
     * Overridden method to yse local
     * renderTileLayer method
     * @param layer - tile layer
     */
    @Override
    public void renderTileLayer(TiledMapTileLayer layer) {
        renderTileLayer(layer, null);
    }

}
