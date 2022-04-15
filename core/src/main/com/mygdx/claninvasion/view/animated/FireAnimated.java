package com.mygdx.claninvasion.view.animated;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import org.javatuples.Pair;

public class FireAnimated implements ApplicationListener {
    private static final float MOVE_BY = 1f;
    private static final float OFFSET = 2;
    private SpriteBatch batch;
    private Texture texture;
    private Sprite sprite;
    private Vector2 positionDest;
    private Vector2 currentPosition;

    public FireAnimated(Vector2 position1, Vector2 position2, SpriteBatch batch) {
        positionDest = position2;
        currentPosition = position1;
        this.batch = batch;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("./BuildingBlocks/fire.png"));
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

    public void setView (OrthographicCamera camera) {
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void render() {
        if (currentPosition.dst(positionDest) - OFFSET <= 0f) {
            return;
        }
        System.out.println("render fire at position " + currentPosition.toString());
        System.out.println("destination " + positionDest);

        batch.begin();
        sprite.translate(currentPosition.x, currentPosition.y);
        sprite.draw(batch);
        batch.end();

        float initDot = currentPosition.dst(positionDest);
        Vector2 newPosition = currentPosition.cpy();
        // try to move to the right
        moveCurrent(new Pair<>(MOVE_BY, 0f), initDot, newPosition);
        // try to move to the bottom
        moveCurrent(new Pair<>(0f, MOVE_BY), initDot, newPosition);

        // try to move to the left
        moveCurrent(new Pair<>(-MOVE_BY, 0f), initDot, newPosition);
        // try to move to the top
        moveCurrent(new Pair<>(0f, -MOVE_BY), initDot, newPosition);

        // try to move to the right-bottom
        moveCurrent(new Pair<>(MOVE_BY, MOVE_BY), initDot, newPosition);
        // try to move to the left-top
        moveCurrent(new Pair<>(-MOVE_BY, -MOVE_BY), initDot, newPosition);

        // try to move to the right-top
        moveCurrent(new Pair<>(MOVE_BY, -MOVE_BY), initDot, newPosition);
        // try to move to the left-bottom
        moveCurrent(new Pair<>(-MOVE_BY, MOVE_BY), initDot, newPosition);
//        newPosition.set(newPosition.x + MOVE_BY, newPosition.y);
//        if (initDot > newPosition.dst(positionDest)) {
//            currentPosition.set(newPosition);
//        }
//
//        newPosition.set(newPosition.x, newPosition.y + MOVE_BY);
//        if (initDot > newPosition.dst(positionDest)) {
//            currentPosition.set(newPosition);
//        }
//
//        newPosition.set(newPosition.x - MOVE_BY, newPosition.y);
//        if (initDot > newPosition.dst(positionDest)) {
//            currentPosition.set(newPosition);
//        }
//
//        newPosition.set(newPosition.x , newPosition.y - MOVE_BY);
//        if (initDot > newPosition.dst(positionDest)) {
//            currentPosition.set(newPosition);
//        }
//
//        newPosition.set(newPosition.x + MOVE_BY, newPosition.y + MOVE_BY);
//        if (initDot > newPosition.dst(positionDest)) {
//            currentPosition.set(newPosition);
//        }
//
//        newPosition.set(newPosition.x - MOVE_BY, newPosition.y - MOVE_BY);
//        if (initDot > newPosition.dst(positionDest)) {
//            currentPosition.set(newPosition);
//        }
//
//        newPosition.set(newPosition.x + MOVE_BY, newPosition.y - MOVE_BY);
//        if (initDot > newPosition.dst(positionDest)) {
//            currentPosition.set(newPosition);
//        }
//
//        newPosition.set(newPosition.x - MOVE_BY, newPosition.y + MOVE_BY);
//        if (initDot > newPosition.dst(positionDest)) {
//            currentPosition.set(newPosition);
//        }
    }

    private void moveCurrent(Pair<Float, Float> MOVE_BY, float initDistance, Vector2 newPosition) {
        newPosition.set(newPosition.x + MOVE_BY.getValue0(), newPosition.y + MOVE_BY.getValue1());
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