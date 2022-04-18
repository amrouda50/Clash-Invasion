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
    public void attack(Tower tower) {
        ArrayList<Soldier> enemies;
        enemies = tower.getNeighbors();
       /* for(Soldier enemy: enemies) {
            enemy.damage(3); //Arbitrary value can change later
        }*/
    }

    @Override
    public Attacks getName() {
        return Attacks.ARCHER;
    }
}
