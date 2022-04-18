package com.mygdx.claninvasion.model.gamestate;
import com.mygdx.claninvasion.model.GameModel;

import java.util.Timer;
import java.util.TimerTask;

/**
 * This class is responsible for beginning and
 * ending of the
 * building state in the game. It comes before
 * the battle state.
 * @author andreicristea
 * @author omarashour
 * @author Dinari
 */
public class BuildingState extends CommonGameState implements Building {
    private float timeSeconds = 0f;
    private final float diff = 1f;
    private int counter = 60;
    private int totalTime = 0;

    private void setTimer() {
        Timer time = new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                totalTime++;
            }
        }, 1);
    }


    public BuildingState(GameModel game) {
        super(game);
    }

    /**
     * Changes to the next state
     */
    @Override
    public void changeState() {
        this.game.setGameState(new BattleState(game));
    }

    /**
     * Changes to the next phase
     */
    @Override
    public void changePhase() {
        if(this.game.getPhase() == GamePhase.BUILDING) {
            this.game.setPhase(GamePhase.ATTACK);
        }
    }

    @Override
    public void initState() {
        setTimer();
    }

    @Override
    public void updateTime(Runnable runnable) {
        if (counter > 0) {
            totalTime++;
            counter--;
            runnable.run();
        } else if (counter == 0 && totalTime <= 60) {
            counter = 30;
        } else if (totalTime > 60) {
            changePhase();
        }
    }

    @Override
    public void updateState(float delta, Runnable runnable) {
        timeSeconds += delta;
        if (timeSeconds > diff) {
            timeSeconds -= diff;
            updateTime(runnable);
        }
    }

    @Override
    public int getCounter() {
        return counter;
    }
}
