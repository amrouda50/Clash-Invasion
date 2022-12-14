package com.mygdx.claninvasion.model.player;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.claninvasion.model.GameModel;
import com.mygdx.claninvasion.model.entity.*;
import com.mygdx.claninvasion.model.gamestate.BattleState;
import com.mygdx.claninvasion.model.entity.attacktype.AttackType;
import com.mygdx.claninvasion.model.level.*;
import com.mygdx.claninvasion.model.map.WorldCell;
import com.mygdx.claninvasion.model.map.WorldMap;
import org.javatuples.Pair;

import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static com.mygdx.claninvasion.model.level.Levels.*;

/**
 * This class is responsible for handling
 * the workings of player
 * @author andreicristea
 * @author omarashour
 * @author Dinari
 */

public class Player implements Winnable {
    /** A synchronization lock for moving soldiers
     * Has two types of locks inside for supporting multiple thread
     * execution in safe mode:
     * 2. Use sync.readLock() when you know that this chunk of code will access
     * thread vulnerable data for reading (like then the graph accesses cells
     * (cells are mutated constantly) inside itself)
     * readLock() wont block other readLock() calls, it wont affect writeLock() parts as well
     * 1. Use sync.writeLock() for when you want to lock something which will is
     * rewritten concurrently in the application.
     * Like  WorldMap.mutate(...args) method changed map cells, which are used inside threads as well.
     * writeLock() will lock all other re-writes of the code together with readLock parts
     * @see WorldMap
     * @see Player
     * @see ReadWriteLock
     */
    static final ReadWriteLock sync = new ReentrantReadWriteLock();


    private static final int INITIAL_WEALTH = 1000;
    private static final int INCREASE_LEVEL_COST = 500;
    private static final int MAX_GOLDMINE = 10;
    /**
     * Opponent of the active player
     */
    private Player opponent;

    /**
     * All the towers of the player
     */
    private final List<Tower> towers;

    /**
     * Name of the player
     */
    private String name;

    /**
     * All the mines of the player
     */
    private final List<MiningFarm> miningFarms;

    /**
     * Status of player in the game
     */
    private WinningState winningState;

    /**
     * Amount of gold of the player
     */
    private final AtomicInteger wealth;

    /**
     * All the soldiers of the player
     */
    private final List<Soldier> soldiers;
    private final GameModel game;

    /**
     * Castle of the active player
     */
    private Castle castle;
    private final UUID id;
    private final BlockingQueue<Integer> coinProduceQueue;
    private final ExecutorService executorService;
    private final Color color;

    private final GameTowerLevelIterator gameRomanFortIterator;
    private final GameTowerLevelIterator gameHillTowerIterator;
    private final GameTowerLevelIterator gameStrategicTowerIterator;
    private final GameMiningLevelIterator miningLevelIterator;
    private final GameSoldierLevelIterator barbarianLevelIterator;
    private final GameSoldierLevelIterator dragonLevelIterator;

    public Player(GameModel game, Color c) {
        this.color = c;
        this.id = UUID.randomUUID();
        this.game = game;
        miningFarms = Collections.synchronizedList(new CopyOnWriteArrayList<>());
        soldiers = Collections.synchronizedList(new CopyOnWriteArrayList<>());
        towers = Collections.synchronizedList(new CopyOnWriteArrayList<>());
        wealth = new AtomicInteger(INITIAL_WEALTH);
        executorService = Executors.newFixedThreadPool(MAX_GOLDMINE + 1);
        coinProduceQueue = new LinkedBlockingDeque<>(MAX_GOLDMINE);
        executorService.execute(this::consumeGold);
        winningState = WinningState.UKNOWN;

        gameRomanFortIterator = createRomanFortTowerIterator();
        gameHillTowerIterator = createHillTowerIterator();
        gameStrategicTowerIterator = createStrategicTowerIterator();
        miningLevelIterator = createMiningLevelIterator();
        barbarianLevelIterator = createBarbarianLevelIterator();
        dragonLevelIterator = createDragonLevelIterator();
    }

    public void changeCastle(Castle castle) {
        this.castle = castle;
    }

    /*
     *  This method creates a new mining farm
     * @params cell
     * @returns MiningFarm
     * */
    public MiningFarm createNewMining(WorldCell cell) {
        if (!canCreateMining()) {
            System.out.println("Not enough money for this action");
            return null;
        }
        MiningFarm farm = (MiningFarm) game.getWorldMap().createMapEntity(EntitySymbol.MINING, cell, coinProduceQueue);
        executorService.execute(farm::startMining);
        miningFarms.add(farm);
        wealth.set(wealth.get() - miningLevelIterator.current().getCreationCost());
        return farm;
    }

