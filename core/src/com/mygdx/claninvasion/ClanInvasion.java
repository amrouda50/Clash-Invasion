package com.mygdx.claninvasion;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
		screens.push(new MainGamePage(this));
		screens.push(new SplashScreen(this));
		font = new BitmapFont();

		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, width, height);

		setScreen(screens.get());
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
}
