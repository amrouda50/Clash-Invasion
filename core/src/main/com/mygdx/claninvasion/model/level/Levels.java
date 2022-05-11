package com.mygdx.claninvasion.model.level;

import java.util.Arrays;
import java.util.ArrayList;

import org.javatuples.Septet;

public class Levels {
    // creationTime, creationCost, maxHealth, minHealth, reactionTime, healHealthIncrease, healGoalPoint
    public static final Septet<Integer, Integer, Integer, Integer, Integer, Integer, Integer> level0 =
            new Septet<>(600, 200, 1000, 0, 600, 10, 30);
    public static final Septet<Integer, Integer, Integer, Integer, Integer, Integer, Integer> level1 =
            new Septet<>(500, 210, 1090, 0, 500, 10, 35);
    public static final Septet<Integer, Integer, Integer, Integer, Integer, Integer, Integer> level2 =
            new Septet<>(350, 280, 1090, 0, 460, 10, 35);
    public static final Septet<Integer, Integer, Integer, Integer, Integer, Integer, Integer> level3 =
            new Septet<>(320, 400, 1190, 0, 420, 20, 38);

    private static final ArrayList<GameTowerLevel> romanFortLevels = new ArrayList<>(
            Arrays.asList(
                    // level 0
                    new GameTowerLevel(level0, 200, 0, 0),
                    // level 1
                    new GameTowerLevel(level1, 200, 0, 1),
                    // level 2
                    new GameTowerLevel(level2, 170, 0, 5),
                    new GameTowerLevel(level3, 190, 1, 10)
            )
    );

    private static final ArrayList<GameTowerLevel> hillTowerLevels = new ArrayList<>(
            Arrays.asList(
                    // level 0
                    new GameTowerLevel(new Septet<>(1000, 500, 2000, 0, 3500, 20, 10), 200, 0, 0),
                    // level 1
                    new GameTowerLevel(new Septet<>(950, 550, 2000, 0, 3200, 20, 10), 200, 1, 1),
                    new GameTowerLevel(new Septet<>(920, 609, 2200, 0, 3100, 20, 10), 200, 2, 1),
                    new GameTowerLevel(new Septet<>(860, 664, 2300, 0, 2900, 20, 10), 200, 2, 7)
            )
    );

    private static final ArrayList<GameTowerLevel> strategicTowerLevels = new ArrayList<>(
            Arrays.asList(
                    // level 0
                    new GameTowerLevel(new Septet<>(700, 540, 1000, 0, 400, 20, 10), 200, 0, 0),
                    // level 1
                    new GameTowerLevel(new Septet<>(850, 540, 1000, 0, 400, 20, 10), 200, 0, 100),
                    new GameTowerLevel(new Septet<>(899, 490, 1000, 0, 350, 20, 10), 200, 0, 140),
                    new GameTowerLevel(new Septet<>(1000, 490, 1000, 0, 350, 20, 10), 200, 0, 240)
            )
    );

    private static final ArrayList<Level> defaultLevels = new ArrayList<>(
            Arrays.asList(
                    new Level(level0),
                    new Level(level1),
                    new Level(level2),
                    new Level(level3)
            )
    );

    private static final ArrayList<GameMiningLevel> miningLevels = new ArrayList<>(
            Arrays.asList(
                    new GameMiningLevel(level0, 10),
                    new GameMiningLevel(level1, 14),
                    new GameMiningLevel(level2, 16),
                    new GameMiningLevel(level3, 18)
            )
    );

    private static final ArrayList<GameSoldierLevel> barbarianLevels = new ArrayList<>(
            Arrays.asList(
                    new GameSoldierLevel(level0, 30, 600, 1, 2),
                    new GameSoldierLevel(level1, 40, 500, 2, 2),
                    new GameSoldierLevel(level2, 44, 460, 3, 3),
                    new GameSoldierLevel(level3, 46, 455, 4, 3)
            )
    );

    private static final ArrayList<GameSoldierLevel> dragonLevels = new ArrayList<>(
            Arrays.asList(
                    new GameSoldierLevel(new Septet<>(750, 350, 1100, 0, 450, 10, 30), 50, 300, 4, 3),
                    new GameSoldierLevel(new Septet<>(650, 450, 1200, 0, 450, 10, 30), 60, 250, 5, 3),
                    new GameSoldierLevel(new Septet<>(650, 550, 1250, 0, 450, 10, 30), 65, 246, 5, 5),
                    new GameSoldierLevel(new Septet<>(600, 580, 1280, 0, 470, 10, 30), 65, 246, 5, 5)
            )
    );

    /*
     * @return all the levels*/
    public static DefaultGameLevelIterator createLevelIterator() {
        return new DefaultGameLevelIterator(defaultLevels);
    }

    /*
     * @return all the levels of the minings*/
    public static GameMiningLevelIterator createMiningLevelIterator() {
        return new GameMiningLevelIterator(miningLevels);
    }

    /*
     * @return all the levels of the barbarians*/
    public static GameSoldierLevelIterator createBarbarianLevelIterator() {
        return new GameSoldierLevelIterator(barbarianLevels);
    }

    /*
     * @return all the levels of the dragon*/
    public static GameSoldierLevelIterator createDragonLevelIterator() {
        return new GameSoldierLevelIterator(dragonLevels);
    }

    public static GameTowerLevelIterator createStrategicTowerIterator() {
        return new GameTowerLevelIterator(strategicTowerLevels);
    }

    public static GameTowerLevelIterator createRomanFortTowerIterator() {
        return new GameTowerLevelIterator(romanFortLevels);
    }

    public static GameTowerLevelIterator createHillTowerIterator() {
        return new GameTowerLevelIterator(hillTowerLevels);
    }
}
