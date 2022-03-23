package com.mygdx.claninvasion.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.mygdx.claninvasion.ClanInvasion;
import com.mygdx.claninvasion.game.Globals;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import java.util.ArrayList;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class LoadingScreen implements GamePage {
    private Image animated;
    private final Stage stage;
    private final ClanInvasion app;
    private Sound Sound;

    public LoadingScreen(final ClanInvasion app) {
        this.app = app;
        stage = new Stage(new FillViewport(Globals.V_WIDTH, Globals.V_HEIGHT, app.getCamera()));
        Gdx.input.setInputProcessor(stage);
    }


    private void initAnimation() {
        Texture texture = new Texture(Gdx.files.internal("LoadingscreenAnimation/Loading-Screen-icon0.png"));
        animated = new Image(texture);
        animated.setSize(200, 200);
        animated.setPosition((stage.getWidth() / 2) - 100, stage.getHeight() / 4);

        stage.addActor(animated);
        Sound = Gdx.audio.newSound(Gdx.files.internal("SoundEffects/LoadingScreen.ogg"));
        long id = Sound.play(1.0f);
        Sound.setPitch(id , 2);
        Sound.setLooping(id , false);
    }


    @Override
    public void show() {
        this.initAnimation();

        animated.addAction(forever(sequence(alpha(0f), fadeIn(0.6f))) );
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
     Sound.dispose();
    }
}
