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
    private static final int COUNTER_INIT = 10;
    private static final int END_PHASE_TIME = 20;
    private float timeSeconds = 0f;
    private final float diff = 1f;
    private int counter = COUNTER_INIT;
    private int totalTime = 0;
    private Timer time;

    private void setTimer() {
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                totalTime++;
            }
        }, 1);
    }


    public BuildingState(GameModel game) {
        super(game);
        time = new Timer();
        changePhase();
    }

    /**
     * Changes to the next state
     */
    @Override
    public void changeState() {
        this.game.setGameState(new BattleState(game));
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
        }

        if (totalTime > END_PHASE_TIME) {
            time.purge();
            time.cancel();
            changeState();
        }

        if (counter == 0) {
            changeTurn();
            counter = COUNTER_INIT;
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

    @Override
    public void changePhase() {
        game.setPhase(GamePhase.BUILDING);
    }
}
