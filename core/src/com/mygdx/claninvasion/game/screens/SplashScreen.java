package com.mygdx.claninvasion.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.claninvasion.ClanInvasion;
import com.mygdx.claninvasion.game.Globals;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class SplashScreen implements GamePage {
    private final ClanInvasion app;
    private final Stage stage;
    private final SpriteBatch batch;
    private Image splash;
    private Image background;
    private TextButton startGameButton;
    private TextButton endGameButton;
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
        splash.setPosition((stage.getWidth() / 1000) - 170 , (stage.getHeight() /1000) - 100 );
        stage.addActor(background);
        stage.addActor(splash);
        addMusic();
        addButtons();
        addActionListeners();
    }

    private void addButtons(){
        BitmapFont Font  = new BitmapFont(Gdx.files.internal("skin/skin/default.fnt"));
        TextureAtlas atlas = new TextureAtlas("skin/skin/uiskin.atlas");
        Skin skin = new Skin(atlas);
        Table table = new Table(skin);
        table.setBounds(-85 , -50 , Gdx.graphics.getWidth() , Gdx.graphics.getHeight());

        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = skin.getDrawable("default-window");
        buttonStyle.down = skin.getDrawable("default-window");
        buttonStyle.pressedOffsetX = 1;
        buttonStyle.pressedOffsetY = -1;
        buttonStyle.font = Font ;
        startGameButton = new TextButton("Start Game" , buttonStyle );
        endGameButton = new TextButton("End Game" , buttonStyle );
        startGameButton.pad(2);
        endGameButton.pad(2);
        table.add(startGameButton);
        table.add(endGameButton);
        stage.addActor(table);
    }

    private void addActionListeners() {
        startGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                app.changeScreen();
            }
        });

        endGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Gdx.app.exit();
            }
        });
    }


    private void addMusic(){
        Music = Gdx.audio.newMusic(Gdx.files.internal("music/SplashScreenMusic.mp3"));
        Music.setVolume(1.0f);
        Music.setLooping(true);
        Music.play();
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
        Music.dispose();
    }
}
