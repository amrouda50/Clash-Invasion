package com.mygdx.claninvasion.model.entity.attacktype;

import com.mygdx.claninvasion.model.entity.Castle;
import com.mygdx.claninvasion.model.entity.Soldier;
import com.mygdx.claninvasion.model.entity.Tower;
import com.mygdx.claninvasion.model.level.GameSoldierLevel;

import java.util.ArrayList;

/**
 * Sword attack type
 * simple attack on for a soldier in radius of 1 square cell
 */
public class AttackTypeSword implements AttackType {
    @Override
    public int getCost() {
        return 150;
    }

    @Override
    public int damageAmount() {
        return 1;
    }

    @Override
    public int maxDistance() {
        return 0;
    }
}
