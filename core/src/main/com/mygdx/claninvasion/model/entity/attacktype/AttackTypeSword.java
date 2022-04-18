package com.mygdx.claninvasion.model.entity.attacktype;

import com.mygdx.claninvasion.model.entity.Soldier;
import com.mygdx.claninvasion.model.entity.Tower;

import java.util.ArrayList;

/**
 * Sword attack type
 * simple attack on for a soldier in radius of 1 square cell
 */
public class AttackTypeSword extends AttackTypeSoldier{


    @Override
    public void attack(Soldier soldier) {
        ArrayList<Tower> enemies;
        /*enemies = soldier.getNeighbors();
        for(Soldier enemy: enemies) {
           if(enemy.checkNeighbors(1)) { //Checks if the neighbor is within 1 radius distance
               enemy.damage(5); //Highest damage amount
           }
        }*/
    }

    @Override
    public Attacks getName() {
        return Attacks.SWORD;
    }
}
