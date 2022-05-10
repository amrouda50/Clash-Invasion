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

    private static final ArrayList<GameTowerLevel> towerLevels = new ArrayList<>(
            Arrays.asList(
                    // level 0
                    new GameTowerLevel(level0, 200),
                    // level 1
                    new GameTowerLevel(level1, 200),
                    // level 2
                    new GameTowerLevel(level2, 170),
                    new GameTowerLevel(level3, 190)
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

    public static DefaultGameLevelIterator createLevelIterator() {
        return new DefaultGameLevelIterator(defaultLevels);
    }

    public static GameMiningLevelIterator createMiningLevelIterator() {
        return new GameMiningLevelIterator(miningLevels);
    }

    public static GameSoldierLevelIterator createBarbarianLevelIterator() {
        return new GameSoldierLevelIterator(barbarianLevels);
    }

    public static GameSoldierLevelIterator createDragonLevelIterator() {
        return new GameSoldierLevelIterator(dragonLevels);
    }

    public static GameTowerLevelIterator createTowerLevelIterator() {
        return new GameTowerLevelIterator(towerLevels);
    }
}
