package com.mygdx.claninvasion.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.mygdx.claninvasion.ClanInvasion;
import com.mygdx.claninvasion.game.Globals;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class SplashScreen implements GamePage {
    private final ClanInvasion app;
    private final Stage stage;
    private final SpriteBatch batch;
    private Image splash;

    public SplashScreen(final ClanInvasion app) {
        this.app = app;
        stage = new Stage(new FillViewport(Globals.V_WIDTH, Globals.V_HEIGHT, app.getCamera()));
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(stage);
    }

    private void initSplash() {
        Texture splashTexture = new Texture(Gdx.files.internal("splash/test-splash.png"));
        splash = new Image(splashTexture);
        splash.setScale(0.2f);
        splash.setPosition((stage.getWidth() / 2) - ((splash.getWidth() * 0.2f) / 2), stage.getHeight() / 2);
        stage.addActor(splash);
    }

    @Override
    public void show() {
        this.initSplash();

        splash.addAction( sequence(alpha(0f), fadeIn(2f)) );
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);

        stage.draw();

        batch.begin();
        app.getFont().draw(batch, "Clan Invasion", (stage.getWidth() / 2) - 40, stage.getHeight() / 2);
        batch.end();
    }

    public void update(float delta) {
        stage.act(delta);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
