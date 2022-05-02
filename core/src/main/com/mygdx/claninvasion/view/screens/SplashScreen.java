package com.mygdx.claninvasion.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.*;
import com.mygdx.claninvasion.ClanInvasion;
import com.mygdx.claninvasion.model.Globals;
import com.mygdx.claninvasion.view.actors.GameButton;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * One of the screens which implement GamePage interface, shows splash info
 * @author andreicristea
 * @author omarashour
 * @version 0.1
 * @see GamePage, UiUpdatable
 */
public class SplashScreen implements GamePage, UiUpdatable {
    private final ClanInvasion app;
    private Stage stage;
    private Image splash;
    private GameButton startGameButton;
    private GameButton endGameButton;
    private final OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    private Viewport viewport;
    private boolean firstResize = true;

    /**
     * @param app - app instance
     */
    public SplashScreen(final ClanInvasion app) {
        this.app = app;
    }

    private void initSplash() {
        viewport = new StretchViewport(Globals.V_WIDTH, Globals.V_HEIGHT, camera);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        Texture splashTexture = new Texture(Gdx.files.internal("splash/clashmenulogo.png"));
        splash = new Image(splashTexture);
        splash.setScale(1, 1.2f);

        Texture backgroundTexture = new Texture(Gdx.files.internal("splash/background.jpg"));
        Image background = new Image(backgroundTexture);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage.addActor(background);
        addButtons();
        addActionListeners();
    }

    private void addButtons() {
        TextureAtlas atlas = new TextureAtlas("skin/new-skin/skin.atlas");
        Skin skin = new Skin(atlas);
        Table table = new Table(skin);
        table.background(skin.getDrawable("Asset 1"));
        float tableWidthRation = 0.625f;
        float tableHeightRation = 0.73f;
        table.setBounds(
                Gdx.graphics.getWidth() / 6f,
                Gdx.graphics.getHeight() / 6f,
                tableWidthRation * Gdx.graphics.getWidth(),
                tableHeightRation * Gdx.graphics.getHeight()
        );

        table.add(splash).padTop(50);
        table.row();

        startGameButton = new GameButton(skin, "Start Game", app.getFont(), null);
        endGameButton = new GameButton(skin, "End Game", app.getFont(), "Asset 5");
        startGameButton.getButton().pad(1);
        endGameButton.getButton().pad(1);
        table.add(startGameButton.getButton()).padTop(-100);
        table.row();
        table.add(endGameButton.getButton()).padTop(-20);
        stage.addActor(table);
    }

    private void addActionListeners() {
        startGameButton.addClickListener(app::changeScreen);
        endGameButton.addClickListener(() -> Gdx.app.exit());
    }


    /**
     * Is fired once the page becomes active in application
     * See GamePage interface
     */
    @Override
    public void show() {
        this.initSplash();

        splash.addAction(sequence(alpha(0f), fadeIn(2f)));
    }

    /**
     * Fired on every frame update
     * See GamePage interface
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);
    }

    /**
     * Used inside render method
     * See GamePage interface
     * @param delta - difference between two render calls
     */
    @Override
    public void update(float delta) {
        stage.act(delta);
        stage.draw();
    }

    /**
     * Fired on every resize event by libgdx
     * See GamePage interface
     * @param width - resized width value
     * @param height - resized height value
     */
    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        if (firstResize) {
            viewport.setWorldSize(width, height);
            firstResize = false;
        }
        stage.getViewport().update(width, height, true);
    }

    /**
     * See GamePage interface
     */
    @Override
    public void pause() {

    }

    /**
     * See GamePage interface
     */
    @Override
    public void resume() {

    }

    /**
     * See GamePage interface
     */
    @Override
    public void hide() {

    }

    /**
     * See GamePage interface
     * Flushes state and fires cleanup
     */
    @Override
    public void dispose() {
        stage.dispose();
    }
}
