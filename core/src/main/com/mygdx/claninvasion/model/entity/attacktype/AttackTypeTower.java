package com.mygdx.claninvasion.model.entity.attacktype;

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
    public void attack() {
        return false;
    }

    public void checkNeighboringCells() {

    }
}
