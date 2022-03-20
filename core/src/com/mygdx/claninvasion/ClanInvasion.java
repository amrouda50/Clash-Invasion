package com.mygdx.claninvasion;

import com.badlogic.gdx.Game;
import com.mygdx.claninvasion.game.screens.GameScreens;
import com.mygdx.claninvasion.game.screens.MainGamePage;


public class ClanInvasion extends Game {
	private final GameScreens screens;

	public ClanInvasion() {
		screens = new GameScreens();
		screens.push(new MainGamePage());
	}

	@Override
	public void create() {
		setScreen(screens.get());
	}


	@Override
	public void render(){
		super.render();
	}
}
