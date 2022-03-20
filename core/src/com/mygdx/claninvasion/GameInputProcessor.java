package com.mygdx.claninvasion;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class GameInputProcessor implements InputProcessor {
    private final Camera camera;

    public GameInputProcessor(Camera camera) {
        this.camera = camera;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        Vector3 translate = new Vector3(0, 0, 0);
        if (keycode == Input.Keys.LEFT) {
            translate.x -= 32;
        } else if (keycode == Input.Keys.RIGHT) {
            translate.x += 32;
        } else if (keycode == Input.Keys.UP) {
            translate.y -= 32;
        } else if (keycode == Input.Keys.DOWN) {
            translate.y += 32;
        }

        camera.translate(translate);

        return false;
    }

    public void onRender() {
        if (Gdx.input.isKeyPressed(Input.Keys.Z) && camera instanceof OrthographicCamera) {
            ((OrthographicCamera)camera).zoom -= 0.01;
        } else if (Gdx.input.isKeyPressed(Input.Keys.X) && camera instanceof OrthographicCamera) {
            ((OrthographicCamera)camera).zoom += 0.01;
        }
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
