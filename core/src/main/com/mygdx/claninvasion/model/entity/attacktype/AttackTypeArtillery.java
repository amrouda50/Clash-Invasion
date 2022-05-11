package com.mygdx.claninvasion.model.entity.attacktype;

/**
 * Bullet attack type
 */
public class AttackTypeArtillery extends AttackTypeTower {

    public AttackTypeArtillery() {
    }

    @Override
    public int getDecreaseHealth() {
        return super.getDecreaseHealth();
    }

    @Override
    public void setDecreaseHealth(int decreaseHealth) {
        super.setDecreaseHealth(decreaseHealth);
    }

    /**
     * Overrides base attack implementation
     * @see AttackType
     */

    @Override
    public void attack() {
        setDecreaseHealth(85);
    }

    @Override
    public Attacks getName() {
        return super.getName();
    }
}
