package com.mygdx.claninvasion;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;


public class ClanInvasion extends ApplicationAdapter {
	private TiledMap map;
	private OrthographicCamera camera;
	private TiledMapRenderer renderer;
	private InputProcessor inputProcessor;

	@Override
	public void create() {
		Gdx.app.log("Message", "Game screen created");
		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, width, height);

		camera.update();

		inputProcessor = new GameInputProcessor(camera);

		map = new TmxMapLoader().load( Gdx.files.getLocalStoragePath() + "/TileMap/TiledMap.tmx");
		renderer = new IsometricTiledMapRenderer(map);
		Gdx.input.setInputProcessor(inputProcessor);
	}


	@Override
	public void render(){
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
			camera.zoom -= 0.01;
		} else if (Gdx.input.isKeyPressed(Input.Keys.X)) {
			camera.zoom += 0.01;
		}

		camera.update();
		renderer.setView(camera);
		renderer.render();
	}
	@Override
	public void dispose(){
	}

}
