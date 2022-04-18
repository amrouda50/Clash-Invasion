package com.mygdx.claninvasion.model.entity.attacktype;

import com.mygdx.claninvasion.model.entity.Soldier;
import com.mygdx.claninvasion.model.entity.Tower;

/**
 * Tower attack type
 * TODO: Logic part is missing
 */
public class AttackTypeTower implements AttackType{
    /**
     * Overrides base attack implementation
     * @see AttackType
     */
    @Override
    public void attack(Tower tower) {
    }

    @Override
    public void attack(Soldier soldier) {
    }

    @Override
    public Attacks getName() {
        return Attacks.ARCHER;
    }

}
