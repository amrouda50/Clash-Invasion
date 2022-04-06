package com.mygdx.claninvasion.view.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.BatchTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.mygdx.claninvasion.model.Globals;
import com.mygdx.claninvasion.view.tiledmap.TiledMapActor;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.badlogic.gdx.graphics.g2d.Batch.*;
import static com.badlogic.gdx.graphics.g2d.Batch.U2;

public class IsometricTiledMapGameRendereder extends BatchTiledMapRenderer {
    protected Matrix4 isoTransform;
    protected Matrix4 invIsotransform;
    protected Vector3 screenPos = new Vector3();

    protected Vector2 topRight = new Vector2();
    protected Vector2 bottomLeft = new Vector2();
    protected Vector2 topLeft = new Vector2();
    protected Vector2 bottomRight = new Vector2();
    protected boolean updateActors = false;

    protected HashMap<String, ArrayList<Actor>> actors;

    public IsometricTiledMapGameRendereder(TiledMap map) {
        super(map);
        init();
    }

    public IsometricTiledMapGameRendereder(TiledMap map, Batch batch) {
        super(map, batch);
        init();
    }

    public IsometricTiledMapGameRendereder(TiledMap map, float unitScale) {
        super(map, unitScale);
        init();
    }

    public IsometricTiledMapGameRendereder(TiledMap map, float unitScale, Batch batch) {
        super(map, unitScale, batch);
        init();
    }

    private void init () {
        // create the isometric transform
        isoTransform = new Matrix4();
        isoTransform.idt();

        // isoTransform.translate(0, 32, 0);
        isoTransform.scale((float)(Math.sqrt(2.0) / 2.0), (float)(Math.sqrt(2.0) / 4.0), 1.0f);
        isoTransform.rotate(0.0f, 0.0f, 1.0f, -45);

        // ... and the inverse matrix
        invIsotransform = new Matrix4(isoTransform);
        invIsotransform.inv();
        actors = new HashMap<>();
    }

    private Vector3 translateScreenToIso (Vector2 vec) {
        screenPos.set(vec.x, vec.y, 0);
        screenPos.mul(invIsotransform);

        return screenPos;
    }

    private Vector3 translateIsoToScreen(Vector2 vec) {
        screenPos.set(vec.x, vec.y, 0);
        screenPos.mul(isoTransform);

        return screenPos;
    }

    public void render(boolean updateActors) {
        this.updateActors = updateActors;
        beginRender();
        for (MapLayer layer : map.getLayers()) {
            renderMapLayer(layer);
        }
        endRender();
    }

    @Override
    public void renderTileLayer (TiledMapTileLayer layer) {
        if (layer.getName().equals("Layer2")) {
            return;
        }
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
        int row1 = (int)(translateScreenToIso(topLeft).y / tileWidth) + toIso;
        int row2 = (int)(translateScreenToIso(bottomRight).y / tileWidth) - toIso ;

        int col1 = (int)(translateScreenToIso(bottomLeft).x / tileWidth) - toIso ;
        int col2 = (int)(translateScreenToIso(topRight).x / tileWidth) + toIso ;

        if (actors.containsKey(layer.getName())) {
            actors.remove(layer.getName());
        }

        ArrayList<Actor> actors = new ArrayList<>();
        for (int row = row2; row >= row1; row--) {
            for (int col = col1; col <= col2; col++) {
                float x = (col * halfTileWidth) + (row * halfTileWidth);
                float y = (row * halfTileHeight) - (col * halfTileHeight);

                final TiledMapTileLayer.Cell cell = layer.getCell(col, row);
                if (cell == null) continue;
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

                    float widthRat = viewBounds.width - Globals.V_WIDTH;
                    float heightRat = viewBounds.height - Globals.V_HEIGHT;

                    batch.draw(region.getTexture(), vertices, 0, NUM_VERTICES);
                    Actor actor = new TiledMapActor(this.map, layer, cell);
                    actor.setX( -viewBounds.x + x2 -  tileWidth  );
                    actor.setY( -viewBounds.y + y2 -   tileHeight  );
                    actor.setSize(tileWidth, tileHeight);
                    actors.add(actor);
                }

//                System.out.println(Arrays.toString(vertices));
            }

//            System.out.println((viewBounds.height -  Globals.V_HEIGHT ));
            this.actors.put(layer.getName(), actors);
        }
    }

    public HashMap<String, ArrayList<Actor>> getActors() {
        return actors;
    }
}