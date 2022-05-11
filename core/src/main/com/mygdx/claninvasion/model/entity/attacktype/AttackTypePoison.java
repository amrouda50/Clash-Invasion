package com.mygdx.claninvasion.model.entity.attacktype;
import com.mygdx.claninvasion.model.entity.Soldier;
import com.mygdx.claninvasion.model.entity.Tower;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Poison attack type.
 * makes area around it poisonous. Has big timeout (will be active for 3 seconds as example ).
 * It poisons the host itself (everyone is in danger).
 */
public class AttackTypePoison extends AttackTypeSoldier{

    /**
     * Overrides base attack implementation
     * @see AttackType
     */
    @Override
    public void attack() {

    }

    @Override
    public Attacks getName() {
        return Attacks.POISON;
    }
}
