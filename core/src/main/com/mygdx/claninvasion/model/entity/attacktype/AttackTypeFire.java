package com.mygdx.claninvasion.model.entity.attacktype;

import com.mygdx.claninvasion.model.entity.Soldier;
import com.mygdx.claninvasion.model.entity.Tower;

/**
 * Fire attack type
 * TODO: Logic part is missing
 */
public class AttackTypeFire extends AttackTypeSoldier{

    /**
     * Overrides base attack implementation
     * @see AttackType
     */
    @Override
    public void attack(Tower tower) {
        if(tower.checkNeighbors(3)) {

        }
    }

    @Override
    public void attack(Soldier soldier) {

    }
}
