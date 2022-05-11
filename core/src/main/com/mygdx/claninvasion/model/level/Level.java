package com.mygdx.claninvasion.model.level;

import org.javatuples.Septet;
import org.javatuples.Sextet;

/**
 * This class determines the time
 * and cost needed to change the level
 * soldiers and mining
 * @author andreicristea
 * @author omarashour
 * @author Dinari
 */
public class Level {
    protected int creationTime;
    protected int creationCost;
    protected int maxHealth;
    protected int minHealth;
    protected int reactionTime;
    protected int healHealthIncrease;
    protected int healGoalPoint;

    public Level(Septet<Integer, Integer, Integer, Integer, Integer, Integer, Integer> values) {
        creationTime = values.getValue0();
        creationCost = values.getValue1();
        maxHealth = values.getValue2();
        minHealth = values.getValue3();
        reactionTime = values.getValue4();
        healHealthIncrease = values.getValue5();
        healGoalPoint = values.getValue6();
    }

    public int getCreationCost() {
        return creationCost;
    }

    public int getCreationTime() {
        return creationTime;
    }

    public int getHealHealthIncrease() {
        return healHealthIncrease;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getMinHealth() {
        return minHealth;
    }

    public int getReactionTime() {
        return reactionTime;
    }

    public int getHealGoalPoint() {
        return healGoalPoint;
    }
}
