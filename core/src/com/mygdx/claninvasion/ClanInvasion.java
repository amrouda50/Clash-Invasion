package com.mygdx.claninvasion;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class ClanInvasion extends Game {
	private SpriteBatch batch;
	private Texture img;
	private GameScreen gScreen;
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("cubic.png");
		gScreen = new GameScreen(batch , img);
		setScreen(gScreen);
	}

	@Override
	public void render () {

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
