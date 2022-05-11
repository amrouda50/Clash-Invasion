package com.mygdx.claninvasion.model.entity.attacktype;

import com.mygdx.claninvasion.model.entity.Soldier;
import com.mygdx.claninvasion.model.entity.Tower;

/**
 * Soldier attack type
 * TODO: Logic part is missing
 */
public class AttackTypeSoldier implements AttackType{
    @Override
    public void attack() {

    }

    /**
     * Overrides base attack implementation
     * @see AttackType
     */

    @Override
    public Attacks getName() {
        return null;
    }

}
