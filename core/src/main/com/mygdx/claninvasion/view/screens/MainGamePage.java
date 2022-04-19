package com.mygdx.claninvasion.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.claninvasion.ClanInvasion;
import com.mygdx.claninvasion.model.entity.*;
import com.mygdx.claninvasion.view.actors.HealthBar;
import com.mygdx.claninvasion.view.applicationlistener.FireAnimated;
import com.mygdx.claninvasion.view.applicationlistener.MainGamePageUI;
import com.mygdx.claninvasion.view.tiledmap.TiledMapStage;
import com.mygdx.claninvasion.view.utils.EntityPlacer;
import com.mygdx.claninvasion.view.utils.GameInputProcessor;
import com.mygdx.claninvasion.view.utils.IsometricTiledMapGameRenderer;
import com.mygdx.claninvasion.view.utils.InputClicker;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


import java.util.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * One of the screens which implement GamePage interface, responsible for gameplay
 * @author andreicristea
 * @author omarashour
 * @version 0.1
 * @see GamePage, UiUpdatable
 */
public class MainGamePage implements GamePage, UiUpdatable {
    private static final Vector2 translateCamera = new Vector2(280, -200);
    private static final Vector2 translateCameraBack = new Vector2(-280,200);
    private IsometricTiledMapGameRenderer renderer;
    private GameInputProcessor inputProcessor;
    private final ClanInvasion app;
    private Stage entitiesStage;
    private final List<FireAnimated> fireballs = Collections.synchronizedList(new CopyOnWriteArrayList<>());
    private final List<HealthBar> hpBars = Collections.synchronizedList(new CopyOnWriteArrayList<>());
    private final MainGamePageUI mainGamePageUI;
    private TiledMap map;



    /**
     * @param app - app instance
     */
    public MainGamePage(ClanInvasion app) {
        this.app = app;
        mainGamePageUI = new MainGamePageUI(app);
    }


    /**
     * Is fired once the page becomes active in application
     * See GamePage interface
     */
    @Override
    public void show() {
        app.getModel().changePhase();

        app.getCamera().update();
        inputProcessor = new GameInputProcessor(app.getCamera(), new InputClicker(app ,mainGamePageUI, hpBars));
        map = new TmxMapLoader().load(Gdx.files.getLocalStoragePath() + "/TileMap/Tilemap.tmx");
        app.getMap().setTileset(map.getTileSets());
        renderer = new IsometricTiledMapGameRenderer(
                map,
                1,
                new EntityPlacer(app.getModel())
        );

        // transform camera position ans scale to be in the center
        app.getCamera().translate(translateCamera);
        app.getCamera().zoom -= -.7;
        renderer.setView(app.getCamera());
        renderer.render(app.getMap(), true);
        entitiesStage = new TiledMapStage();
        Gdx.input.setInputProcessor(entitiesStage);
        app.getMap().setGraph(32);
        //fireTower();
        mainGamePageUI.create();
    }

    private void fireTower() {
        ArrayList<Tower> towers = app.getMap().getTowers();
        ArrayList<Soldier> soldiers = app.getMap().getSoldiers();
        if (towers.size() > 0 && soldiers.size() > 0) {
            Tower tower = towers.get(0);
            tower.attack(soldiers.get(0), (src, dest) -> CompletableFuture.supplyAsync(() -> {
                Vector2 positionSrc = app.getMap().tranformMapPositionToIso(src);
                Vector2 positionDest = app.getMap().tranformMapPositionToIso(dest);
                FireAnimated animated = new FireAnimated(positionSrc,
                        positionDest, (SpriteBatch) renderer.getBatch());
                fireballs.add(animated);
                return true;
            }));
        }
    }
    /**
     * Fired on every frame update
     * See GamePage interface
     * @param delta - difference between two render calls
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor((255f/255f), (255f/255f), (255f/255f), 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        for (FireAnimated fireAnimated : fireballs) {
            fireAnimated.create();
        }

        app.getCamera().update();
        inputProcessor.onRender();
        renderer.setView(app.getCamera());
        renderer.render(app.getMap());

        // health bar render
        for (HealthBar curr : hpBars) {
            curr.rendering(app.getCamera().combined);
            if (!curr.isActive()) {
                hpBars.remove(curr);
            }
        }

        // render game page ui
        mainGamePageUI.render();

        // render animated object (fireballs, arrows, etc.)
        updateAnimated();
        app.getModel().getPlayerOne().removeDead();
        app.getModel().getPlayerTwo().removeDead();
        // update actors
        update(delta);
    }



    private void updateAnimated() {
        for (FireAnimated fireAnimated : fireballs) {
            fireAnimated.setView(app.getCamera());
            fireAnimated.render();
            if (fireAnimated.isDone()) {
                fireAnimated.dispose();
                fireballs.remove(fireAnimated);
            }
        }
    }

    /**
     * Fired on every resize event by libgdx
     * See GamePage interface
     * @param width - resized width value
     * @param height - resized height value
     */
    @Override
    public void resize(int width, int height) {
        app.getCamera().viewportHeight = height;
        app.getCamera().viewportWidth = width;

        renderer.setView(app.getCamera());
        renderer.render();

        entitiesStage.getViewport().setScreenBounds(
                (int) renderer.getViewBounds().x,
                (int) renderer.getViewBounds().y,
                width,
                height
                );
        mainGamePageUI.resize(width, height);
    }

    /**
     * See GamePage interface
     */
    @Override
    public void pause() {

    }

    /**
     * See GamePage interface
     */
    @Override
    public void resume() {

    }

    /**
     * See GamePage interface
     */
    @Override
    public void hide() {

    }

    /**
     * See GamePage interface
     * Flushes state and fires cleanup
     */
    @Override
    public void dispose() {
        entitiesStage.dispose();
        mainGamePageUI.dispose();

        // transform camera position back
        app.getCamera().translate(translateCameraBack);
        app.getCamera().zoom = 1;
    }

    /**
     * Used inside render method
     * See GamePage interface
     */
    @Override
    public void update(float delta) {
        entitiesStage.act(delta);
        entitiesStage.draw();
    }
}
