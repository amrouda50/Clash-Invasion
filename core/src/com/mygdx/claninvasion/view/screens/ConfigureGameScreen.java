package com.mygdx.claninvasion.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.claninvasion.ClanInvasion;
import com.mygdx.claninvasion.model.Globals;
import com.mygdx.claninvasion.view.actors.GameButton;

public class ConfigureGameScreen implements GamePage {
    private final ClanInvasion app;
    private final Stage stage;
    private Image background;
    private GameButton startGameButton;

    public ConfigureGameScreen(ClanInvasion app) {
        this.app = app;
        stage = new Stage(new StretchViewport(Globals.V_WIDTH, Globals.V_HEIGHT, app.getCamera()));
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {

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

    }
}
