package com.mygdx.claninvasion.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.mygdx.claninvasion.ClanInvasion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.mygdx.claninvasion.model.Globals;
import com.mygdx.claninvasion.view.utils.GameInputProcessor;
import com.mygdx.claninvasion.view.actors.GameButton;


public class MainGamePage implements GamePage {
    private TiledMap map;
    private TiledMapRenderer renderer;
    private GameInputProcessor inputProcessor;
    private final ClanInvasion app;
    private final Stage stage;
    private final OrthographicCamera camera;
    private GameButton soldierButton;
    private GameButton towerButton;
    private GameButton mineButton;


    public MainGamePage(ClanInvasion app) {
        this.app = app;
        camera = new OrthographicCamera();
        stage = new Stage(new FillViewport(Globals.V_WIDTH, Globals.V_HEIGHT, camera));

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


    @Override
    public void show() {
        app.getCamera().update();

        inputProcessor = new GameInputProcessor(app.getCamera());

        map = new TmxMapLoader().load( Gdx.files.getLocalStoragePath() + "/TileMap/Tilemap.tmx");
        renderer = new IsometricTiledMapRenderer(map);
        Gdx.input.setInputProcessor(inputProcessor);
        addButtons();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        inputProcessor.onRender();

        stage.draw();

        app.getCamera().update();
        renderer.setView(app.getCamera());
        renderer.render();
    }

    @Override
    public void resize(int width, int height) {
        app.getCamera().viewportHeight = height;
        app.getCamera().viewportWidth = width;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
