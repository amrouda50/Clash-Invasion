package com.mygdx.claninvasion.model.entity.attacktype;

/**
 * Fire attack type
 * TODO: Logic part is missing
 */
public class AttackTypeFire extends AttackTypeSoldier{
    /**
     * Overrides base attack implementation
     * @see AttackType
     * @return
     */
    @Override
    public boolean attack() {
        super.attack();
        return false;
    }
}
