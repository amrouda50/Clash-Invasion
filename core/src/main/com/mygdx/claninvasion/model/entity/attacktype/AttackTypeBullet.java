package com.mygdx.claninvasion.model.entity.attacktype;

/**
 * Bullet attack type
 * TODO: Logic part is missing
 */
public class AttackTypeBullet extends AttackTypeTower {
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
