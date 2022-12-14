package com.mygdx.claninvasion.model.level;

import org.javatuples.Septet;
import org.javatuples.Sextet;

/**
 * This class is responsible for determining the
 * soldier level in the game
 * @author andreicristea
 * @author omarashour
 * @author Dinari
 */
public class GameSoldierLevel extends Level {
    protected int movementSpeed;
    protected int attackIncrease;
    protected int visibleArea;

    public GameSoldierLevel(Septet<Integer, Integer, Integer, Integer, Integer, Integer, Integer> values, int hitsPointBonus,
                            int movementSpeed, int attackIncrease, int visibleArea) {
        super(values);
        this.movementSpeed = movementSpeed;
        this.attackIncrease = attackIncrease;
        this.visibleArea = visibleArea;
    }

    public int getMovementSpeed() {
        return movementSpeed;
    }

    public int getAttackIncrease() {
        return attackIncrease;
    }

    public int getVisibleArea() {
        return visibleArea;
    }
}
