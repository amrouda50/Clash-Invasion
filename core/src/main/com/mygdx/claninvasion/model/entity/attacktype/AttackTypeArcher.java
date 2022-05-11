package com.mygdx.claninvasion.model.entity.attacktype;

import java.util.Random;

import com.mygdx.claninvasion.model.entity.Castle;
import com.mygdx.claninvasion.model.level.GameSoldierLevel;

public class AttackTypeArcher implements AttackType {
    @Override
    // sometimes archer does not hit the goal
    public void attack(Castle castle, float distance, GameSoldierLevel level) {
        Random random = new Random();
        boolean success = random.nextBoolean();
        if (success && distance < level.getVisibleArea() + maxDistance()) {
            castle.damage(level.getAttackIncrease() - damageAmount());
        }
    }

    @Override
    public int damageAmount() {
        return 1;
    }

    @Override
    public int maxDistance() {
        return 10;
    }

    @Override
    public int getCost() {
        return 200;
    }
}
