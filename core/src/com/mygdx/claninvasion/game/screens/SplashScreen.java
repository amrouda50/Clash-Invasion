package com.mygdx.claninvasion.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.claninvasion.ClanInvasion;
import com.mygdx.claninvasion.model.Globals;
import com.mygdx.claninvasion.game.actors.GameButton;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class SplashScreen implements GamePage {
    private final ClanInvasion app;
    private final Stage stage;
    private Image splash;
    private Image background;
    private GameButton startGameButton;
    private GameButton endGameButton;
    private Music music;

    public SplashScreen(final ClanInvasion app) {
        this.app = app;
        stage = new Stage(new FitViewport(Globals.V_WIDTH, Globals.V_HEIGHT, app.getCamera()));
        Gdx.input.setInputProcessor(stage);
    }

    private void initSplash() {
        Texture splashTexture = new Texture(Gdx.files.internal("splash/clashmenulogo-WhiteBackground.png"));
        splash = new Image(splashTexture);

        Texture backgroundTexture = new Texture(Gdx.files.internal("splash/background.jpg"));
        background = new Image(backgroundTexture);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        splash.setScale(2f);
        splash.setPosition((stage.getWidth() / 1000) - 170 , (stage.getHeight() /1000) - 100 );
        stage.addActor(background);
        stage.addActor(splash);
        addMusic();
        addButtons();
        addActionListeners();
    }

    private void addButtons(){
        TextureAtlas atlas = new TextureAtlas("skin/skin/uiskin.atlas");
        Skin skin = new Skin(atlas);
        Table table = new Table(skin);
        table.setBounds(-85 , -50 , Gdx.graphics.getWidth() , Gdx.graphics.getHeight());

        startGameButton = new GameButton(skin, "Start Game");
        endGameButton = new GameButton(skin, "End Game" );
        startGameButton.getButton().pad(2);
        endGameButton.getButton().pad(2);
        table.add(startGameButton.getButton());
        table.add(endGameButton.getButton());
        stage.addActor(table);
    }

    private void addActionListeners() {
        startGameButton.addClickListener(app::changeScreen);
        endGameButton.addClickListener(() -> Gdx.app.exit());
    }


    private void addMusic(){
        music = Gdx.audio.newMusic(Gdx.files.internal("music/SplashScreenMusic.mp3"));
        music.setVolume(1.0f);
        music.setLooping(true);
        music.play();
    }

    @Override
    public void show() {
        this.initSplash();

        splash.addAction( sequence(alpha(0f), fadeIn(2f)) );
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);

        stage.draw();
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
        music.dispose();
    }
}
