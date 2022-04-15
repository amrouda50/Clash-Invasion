package com.mygdx.claninvasion.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.claninvasion.ClanInvasion;
import com.mygdx.claninvasion.model.adapters.IsometricToOrthogonalAdapt;
import com.mygdx.claninvasion.model.map.WorldCell;
import com.mygdx.claninvasion.model.map.WorldMap;
import com.mygdx.claninvasion.view.actors.GameButton;
import com.mygdx.claninvasion.view.utils.IsometricTiledMapGameRenderer;
import com.mygdx.claninvasion.view.tiledmap.TiledMapStage;
import com.mygdx.claninvasion.view.utils.GameInputProcessor;
import org.javatuples.Pair;

import java.util.ArrayList;


/**
 * One of the screens which implement GamePage interface, responsible for gameplay
 * @author andreicristea
 * @author omarashour
 * @version 0.1
 * @see GamePage, UiUpdatable
 */
public class MainGamePage implements GamePage, UiUpdatable {
    private IsometricTiledMapGameRenderer renderer;
    private GameInputProcessor inputProcessor;
    private final ClanInvasion app;
    private Stage entitiesStage;
    private final Stage uiStage;
    private final OrthographicCamera camera;
    private GameButton soldierButton;
    private GameButton towerButton;
    private GameButton mineButton;
    private Image muteButton;
    private Stage bStage;



    /**
     * @param app - app instance
     */
    public MainGamePage(ClanInvasion app) {
        this.app = app;
        camera = new OrthographicCamera();
        uiStage = new Stage();
    }

    private void addButtons() {
        TextureAtlas atlas = new TextureAtlas("skin/skin/uiskin.atlas");
        Skin skin = new Skin(atlas);
        Table table = new Table(skin);
        table.setBounds(-145, 150, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

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

    public void muteButton() {
        bStage = new Stage();
        Gdx.input.setInputProcessor(bStage);
        muteButton = new Image(new Texture("muteButton/speaker.png"));

        //muteButton.setTouchable(Touchable.enabled);
        muteButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (app.music.isPlaying()) {
                    app.music.pause();
                } else if (!app.music.isPlaying()) {
                    app.music.play();
                }
            }
        });
        muteButton.setPosition(0, 0);
        muteButton.setSize(36, 36);
        bStage.addActor(muteButton);
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
                    System.out.println(worldCell.getMapPosition().getValue0() + " " + worldCell.getMapPosition().getValue1());
                    if (worldCell.getOccupier() == null) {
                        return;
                    }
                    System.out.println(worldCell.getOccupier().getEntitySymbol());
                    //worldCell.getTileCell().setTile(null);
                }
            }
        });
        TiledMap map = new TmxMapLoader().load(Gdx.files.getLocalStoragePath() + "/TileMap/Tilemap.tmx");
        renderer = new IsometricTiledMapGameRenderer(map, 1);

        // transform camera position ans scale to be in the center
        app.getCamera().translate(new Vector2(280, -200));
        app.getCamera().zoom -= -.7;
        renderer.setView(app.getCamera());
        renderer.render(app.getMap());
        entitiesStage = new TiledMapStage();
        Gdx.input.setInputProcessor(entitiesStage);
        addButtons();
        muteButton();
        WorldCell c1 = app.getMap().getCell(new Pair<>(7, 4));
        app.getMap().mutate(c1);
        //System.out.println(app.getMap().getlayer1.getcell());
       // app.getMap().mutate(currentx  + 3 ,currenty + 5);
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
        app.getCamera().update();
        inputProcessor.onRender();

        renderer.setView(app.getCamera());
        app.getMap().clear();
        renderer.render(app.getMap());
        update(delta);
        entitiesStage.act(delta);
        entitiesStage.draw();
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
        entitiesStage.act();
        entitiesStage.draw();
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
        bStage.act(delta);
        bStage.draw();
    }
}
