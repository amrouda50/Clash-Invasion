package com.mygdx.claninvasion.model.entity.attacktype;

import com.mygdx.claninvasion.model.entity.Soldier;
import com.mygdx.claninvasion.model.entity.Tower;

import java.util.ArrayList;

/**
 * Spear attack type
 * Used by barbarians. Very accurate and most damagine to anything within 3 radius
 */
public class AttackTypeSpear extends AttackTypeSoldier{
    /**
     * Overrides base attack implementation
     * Not implementing movement as this can be implemented from Barbarian class
     * @see AttackType
     */
    @Override
    public void attack(Tower tower) {
        ArrayList<Soldier> enemies;
        enemies = tower.getNeighbors();
        /*for(Soldier enemy: enemies) {
            if(enemy.checkNeighbors(3)) { //Checks if the neighbor is within 3 radius distance
                enemy.damage(5); //Highest damage amount
            }
        }*/
    }

    @Override
    public void attack(Soldier soldier) {
        super.attack(soldier);
    }

    @Override
    public Attacks getName() {
        return Attacks.ARTILLERY;
    }
}
