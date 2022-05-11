package com.mygdx.claninvasion.view.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.claninvasion.ClanInvasion;
import com.mygdx.claninvasion.model.Globals;
import com.mygdx.claninvasion.model.adapters.IsometricToOrthogonalAdapt;
import com.mygdx.claninvasion.model.entity.Castle;
import com.mygdx.claninvasion.model.map.WorldCell;
import com.mygdx.claninvasion.view.screens.MainGamePage;
import org.javatuples.Pair;

/**
 * Represents an event handler for libgdx input
 * @author andreicristea
 * @author omarashour
 * @version 0.1
 * @see com.badlogic.gdx.InputProcessor
 */
public class GameInputProcessor extends InputAdapter {

    private final RunnableTouchEvent onTouchEvent;

    private final ClanInvasion app;

    private Pair<Integer, Integer> previousPosition;

    /**
     * @param event - event for click listeners
     */
    public GameInputProcessor(RunnableTouchEvent event, ClanInvasion app) {
        onTouchEvent = event;
        this.app = app;
        System.out.println(app.getScreen());
        previousPosition = new Pair<>(0,0);
    }

    /**
     * method responsible for handling game clicks
     */
    private void onTouch() {
        Vector3 mousePosition = getMousePosition();
        app.getCamera().unproject(mousePosition); // get the world position from camera
        onTouchEvent.run(mousePosition);
    }

    private void onHover() {
        Vector3 mousePosition = getMousePosition();
        app.getCamera().unproject(mousePosition);
        if (app.getScreen() instanceof MainGamePage
                && ((MainGamePage) app.getScreen()).getChosenSymbol() != null
        ) {
            Vector2 mouseOrtho = new IsometricToOrthogonalAdapt(new Vector2(mousePosition.x, mousePosition.y)).getPoint();
            Vector3 mouseOrtho3 = new Vector3(mouseOrtho.x + WorldCell.getTransformWidth(), mouseOrtho.y - WorldCell.getTransformWidth(), 0);
            Vector2 mouseOrtho2 = new Vector2(mouseOrtho3.x, mouseOrtho3.y);

            Castle currentOpponentCastle = app.getModel()
                    .getActivePlayer()
                    .getOpponent()
                    .getCastle();

            float difference = mouseOrtho2.dst(
                    app
                            .getMap()
                            .getCell(currentOpponentCastle.getPosition()).getWorldPosition()
            );
            if (difference <= Globals.MAX_ENTITY_CREATION_DISTANCE) {
                InputClicker.enabled = false;
                Cursor.changeToDisabled();
            } else {
                InputClicker.enabled = true;
                Cursor.changeToDefault();
            }
        }
    }

    private void moveCamera() {
        if (app.getModel().restrictCameraManipulations()) {
            return;
        }
        Vector3 translate = new Vector3(0, 0, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            translate.x -= 4;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            translate.x += 4;
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            translate.y -= 4;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            translate.y += 4;
        }
        app.getCamera().translate(translate);
    }

    private void zoomCamera() {
        if (app.getModel().restrictCameraManipulations()) {
            return;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            app.getCamera().zoom -= 0.03;
        } else if (Gdx.input.isKeyPressed(Input.Keys.X)) {
            app.getCamera().zoom += 0.03;
        }
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
        zoomCamera();
        moveCamera();

        if (Gdx.input.isKeyPressed(Input.Keys.M)) {
             app.changeVolume();
        }

        if ( previousPosition.getValue0() != Gdx.input.getX()
            || previousPosition.getValue1() != Gdx.input.getY()
        ) {
            previousPosition = previousPosition.setAt0(Gdx.input.getX());
            previousPosition = previousPosition.setAt1(Gdx.input.getY());
            onHover();
        }

        if (Gdx.input.justTouched()) {
            this.onTouch();
        }
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return super.mouseMoved(screenX, screenY);
    }
}
