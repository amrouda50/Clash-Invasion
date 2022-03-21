package com.mygdx.claninvasion.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.mygdx.claninvasion.ClanInvasion;
import com.mygdx.claninvasion.game.GameInputProcessor;

public class MainGamePage implements GamePage {
    private TiledMap map;
    private TiledMapRenderer renderer;
    private GameInputProcessor inputProcessor;
    private final ClanInvasion app;

    public MainGamePage(ClanInvasion app) {
        this.app = app;
    }

    @Override
    public void show() {
        app.getCamera().update();

        inputProcessor = new GameInputProcessor(app.getCamera());

        map = new TmxMapLoader().load( Gdx.files.getLocalStoragePath() + "/TileMap/TiledMap.tmx");
        renderer = new IsometricTiledMapRenderer(map);
        Gdx.input.setInputProcessor(inputProcessor);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        inputProcessor.onRender();

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
