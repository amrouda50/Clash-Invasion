package com.mygdx.claninvasion.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.claninvasion.ClanInvasion;
import com.mygdx.claninvasion.model.Globals;
import com.mygdx.claninvasion.model.player.Player;
import com.mygdx.claninvasion.view.actors.GameButton;
import com.mygdx.claninvasion.view.actors.LabeledTextField;

public class ConfigureGameScreen implements GamePage, UiUpdatable {
    private final ClanInvasion app;
    private final Stage stage;
    private GameButton confirmButton;
    private LabeledTextField playerOneField;
    private LabeledTextField playerTwoField;
    private LabeledTextField mapSizeField;
    private final Player playerOne;
    private final Player playerTwo;
    private final OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


    public ConfigureGameScreen(ClanInvasion app) {
        this.app = app;
        stage = new Stage(new StretchViewport(Globals.V_WIDTH, Globals.V_HEIGHT, camera));
        playerOne = app.getModel().getPlayerOne();
        playerTwo = app.getModel().getPlayerTwo();
    }

    @Override
    public void show() {
        initView();
        initEvents();
        confirmButton.addClickListener(() -> {
            app.getModel().changeState();
            app.changeScreen();
        });
        Gdx.input.setInputProcessor(stage);
    }

    void initEvents() {
        playerOneField.onInput(playerOne::setName);
        playerTwoField.onInput(playerTwo::setName);
    }


    void initView() {
        TextureAtlas atlas = new TextureAtlas("skin/skin/uiskin.atlas");
        Skin skin = new Skin(atlas);
        Table table = new Table(skin);
        table.setOrigin(camera.position.x, camera.position.y);
        table.setFillParent(true);

        playerOneField = new LabeledTextField(skin, "Enter player 1 name", app.getFont());
        playerTwoField = new LabeledTextField(skin, "Enter player 2 name", app.getFont());
        mapSizeField = new LabeledTextField(skin, "Enter map size", app.getFont());
        confirmButton = new GameButton(skin, "Confirm", app.getFont());

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

    @Override
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
