package com.mygdx.claninvasion.model.entity.attacktype;

/**
 * Sword attack type
 *
 */
public class AttackTypeSword extends AttackTypeSoldier{
    boolean checkDistance = false;
    boolean attackSword = false;

    /**
     * Overrides base attack implementation
     * @see AttackType
     * @return
     */
    @Override
    public boolean attack() {
        checkDistance = (checkEnemyDistance() == 1);
        if (checkDistance) {
            return attackSword;
        }
        return false;
    }

    private int checkEnemyDistance() {

        return 0;
    }
}
