package com.mygdx.claninvasion.model.gamestate;

import com.mygdx.claninvasion.model.GameModel;

import java.util.Timer;
import java.util.TimerTask;

/**
 * This class is responsible for beginning and
 * ending of the
 * battle state in the game
 * @author andreicristea
 * @author omarashour
 * @author Dinari
 */
public class BattleState extends CommonGameState {
    private int totalTime = 0;
    private final int delay = 1;
    private final Timer time;

    public BattleState(GameModel game) {
        super(game);
        changePhase();
        time = new Timer();
        setTimer();
    }

    private void setTimer() {
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Current timer 1 " + totalTime);
                game.getPlayerOne().addTrainedToMapSoldier();
                game.getPlayerOne().moveSoldiers();

                totalTime++;
            }
        }, delay, 1000);

        time.schedule(new TimerTask() {
            @Override
            public void run() {
                game.getPlayerTwo().addTrainedToMapSoldier();
                game.getPlayerTwo().moveSoldiers();
            }
        }, delay, 1000);
    }

    @Override
    public void changeState() {
        this.game.setGameState(new EndGameState(game));
    }
}
