package com.mygdx.claninvasion.model.level;

import java.util.Arrays;
import java.util.ArrayList;

import org.javatuples.Septet;

public class Levels {
    private static final Septet<Integer, Integer, Integer, Integer, Integer, Integer, Integer> level0 =
            new Septet<>(600, 200, 1000, 0, 600, 10, 30);
    private static final Septet<Integer, Integer, Integer, Integer, Integer, Integer, Integer> level1 =
            new Septet<>(500, 210, 1090, 0, 500, 10, 35);
    private static final Septet<Integer, Integer, Integer, Integer, Integer, Integer, Integer> level2 =
            new Septet<>(200, 280, 1090, 0, 460, 10, 35);

    private static final ArrayList<GameTowerLevel> towerLevels = new ArrayList<>(
            Arrays.asList(
                    // level 0
                    new GameTowerLevel(level0, 200),
                    // level 1
                    new GameTowerLevel(level1, 200),
                    // level 2
                    new GameTowerLevel(level2, 170)
            )
    );

    private static final ArrayList<Level> defaultLevels = new ArrayList<>(
            Arrays.asList(
                    new Level(level0),
                    new Level(level1),
                    new Level(level2)
            )
    );

    private static final ArrayList<GameMiningLevel> miningLevels = new ArrayList<>(
            Arrays.asList(
                    new GameMiningLevel(level0, 10),
                    new GameMiningLevel(level1, 14),
                    new GameMiningLevel(level2, 16)
            )
    );

    private static final ArrayList<GameSoldierLevel> soldierLevels = new ArrayList<>(
            Arrays.asList(
                    new GameSoldierLevel(level0, 30, 100, 1, 2),
                    new GameSoldierLevel(level1, 40, 170, 2, 2),
                    new GameSoldierLevel(level2, 44, 190, 3, 3)
            )
    );

    public static DefaultGameLevelIterator createLevelIterator() {
        return new DefaultGameLevelIterator(defaultLevels);
    }

    public static GameMiningLevelIterator createMiningLevelIterator() {
        return new GameMiningLevelIterator(miningLevels);
    }

    public static GameSoldierLevelIterator createSoldierLevelIterator() {
        return new GameSoldierLevelIterator(soldierLevels);
    }

    public static GameTowerLevelIterator createTowerLevelIterator() {
        return new GameTowerLevelIterator(towerLevels);
    }
}
