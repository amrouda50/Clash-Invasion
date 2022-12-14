package com.mygdx.claninvasion.model.gamestate;
import com.mygdx.claninvasion.model.GameModel;
import com.mygdx.claninvasion.model.Globals;
import com.mygdx.claninvasion.view.utils.InputClicker;

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
    private final int counterInit = Globals.TURN_TIME;
    private final int endPhaseTime = counterInit * 2;
    private float timeSeconds = 0f;
    private final float diff = 1f;
    private int counter = counterInit;
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
    public void changeTurn() {
        super.changeTurn();
        game.getChangeTurnCallback().run();
    }

    @Override
    public void updateTime(Runnable runnable) {
        if (counter > 0) {
            totalTime++;
            counter--;
            runnable.run();
        }

        if (totalTime > endPhaseTime) {
            time.purge();
            time.cancel();
            changeState();
        }

        if (counter == 0) {
            InputClicker.enabled = false;
            changeTurn();
            counter = counterInit;
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
