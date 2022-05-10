package com.mygdx.claninvasion.view.applicationlistener;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import org.javatuples.Pair;

public class ArtilleryAnimated implements ApplicationListener {
    private static final float MOVE_BY = 1f;
    private static final float OFFSET = 2;
    private SpriteBatch batch;
    private Texture texture;
    private Sprite sprite;
    private final Vector2 positionDest;
    private final Vector2 currentPosition;

    public ArtilleryAnimated(Vector2 position1, Vector2 position2, SpriteBatch batch) {
        positionDest = position2;
        currentPosition = position1;
        this.batch = batch;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("./BuildingBlocks/artillery.png"));
        sprite = new Sprite(texture);
    }

    @Override
    public void dispose() {
        batch.dispose();
        texture.dispose();
    }

    public boolean isDone() {
        return currentPosition.equals(positionDest);
    }

    public void setView(OrthographicCamera camera) {
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void render() {
        if (currentPosition.dst(positionDest) - OFFSET <= 0f) {
            return;
        }

        if (sprite == null) {
            create();
        }

        System.out.println("render fire at position " + currentPosition.toString());
        System.out.println("destination " + positionDest);

        batch.begin();
        sprite.translate(currentPosition.x, currentPosition.y);
        sprite.draw(batch);
        batch.end();

        changeCurrentPosition();
    }

    protected void changeCurrentPosition() {
        float initDot = currentPosition.dst(positionDest);
        // try to move to the right
        moveCurrent(new Pair<>(MOVE_BY, 0f), initDot, currentPosition);
        // try to move to the bottom
        moveCurrent(new Pair<>(0f, MOVE_BY), initDot, currentPosition);

        // try to move to the left
        moveCurrent(new Pair<>(-MOVE_BY, 0f), initDot, currentPosition);
        // try to move to the top
        moveCurrent(new Pair<>(0f, -MOVE_BY), initDot, currentPosition);

        // try to move to the right-bottom
        moveCurrent(new Pair<>(MOVE_BY, MOVE_BY), initDot, currentPosition);
        // try to move to the left-top
        moveCurrent(new Pair<>(-MOVE_BY, -MOVE_BY), initDot, currentPosition);

        // try to move to the right-top
        moveCurrent(new Pair<>(MOVE_BY, -MOVE_BY), initDot, currentPosition);
        // try to move to the left-bottom
        moveCurrent(new Pair<>(-MOVE_BY, MOVE_BY), initDot, currentPosition);
    }

    protected void moveCurrent(Pair<Float, Float> moveBy, float initDistance, Vector2 oldPosition) {
        Vector2 newPosition = new Vector2(oldPosition.x + moveBy.getValue0(), oldPosition.y + moveBy.getValue1());
        if (initDistance > newPosition.dst(positionDest)) {
            currentPosition.set(newPosition);
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}