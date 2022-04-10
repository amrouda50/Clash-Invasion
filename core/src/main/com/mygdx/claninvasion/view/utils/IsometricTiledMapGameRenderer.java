package com.mygdx.claninvasion.view.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
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
import com.mygdx.claninvasion.model.entity.Entity;
import com.mygdx.claninvasion.model.entity.EntitySymbol;
import com.mygdx.claninvasion.model.map.WorldCell;
import com.mygdx.claninvasion.model.map.WorldMap;
import org.graalvm.compiler.nodes.NodeView;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static com.badlogic.gdx.graphics.g2d.Batch.*;


public class IsometricTiledMapGameRenderer extends BatchTiledMapRenderer {
    protected Matrix4 isoTransform;
    protected Matrix4 invIsotransform;
    protected Vector3 screenPos = new Vector3();

    protected Vector2 topRight = new Vector2();
    protected Vector2 bottomLeft = new Vector2();
    protected Vector2 topLeft = new Vector2();
    protected Vector2 bottomRight = new Vector2();protected boolean firstRender = true;

    public IsometricTiledMapGameRenderer(TiledMap map) {
        super(map);
        init();
    }

    public IsometricTiledMapGameRenderer(TiledMap map, Batch batch) {
        super(map, batch);
        init();
    }

    public IsometricTiledMapGameRenderer(TiledMap map, float unitScale) {
        super(map, unitScale);
        init();
    }

