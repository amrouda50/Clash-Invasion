package com.mygdx.claninvasion.model.entity.attacktype;

/**
 * Fire attack type
 */
public class AttackTypeFire extends AttackTypeTower{

    /**
     * Overrides base attack implementation
     * @see AttackType
     */
    @Override
    public void attack() {
     setDecreaseHealth(70);
    }

    @Override
    public int getDecreaseHealth() {
        return super.getDecreaseHealth();
    }

    @Override
    public void setDecreaseHealth(int decreaseHealth) {
        super.setDecreaseHealth(decreaseHealth);
    }

    @Override
    public Attacks getName() {
        return super.getName();
    }
}
