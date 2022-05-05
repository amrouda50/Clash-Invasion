package com.mygdx.claninvasion.model.player;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.claninvasion.model.GameModel;
import com.mygdx.claninvasion.model.entity.*;
import com.mygdx.claninvasion.model.level.GameMiningLevelIterator;
import com.mygdx.claninvasion.model.level.GameSoldierLevelIterator;
import com.mygdx.claninvasion.model.level.GameTowerLevelIterator;
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
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;

import static com.mygdx.claninvasion.model.level.Levels.*;

/**
 * This class is responsible for handling
 * the workings of player
 * @author andreicristea
 * @author omarashour
 * @author Dinari
 * TODO: Logic part is missing
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


    public static final int INITIAL_WEALTH = 3000;
    public static final int MAX_GOLDMINE = 3;
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

    public static GameTowerLevelIterator gameTowerLevelIterator;
    public static GameMiningLevelIterator miningLevelIterator;
    public static GameSoldierLevelIterator soldierLevelIterator;

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


        gameTowerLevelIterator = createTowerLevelIterator();
        Tower.gameTowerLevel = gameTowerLevelIterator.next();


        miningLevelIterator = createMiningLevelIterator();
        MiningFarm.gameMiningLevel = miningLevelIterator.next();

        soldierLevelIterator = createSoldierLevelIterator();
        Soldier.gameSoldierLevel = soldierLevelIterator.next();
    }

    public void changeCastle(Castle castle) {
        this.castle = castle;
    }

    public MiningFarm createNewMining(WorldCell cell) {
        if (!canCreateMining()) {
            System.out.println("Not enough money for this action");
            return null;
        }
        MiningFarm farm = (MiningFarm) game.getWorldMap().createMapEntity(EntitySymbol.MINING, cell, coinProduceQueue);
        executorService.execute(farm::startMining);
        miningFarms.add(farm);
        wealth.set(wealth.get() - MiningFarm.COST);
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

    private void consumeGold() {
        while (true) {
            try {
                Integer gold = coinProduceQueue.take();
                wealth.addAndGet(gold);
                System.out.println("Updated wealth: " + wealth.get());
                // for thread debugging
                // int active = Thread.activeCount();
                // if (miningFarms.stream().noneMatch(ArtificialEntity::isAlive)) {
                //   shutdownThreads();
                //    break;
                // }
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
    public Tower buildTower(WorldCell cell) {
        if (!canCreateTower()) {
            System.out.println("Not enough money for this action");
            return null;
        }
        Tower tower = (Tower) game.getWorldMap().createMapEntity(EntitySymbol.TOWER, cell, null);
        int healHealthIncrease = Tower.gameTowerLevel.getHealHealthIncrease();
        castle.healHealthIncrease(healHealthIncrease);
        System.out.println("Castle's health has been healed and now it is " + castle.getHealth());

        towers.add(tower);
        wealth.set(wealth.get() - Tower.COST);
        return tower;
    }

    public void removeDeadMiningFarm() {
        for (MiningFarm farm : miningFarms) {
            if (!farm.isAlive()) {
                miningFarms.remove(farm);
                game.getWorldMap().removeMapEntity(farm);
            }
        }
    }

    /**
     * This method starts the mining for the active player
     */
    public void doMining() {
    }

    /**
     * This will add more soldiers
     * to player's army
     */
    public CompletionStage<Void> trainSoldiers(EntitySymbol entitySymbol) {
        return castle
                .trainSoldiers(entitySymbol, (cost) -> {
                    int healHealthIncrease = Soldier.gameSoldierLevel.getHealHealthIncrease();
                    castle.healHealthIncrease(healHealthIncrease);
                    wealth.set(wealth.get() - cost);
                    return false;
                })
                .thenRunAsync(() -> System.out.println("New soldiers were successfully added"));
    }

    public void addTrainedToMapSoldier() {
        Soldier soldier = castle.getSoldiers().pop();
        try {
            Thread.sleep(soldier.getReactionTime().intValue());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Soldier soldier1 = (Soldier) game.getWorldMap().createMapEntity(soldier.getSymbol(), soldier.getPosition(), null);
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
    public void trainSoldiers(EntitySymbol entitySymbol, Runnable after) {
        trainSoldiers(entitySymbol)
                .thenRunAsync(after);
    }

    public boolean attackCastle(int index) {
        if (soldiers.size() <= index) {
            return false;
        }
        Soldier soldier = soldiers.get(index);
        attackCastle(soldier);
        return true;
    }

    public void attackCastle(Soldier soldier) {
        soldier.attackCastle(opponent.castle);
    }

    public void attackCastle() {
        for (Soldier soldier : soldiers) {
            attackCastle(soldier);
        }
    }

    public void moveSoldier(int index, Thread upcoming) {
        Soldier soldier = getSoldiers().get(index);

        Pair<Integer, Integer> posSrc = new Pair<>(
                soldier.getPosition().getValue0(),
                soldier.getPosition().getValue1()
        );
        Pair<Integer, Integer> posDst = new Pair<>(
                opponent.getCastle().getPosition().getValue0() + index + 1,
                opponent.getCastle().getPosition().getValue1() + index + 1
        );
        int positionSrc = game.getWorldMap().transformMapPositionToIndex(posSrc);
        int positionDest = game.getWorldMap().transformMapPositionToIndex(posDst);
        int counter = 0;
        int movingSpeed = Soldier.gameSoldierLevel.getMovementSpeed();
        while (positionSrc != positionDest) {
            positionSrc = moveSoldier(soldier, positionSrc, positionDest, 0);
            if (counter == 2 && upcoming != null) {
                upcoming.start();
            }
            counter++;
            synchronized (sync) {
                try {
                    Thread.sleep(movingSpeed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

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

    public boolean canCreateTower() {
        return getWealth() >= Tower.COST;
    }

    public boolean canCreateDragon() {
        return getWealth() >= Castle.AMOUNT_OF_SOLDIERS * Dragon.COST;
    }

    public boolean canCreateBarbarian() {
        return getWealth() >= Castle.AMOUNT_OF_SOLDIERS * Barbarian.COST;
    }

    public boolean canCreateMining() {
        return getWealth() >= MiningFarm.COST;
    }

    public boolean canUpdateLevel() {
        return getWealth() >= 1000;
    }

    public Stack<Soldier> getTrainingSoldiers() {
        return castle.getSoldiers();
    }

    public Player getOpponent() {
        return opponent;
    }

    public boolean isAlive() {
        return castle.isAlive();
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

    public void levelUp() {
        try {
        if (gameTowerLevelIterator.hasNext() ) {
                Tower.gameTowerLevel = gameTowerLevelIterator.next();
                MiningFarm.gameMiningLevel = miningLevelIterator.next();
                Soldier.gameSoldierLevel = soldierLevelIterator.next();
                castle.nextLevel();
                Tower.changeLevel();
                MiningFarm.changeLevel();
                Soldier.changeLevel();

                //System.out.println("Value of creation cost " + MiningFarm.COST);
                /*System.out.println("Health of tower is " + Tower.maxHealth);
                System.out.println("After update the level of tower is " + gameTowerLevelIterator.getLevelName());*/
        } } catch (Exception e) {
                System.out.println("There are no new levels");
        }
    }
}

