package com.mygdx.claninvasion;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mygdx.claninvasion.game.Globals;
import com.mygdx.claninvasion.game.screens.GameScreens;
import com.mygdx.claninvasion.game.screens.MainGamePage;
import com.mygdx.claninvasion.game.screens.SplashScreen;


public class ClanInvasion extends Game {
	private final GameScreens screens;
	private BitmapFont font;
	private OrthographicCamera camera;

	public ClanInvasion() {
		screens = new GameScreens();
	}

	@Override
	public void create() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Globals.V_WIDTH, Globals.V_HEIGHT);

		screens.push(new MainGamePage(this));
		screens.push(new SplashScreen(this));

		setScreen(screens.get());
		font = new BitmapFont();
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
	public void dispose() {
		this.getScreen().dispose();
	}
}
