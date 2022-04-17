package com.mygdx.claninvasion.view.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.claninvasion.model.adapters.IsometricToOrthogonalAdapt;
import com.mygdx.claninvasion.model.map.WorldCell;
import com.mygdx.claninvasion.model.map.WorldMap;

/**
 * Represents an event handler for libgdx input
 * @author andreicristea
 * @author omarashour
 * @version 0.1
 * @see com.badlogic.gdx.InputProcessor
 */
public class GameInputProcessor implements InputProcessor {
    /**
     * camera of the application
     */
    private final Camera camera;

    private final RunnableTouchEvent onTouchEvent;

    /**
     * @param camera - camera of the application
     * @param event - event for click listeners
     */
    public GameInputProcessor(Camera camera, RunnableTouchEvent event) {
        this.camera = camera;
        onTouchEvent = event;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    /**
     * method responsible for handling game clicks
     */
    private void onTouch() {
        Vector3 mousePosition = getMousePosition();
        camera.unproject(mousePosition); // get the world position from camera
        onTouchEvent.run(mousePosition);
    }


    /**
     * @return vector of the current mouse position
     */
    public Vector3 getMousePosition() {
        return new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
    }

    /**
     * Method which ought to be called inside render() or update() methods
     * in parent class
     */
    public void onRender() {
        Vector3 translate = new Vector3(0, 0, 0);

        if (Gdx.input.isKeyPressed(Input.Keys.Z) && camera instanceof OrthographicCamera) {
            ((OrthographicCamera) camera).zoom -= 0.03;
        } else if (Gdx.input.isKeyPressed(Input.Keys.X) && camera instanceof OrthographicCamera) {
            ((OrthographicCamera) camera).zoom += 0.03;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            translate.x -= 4;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            translate.x += 4;
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            translate.y -= 4;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            translate.y += 4;
        }

        if (Gdx.input.justTouched()) {
            this.onTouch();
        }

        camera.translate(translate);
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
