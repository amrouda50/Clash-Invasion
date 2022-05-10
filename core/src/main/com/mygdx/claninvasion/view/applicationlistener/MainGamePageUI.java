package com.mygdx.claninvasion.view.applicationlistener;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.claninvasion.ClanInvasion;
import com.mygdx.claninvasion.model.Globals;
import com.mygdx.claninvasion.model.entity.*;
import com.mygdx.claninvasion.model.gamestate.Building;
import com.mygdx.claninvasion.model.player.Player;
import com.mygdx.claninvasion.view.actors.GameButton;
import com.mygdx.claninvasion.view.actors.HealthBar;
import com.mygdx.claninvasion.view.actors.TableWithOptions;
import com.mygdx.claninvasion.view.screens.MainGamePage;
import com.mygdx.claninvasion.view.utils.InputClicker;
import com.mygdx.claninvasion.view.utils.PlayerUIData;
import org.javatuples.Pair;

import java.util.*;
import java.util.List;

public final class MainGamePageUI implements ApplicationListener {
    private Stage uiStage;
    private final TextureAtlas atlas = Globals.DEFAULT_ATLAS;
    private final Skin atlasSkin = new Skin(atlas);
    private final OrthographicCamera camera;
    private final ClanInvasion app;
    private final MainGamePage page;

    private Label timeLabel;
    private Label phaseLabel;
    private Label turnLabel;
    private Table tableTwo;
    private Table tableOne;

    private GameButton actionsButtonPlayer1;
    private GameButton actionsButtonPlayer2;
    private TableWithOptions tableWithOptions;
    private List<TableWithOptions.Option> options = new ArrayList<>();

    // icon textures
    private final Texture heartTexture = Globals.HEART_TEXTURE;
    private final Texture goldTexture = Globals.GOLD_TEXTURE;
    private final Texture starTexture = Globals.STAR_TEXTURE;

    // entities texture
    private final Texture soldierTexture = Globals.SOLDIER_TEXTURE;
    private final Texture goldmineTexture = Globals.GOLDMINE_TEXTURE;
    private final Texture towerTexture = Globals.TOWER_TEXTURE;


    public MainGamePageUI(ClanInvasion app, MainGamePage gamePage) {
        this.app = app;
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        page = gamePage;
    }


    private void initializeActions(Player player) {
        options = new ArrayList<>();
        PlayerActionMethods methods = new PlayerActionMethods(player);

        // train soldiers
        TableWithOptions.Option trainBarbarian = new TableWithOptions.Option(
                "Train Barbarian",
                player.getBarbarianCost(),
                atlasSkin,
                app.getFont(),
                0
        );
        trainBarbarian.setActionable((option) -> {
            methods.createBarbarian();
            this.tableWithOptions.setIsOpen(false);
        });
        TableWithOptions.Option trainDragon = new TableWithOptions.Option("Train Dragon", player.getDragonCost(), atlasSkin, app.getFont(), 1);
        trainDragon.setActionable((option) -> {
            methods.createDragon();
            this.tableWithOptions.setIsOpen(false);
        });
        TableWithOptions.Option chooseTrainSoldier = new TableWithOptions.Option(
                "Choose Train Soldier",
                0,
                atlasSkin,
                app.getFont(),
                new ArrayList<>(List.of(trainBarbarian, trainDragon)),
                0
        );

        chooseTrainSoldier.setActionable((option) -> tableWithOptions.goIntoChildOptions(option.getIndex()));
        options.add(chooseTrainSoldier);

        // create towers
        TableWithOptions.Option trainTowers = new TableWithOptions.Option("Train tower", player.getTowerCost(), atlasSkin, app.getFont(), 0);
        trainTowers.setActionable((option) -> {
            methods.createTower();
            this.tableWithOptions.setIsOpen(false);
        });

        TableWithOptions.Option chooseTowerType = new TableWithOptions.Option(
                "Choose Tower type",
                0,
                atlasSkin,
                app.getFont(),
                new ArrayList<>(List.of(trainTowers)),
                1
        );
        chooseTowerType.setActionable((option) -> tableWithOptions.goIntoChildOptions(option.getIndex()));
        options.add(chooseTowerType);

        TableWithOptions.Option buildGoldmine = new TableWithOptions.Option("Build Goldmine", player.getMiningCost(), atlasSkin, app.getFont(), 2);
        buildGoldmine.setActionable((option) -> {
            methods.createGoldmine();
            this.tableWithOptions.setIsOpen(false);
        });
        options.add(buildGoldmine);
        TableWithOptions.Option updateLevel = new TableWithOptions.Option("Upgrade Level", 0, atlasSkin, app.getFont(), 3);
        updateLevel.setActionable(option -> {
            player.levelUp();
            System.out.println("Level Successfully Updated");
            page.setChosenSymbol(null);
            this.tableWithOptions.setIsOpen(false);
        });
        options.add(updateLevel);

        if (tableWithOptions == null) {
            tableWithOptions = new TableWithOptions(70, 70 , options, atlasSkin);
        } else  {
            tableWithOptions.changeOptions(options);
            tableWithOptions.initBounds();
            tableWithOptions.removeBackButton();
        }

        tableWithOptions.setOnGoBack(() -> {
            page.setChosenSymbol(null);
            InputClicker.enabled = false;
        });
    }

