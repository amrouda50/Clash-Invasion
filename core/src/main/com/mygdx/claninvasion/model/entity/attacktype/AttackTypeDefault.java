package com.mygdx.claninvasion.model.entity.attacktype;

import com.mygdx.claninvasion.model.entity.Castle;
import com.mygdx.claninvasion.model.level.GameSoldierLevel;

public class AttackTypeDefault implements AttackType {
    @Override
    public boolean attack(Castle castle, float distance, GameSoldierLevel level) {
        if (distance < level.getVisibleArea()) {
            castle.damage(level.getAttackIncrease());
            return true;
        }
        return false;
    }

    @Override
    public int damageAmount() {
        return 0;
    }

    @Override
    public int maxDistance() {
        return 0;
    }

    @Override
    public int getCost() {
        return 0;
    }
}
