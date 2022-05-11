package com.mygdx.claninvasion.model.entity.attacktype;

import com.mygdx.claninvasion.model.entity.Soldier;
import com.mygdx.claninvasion.model.entity.Tower;

import java.util.ArrayList;

public class AttackTypeArcher extends AttackTypeTower {

    /*
    * Uses archer attack type to decrease the health of enemy
    * first finds the neighbors then decreases their health
    * @param Tower type
    */
    @Override
    public void attack() {
            setDecreaseHealth(85);
    }

    public int getDecreaseHealth() {
        return decreaseHealth;
    }

    public void setDecreaseHealth(int decreaseHealth) {
        this.decreaseHealth = decreaseHealth;
    }

    @Override
    public Attacks getName() {
        return Attacks.ARCHER;
    }
}
