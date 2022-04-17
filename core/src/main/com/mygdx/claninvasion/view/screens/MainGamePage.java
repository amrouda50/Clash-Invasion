package com.mygdx.claninvasion.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.claninvasion.ClanInvasion;
import com.mygdx.claninvasion.model.Player;
import com.mygdx.claninvasion.model.adapters.IsometricToOrthogonalAdapt;
import com.mygdx.claninvasion.model.entity.EntitySymbol;
import com.mygdx.claninvasion.model.entity.Soldier;
import com.mygdx.claninvasion.model.entity.Tower;
import com.mygdx.claninvasion.model.map.WorldCell;
import com.mygdx.claninvasion.view.actors.GameButton;
import com.mygdx.claninvasion.view.animated.FireAnimated;
import com.mygdx.claninvasion.view.tiledmap.TiledMapStage;
import com.mygdx.claninvasion.view.utils.GameInputProcessor;
import com.mygdx.claninvasion.view.utils.IsometricTiledMapGameRenderer;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.mygdx.claninvasion.model.GameModel;
import java.util.Timer;
import java.util.TimerTask;


import java.util.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * One of the screens which implement GamePage interface, responsible for gameplay
 * @author andreicristea
 * @author omarashour
 * @version 0.1
 * @see GamePage, UiUpdatable
 */
public class MainGamePage implements GamePage, UiUpdatable {
    private static final Vector2 translateCamera = new Vector2(280, -200);
    private IsometricTiledMapGameRenderer renderer;
    private GameInputProcessor inputProcessor;
    private final ClanInvasion app;
    private Stage entitiesStage;
    private final Stage uiStage;
    private final OrthographicCamera camera;
    private GameButton soldierButton;
    private GameButton towerButton;
    private GameButton mineButton;
    private final  TextureAtlas atlas = new TextureAtlas("skin/skin/uiskin.atlas");
    private final Skin skin = new Skin(atlas);
    private final Skin s2 = new Skin(Gdx.files.internal("skin/skin/uiskin.json"));
    private final List<FireAnimated> fireballs = Collections.synchronizedList(new CopyOnWriteArrayList<>());
    private EntitySymbol mapClickEntityCreate;

    public GameModel gameModel;
    private float timeCount;
    private TiledMap map;


    Table Toptable;
    int counter = 30;
    Label Time;



