package com.mygdx.claninvasion.model.entity.attacktype;

/**
 * Spear attack type
 * TODO: Logic part is missing
 */
public class AttackTypeSpear extends AttackTypeSoldier{
    /**
     * Overrides base attack implementation
     * @see AttackType
     */
    @Override
    public void attack() {
        super.attack();
        return false;
    }
}
