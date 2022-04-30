package com.mygdx.claninvasion.model.map;

import com.mygdx.claninvasion.model.entity.*;
import org.javatuples.Pair;
import org.javatuples.Quartet;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.claninvasion.model.adapters.IsometricToOrthogonalAdapt;

import java.util.ArrayList;
import java.util.List;

/** World Cell represents the entity position at the current time.
 */
public class WorldCell {
    /** orthogonal (virtual) position of the cell.
     */
    private Vector2 worldPosition;
    /** tiled map position.
     */
    private Pair<Integer, Integer> mapPosition;
    /**
     * Entity which occupies the place
     */
    private List<Entity> occupier;
    /**
     * Texture for drawing
     */
    private TextureRegion texture;
    /**
     * isometric point of the left-top side of the cell
     */
    private final Vector2 worldIsoPoint;

    /**
     * isometric point of the right-bottom side of the cell
     */
    private final Vector2 worldIsoPoint1;
    private final String id;
    /**
     * tile width or cell width (and height) in orthogonal
     */
    public static final int WIDTH = 16;
    public static final int HEIGHT = 16;

    private TiledMapTileLayer.Cell cell;

    public WorldCell(Quartet<Float, Float, Float, Float> worldPos, Pair<Integer, Integer> mapPos, TextureRegion textureRegion, String id) {
        this.id = id;
        worldIsoPoint1 = new Vector2(worldPos.getValue2(), worldPos.getValue3());
        worldIsoPoint = new Vector2(worldPos.getValue0(), worldPos.getValue1());
        init(new Pair<>(worldPos.getValue0(), worldPos.getValue1()), mapPos, textureRegion);
    }

    private void init(Pair<Float, Float> worldPos, Pair<Integer, Integer> mapPos, TextureRegion textureRegion) {
        occupier = new ArrayList<>();
        worldPosition = new IsometricToOrthogonalAdapt(worldPos).getPoint();
        mapPosition = mapPos;
        texture = textureRegion;
    }

    /**
     * @return - orthogonal (virtual) position of the cell
     */
    public Vector2 getWorldPosition() {
        return worldPosition.cpy();
    }

    /**
     * @return - isometric point of the left-top side of the cell
     */
    public Vector2 getWorldIsoPoint() {
        return worldIsoPoint.cpy();
    }

    /**
     * @return - isometric vector of the left-top side of the cell in 3d space (might be useful)
     */
    public Vector3 getWorldIsoPointV3() {
        return new Vector3(getWorldIsoPoint().x, getWorldIsoPoint().y, 0);
    }

    /**
     * @return - is the cell contains this vector (3d check - y is ignored)
     */
    public boolean contains(Vector3 vector3) {
        return new Rectangle(worldPosition.x, worldPosition.y, WIDTH, HEIGHT).contains(new Vector2(vector3.x, vector3.y));
    }

    /**
     * @return - id of the cell
     */
    public String getId() {
        return this.id;
    }

    /**
     * Replaces an entity with another entity
     * @param newOccupier - entity which will be at this place
     */
    public void changeOccupier(Entity newOccupier) {
        this.occupier.set(0,  newOccupier);
    }

    /**
     * @param cell - tile used for cell representation
     */
    public void setTileCell(TiledMapTileLayer.Cell cell) {
        this.cell = cell;
    }

    /**
     * @return - tile used for cell representation
     */
    public TiledMapTileLayer.Cell getTileCell() {
        return cell;
    }

    /**
     * @return - tiled map position
     */
    public Pair<Integer, Integer> getMapPosition() {
        return mapPosition;
    }

    public Entity getOccupier() {
        if (occupier.size() == 0) {
            return null;
        }
        return occupier.get(0);
    }

    public boolean hasOccupier() {
        return occupier != null && !occupier.isEmpty() && occupier.get(0) != null;
    }

    public boolean hasArtificialOccupier() {
        return !occupier.isEmpty() && (occupier.get(0) instanceof Soldier);
    }

    public void setOccupier(Entity occupier, boolean multiple) {
        if (multiple) {
            if (this.occupier.size() > 0 && this.occupier.get(0) == null) {
                this.occupier.set(0, occupier);
            } else {
                this.occupier.add(occupier);
            }
        } else if (this.occupier.size() > 0) {
            this.occupier.set(0, occupier);
        } else {
            this.occupier.add(occupier);
        }

    }

    public void setOccupier(Entity occupier) {
        this.setOccupier(occupier, occupier instanceof Soldier);
    }

    public TextureRegion getTexture() {
        return texture;
    }

    /**
     * @return - isometric point of the right-bottom side of the cell
     */
    public Vector2 getWorldIsoPoint1() {
        return worldIsoPoint1.cpy();
    }

    /**
     * @return - magic value used for correct click detector (for now)
     */
    public static int getTransformWidth() {
        return WorldCell.WIDTH + (WorldCell.WIDTH  / 2);
    }

    /**
     * Sets the occupier to null
     */
    public void removeEntity() {
        occupier = null;
    }


}
