package com.mygdx.claninvasion.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.claninvasion.ClanInvasion;
import com.mygdx.claninvasion.model.Globals;
import com.mygdx.claninvasion.model.entity.*;
import com.mygdx.claninvasion.model.gamestate.BattleState;
import com.mygdx.claninvasion.model.map.WorldCell;
import com.mygdx.claninvasion.view.actors.HealthBar;
import com.mygdx.claninvasion.view.applicationlistener.FireAnimated;
import com.mygdx.claninvasion.view.applicationlistener.FireFromEntity;
import com.mygdx.claninvasion.view.applicationlistener.MainGamePageUI;
import com.mygdx.claninvasion.view.tiledmap.TiledMapStage;
import com.mygdx.claninvasion.view.utils.EntityPlacer;
import com.mygdx.claninvasion.view.utils.GameInputProcessor;
import com.mygdx.claninvasion.view.utils.IsometricTiledMapGameRenderer;
import com.mygdx.claninvasion.view.utils.InputClicker;
import org.javatuples.Pair;
import java.util.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;


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
    private final MainGamePageUI mainGamePageUI;
    private TiledMap map;
    private final FireFromEntity<Tower, Soldier> fireFromEntity = this::fireTower;
    private EntitySymbol chosenSymbol;

    /**
     * @param app - app instance
     */
    public MainGamePage(ClanInvasion app) {
        this.app = app;
        mainGamePageUI = new MainGamePageUI(app, this);
        chosenSymbol = null;
    }

    public void setChosenSymbol(EntitySymbol entity) {
        chosenSymbol = entity;
    }

    public EntitySymbol getChosenSymbol() {
        return chosenSymbol;
    }

    public MainGamePageUI getUI() {
        return mainGamePageUI;
    }

    /**
     * Is fired once the page becomes active in application
     * See GamePage interface
     */
    @Override
    public void show() {
        if (Globals.DEBUG) {
            app.getModel().changeState();
        } else {
            app.getModel().changePhase();
        }

        app.getCamera().update();
        inputProcessor = new GameInputProcessor(app.getCamera(), new InputClicker(app, this), app);
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
        mainGamePageUI.create();
    }

    private void fireTower(Tower tower, Soldier soldier) {
        Vector2 positionSrc = app.getMap().transformMapPositionToIso(tower.getPosition());
        Vector2 positionDest = app.getMap().transformMapPositionToIso(soldier.getPosition());
        FireAnimated animated = new FireAnimated(positionSrc,
                positionDest, (SpriteBatch) renderer.getBatch());
        fireballs.add(animated);
    }

    private <T extends ArtificialEntity>
    void renderHealthBars(List<T> containers) {
        List<HealthBar> healthBars = containers.stream().map(ArtificialEntity::getHealthBar).collect(Collectors.toList());
        for (HealthBar healthBar : healthBars) {
            if (healthBar != null && healthBar.getCoordinates() != null) {
                healthBar.rendering(app.getCamera().combined);
            }
        }
    }

    private <T extends ArtificialEntity>
    void createHealthBars(List<T> containers, Color color) {
        for (T container : containers) {
                HealthBar healthBar = container.getHealthBar() == null
                        ? new HealthBar(color)
                        : container.getHealthBar();
                for (WorldCell cell : app.getMap().getCells()) {
                    if (cell.hasOccupier() && cell.hasArtificialOccupier()
                            && container.getId().equals(((ArtificialEntity)cell.getOccupier()).getId())) {
                        Vector2 coordinate = cell.getWorldIsoPoint1();
                        healthBar.setProjectionMatrix(app.getCamera().combined);
                        healthBar
                                .setCoordinates(new Pair<>(coordinate.x, coordinate.y));
                    }
                }

                if (container.getHealthBar() == null) {
                    container.setHealthBar(healthBar);
                }
        }
    }

    private void createHealthBars() {
        createHealthBars(
                app.getModel().getPlayerOne().getSoldiers(),
                app.getModel().getPlayerOne().getColor()
        );
        createHealthBars(
                app.getModel().getPlayerTwo().getSoldiers(),
                app.getModel().getPlayerTwo().getColor()
        );
    }

    private void renderHealthBars() {
        // health bar render
        // render towers health
        renderHealthBars(app.getModel().getPlayerOne().getTowers());
        renderHealthBars(app.getModel().getPlayerTwo().getTowers());
        // render soldiers health
        renderHealthBars(app.getModel().getPlayerOne().getSoldiers());
        renderHealthBars(app.getModel().getPlayerTwo().getSoldiers());
        // render mining health
        renderHealthBars(app.getModel().getPlayerOne().getMiningFarms());
        renderHealthBars(app.getModel().getPlayerTwo().getMiningFarms());
    }

    /* Create fire animation
     */
    private void createFireAnimated() {
        for (FireAnimated fireAnimated : fireballs) {
            fireAnimated.create();
        }
    }

    /**
     * Fired on every frame update
     * See GamePage interface
     * @param delta - difference between two render calls
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (app.getModel().getState() instanceof BattleState) {
            ((BattleState) app.getModel().getState()).setFireFromEntity(fireFromEntity);
        }

        // create fire
        createFireAnimated();

        app.getCamera().update();
        inputProcessor.onRender();

        // create health bars
        createHealthBars();

        renderer.setView(app.getCamera());
        renderer.render(app.getMap());

        app.getModel().getPlayerOne().removeDeadMiningFarm();
        app.getModel().getPlayerTwo().removeDeadMiningFarm();

        // update actors
        update(delta);

        // render health bars
        renderHealthBars();

        // render animated object (fireballs, arrows, etc.)
        updateAnimated();

        // render game page ui
        mainGamePageUI.render();
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
