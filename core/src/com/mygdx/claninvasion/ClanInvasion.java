package com.mygdx.claninvasion;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.claninvasion.model.Globals;
import com.mygdx.claninvasion.view.screens.*;

import java.util.ArrayList;
import java.util.Arrays;


public class ClanInvasion extends Game {
	private final GameScreens screens;
	private BitmapFont font;
	private OrthographicCamera camera;
	private ArrayList<GamePage> gamePages;

	public ClanInvasion() {
		screens = new GameScreens();
	}

	@Override
	public void create() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Globals.V_WIDTH, Globals.V_HEIGHT);
		font = new BitmapFont();

		this.gamePages = new ArrayList<>(
				Arrays.asList(
						new ConfigureGameScreen(this),
						new LoadingScreen(this),
						new SplashScreen(this),
						new MainGamePage(this)
				)
		);

		initScreens();

		setScreen(screens.pop());
	}

	private void initScreens() {
		for (GamePage gamePage : gamePages) {
			screens.push(gamePage);
		}
	}

	public void changeScreen() {
		if (screens.isEmpty()) {
			initScreens();
		}
		setScreen(screens.pop());
	}


	@Override
	public void render(){
		super.render();
	}

	public BitmapFont getFont() {
		return font;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void dispose() {
		this.getScreen().dispose();
	}
}