    public void resetDropdown() {
        this.tableWithOptions.goBack();
        this.tableWithOptions.setIsOpen(false);
    }

    private void init() {
        uiStage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera));
        Label.LabelStyle labelStyle = new Label.LabelStyle();

        // select box style
        labelStyle.font = app.getFont();

        // labels
        timeLabel = new Label(getTimerText(), labelStyle);
        turnLabel = new Label(getPlayerTopBar() ,labelStyle);
        phaseLabel = new Label(getPlayerPhase(), labelStyle);
        // table one
        tableOne = new Table();
        tableTwo = new Table();
    }

    private String getPlayerTopBar() {
        if (app.getModel().getState() instanceof Building) {
            return app.getCurrentPlayer().getName();
        }
        return "";
    }

    private String getPlayerPhase() {
        return "" + app.getModel().getPhase();
    }

    private String getTimerText() {
        if (app.getModel().getState() instanceof Building) {
            return ((Building)app.getModel().getState()).getCounter() + " s";
        }

        return "";
    }

    private void initDropDown(SelectBox<String> box, String[] values) {
        box.setSize(5f , 5f);
        box.setItems(values);
        box.setTouchable(Touchable.enabled);
      //  box.isTouchFocusListener();
    }

    HealthBar player1HealthBar = new HealthBar(Color.valueOf("#BC1919"));
    HealthBar player2HealthBar = new HealthBar(Color.valueOf("#BC1919"));

    private void changeHealthBars() {
        player2HealthBar.setStamina(app.getModel().getPlayerTwo().getHealth());
        player1HealthBar.setStamina(app.getModel().getPlayerOne().getHealth());
    }

    private void initHealthBar(HealthBar playerHealthBar, Table table) {
        playerHealthBar.setDimensions(new Pair<>(70f, 12f));
        playerHealthBar.setCoordinates(new Pair<>(table.getX() + 60, table.getY() + 40));
        playerHealthBar.setPositionOffset(new Pair<>(0f, 0f));
        playerHealthBar.setStrokeColor(Color.WHITE);
        playerHealthBar.rendering(camera.combined);
    }

    private void headerTableOneInit(Table table) {
        // add heart
        Image heart = new Image(heartTexture);
        table.add(heart).padRight(0).padLeft(30);

        // add gold
        Image gold = new Image(goldTexture);
        table.add(gold).padLeft(95).padRight(20);

        // soldier
        Image soldier = new Image(soldierTexture);
        soldier.setScale(0.8f);
        table.add(soldier);

        // towers
        Image tower = new Image(towerTexture);
        tower.setScale(0.5f);
        table.add(tower).padTop(-25);

        // goldmines
        Image goldmine = new Image(goldmineTexture);
        goldmine.setScale(0.4f);
        table.add(goldmine).padTop(-25).padLeft(-10);
    }

    private void headerTableTwoInit(Table table, PlayerUIData playerData) {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = app.getFont();

        // add level
        Image star = new Image(starTexture);
        table.add(star).padRight(-10);
        Label level = new Label(playerData.level, labelStyle);
        level.setFontScale(0.8f);
        level.setColor(Color.BLACK);
        table.add(level).padLeft(-5).padTop(5);

        // add name of player

        Label name = new Label(playerData.name, labelStyle);
        table.add(name).padLeft(20).padRight(5);

        // add wealth
        Label wealth = new Label(playerData.wealth, labelStyle);
        table.add(wealth).padLeft(20).padRight(5);

        // add soldiers size
        Label soldiers = new Label(playerData.soldiers, labelStyle);
        table.add(soldiers).padLeft(25).padRight(10);

        // add towers
        Label towers = new Label(playerData.towers, labelStyle);
        table.add(towers).padLeft(10).padRight(10);

        // add mineables
        Label minings = new Label(playerData.minings, labelStyle);
        table.add(minings).padLeft(10).padRight(10);
    }

    private void setPlayerData(Table table, HealthBar playerHealthBar, PlayerUIData playerData) {

        Table table1 = new Table();
        Table table2 = new Table();
        initHealthBar(playerHealthBar, table);
        headerTableOneInit(table1);
        headerTableTwoInit(table2, playerData);

        table.add(table1);
        table.row();
        table.add(table2);

        uiStage.addActor(table);
    }


    private PlayerUIData getPlayerData(Player player) {
        return new PlayerUIData(
                // level
                player.getCastle().getLevel().getLevelName(),
                // name
                player.getName(),
                // money
                player.getWealth(),
                // towers
                player.getTowers().size(),
                // soldiers
                player.getSoldiers().size(),
                // goldmines
                player.getMiningFarms().size(),
                // health
                (int)player.getHealth()
        );
    }

    private void parsePlayerDataToView(Player player, Table table, HealthBar bar) {
        PlayerUIData player1Data = getPlayerData(player);
        setPlayerData(table, bar, player1Data);
    }

    private void updatePlayerData(Player player, Table table) {
        PlayerUIData player1Data = getPlayerData(player);
        int counter = 0;
        // careful with implementation!! Change if layout is changed
        Table table2 = (Table) table.getCells().get(1).getActor();
        for (int i = 0; i < table2.getCells().size; i++) {
            if (table2.getCells().get(i).getActor() instanceof Label) {
                Label label = ((Label) table2.getCells().get(i).getActor());
                label.setText(player1Data.get(counter));
                table2.addActorAt(i, label);
                counter++;
            }
        }
    }

    private void addBottomBar() {
        tableOne.background(atlasSkin.getDrawable(Globals.ATLAS_HEADER_BLUE));
        tableOne.setBounds(10, (Gdx.graphics.getHeight() / 1.2f), 300, 70);
        Player player1 = app.getModel().getPlayerOne();
        parsePlayerDataToView(player1, tableOne, player1HealthBar);

        tableTwo.background(atlasSkin.getDrawable(Globals.ATLAS_HEADER_RED));
        tableTwo.setBounds(320, (Gdx.graphics.getHeight() / 1.2f), 300, 70);
        Player player2 = app.getModel().getPlayerTwo();
        parsePlayerDataToView(player2, tableTwo, player2HealthBar);
    }

    private void addTopBar() {
        Table topTable = new Table(atlasSkin);
        Table labelTable = new Table();
        topTable.background(atlasSkin.getDrawable(Globals.ATLAS_WINDOW_SMALL));
        topTable.setBounds(10, 20 , 200, 180);

        Color labelColor = Color.valueOf(Globals.COLOR_ROSE);

        turnLabel.setColor(labelColor);
        turnLabel.setAlignment(Align.left);
        timeLabel.setColor(labelColor);
        timeLabel.setAlignment(Align.left);
        phaseLabel.setColor(labelColor);
        phaseLabel.setAlignment(Align.left);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = app.getFont();

        Label phaseText = new Label("Phase", labelStyle);
        phaseText.setColor(Color.BLACK);
        phaseText.setAlignment(Align.left);
        Label timeText = new Label("Time", labelStyle);
        timeText.setColor(Color.BLACK);
        timeText.setAlignment(Align.left);
        Label turnText = new Label("Turn", labelStyle);
        turnText.setColor(Color.BLACK);
        turnText.setAlignment(Align.left);

        labelTable.align(Align.left);
        labelTable.add(phaseText).padLeft(-25).padBottom(5).padTop(5);
        labelTable.add(phaseLabel).padLeft(15).padBottom(5).padTop(5);
        labelTable.row();
        labelTable.add(turnText).padLeft(-25).padBottom(5).padTop(5);
        labelTable.add(turnLabel).padLeft(10).padBottom(5).padTop(5);
        labelTable.row();
        labelTable.add(timeText).padLeft(-25).padBottom(5).padTop(5);
        labelTable.add(timeLabel).padLeft(-25).padBottom(5).padTop(5);

        actionsButtonPlayer1 = new GameButton(
                atlasSkin,
                 "Action " + app.getModel().getPlayerOne().getName(),
                app.getFont(),
                Globals.ATLAS_BUTTON_PRIMARY
        );

        actionsButtonPlayer2 = new GameButton(
                atlasSkin,
                "Action " + app.getModel().getPlayerTwo().getName(),
                app.getFont(),
                Globals.ATLAS_BUTTON_SECONDARY
        );

        actionsButtonPlayer1.addClickListener(() -> {
            if (!app.getModel().getPlayerOne().equals(app.getCurrentPlayer())) {
                return;
            }
            initializeActions(app.getModel().getPlayerOne());
            this.tableWithOptions.setIsOpen(!tableWithOptions.isVisible());
            uiStage.addActor(tableWithOptions);
            InputClicker.enabled = false;
        });


        actionsButtonPlayer2.addClickListener(() -> {
            if (!app.getModel().getPlayerTwo().equals(app.getCurrentPlayer())) {
                return;
            }

            initializeActions(app.getModel().getPlayerTwo());
            this.tableWithOptions.setIsOpen(!tableWithOptions.isVisible());
            uiStage.addActor(tableWithOptions);
            InputClicker.enabled = false;
        });


        Table tableButton = new Table();
        tableButton.add(actionsButtonPlayer1.getButton()).padLeft(6);
        tableButton.row();
        tableButton.add(actionsButtonPlayer2.getButton()).padLeft(6);

        topTable.add(labelTable).padTop(10).padBottom(0);
        topTable.row();
        topTable.add(tableButton).padTop(0);

        uiStage.addActor(topTable);
    }

    private void updateTopBar() {
        phaseLabel.setText(getPlayerPhase());
        turnLabel.setText(getPlayerTopBar());
    }

    class PlayerActionMethods {
        private final Player player;

        public PlayerActionMethods(Player player) {
            this.player = player;
        }

        private boolean disableClick() {
            return !player.equals(app.getCurrentPlayer());
        }

        public void createTower() {
            if (player.canCreateTower()) {
                page.setChosenSymbol(EntitySymbol.TOWER);
                InputClicker.enabled = true;
            } else {
                System.out.println("Not enough money for this action");
            }
        }

        public void createGoldmine() {
            if (disableClick()) return;
            if (player.canCreateMining()) {
                page.setChosenSymbol(EntitySymbol.MINING);
                InputClicker.enabled = true;
            } else {
                System.out.println("Not enough money for this action");
            }
        }

        public void createBarbarian() {
            if (disableClick()) return;
            if (player.canCreateBarbarian()) {
                InputClicker.enabled = false;
                player.trainSoldiers(EntitySymbol.BARBARIAN, () -> System.out.println("New barbarian trained"));
            } else {
                System.out.println("Not enough money for this action");
            }
        }

        public void createDragon() {
            if (disableClick()) return;
            if (player.canCreateDragon()) {
                InputClicker.enabled = false;
                player.trainSoldiers(EntitySymbol.DRAGON, () -> System.out.println("New dragon trained"));
            } else {
                System.out.println("Not enough money for this action");
            }
        }
    }

    private void addButtonListeners() {
        uiStage.setDebugUnderMouse(true);
        Gdx.input.setInputProcessor(uiStage);
    }

    @Override
    public void create() {
        init();
        addTopBar();
        addBottomBar();
        addButtonListeners();
        app.getModel().getState().initState();
    }

    @Override
    public void resize(int width, int height) {
        uiStage.getViewport().update(width, height, true);
    }

    @Override
    public void render() {
        app.getModel().updateState(Gdx.graphics.getDeltaTime(), () -> timeLabel.setText(getTimerText()));
        app.getModel().endGame(app::changeScreen);
        updatePlayerData(app.getModel().getPlayerOne(), tableOne);
        updatePlayerData(app.getModel().getPlayerTwo(), tableTwo);
        updateTopBar();
        changeHealthBars();
        uiStage.act(Gdx.graphics.getDeltaTime());
        uiStage.draw();

        player1HealthBar.rendering(camera.combined);
        player2HealthBar.rendering(camera.combined);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        uiStage.dispose();
    }
}
