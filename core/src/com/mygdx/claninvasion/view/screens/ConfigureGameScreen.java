package com.mygdx.claninvasion.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.claninvasion.ClanInvasion;
import com.mygdx.claninvasion.model.Globals;
import com.mygdx.claninvasion.view.actors.GameButton;
import com.mygdx.claninvasion.view.actors.LabeledTextField;

public class ConfigureGameScreen implements GamePage, UiUpdatable {
    private final ClanInvasion app;
    private final Stage stage;
//    private Image background;
    private GameButton confirmButton;
    private LabeledTextField playerOneField;
    private LabeledTextField playerTwoField;
    private LabeledTextField mapSizeField;


    public ConfigureGameScreen(ClanInvasion app) {
        this.app = app;
        stage = new Stage(new StretchViewport(Globals.V_WIDTH, Globals.V_HEIGHT, app.getCamera()));
    }

    @Override
    public void show() {
        initView();
        confirmButton.addClickListener(app::changeScreen);
        Gdx.input.setInputProcessor(stage);
    }


    void initView() {
        TextureAtlas atlas = new TextureAtlas("skin/skin/uiskin.atlas");
        Skin skin = new Skin(atlas);
        Table table = new Table(skin);
        table.setOrigin(app.getCamera().position.x, app.getCamera().position.y);
        table.setFillParent(true);

        playerOneField = new LabeledTextField(skin, "Enter player 1 name");
        playerTwoField = new LabeledTextField(skin, "Enter player 2 name");
        mapSizeField = new LabeledTextField(skin, "Enter map size");
        confirmButton = new GameButton(skin, "Confirm");

        playerOneField.applyToTheTable(table);
        table.row();
        playerTwoField.applyToTheTable(table);
        table.row();
        mapSizeField.applyToTheTable(table);
        table.row();
        table.add(confirmButton.getButton().pad(2)).colspan(2).spaceTop(10);

        stage.addActor(table);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);
    }

    public void update(float delta) {
        stage.act(delta);
        stage.draw();
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
