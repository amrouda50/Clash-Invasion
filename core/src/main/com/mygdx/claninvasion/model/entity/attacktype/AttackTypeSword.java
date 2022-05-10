package com.mygdx.claninvasion.model.entity.attacktype;

import com.mygdx.claninvasion.model.entity.Soldier;
import com.mygdx.claninvasion.model.entity.Tower;

import java.util.ArrayList;

/**
 * Sword attack type
 * simple attack on for a soldier in radius of 1 square cell
 */
public class AttackTypeSword extends AttackTypeSoldier{
    @Override
    public int getVisibleArea() {
        return 1;
    }

    @Override
    public void attack(Soldier soldier) {
        //
    }

    @Override
    public Attacks getName() {
        return Attacks.SWORD;
    }
}
