package com.mygdx.claninvasion.model.entity.attacktype;

import com.mygdx.claninvasion.model.entity.Soldier;
import com.mygdx.claninvasion.model.entity.Tower;

import java.util.ArrayList;

/**
 * Spear attack type
 * Used by barbarians. Very accurate and most damagine to anything within 3 radius
 */
public class AttackTypeSpear extends AttackTypeSoldier{

    public AttackTypeSpear() {
    }

    @Override
    public Attacks getName() {
        return Attacks.SPEAR;
    }
}
