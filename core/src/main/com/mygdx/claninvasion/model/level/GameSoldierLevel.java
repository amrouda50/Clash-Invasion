package com.mygdx.claninvasion.model.level;

import org.javatuples.Septet;
import org.javatuples.Sextet;

/**
 * This class is responsible for determining the
 * soldier level in the game
 * @author andreicristea
 * @author omarashour
 * @author Dinari
 * TODO: Logic part is missing
 */
public class GameSoldierLevel extends GameTowerLevel {
    protected int movementSpeed;
    protected int attackIncrease;
    protected int visibleArea;

    public GameSoldierLevel(Septet<Integer, Integer, Integer, Integer, Integer, Integer, Integer> values, int hitsPointBonus,
                            int movementSpeed, int attackIncrease, int visibleArea) {

        super(values, hitsPointBonus);
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
