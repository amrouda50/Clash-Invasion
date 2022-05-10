package com.mygdx.claninvasion.model.entity.attacktype;

import com.mygdx.claninvasion.model.entity.Soldier;
import com.mygdx.claninvasion.model.entity.Tower;

/**
 * Specifies the type of entity/soldier attack
 */
public interface AttackType {
    /**
     * Should represent the way of attacking of certain entity
     */
    void attack(Tower tower);
    void attack(Soldier soldier);
    Attacks getName();

    int getVisibleArea();
}
