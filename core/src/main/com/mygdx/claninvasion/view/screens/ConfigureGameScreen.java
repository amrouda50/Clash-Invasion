package com.mygdx.claninvasion.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
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
    private final Player playerOne;
    private final Player playerTwo;
    private final OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    private Viewport viewport;


    public ConfigureGameScreen(ClanInvasion app) {
        this.app = app;
        viewport = new StretchViewport(Globals.V_WIDTH, Globals.V_HEIGHT, camera);
        stage = new Stage();
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
        // background
        Texture backgroundTexture = Globals.APP_BACKGROUND_TEXTURE;
        Image background = new Image(backgroundTexture);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        TextureAtlas atlas = Globals.DEFAULT_ATLAS;
        Skin skin = new Skin(atlas);
        Table table = new Table(skin);
        table.setOrigin(camera.position.x, camera.position.y);
        table.background(skin.getDrawable(Globals.ATLAS_WINDOW));
        float tableWidthRation = 0.625f;
        float tableHeightRation = 0.63f;
        table.setBounds(
                Gdx.graphics.getWidth() / 6f,
                Gdx.graphics.getHeight() / 6f,
                tableWidthRation * Gdx.graphics.getWidth(),
                tableHeightRation * Gdx.graphics.getHeight()
        );

        playerOneField = new LabeledTextField(skin, "Enter player 1 name", app.getFont());
        playerTwoField = new LabeledTextField(skin, "Enter player 2 name", app.getFont());
        confirmButton = new GameButton(skin, "Confirm", app.getFont(), Globals.ATLAS_BUTTON_PRIMARY);
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = app.getFont();
        Label titleLabel = new Label("Configure", labelStyle);
        titleLabel.setFontScale(1.5f);
        titleLabel.setColor(Color.valueOf(Globals.COLOR_BLACK_BLUE));

        table.add(titleLabel).padBottom(20);
        table.row();
        playerOneField.applyToTheTable(table);
        table.row();
        playerTwoField.applyToTheTable(table);
        table.row();
        table.add(confirmButton.getButton().pad(2)).colspan(2);

        stage.addActor(background);
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

    private boolean firstResize = true;

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        if (firstResize) {
            viewport.setWorldSize(width, height);
            firstResize = false;
        }
        stage.getViewport().update(width, height, true);
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
