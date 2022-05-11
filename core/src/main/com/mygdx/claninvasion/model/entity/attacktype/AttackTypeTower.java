package com.mygdx.claninvasion.model.entity.attacktype;

import com.mygdx.claninvasion.model.entity.Soldier;
import com.mygdx.claninvasion.model.entity.Tower;

/**
 * Tower attack type
 * TODO: Logic part is missing
 */
public class AttackTypeTower implements AttackType{

    protected int decreaseHealth;

    public int getDecreaseHealth() {
        return decreaseHealth;
    }

    public void setDecreaseHealth(int decreaseHealth) {
        this.decreaseHealth = decreaseHealth;
    }

    /**
     * Overrides base attack implementation
     * @see AttackType
     */
    @Override
    public void attack() {
    }

    @Override
    public Attacks getName() {
        return Attacks.TOWER;
    }


}
