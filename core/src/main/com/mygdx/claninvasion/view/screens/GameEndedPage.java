package com.mygdx.claninvasion.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.mygdx.claninvasion.ClanInvasion;
import com.mygdx.claninvasion.model.Globals;
import com.mygdx.claninvasion.model.gamestate.EndGameState;
import com.mygdx.claninvasion.model.player.Player;
import com.mygdx.claninvasion.view.actors.GameButton;

public class GameEndedPage implements GamePage, UiUpdatable {
    private final ClanInvasion app;
    private final Stage stage;
    private Image background;
    private GameButton startGameButton;
    private GameButton endGameButton;
    private OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    private final Skin jsonSkin = new Skin(Gdx.files.internal("skin/skin/uiskin.json"));
    private Label winnerText;
    private Label headerText;
    private final Skin skin;

    public GameEndedPage(ClanInvasion app) {
        this.app = app;
        stage = new Stage(new FillViewport(Globals.V_WIDTH, Globals.V_HEIGHT, camera));
        stage.setDebugUnderMouse(true);
        TextureAtlas atlas = new TextureAtlas("skin/skin/uiskin.atlas");
        skin = new Skin(atlas);
        winnerText = new Label("",jsonSkin);
        headerText = new Label("Winner is: ", jsonSkin);
    }

    private void initText() {
        if (app.getModel().getState() instanceof EndGameState) {
            Player winner = ((EndGameState) app.getModel().getState()).getWinnerPlayer();
            winnerText.setText(winner.getName());
        } else {
            winnerText.setText(app.getCurrentPlayer().getName());
        }

        winnerText.setFontScale(1.4f);
    }

    private void init() {
        Gdx.input.setInputProcessor(stage);
        Texture backgroundTexture = new Texture(Gdx.files.internal("splash/background.jpg"));
        background = new Image(backgroundTexture);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(background);
        addButtons();
        addActionListeners();
    }

    private void addButtons() {
        Table table = new Table(skin);
        table.align(Align.center);
        table.setBounds(-80, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        startGameButton = new GameButton(skin, "Play Again");
        endGameButton = new GameButton(skin, "Exit Game" );
        startGameButton.getButton().pad(2);
        endGameButton.getButton().pad(2);
        table.add(headerText);
        table.row();
        table.add(winnerText).spaceBottom(10).padRight(3);
        table.row();
        table.add(startGameButton.getButton());
        table.add(endGameButton.getButton());
        stage.addActor(table);
    }

    private void addActionListeners() {
        startGameButton.addClickListener(() -> {
            app.changeScreen();
        });
        endGameButton.addClickListener(() -> Gdx.app.exit());
    }

    @Override
    public void show() {
        init();
        initText();
    }


    @Override
    public void update(float delta) {
        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void render(float delta) {
        update(delta);
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
