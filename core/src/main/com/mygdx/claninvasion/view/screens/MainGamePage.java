package com.mygdx.claninvasion.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.claninvasion.ClanInvasion;
import com.mygdx.claninvasion.model.adapters.IsometricToOrthogonalAdapt;
import com.mygdx.claninvasion.model.entity.EntitySymbol;
import com.mygdx.claninvasion.model.entity.Soldier;
import com.mygdx.claninvasion.model.entity.Tower;
import com.mygdx.claninvasion.model.map.WorldCell;
import com.mygdx.claninvasion.view.actors.GameButton;
import com.mygdx.claninvasion.view.actors.HealthBar;
import com.mygdx.claninvasion.view.animated.FireAnimated;
import com.mygdx.claninvasion.view.tiledmap.TiledMapStage;
import com.mygdx.claninvasion.view.utils.GameInputProcessor;
import com.mygdx.claninvasion.view.utils.IsometricTiledMapGameRenderer;
import org.javatuples.Pair;


import java.util.*;
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
    private IsometricTiledMapGameRenderer renderer;
    private GameInputProcessor inputProcessor;
    private final ClanInvasion app;
    private Stage entitiesStage;
    private final Stage uiStage;
    private final OrthographicCamera camera;
    private GameButton soldierButton;
    private GameButton towerButton;
    private GameButton mineButton;
    private TiledMap map;
    private final List<FireAnimated> fireballs = Collections.synchronizedList(new CopyOnWriteArrayList<>());
    private EntitySymbol mapClickEntityCreate;
    private HealthBar hpBar;


    /**
     * @param app - app instance
     */
    public MainGamePage(ClanInvasion app) {
        this.app = app;
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        uiStage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera));
    }

    private void addButtons() {
        TextureAtlas atlas = new TextureAtlas("skin/skin/uiskin.atlas");
        Skin skin = new Skin(atlas);
        Table table = new Table(skin);
        table.setBounds(-100, Gdx.graphics.getWidth() / 4f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        soldierButton = new GameButton(skin, "Train soldiers");
        towerButton = new GameButton(skin, "Place towers");
        mineButton = new GameButton(skin, "Place goldmine");
        soldierButton.getButton().pad(2);
        towerButton.getButton().pad(2);
        mineButton.getButton().pad(2);
        table.add(soldierButton.getButton()).space(10);
        table.add(towerButton.getButton()).spaceLeft(10);
        table.add(mineButton.getButton()).spaceLeft(10);
        uiStage.addActor(table);
    }

    private void addButtonListeners() {
        soldierButton.addClickListener(() -> {
            System.out.println("Train barbarians...");
            this.mapClickEntityCreate = EntitySymbol.BARBARIAN;
            soldierButton.getButton().setText("Training in progress...");
            app.getCurrentPlayer().addSoldiers(() ->
                    soldierButton.getButton().setText("Train soldiers"));
        });

        towerButton.addClickListener(() -> {
            System.out.println("Place tower...");
            this.mapClickEntityCreate = EntitySymbol.TOWER;
        });

        mineButton.addClickListener(() -> {
            System.out.println("Create mining...");
            this.mapClickEntityCreate = EntitySymbol.MINING;
        });

        uiStage.setDebugUnderMouse(true);
        Gdx.input.setInputProcessor(uiStage);
    }


    /**
     * Is fired once the page becomes active in application
     * See GamePage interface
     */
    @Override
    public void show() {
        app.getCamera().update();

        inputProcessor = new GameInputProcessor(app.getCamera(), (Vector3 mousePosition) -> {
            Vector2 mouseOrtho = new IsometricToOrthogonalAdapt(new Vector2(mousePosition.x, mousePosition.y)).getPoint();
            Vector3 mouseOrtho3 = new Vector3(mouseOrtho.x + WorldCell.getTransformWidth(), mouseOrtho.y - WorldCell.getTransformWidth(), 0);

            for (WorldCell worldCell : app.getMap().getCells()) {
                if (worldCell.contains(mouseOrtho3)) {

                    if (worldCell.getOccupier() == null && EntitySymbol.TOWER == mapClickEntityCreate) {
                        app.getCurrentPlayer().buildTower(worldCell);
                    } else if (worldCell.getOccupier() == null && EntitySymbol.MINING == mapClickEntityCreate) {
                        app.getCurrentPlayer().createNewMining(worldCell);
                    }
                    System.out.println(worldCell.getMapPosition().getValue0() + " " + worldCell.getMapPosition().getValue1());
                    System.out.println(worldCell.getWorldIsoPoint1().x + " " + worldCell.getWorldIsoPoint1().y);
                    hpBar = new HealthBar();
                    hpBar.setCoordinates(new Pair<Float , Float>(worldCell.getWorldIsoPoint1().x , worldCell.getWorldIsoPoint1().y));
                    //System.out.println(worldCell.getworldIsoPoint().x + " " + worldCell.getworldIsoPoint().y);
                   // shape.setColor(clue);


                    //worldCell.getTileCell().setTile(null);
                }
            }
        });
        map = new TmxMapLoader().load(Gdx.files.getLocalStoragePath() + "/TileMap/Tilemap.tmx");
        app.getMap().setTileset(map.getTileSets());
        renderer = new IsometricTiledMapGameRenderer(map, 1);

        // transform camera position ans scale to be in the center
        app.getCamera().translate(translateCamera);
        app.getCamera().zoom -= -.7;
        renderer.setView(app.getCamera());
        renderer.render(app.getMap());
        entitiesStage = new TiledMapStage();
        Gdx.input.setInputProcessor(entitiesStage);
        addButtons();
        addButtonListeners();
        app.getMap().setGraph(32, app.getMap().getCells());
        //fireTower();
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
        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        for (FireAnimated fireAnimated : fireballs) {
            fireAnimated.create();
        }

        app.getCamera().update();
        inputProcessor.onRender();

        renderer.setView(app.getCamera());
        app.getMap().clear();

        renderer.render(app.getMap());
        // render animated object (fireballs, arrows, etc.)
        updateAnimated();
        if(hpBar != null){
            hpBar.rendering(app.getCamera().combined);
        }

      /*  if(shape != null){
            shape.begin(ShapeRenderer.ShapeType.Line);
            shape.setColor(Color.BLACK);
            shape.rect(610 ,-45 , 15 , 5);
            shape.end();
            shape.begin(ShapeRenderer.ShapeType.Filled);
            shape.setColor(Color.RED);
            shape.rect((float)610.5 , (float)-44.5 , 14 , 4);
            shape.setProjectionMatrix(app.getCamera().combined);
            shape.end();
        }*/


        update(delta);
        entitiesStage.act(delta);
        entitiesStage.draw();

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
        uiStage.getViewport().update(width, height, true);
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
        hpBar.dispose();
        uiStage.dispose();
    }

    /**
     * Used inside render method
     * See GamePage interface
     */
    @Override
    public void update(float delta) {
        entitiesStage.act(delta);
        entitiesStage.draw();
        uiStage.act(delta);
        uiStage.draw();
    }
}