    /**
     * @param app - app instance
     */
    public MainGamePage(ClanInvasion app) {
        this.app = app;
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        uiStage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera));
        gameModel = new GameModel();
        Time = new Label(Integer.toString(counter) , s2);

    }
    private void AddSeprationLines(){
        ShapeRenderer sr = new ShapeRenderer();
        sr.setColor(Color.BLACK);
        sr.setProjectionMatrix(camera.combined);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.rectLine(-100, 425, 1100, 425, 2);
        sr.end();
        sr.setColor(Color.BLACK);
        sr.setProjectionMatrix(camera.combined);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.rectLine(-100, 85, 1100, 85, 2);
        sr.end();
    }
    private void SetTopBar() {
        Table Toptable = new Table(skin);
        Toptable.setBounds(-10, Gdx.graphics.getWidth() / 3, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Label Turn = new Label("Turn: "+ app.getCurrentPlayer().getName() , s2);
      // Label Time = new Label("Time: 29 sec left" , s2);
        Label Phase = new Label("Phase: " + gameModel.getPhase() , s2);
        Turn.setColor(Color.BLACK);
        Time.setColor(Color.BLACK);
        Phase.setColor(Color.BLACK);
        Toptable.add(Turn).spaceLeft(2);
        Toptable.add(Time).spaceLeft(100);
        Toptable.add(Phase).spaceLeft(100);

        uiStage.addActor(Toptable);
    }
    private void SetButtonBar(){
        Table Bottomtable = new Table(skin);
        Bottomtable.setBounds(160, -200, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        setPlayer2(Bottomtable);
        Table Bottomtable2 = new Table(skin);
        Bottomtable2.setBounds(-160, -200, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        setPlayer1(Bottomtable2);


       // setPlayer1(Bottomtable);


    }
    private void setPlayer2(Table Bottomtable){
        String[] values = new String[]{"Train Soliders", "Building Tower", "Build Goldmine"};
        SelectBox<String> selectBox = new SelectBox<String>(s2);
        selectBox.setSize(5f , 5f);
        selectBox.setItems(values);
        Label P2 = new Label("Player 2 " , s2);
        Label P2Money = new Label(" $ 4000" , s2);
        Label P2Castels = new Label("10 castels" , s2);
        Label P2Towers = new Label("    10 towers " , s2);
        Label P2Soliders = new Label("100 Soliders" , s2);
        Label P2Level = new Label("Level 1" , s2);
        P2.setColor(Color.RED);
        P2Money.setColor(Color.BLACK);
        P2Castels.setColor(Color.BLACK);
        P2Level.setColor(Color.BLACK);
        P2Towers.setColor(Color.BLACK);
        P2Soliders.setColor(Color.BLACK);
        Bottomtable.add(P2);
        Bottomtable.add(P2Money);
        Bottomtable.add(selectBox);
        Bottomtable.row();
        Bottomtable.add(P2Castels);
        Bottomtable.add(P2Towers);
        Bottomtable.add(P2Soliders);
        Bottomtable.row();
        Bottomtable.add(P2Level).spaceLeft(0);

        uiStage.addActor(Bottomtable);
        /*selectBox.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println( selectBox.getSelected());
            }
        });*/
    }
    private void setPlayer1(Table Bottomtable){
        String[] values = new String[]{"Train Soliders", "Building Tower", "Build Goldmine"};
        SelectBox<String> selectBox = new SelectBox<String>(s2);
        selectBox.setSize(5f , 5f);
        selectBox.setItems(values);
        Label P2 = new Label("Player 2 " , s2);
        Label P2Money = new Label(" $ 4000" , s2);
        Label P2Castels = new Label("10 castels" , s2);
        Label P2Towers = new Label("    10 towers " , s2);
        Label P2Soliders = new Label("100 Soliders" , s2);
        Label P2Level = new Label("Level 1" , s2);
        P2.setColor(Color.RED);
        P2Money.setColor(Color.BLACK);
        P2Castels.setColor(Color.BLACK);
        P2Level.setColor(Color.BLACK);
        P2Towers.setColor(Color.BLACK);
        P2Soliders.setColor(Color.BLACK);
        Bottomtable.add(P2);
        Bottomtable.add(P2Money);
        Bottomtable.add(selectBox);
        Bottomtable.row();
        Bottomtable.add(P2Castels);
        Bottomtable.add(P2Towers);
        Bottomtable.add(P2Soliders);
        Bottomtable.row();
        Bottomtable.add(P2Level);

        uiStage.addActor(Bottomtable);
    }

    private void addButtonListeners() {
        soldierButton.addClickListener(() -> {
            System.out.println("Train barbarians...");
            this.mapClickEntityCreate = EntitySymbol.BARBARIAN;
            soldierButton.getButton().setText("Training in progress...");
            app.getCurrentPlayer().addSoldiers(() ->
                    soldierButton.getButton().setText("Train soldiers"));
        });

        towerButton.addClickListener(() -> {
            System.out.println("Place tower...");
            this.mapClickEntityCreate = EntitySymbol.TOWER;
        });

        mineButton.addClickListener(() -> {
            System.out.println("Create mining...");
            this.mapClickEntityCreate = EntitySymbol.MINING;
        });

        uiStage.setDebugUnderMouse(true);
        Gdx.input.setInputProcessor(uiStage);
    }


    /**
     * Is fired once the page becomes active in application
     * See GamePage interface
     */
    @Override
    public void show() {
        app.getCamera().update();
        inputProcessor = new GameInputProcessor(app.getCamera(), (Vector3 mousePosition) -> {
            Vector2 mouseOrtho = new IsometricToOrthogonalAdapt(new Vector2(mousePosition.x, mousePosition.y)).getPoint();
            Vector3 mouseOrtho3 = new Vector3(mouseOrtho.x + WorldCell.getTransformWidth(), mouseOrtho.y - WorldCell.getTransformWidth(), 0);

            for (WorldCell worldCell : app.getMap().getCells()) {
                if (worldCell.contains(mouseOrtho3)) {

                    if (worldCell.getOccupier() == null && EntitySymbol.TOWER == mapClickEntityCreate) {
                        app.getCurrentPlayer().buildTower(worldCell);
                    } else if (worldCell.getOccupier() == null && EntitySymbol.MINING == mapClickEntityCreate) {
                        app.getCurrentPlayer().createNewMining(worldCell);
                    }
                    System.out.println(worldCell.getMapPosition().getValue0() + " " + worldCell.getMapPosition().getValue1());

                }
            }
        });
        map = new TmxMapLoader().load(Gdx.files.getLocalStoragePath() + "/TileMap/Tilemap.tmx");
        app.getMap().setTileset(map.getTileSets());
        renderer = new IsometricTiledMapGameRenderer(map, 1);

        // transform camera position ans scale to be in the center
        app.getCamera().translate(translateCamera);
        app.getCamera().zoom -= -.7;
        renderer.setView(app.getCamera());
        renderer.render(app.getMap());
        entitiesStage = new TiledMapStage();
        Gdx.input.setInputProcessor(entitiesStage);

        SetTopBar();
        SetButtonBar();

       // addButtonListeners();
        app.getMap().setGraph(32, app.getMap().getCells());
        //fireTower();
    }

    private void fireTower() {
        ArrayList<Tower> towers = app.getMap().getTowers();
        ArrayList<Soldier> soldiers = app.getMap().getSoldiers();
        if (towers.size() > 0 && soldiers.size() > 0) {
            Tower tower = towers.get(0);
            tower.attack(soldiers.get(0), (src, dest) -> CompletableFuture.supplyAsync(() -> {
                Vector2 positionSrc = app.getMap().tranformMapPositionToIso(src);
                Vector2 positionDest = app.getMap().tranformMapPositionToIso(dest);
                FireAnimated animated = new FireAnimated(positionSrc,
                        positionDest, (SpriteBatch) renderer.getBatch());
                fireballs.add(animated);
                return true;
            }));
        }
    }
    /**
     * Fired on every frame update
     * See GamePage interface
     * @param delta - difference between two render calls
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        for (FireAnimated fireAnimated : fireballs) {
            fireAnimated.create();
        }
        app.getCamera().update();
        inputProcessor.onRender();

        renderer.setView(app.getCamera());
        app.getMap().clear();
        renderer.render(app.getMap());

        // render animated object (fireballs, arrows, etc.)
        updateAnimated();

        Timer t = new Timer( );
        t.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                //updateTime();
                if(counter > 0) {
                counter--;
                System.out.println(counter);}
            }
        }, 1000,1000);

        showText();
        update(delta);
        entitiesStage.act(delta);
        entitiesStage.draw();
        AddSeprationLines();

    }

    private void updateTime() {
        counter--;
        Time.setText(counter);
    }


    private void showText() {
        //TextureAtlas atlas = new TextureAtlas("skin/skin/uiskin.atlas");
        //Skin skin = new Skin(atlas);
     /*   SpriteBatch batch;
        BitmapFont font = app.getFont();
        batch = new SpriteBatch();
        batch.begin();
        font.setColor(Color.BLACK);
        Player activePlayerName = gameModel.getActivePlayer();
        String name = activePlayerName.getName();
        font.draw(batch, "Turn:" + name, 0, 480);
        batch.end();*/
    }


    private void updateAnimated() {
        for (FireAnimated fireAnimated : fireballs) {
            fireAnimated.setView(app.getCamera());
            fireAnimated.render();
            if (fireAnimated.isDone()) {
                fireAnimated.dispose();
                fireballs.remove(fireAnimated);
            }
        }
    }

    /**
     * Fired on every resize event by libgdx
     * See GamePage interface
     * @param width - resized width value
     * @param height - resized height value
     */
    @Override
    public void resize(int width, int height) {
        app.getCamera().viewportHeight = height;
        app.getCamera().viewportWidth = width;

        renderer.setView(app.getCamera());
        renderer.render();

        entitiesStage.getViewport().setScreenBounds(
                (int) renderer.getViewBounds().x,
                (int) renderer.getViewBounds().y,
                width,
                height
                );
        uiStage.getViewport().update(width, height, true);
    }

    /**
     * See GamePage interface
     */
    @Override
    public void pause() {

    }

    /**
     * See GamePage interface
     */
    @Override
    public void resume() {

    }

    /**
     * See GamePage interface
     */
    @Override
    public void hide() {

    }

    /**
     * See GamePage interface
     * Flushes state and fires cleanup
     */
    @Override
    public void dispose() {
        entitiesStage.dispose();
        uiStage.dispose();
    }

    /**
     * Used inside render method
     * See GamePage interface
     */
    @Override
    public void update(float delta) {
        entitiesStage.act(delta);
        entitiesStage.draw();
        uiStage.act(delta);
        uiStage.draw();
    }
}
