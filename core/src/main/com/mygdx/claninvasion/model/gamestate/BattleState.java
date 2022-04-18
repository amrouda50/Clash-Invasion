package com.mygdx.claninvasion.model.gamestate;

import com.mygdx.claninvasion.model.GameModel;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingDeque;

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
        BlockingQueue<Thread> threadBlockingQueue = new LinkedBlockingDeque<>();
        CyclicBarrier barrier = new CyclicBarrier(2);
        //    time.schedule(new TimerTask() {
            //     @Override
                    //    public void run() {
        //  System.out.println("Current timer 1 " + totalTime);

        Thread addTrainedSoldiers = new Thread(() -> {
            while (true) {
                barrier.reset();
                game.getPlayerOne().addTrainedToMapSoldier();
                Thread th = new Thread(() -> {
                    game.getPlayerOne().moveSoldier(0, barrier);
                });

                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        });

        //            game.getPlayerOne().attackCastle(0);
        //    game.getPlayerOne().attackCastle();
        //      totalTime++;
                //    }
            //  }, delay, 1000);
//
//        time.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                game.getPlayerTwo().addTrainedToMapSoldier();
//                game.getPlayerTwo().moveSoldiers();
//                game.getPlayerTwo().attackCastle();
//            }
//        }, delay, 1000);
    }

    @Override
    public void changeState() {
        this.game.setGameState(new EndGameState(game));
    }
}
