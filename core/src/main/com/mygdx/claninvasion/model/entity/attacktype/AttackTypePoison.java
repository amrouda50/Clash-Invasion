package com.mygdx.claninvasion.model.entity.attacktype;

/**
 * Poison attack type
 * TODO: Logic part is missing
 */
public class AttackTypePoison extends AttackTypeTower{
    /**
     * Overrides base attack implementation
     * @see AttackType
     * @return
     */
    @Override
    public boolean attack() {
        return false;
    }
}
