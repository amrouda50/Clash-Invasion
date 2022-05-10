package com.mygdx.claninvasion.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.claninvasion.ClanInvasion;
import com.mygdx.claninvasion.model.Globals;
import com.badlogic.gdx.audio.Sound;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * One of the screens which implement GamePage interface, shows loading animation icon
 * @author andreicristea
 * @author omarashour
 * @version 0.1
 * @see GamePage, UiUpdatable
 */
public class LoadingScreen implements GamePage {
    private Image animated;
    private Stage stage;
    private final ClanInvasion app;
    private Sound sound;
    private float elapsedTime = 0f;
    private RepeatAction loadingAction;
    private final OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    private Viewport viewport;

    /**
     * @param app - app instance
     */
    public LoadingScreen(final ClanInvasion app) {
        this.app = app;
    }

    private void initActions() {
        loadingAction = forever(sequence(alpha(0f), fadeIn(0.6f)));
    }

    private void initAnimation() {
        viewport = new FillViewport(Globals.V_WIDTH, Globals.V_HEIGHT, camera);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        Texture texture = Globals.LOADING_TEXTURE;
        animated = new Image(texture);
        animated.setSize(200, 200);
        animated.setPosition(Gdx.graphics.getWidth() / 3.1f, Gdx.graphics.getHeight() / 3.4f);


        // background
        Texture backgroundTexture = Globals.APP_BACKGROUND_TEXTURE;
        Image background = new Image(backgroundTexture);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // table
        TextureAtlas atlas = Globals.DEFAULT_ATLAS;
        Skin skin = new Skin(atlas);
        Table table = new Table(skin);
        table.background(skin.getDrawable(Globals.ATLAS_WINDOW));
        float tableWidthRation = 0.625f;
        float tableHeightRation = 0.73f;
        table.setBounds(
                Gdx.graphics.getWidth() / 6f,
                Gdx.graphics.getHeight() / 6f,
                tableWidthRation * Gdx.graphics.getWidth(),
                tableHeightRation * Gdx.graphics.getHeight()
        );


        // add actors
        stage.addActor(background);
        stage.addActor(table);
        stage.addActor(animated);

        sound = Gdx.audio.newSound(Gdx.files.internal("SoundEffects/LoadingScreen.ogg"));
        long id = sound.play(1.0f);
        sound.setPitch(id, 2);
        sound.setLooping(id, false);
    }

    /**
     * Is fired once the page becomes active in application
     * See GamePage interface
     */
    @Override
    public void show() {
        initActions();
        initAnimation();

        animated.addAction(loadingAction);
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

        elapsedTime += delta;
        stage.draw();

        if (elapsedTime > 1.5f) {
            animated.removeAction(loadingAction);
            animated.addAction(fadeOut(0.5f, Interpolation.bounceIn));
        }

        if (elapsedTime > 2.2f) {
            app.changeScreen();
        }
    }

    /**
     * Used inside render method
     * See GamePage interface
     * @param delta - difference between two render calls
     */
    public void update(float delta) {
        stage.act(delta);
    }


    private boolean firstResize = true;
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
     sound.dispose();
    }
}