    public IsometricTiledMapGameRenderer(TiledMap map, float unitScale, Batch batch) {
        super(map, unitScale, batch);
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

    private Vector3 translateIsoToScreen(Vector2 vec) {
        screenPos.set(vec.x, vec.y, 0);
        screenPos.mul(isoTransform);

        return screenPos;
    }

    /**
     * Rewritten render with special value supplied
     * @param worldMap - map of the application
     */
    public void render(WorldMap worldMap) {
        beginRender();
        worldMap.setLayer2((TiledMapTileLayer) map.getLayers().get("Layer2"));
        for (MapLayer layer : map.getLayers()) {
            renderMapLayer(layer, worldMap);
        }

        endRender();
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

    /**
     * Most of application rendering logic lies here
     * Goes though every cell, gets though magic its coordinates
     * Then creates a vertices array for feeding it up to the draw method
     * Also uploads newly updated cells to the map
     * @param layer - tile layer
     * @param map - world model map
     */
    public void renderTileLayer(TiledMapTileLayer layer, WorldMap map) {

        final Color batchColor = batch.getColor();
        final float color = Color.toFloatBits(batchColor.r, batchColor.g, batchColor.b, batchColor.a * layer.getOpacity());

        float tileWidth = layer.getTileWidth() * unitScale;
        float tileHeight = layer.getTileHeight() * unitScale;

        final float layerOffsetX = layer.getRenderOffsetX() * unitScale;
        // offset in tiled is y down, so we flip it
        final float layerOffsetY = -layer.getRenderOffsetY() * unitScale;

        float halfTileWidth = tileWidth * 0.5f;
        float halfTileHeight = tileHeight * 0.5f;

        // setting up the screen points
        // COL1
        topRight.set(viewBounds.x + viewBounds.width - layerOffsetX, viewBounds.y - layerOffsetY);
        // COL2
        bottomLeft.set(viewBounds.x - layerOffsetX, viewBounds.y + viewBounds.height - layerOffsetY);
        // ROW1
        topLeft.set(viewBounds.x - layerOffsetX, viewBounds.y - layerOffsetY);
        // ROW2
        bottomRight.set(viewBounds.x + viewBounds.width - layerOffsetX, viewBounds.y + viewBounds.height - layerOffsetY);

        int toIso = 0;
        // transforming screen coordinates to iso coordinates
        int row1 = (int) (translateScreenToIso(topLeft).y / tileWidth) + toIso;
        int row2 = (int) (translateScreenToIso(bottomRight).y / tileWidth) - toIso ;

        int col1 = (int) (translateScreenToIso(bottomLeft).x / tileWidth) - toIso ;
        int col2 = (int) (translateScreenToIso(topRight).x / tileWidth) + toIso ;

        for (int row = row2; row >= row1; row--) {
            for (int col = col1; col <= col2; col++) {
                float x = (col * halfTileWidth) + (row * halfTileWidth);
                float y = (row * halfTileHeight) - (col * halfTileHeight);

                final TiledMapTileLayer.Cell cell = layer.getCell(col, row);
                if (cell == null) {
                    continue;
                }
                final TiledMapTile tile = cell.getTile();

                if (tile != null) {
                    final boolean flipX = cell.getFlipHorizontally();
                    final boolean flipY = cell.getFlipVertically();
                    final int rotations = cell.getRotation();

                    TextureRegion region = tile.getTextureRegion();


                        float x1 = x + tile.getOffsetX() * unitScale + layerOffsetX;
                        float y1 = y + tile.getOffsetY() * unitScale + layerOffsetY;
                        float x2 = x1 + region.getRegionWidth() * unitScale;
                        float y2 = y1 + region.getRegionHeight() * unitScale;

                        float u1 = region.getU();
                        float v1 = region.getV2();
                        float u2 = region.getU2();
                        float v2 = region.getV();

                        vertices[X1] = x1;
                        vertices[Y1] = y1;
                        vertices[C1] = color;
                        vertices[U1] = u1;
                        vertices[V1] = v1;

                        vertices[X2] = x1;
                        vertices[Y2] = y2;
                        vertices[C2] = color;
                        vertices[U2] = u1;
                        vertices[V2] = v2;

                        vertices[X3] = x2;
                        vertices[Y3] = y2;
                        vertices[C3] = color;
                        vertices[U3] = u2;
                        vertices[V3] = v2;

                        vertices[X4] = x2;
                        vertices[Y4] = y1;
                        vertices[C4] = color;
                        vertices[U4] = u2;
                        vertices[V4] = v1;

                        if (flipX) {
                            float temp = vertices[U1];
                            vertices[U1] = vertices[U3];
                            vertices[U3] = temp;
                            temp = vertices[U2];
                            vertices[U2] = vertices[U4];
                            vertices[U4] = temp;
                        }
                        if (flipY) {
                            float temp = vertices[V1];
                            vertices[V1] = vertices[V3];
                            vertices[V3] = temp;
                            temp = vertices[V2];
                            vertices[V2] = vertices[V4];
                            vertices[V4] = temp;
                        }
                        if (rotations != 0) {
                            switch (rotations) {
                                case TiledMapTileLayer.Cell.ROTATE_90: {
                                    float tempV = vertices[V1];
                                    vertices[V1] = vertices[V2];
                                    vertices[V2] = vertices[V3];
                                    vertices[V3] = vertices[V4];
                                    vertices[V4] = tempV;

                                    float tempU = vertices[U1];
                                    vertices[U1] = vertices[U2];
                                    vertices[U2] = vertices[U3];
                                    vertices[U3] = vertices[U4];
                                    vertices[U4] = tempU;
                                    break;
                                }
                                case TiledMapTileLayer.Cell.ROTATE_180: {
                                    float tempU = vertices[U1];
                                    vertices[U1] = vertices[U3];
                                    vertices[U3] = tempU;
                                    tempU = vertices[U2];
                                    vertices[U2] = vertices[U4];
                                    vertices[U4] = tempU;
                                    float tempV = vertices[V1];
                                    vertices[V1] = vertices[V3];
                                    vertices[V3] = tempV;
                                    tempV = vertices[V2];
                                    vertices[V2] = vertices[V4];
                                    vertices[V4] = tempV;
                                    break;
                                }
                                case TiledMapTileLayer.Cell.ROTATE_270: {
                                    float tempV = vertices[V1];
                                    vertices[V1] = vertices[V4];
                                    vertices[V4] = vertices[V3];
                                    vertices[V3] = vertices[V2];
                                    vertices[V2] = tempV;

                                    float tempU = vertices[U1];
                                    vertices[U1] = vertices[U4];
                                    vertices[U4] = vertices[U3];
                                    vertices[U3] = vertices[U2];
                                    vertices[U2] = tempU;
                                    break;
                                }
                                default:
                                    throw new IllegalStateException("Unexpected value: " + rotations);
                            }
                        }

                        batch.draw(region.getTexture(), vertices, 0, NUM_VERTICES);
                        if (map != null) {
                            if ("Layer1".equals(layer.getName())) {
                                WorldCell worldCell = new WorldCell(new Quartet<>(x1, y1, x2, y2), new Pair<>(row, col), region, x + "" + y);
                                worldCell.setTileCell(cell);
                                map.addCell(worldCell);
                            }
                            else {
                                try {
                                    WorldCell worldCell = map.getCell(new Pair<>(row, col));
                                    EntitySymbol currentSymbol = ChooseEntitySymbol(cell);
                                    worldCell.addEntity(new Entity(currentSymbol, new Pair<>(row, col)));
                                } catch (Exception ignored) {}
                            }
                        }

                }
            }

        }


    }

    /**
     * This function takes in a cell and return the EntitySymbol of the Entity in the cell , if there is no Entity then it return null
     * @return EntitySymbol
     */
    public EntitySymbol ChooseEntitySymbol(TiledMapTileLayer.Cell x) {
        String Path = x.getTile().getTextureRegion().getTexture().toString();
        String entityName =  Paths.get(Path).getFileName().toString();
        String trimmedEntityName  = entityName.substring(0, entityName.lastIndexOf('.'));

        EntitySymbol FinalResult ;
        switch (trimmedEntityName) {
            case "Stone":
                FinalResult = EntitySymbol.STONE;
                break;

            case "tree":
                FinalResult = EntitySymbol.TREE;
                break;

            case "Dragon":
                FinalResult = EntitySymbol.DRAGON;
                break;

            case "Dragon-Flipped":
                FinalResult = EntitySymbol.DRAGON;
                break;

            case "barbarian-fliped":
                FinalResult = EntitySymbol.BARBARIAN;
                break;

            case "barbarian":
                FinalResult = EntitySymbol.BARBARIAN;
                break;
            default:
                FinalResult = null;
                break;
        }


        return FinalResult;
    }

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
