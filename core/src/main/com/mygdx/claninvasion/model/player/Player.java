package com.mygdx.claninvasion.model.player;

import com.mygdx.claninvasion.model.GameModel;
import com.mygdx.claninvasion.model.entity.*;
import com.mygdx.claninvasion.model.map.WorldCell;
import com.mygdx.claninvasion.model.map.WorldMap;
import org.javatuples.Pair;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class is responsible for handling
 * the workings of player
 * @author andreicristea
 * @author omarashour
 * @author Dinari
 * TODO: Logic part is missing
 */

public class Player implements Winnable {
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

    public Player(GameModel game) {
        this.id = UUID.randomUUID();
        this.game = game;
        miningFarms = Collections.synchronizedList(new CopyOnWriteArrayList<>());
        soldiers = Collections.synchronizedList(new CopyOnWriteArrayList<>());
        towers = Collections.synchronizedList(new CopyOnWriteArrayList<>());
        wealth = new AtomicInteger(0);
        executorService = Executors.newFixedThreadPool(MAX_GOLDMINE + 1);
        coinProduceQueue = new LinkedBlockingDeque<>(MAX_GOLDMINE);
        executorService.execute(this::consumeGold);
        winningState = WinningState.UKNOWN;
    }

    public void changeCastle(Castle castle) {
        this.castle = castle;
    }

    public MiningFarm createNewMining(WorldCell cell) {
        MiningFarm farm = (MiningFarm) game.getWorldMap().createMapEntity(EntitySymbol.MINING, cell, coinProduceQueue);
        executorService.execute(farm::startMining);
        miningFarms.add(farm);
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
         Tower tower = (Tower) game.getWorldMap().createMapEntity(EntitySymbol.TOWER, cell, null);
         towers.add(tower);
         return tower;
    }

    public void removeDead() {
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
    public void doMining() {}

    /**
     * This will add more soldiers
     * to player's army
     */
    public CompletionStage<Void> addSoldiers()  {
        return castle
                .trainSoldiers()
                .thenRun(this::addTrainedToMapSoldiers)
                .thenRunAsync(() -> System.out.println("New soldiers were successfully added"));
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
    public void addSoldiers(Runnable after)  {
        addSoldiers()
                .thenRunAsync(after);
    }

    public void moveSoldiers() {
        for (Soldier soldier : soldiers) {
            Pair<Integer, Integer> posSrc = new Pair<>(
                    soldier.getPosition().getValue0() ,
                    soldier.getPosition().getValue1()
                );
            Pair<Integer, Integer> posDst = new Pair<>(
               opponent.getCastle().getPosition().getValue0() +1 ,
               opponent.getCastle().getPosition().getValue1() +1
            );
            int positionSrc = game.getWorldMap().transformMapPositionToIndex(posSrc);
            int positionDest = game.getWorldMap().transformMapPositionToIndex(posDst);
            List<Integer> paths = game.getWorldMap().getGraph().GetShortestDistance( positionSrc, positionDest, 32*32);
                for (int i = paths.size() - 1; i > 0; i--) {
                    game.getWorldMap().mutate(paths.get(i), paths.get(i-1));
//                    System.out.println("delay " + getMap().getCell(paths.get(i)).getOccupier());
                    Pair<Integer, Integer> newPosition = game.getWorldMap().transformMapIndexToPosition(paths.get(i) - 1);
//                    System.out.println("new position" + newPosition);
                    soldier.changePosition(newPosition);
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

//                    System.out.println("done ");
                }
            }
    }

    public void attackAndMove(Pair<Integer, Integer> position) {
        for (Soldier soldier : soldiers) {
            soldier.changePosition(position);
            soldier.attackCastle(opponent.castle);
        }
    }

    public void defendAndAttack(Fireable fireable) {
        for (Tower tower : towers) {
            for (Soldier soldier : opponent.soldiers) {
                tower.attack(soldier, fireable);
            }
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

    @Override
    public WinningState winningState() {
        return winningState;
    }

    @Override
    public boolean hasWon() {
        return winningState == WinningState.LOST;
    }

    @Override
    public boolean hasLost() {
        return winningState == WinningState.WON;
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

}
