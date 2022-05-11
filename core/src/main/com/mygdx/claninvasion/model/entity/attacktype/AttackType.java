package com.mygdx.claninvasion.model.entity.attacktype;

import com.mygdx.claninvasion.model.entity.Castle;
import com.mygdx.claninvasion.model.entity.Soldier;
import com.mygdx.claninvasion.model.entity.Tower;
import com.mygdx.claninvasion.model.level.GameSoldierLevel;
import com.mygdx.claninvasion.model.level.Level;

/**
 * Specifies the type of entity/soldier attack
 */
public interface AttackType {
    /**
     * Should represent the way of attacking of certain entity
     */
    default boolean attack(Castle castle, float distance, GameSoldierLevel level) {
        if (distance < level.getVisibleArea() + maxDistance()) {
            castle.damage(level.getAttackIncrease() - damageAmount());
            return true;
        }
        return false;
    }
    int damageAmount();
    int maxDistance();
    int getCost();
}
