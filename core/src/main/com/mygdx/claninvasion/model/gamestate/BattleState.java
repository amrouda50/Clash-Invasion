package com.mygdx.claninvasion.model.gamestate;

import com.mygdx.claninvasion.model.GameModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

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

        //    time.schedule(new TimerTask() {
            //     @Override
                    //    public void run() {
        //  System.out.println("Current timer 1 " + totalTime);

        Thread tempThread = null;
        int size = game.getPlayerOne().getTrainingSoldiers().size();
        for (int i = size -1; i >= 0 ; i--){
            int finalI = i;
            Thread finalTempThread = tempThread;
            tempThread = new Thread(() -> {
                game.getPlayerOne().addTrainedToMapSoldier();
                game.getPlayerOne().moveSoldier(finalI, finalTempThread);
            });
        }
        tempThread.start();
//        Thread t3= new Thread(() ->
//        {
//            game.getPlayerOne().addTrainedToMapSoldier();
//            game.getPlayerOne().moveSoldier(3 , null);
//        });
//        Thread t2= new Thread(() ->
//        {
//            game.getPlayerOne().addTrainedToMapSoldier();
//            game.getPlayerOne().moveSoldier(2 , t3);
//        });
//        Thread t1= new Thread(() ->
//        {
//            game.getPlayerOne().addTrainedToMapSoldier();
//            game.getPlayerOne().moveSoldier(1 , t2);
//        });
//        Thread t = new Thread(() ->
//         {
//             game.getPlayerOne().addTrainedToMapSoldier();
//             game.getPlayerOne().moveSoldier(0 , t1);
//         });
//
//        t.start();





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
