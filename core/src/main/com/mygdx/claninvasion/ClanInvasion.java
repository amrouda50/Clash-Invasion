package com.mygdx.claninvasion;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mygdx.claninvasion.model.GameModel;
import com.mygdx.claninvasion.model.Globals;
import com.mygdx.claninvasion.model.player.Player;
import com.mygdx.claninvasion.model.map.WorldMap;
import com.mygdx.claninvasion.view.screens.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Application entrance class.
 * Has most important shared data abd methods
 * @author andreicristea
 * @author omarashour
 * @author Dinari
 * @see com.badlogic.gdx.Game
 * @version 0.1
 */
public class ClanInvasion extends Game {
    private final GameScreens screens;
    private BitmapFont font;
    private OrthographicCamera camera;
    private ArrayList<GamePage> gamePages;
    private Music music;
    /**
     * * GameModel responsible for the model handling
     * and is working as a bridge between UI/Logic
     */
    private final GameModel gameModel;

    /** Creates a Clan Invasion object.
     */
    public ClanInvasion() {
        screens = new GameScreens();
        gameModel = new GameModel();
    }

    /** Called when the application is first created.
     * It bundles all the screens as a stack
     */
    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Globals.V_WIDTH, Globals.V_HEIGHT);
        font = new BitmapFont();
        //addMusic();

        this.gamePages = Globals.DEBUG
        ? new ArrayList<>(
            Collections.singletonList(new MainGamePage(this))
        )
        : new ArrayList<>(
            Arrays.asList(
                new GameEndedPage(this),
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

    private void addMusic() {
        music = Gdx.audio.newMusic(Gdx.files.internal("music/SplashScreenMusic.mp3"));
        music.setVolume(0.3f);
        music.setLooping(true);
        music.play();
    }

    public void changeVolume() {
        if (music.getVolume() == 0) {
            music.setVolume(0.3f);
        } else {
            music.setVolume(0.0f);
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
    public void render() {
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

    /**
     * @return - WorldMap class, which handles map interactions
     * and contains virtual map representation
     */
    public WorldMap getMap() {
        return gameModel.getWorldMap();
    }

    public Player getCurrentPlayer() {
        return gameModel.getActivePlayer();
    }

    public GameModel getModel() {
        return gameModel;
    }

    /** Called to destroy the application
     */
    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void dispose() {
        this.getScreen().dispose();
        System.exit(0);
    }
}
