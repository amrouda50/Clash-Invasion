package com.mygdx.claninvasion.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.claninvasion.ClanInvasion;
import com.mygdx.claninvasion.game.Globals;
import com.mygdx.claninvasion.game.actors.StartButton;
import sun.tools.jconsole.Tab;

import java.awt.*;
import java.lang.invoke.MutableCallSite;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class SplashScreen implements GamePage {
    private final ClanInvasion app;
    private final Stage stage;
    private final SpriteBatch batch;
    private Image splash;
    private Image background;
    private TextButton StartGame;
    private TextButton EndGame;
    private TextButton.TextButtonStyle buttonStyle;
    private BitmapFont font;

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
        AddMusic();
        addButtons(stage);


    }
    private void addButtons(Stage stage){

        BitmapFont Font  = new BitmapFont(Gdx.files.internal("skin/skin/default.fnt"));
        TextureAtlas atlas = new TextureAtlas("skin/skin/uiskin.atlas");
        Skin Skin = new Skin(atlas);
        Table Table = new Table(Skin);
        Table.setBounds(-85 , -50 , Gdx.graphics.getWidth() , Gdx.graphics.getHeight());

        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = Skin.getDrawable("default-window");
        buttonStyle.down = Skin.getDrawable("default-window");
        buttonStyle.pressedOffsetX = 1;
        buttonStyle.pressedOffsetY = -1;
        buttonStyle.font = Font ;
        StartGame = new TextButton("Start Game" , buttonStyle );
        EndGame = new TextButton("End Game" , buttonStyle );
        StartGame.pad(2);
        EndGame.pad(2);
        Table.add(StartGame);
        Table.add(EndGame);
        stage.addActor(Table);
    }
    private void AddMusic(){
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
