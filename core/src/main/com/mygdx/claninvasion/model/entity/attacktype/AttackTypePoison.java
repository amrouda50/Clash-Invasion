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
public class AttackTypePoison extends AttackTypeTower{

    public boolean poisonAttackSuccessful = true;
    private Timer timer = new Timer();

    /**
     * Overrides base attack implementation
     * @see AttackType
     */
    @Override
    public void attack(Tower tower) {
        timer.schedule(new TimerTask() {
            //ArrayList<Soldier> finalEnemies = tower.getNeighbors();
            int count = 0;
            @Override
            public void run() {
                count++;
                if( count < 3) {
                    /*for(Soldier enemy: finalEnemies) {
                        enemy.damage(3); //Arbitrary value can change later
                    }*/
                    tower.damage(4); //Damaaging itself
                } else {
                    stopTimer();
                }
            }
        }, 0, 1);
    }

    @Override
    public void attack(Soldier soldier) {
        timer.schedule(new TimerTask() {
            //ArrayList<Tower> finalEnemies = soldier.getNeighbors();
            int count = 0;
            @Override
            public void run() {
                count++;
                if( count < 3) {
                    /*for(Soldier enemy: finalEnemies) {
                        enemy.damage(3); //Arbitrary value can change later
                    }*/
                    soldier.damage(4); //Damaaging itself
                } else {
                    stopTimer();
                }
            }
        }, 0, 1);
    }

    private boolean stopTimer() {
        return poisonAttackSuccessful = true;
    }

    @Override
    public Attacks getName() {
        return Attacks.POISON;
    }
}
