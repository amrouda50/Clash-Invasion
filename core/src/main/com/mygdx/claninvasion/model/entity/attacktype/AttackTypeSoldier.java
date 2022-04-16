package com.mygdx.claninvasion.model.entity.attacktype;

/**
 * Soldier attack type
 * TODO: Logic part is missing
 */
public class AttackTypeSoldier implements AttackType{
    /**
     * Overrides base attack implementation
     * @see AttackType
     * @return
     */
    @Override
    public boolean attack() {
        return false;
    }

    @Override
    public void decreaseHealth() {

    }
}
