package com.mygdx.claninvasion.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.claninvasion.ClanInvasion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.claninvasion.model.Globals;
import com.mygdx.claninvasion.view.utils.GameInputProcessor;
import com.mygdx.claninvasion.view.actors.GameButton;


/**
 * One of the screens which implement GamePage interface, responsible for gameplay
 * @author andreicristea
 * @author omarashour
 * @version 0.1
 * @see GamePage, UiUpdatable
 */
public class MainGamePage implements GamePage, UiUpdatable {
    private TiledMap map;
    private TiledMapRenderer renderer;
    private GameInputProcessor inputProcessor;
    private final ClanInvasion app;
    private final Stage stage;
    private final OrthographicCamera camera;
    private GameButton soldierButton;
    private GameButton towerButton;
    private GameButton mineButton;


    /**
     * @param app - app instance
     */
    public MainGamePage(ClanInvasion app) {
        this.app = app;
        camera = new OrthographicCamera();
        stage = new Stage(new FitViewport(Globals.V_WIDTH, Globals.V_HEIGHT, camera));

    }

    private void addButtons(){
        TextureAtlas atlas = new TextureAtlas("skin/skin/uiskin.atlas");
        Skin skin = new Skin(atlas);
        Table table = new Table(skin);
        table.setBounds(-145 , 150 , Gdx.graphics.getWidth() , Gdx.graphics.getHeight());

        soldierButton = new GameButton(skin, "Train soldiers");
        towerButton = new GameButton(skin, "Place towers" );
        mineButton = new GameButton(skin, "Place goldmine" );
        soldierButton.getButton().pad(2);
        towerButton.getButton().pad(2);
        mineButton.getButton().pad(2);
        table.add(soldierButton.getButton()).space(10);
        table.add(towerButton.getButton()).spaceLeft(10);
        table.add(mineButton.getButton()).spaceLeft(10);
        stage.addActor(table);
    }


    /**
     * Is fired once the page becomes active in application
     * See GamePage interface
     */
    @Override
    public void show() {
        app.getCamera().update();

        inputProcessor = new GameInputProcessor(app.getCamera());

        map = new TmxMapLoader().load( Gdx.files.getLocalStoragePath() + "/TileMap/Tilemap.tmx");
        renderer = new IsometricTiledMapRenderer(map);
        Gdx.input.setInputProcessor(inputProcessor);
        addButtons();
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
        inputProcessor.onRender();

        app.getCamera().update();
        renderer.setView(app.getCamera());
        renderer.render();

        update(delta);
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

    }

    /**
     * Used inside render method
     * See GamePage interface
     */
    @Override
    public void update(float delta) {
        stage.act();
        stage.draw();
    }
}
