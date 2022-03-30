package com.mygdx.claninvasion;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mygdx.claninvasion.model.Globals;
import com.mygdx.claninvasion.view.screens.*;

import java.util.ArrayList;
import java.util.Arrays;


public class ClanInvasion extends Game {
	private final GameScreens screens;
	private BitmapFont font;
	private OrthographicCamera camera;
	private ArrayList<GamePage> gamePages;

	/** Creates a Clan Invasion object.
	 */
	public ClanInvasion() {
		screens = new GameScreens();
	}

	/** Called when the application is first created.
	 * It bundles all the screens as a stack
	 */
	@Override
	public void create() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Globals.V_WIDTH, Globals.V_HEIGHT);
		font = new BitmapFont();

		this.gamePages = new ArrayList<>(
				Arrays.asList(
						new MainGamePage(this),
						new ConfigureGameScreen(this),
						new LoadingScreen(this),
						new SplashScreen(this)
				)
		);

		initScreens();

		setScreen(screens.pop());
	}


	/** Used to add the screen one after the other.
	 * It goes through the array list and pushes the screens.
	 */
	private void initScreens() {
		for (GamePage gamePage : gamePages) {
			screens.push(gamePage);
		}
	}

	/** Used to change the screen one after the other.
	 * It makes use of Stack's pop command.
	 */
	public void changeScreen() {
		if (screens.isEmpty()) {
			initScreens();
		}
		setScreen(screens.pop());
	}

	/** Called when the application should render itself
	 */
	@Override
	public void render(){
		super.render();
	}

	/** Gets the font.
	 * @return A Bitmap font type.
	 */
	public BitmapFont getFont() {
		return font;
	}

	/** Gets the orthographic projecting camera.
	 * @return the OrthographicCamera type camera
	 */
	public OrthographicCamera getCamera() {
		return camera;
	}

	/** Called to destroy the application
	 */
	@Override
	public void dispose() {
		this.getScreen().dispose();
	}
}
