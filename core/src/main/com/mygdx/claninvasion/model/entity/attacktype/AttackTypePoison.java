package com.mygdx.claninvasion.model.entity.attacktype;
import com.mygdx.claninvasion.model.entity.Tower;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Poison attack type
 * TODO: Logic part is missing
 */
public class AttackTypePoison extends AttackTypeTower{

    public boolean poisonAttackStart = false;
    public boolean poisonAttackSuccessful = true;
    private Timer timer = new Timer();

    /**
     * Overrides base attack implementation
     * @see AttackType
     */
    @Override
    public void attack(Tower tower) {
        poisonAttackStart = true;

        timer.schedule(new TimerTask() {
            int count = 0;
            @Override
            public void run() {
                count++;
                if( count < 3) {
                    //decreaseHealth();
                } else {
                    stopTimer();
                }
            }
        }, 0, 1000);
    }

    private boolean stopTimer() {
        return poisonAttackSuccessful = true;
    }

}
