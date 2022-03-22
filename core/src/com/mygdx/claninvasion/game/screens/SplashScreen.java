package com.mygdx.claninvasion.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.claninvasion.ClanInvasion;
import com.mygdx.claninvasion.game.Globals;
import com.mygdx.claninvasion.game.actors.StartButton;

import java.lang.invoke.MutableCallSite;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class SplashScreen implements GamePage {
    private final ClanInvasion app;
    private final Stage stage;
    private final SpriteBatch batch;
    private Image splash;
    private Image background;
    private TextButton button;
    private TextButton.TextButtonStyle buttonStyle;
    private BitmapFont font;
    private Skin skin;
    private Music Music;



    public SplashScreen(final ClanInvasion app) {
        this.app = app;
        stage = new Stage(new FitViewport(Globals.V_WIDTH, Globals.V_HEIGHT, app.getCamera()));
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(stage);
    }

    private void initSplash() {
        Texture splashTexture = new Texture(Gdx.files.internal("splash/clashmenulogo-WhiteBackground.png"));
        splash = new Image(splashTexture);

        Texture backgroundTexture = new Texture(Gdx.files.internal("splash/background.jpg"));
        background = new Image(backgroundTexture);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        splash.setScale(2f);
        System.out.println(stage.getWidth() +" "+ stage.getHeight());
        splash.setPosition((stage.getWidth() / 1000) - 170 , (stage.getHeight() /1000) - 100 );
        stage.addActor(background);
        stage.addActor(splash);
        Music = Gdx.audio.newMusic(Gdx.files.internal("music/SplashScreenMusic.mp3"));
        Music.setVolume(1.0f);
        Music.setLooping(true);
        Music.play();
    }

    @Override
    public void show() {
        this.initSplash();

        splash.addAction( sequence(alpha(0f), fadeIn(2f)) );
//        app.getFont().getData().setScale(2.2f);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);

        stage.draw();

//        batch.begin();
//        app.getFont().draw(batch, "Clan Invasion", (stage.getWidth() / 2) - 40, stage.getHeight() / 2);
//        batch.end();
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
        Music.dispose();
    }
}
