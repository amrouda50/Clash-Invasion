package com.mygdx.claninvasion.model.entity.attacktype;

import com.mygdx.claninvasion.model.entity.Soldier;
import com.mygdx.claninvasion.model.entity.Tower;

import java.util.ArrayList;

/**
 * Fire attack type
 * TODO: Logic part is missing
 */
public class AttackTypeFire extends AttackTypeSoldier{

    /**
     * Overrides base attack implementation
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
        ArrayList<Tower> enemies;
        enemies = soldier.getNeighbors();
         /*for(Soldier enemy: enemies) {
            if(enemy.checkNeighbors(3)) { //Checks if the neighbor is within 3 radius distance
                enemy.damage(5); //Highest damage amount
            }
        }*/
    }
}