    private void shutdownThreads() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(3500, TimeUnit.MILLISECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }

    /*
     *  This method utilize the mines for gold farming
     * */
    private void consumeGold() {
        while (true) {
            try {
                Integer gold = coinProduceQueue.take();
                wealth.addAndGet(gold);
                System.out.println("Updated wealth: " + wealth.get());
                // for thread debugging
//                 int active = Thread.activeCount();
                 if (miningFarms.size() == 0 && game.getState() instanceof BattleState) {
                    shutdownThreads();
                    break;
                 }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Castle getCastle() {
        return castle;
    }

    public void setOpponent(Player player) {
        opponent = player;
    }

    /**
     * This method resets all the resources for the player
     */
    public void reset() {
        if (miningFarms.stream().noneMatch(ArtificialEntity::isAlive)) {
            shutdownThreads();
        }
        miningFarms.clear();
        soldiers.clear();
        towers.clear();
        this.winningState = WinningState.UKNOWN;
    }

    /**
     * This method starts building towers for the active player
     */
    public Tower buildTower(WorldCell cell, EntitySymbol entitySymbol) {
        if (!canCreateTower(entitySymbol)) {
            System.out.println("Not enough money for this action");
            return null;
        }
        Tower tower = (Tower) game.getWorldMap().createMapEntity(entitySymbol, cell, null);
        tower.setLevel(this);

        towers.add(tower);
        wealth.set(wealth.get() - (tower.getLevel().current().getCreationCost()));
        return tower;
    }

    /*
     *  This method will remove the exhausted mining farms
     * */
    public void removeDeadMiningFarm() {
        for (MiningFarm farm : miningFarms) {
            if (!farm.isAlive()) {
                miningFarms.remove(farm);
                game.getWorldMap().removeMapEntity(farm);
            }
        }
    }

    /**
     * This will add more soldiers
     * to player's army
     */
    public CompletionStage<Void> trainSoldiers(EntitySymbol entitySymbol, AttackType attackType) {
        return castle
                .trainSoldiers(entitySymbol, (cost) -> {
                    int attackTypeCost = attackType == null ? 0 : attackType.getCost();
                    wealth.set(wealth.get() - (cost + attackTypeCost));
                    return false;
                },attackType)
                .thenRunAsync(() -> System.out.println("New soldiers were successfully added with attack type " + attackType));
    }

    /*
     *  This method adds the trained soldiers to the map
     * */
    public void addTrainedToMapSoldier() {
        Soldier soldier = castle.getSoldiers().pop();
        try {
            Thread.sleep(soldier.getReactionTime().intValue());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Soldier soldier1 = (Soldier) game.getWorldMap().createMapEntity(soldier.getSymbol(), soldier.getPosition(), null);
        if (soldier1 instanceof Barbarian) {
            soldier1.setLevel(barbarianLevelIterator);
        } else {
            soldier1.setLevel(dragonLevelIterator);
        }

        soldiers.add(soldier1);
    }

    public void addTrainedToMapSoldiers() {
        while (!castle.getSoldiers().empty()) {
            Soldier soldier = castle.getSoldiers().pop();
            soldiers.add((Soldier) game.getWorldMap().createMapEntity(soldier.getSymbol(), soldier.getPosition(), null));
            System.out.println("Soldier added " + soldiers.size());
        }
    }

    /**
     * This will add more soldiers
     * to player's army
     */
    public void trainSoldiers(EntitySymbol entitySymbol, AttackType attackType, Runnable after) {
        trainSoldiers(entitySymbol, attackType)
                .thenRunAsync(after);
    }

    /*
     *  This method moves the soldier during attack phase
     * @params index
     * @return Boolean
     * */
    public boolean attackCastle(int index) {
        if (soldiers.size() <= index) {
            return false;
        }
        Soldier soldier = soldiers.get(index);
        attackCastle(soldier);
        return true;
    }

    /**
     * This method is used to attack the castle of the opponent during attacking phase
     * */
    public void attackCastle(Soldier soldier) {
        soldier.attackCastle(opponent.castle);
    }

    public void attackCastle() {
        for (Soldier soldier : soldiers) {
            attackCastle(soldier);
        }
    }

    /*
     *  This method moves the soldier during attack phase
     * @params index, upcoming
     * */
    public void moveSoldier(int index, Thread upcoming) {
        Soldier soldier = getSoldiers().get(index);

        Pair<Integer, Integer> posSrc = new Pair<>(
                soldier.getPosition().getValue0(),
                soldier.getPosition().getValue1()
        );
        Pair<Integer, Integer> posDst = new Pair<>(
                opponent.getCastle().getPosition().getValue0() + index + 1 ,
                opponent.getCastle().getPosition().getValue1() + 1
        );
        int positionSrc = game.getWorldMap().transformMapPositionToIndex(posSrc);
        int positionDest = game.getWorldMap().transformMapPositionToIndex(posDst);
        int counter = 0;
        int movingSpeed = soldier.getLevel().current().getReactionTime();
        while (positionSrc != positionDest) {
            positionSrc = moveSoldier(soldier, positionSrc, positionDest, 0);
            if (counter == 2 && upcoming != null) {
                upcoming.setDaemon(true);
                upcoming.start();
            }
            counter++;
            try {
                Thread.sleep( movingSpeed );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     *  This method moves the soldier during attack phase
     * @params Soldier, positionSrc, positionDest, callTimes
     * @return Integer
     * */
    public int moveSoldier(Soldier soldier, int positionSrc, int positionDest, int callTimes) {
        // lock reading of thread vulnerable data
        sync.readLock().lock();
        game.getWorldMap().setGraph();
        sync.readLock().unlock();
        // unlock reading of thread vulnerable data

        // Shortest path algorithm
        List<Integer> paths = game.getWorldMap().getGraph()
                .getShortestDistance(
                        positionSrc,
                        positionDest,
                        game.getWorldMap().getGraphSize() * game.getWorldMap().getGraphSize()
                );

        if (paths == null) {
            // case when there is a temporary or permanently blocking occupier
            System.out.println("Paths is null. No moving can be made. Waiting...");
            if (callTimes > 100) {
                throw new RuntimeException("Paths is null. Exiting...");
            }
            try {
                Thread.sleep(1);
                moveSoldier(soldier, positionSrc, positionDest, ++callTimes);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return positionSrc;
        } else {
            sync.writeLock().lock();
            game.getWorldMap().mutate(paths.get(paths.size() - 1), paths.get(paths.size() - 2));
            sync.writeLock().unlock();

            Pair<Integer, Integer> newPosition =
                    game.getWorldMap().transformMapIndexToPosition(paths.get(paths.size() - 2));
            soldier.changePosition(newPosition);
            return paths.get(paths.size() - 2);
        }
    }

    public UUID getId() {
        return id;
    }

    public WorldMap getMap() {
        return game.getWorldMap();
    }

    public List<Soldier> getSoldiers() {
        return soldiers;
    }

    public List<Tower> getTowers() {
        return towers;
    }

    public List<MiningFarm> getMiningFarms() {
        return miningFarms;
    }

    /*
     *  This method removes the dead soldiers from the map
     * */
    public void removeDeadSoldiers() {
        List<Soldier> soldiers = getSoldiers();
        for(int i = 0; i < soldiers.size(); i++) {
            if (!soldiers.get(i).isAlive()) {
                getMap().removeMapEntity(soldiers.get(i));
                soldiers.remove(soldiers.get(i));
            }
        }
    }

    @Override
    public WinningState winningState() {
        return winningState;
    }

    public void setWinningState(WinningState winningState) {
        this.winningState = winningState;
    }

    @Override
    public boolean hasWon() {
        return isAlive();
    }

    @Override
    public boolean hasLost() {
        return !hasWon();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWealth() {
        return wealth.get();
    }

    public float getHealth() {
        return castle.getHealthPercentage();
    }

//    public boolean canCreateTower() {
//        return getWealth() >= gameTowerLevelIterator.current().getCreationCost();
//    }

    private boolean canCreateTower(LevelIterator<? extends Level> it) {
        return getWealth() >= it.current().getCreationCost();
    }

    /*
     *  This method creates a tower
     * @return Boolean
     * */
    public boolean canCreateTower(EntitySymbol entitySymbol) {
        switch (entitySymbol) {
            case HILL_TOWER -> {
                return canCreateHillTower();
            }
            case STRATEGIC_TOWER -> {
                return canCreateStrategicTower();
            }
            case ROMAN_FORT -> {
                return canCreateRomanFort();
            }
            default -> {
                return false;
            }
        }
    }

    /*
     *  This method checks is a hill tower can be created
     * @return Boolean
     * */
    public boolean canCreateHillTower() {
        return canCreateTower(gameHillTowerIterator);
    }

    /*
     *  This method checks is a strategic tower can be created
     * @return Boolean
     * */
    public boolean canCreateStrategicTower() {
        return canCreateTower(gameStrategicTowerIterator);
    }

    /*
     *  This method checks is a roman fort can be created
     * @return Boolean
     * */
    public boolean canCreateRomanFort() {
        return canCreateTower(gameRomanFortIterator);
    }

    /*
     *  This method checks is a dragon can be created
     * @return Boolean
     * */
    public boolean canCreateDragon() {
        return getWealth() >= Castle.AMOUNT_OF_SOLDIERS * dragonLevelIterator.current().getCreationCost();
    }

    /*
     *  This method checks is a barbarian can be created
     * @return Boolean
     * */
    public boolean canCreateBarbarian() {
        return getWealth() >= Castle.AMOUNT_OF_SOLDIERS * barbarianLevelIterator.current().getCreationCost();
    }

    /*
     *  This method checks is a mining farm can be created
     *  @return Boolean
     * */
    public boolean canCreateMining() {
        return getWealth() >= miningLevelIterator.current().getCreationCost() && miningFarms.size() < MAX_GOLDMINE;
    }

    /*
     *  This method checks if there is any next level or not
     *  @return Boolean
     * */
    public boolean canUpdateLevel() {
        return castle.getLevel().hasNext() && getWealth() >= INCREASE_LEVEL_COST;
    }

    public Stack<Soldier> getTrainingSoldiers() {
        return castle.getSoldiers();
    }

    public Player getOpponent() {
        return opponent;
    }

    /*
    *  This method checks is the castle is still healthy or not
    *  @return Boolean
    * */
    public boolean isAlive() {
        return castle.isAlive();
    }

//    public int getTowerCost() {
//        return gameTowerLevelIterator.current().getCreationCost();
//    }

    public int getHillTowerCost() {
        return gameHillTowerIterator.current().getCreationCost();
    }

    public int getRomanFortCost() {
        return gameRomanFortIterator.current().getCreationCost();
    }

    public int getStrategicTowerCost() {
        return gameStrategicTowerIterator.current().getCreationCost();
    }

    public int getBarbarianCost() {
        return barbarianLevelIterator.current().getCreationCost();
    }

    public int getDragonCost() {
        return dragonLevelIterator.current().getCreationCost();
    }

    public int getMiningCost() {
        return miningLevelIterator.current().getCreationCost();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        return ((Player) obj).id == this.id;
    }

    public Color getColor() {
        return color;
    }

    /*
    * This method checks if there is any next level and then iterates to the next
    * */
    public void levelUp() {
        if (castle.getLevel().hasNext()) {
            castle.changeLevel();
            wealth.set(wealth.get() - INCREASE_LEVEL_COST);
            if (gameStrategicTowerIterator.hasNext()) {
                gameStrategicTowerIterator.next();
            }

            if (gameHillTowerIterator.hasNext()) {
                gameHillTowerIterator.next();
            }

            if (gameRomanFortIterator.hasNext()) {
                gameRomanFortIterator.next();
            }

            if (miningLevelIterator.hasNext()) {
                miningLevelIterator.next();
            }

            if (barbarianLevelIterator.hasNext()) {
                barbarianLevelIterator.next();
            }

            if (dragonLevelIterator.hasNext()) {
                dragonLevelIterator.next();
            }

            for (MiningFarm miningFarm : miningFarms) {
                miningFarm.changeLevel();
            }

            for (Soldier soldier : soldiers) {
                soldier.changeLevel();
            }

            for (Tower tower : towers) {
                tower.changeLevel();
            }
        }
    }

    /*
     * This returns the iterator for the RomanFort Tower levels
     * @return GameTowerLevelIterator
     * */
    public GameTowerLevelIterator getGameRomanFortIterator() {
        return gameRomanFortIterator;
    }

    /*
     * This returns the iterator for the Hill Tower levels
     * @return GameTowerLevelIterator
     * */
    public GameTowerLevelIterator getGameHillTowerIterator() {
        return gameHillTowerIterator;
    }

    /*
    * This returns the iterator for the Strategic Tower levels
    * @return GameTowerLevelIterator
    * */
    public GameTowerLevelIterator getGameStrategicTowerIterator() {
        return gameStrategicTowerIterator;
    }
}

