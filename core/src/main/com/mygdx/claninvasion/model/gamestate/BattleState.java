package com.mygdx.claninvasion.model.gamestate;

import com.mygdx.claninvasion.model.GameModel;
import com.mygdx.claninvasion.model.entity.Soldier;
import com.mygdx.claninvasion.model.entity.Tower;
import com.mygdx.claninvasion.model.player.Player;
import com.mygdx.claninvasion.model.player.WinningState;
import com.mygdx.claninvasion.view.applicationlistener.FireFromEntity;

import java.util.List;
import java.util.concurrent.*;

/**
 * This class is responsible for beginning and
 * ending of the
 * battle state in the game
 * @author andreicristea
 * @author omarashour
 * @author Dinari
 */
public class BattleState extends CommonGameState {
    public enum BattleStateChangeVariants {
        BUILDING,
        END_GAME,
    }
    private BattleStateChangeVariants battleStateChangeVariants = null;
    private FireFromEntity<Tower, Soldier> fireFromEntity = null;

    public BattleState(GameModel game) {
        super(game);
        changePhase();
        initializePlayerMove(game.getPlayerOne());
        initializePlayerMove(game.getPlayerTwo());
        fireTower(game.getPlayerOne());
        fireTower(game.getPlayerTwo());
    }

    private void initializePlayerMove(Player player) {
        Thread tempThread = null;
        int size = player.getTrainingSoldiers().size();
        for (int i = size - 1; i >= 0 ; i--) {
            int finalI = i;
            Thread finalTempThread = tempThread;
            tempThread = new Thread(() -> {
                player.addTrainedToMapSoldier();
                player.moveSoldier(finalI, finalTempThread);
                boolean iterate = player.getOpponent().isAlive() && player.isAlive();
                while (iterate) {
                    iterate = player.attackCastle(finalI);
                    if (!iterate) {
                        break;
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        if (tempThread != null) {
            tempThread.setDaemon(true);
            tempThread.start();
        }
    }

    @Override
    public void changeState() {
        if (BattleStateChangeVariants.BUILDING == battleStateChangeVariants) {
            this.game.setGameState(new BuildingState(game));
        } else if (BattleStateChangeVariants.END_GAME == battleStateChangeVariants) {
            this.game.setGameState(new EndGameState(game));
        }
    }

    ScheduledExecutorService scheduledExecutorService =
            Executors.newScheduledThreadPool(1);

    @Override
    public boolean isInteractive() {
        return false;
    }

    private void fireTower(Player player) {
        List<Tower> towers = player.getTowers();
        List<Soldier> soldiers = player.getOpponent().getSoldiers();

        new Thread(() -> {
            while (true) {
                for (Tower tower : towers) {
                    scheduledExecutorService
                            .schedule(() -> {
                                for (Soldier soldier : soldiers) {
                                    if (!tower.canFire(soldier)) {
                                        break;
                                    }
                                    if (tower.getTargetedSolider() == null || !tower.getTargetedSolider().isAlive()) {
                                        tower.setTargetedSolider(soldier);
                                    }
                                    if (fireFromEntity != null) {
                                        fireFromEntity.fire(tower, tower.getTargetedSolider());
                                    }
                                    tower.attack(tower.getTargetedSolider());

                                }
                            }, 20, TimeUnit.MILLISECONDS);
                    try {
                        scheduledExecutorService.awaitTermination(tower.getLevel().current().getReactionTime(), TimeUnit.MILLISECONDS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void setFireFromEntity(FireFromEntity<Tower, Soldier> fire) {
        fireFromEntity = fire;
    }

    @Override
    public void updateState(float delta, Runnable runnable) {
        Boolean noSoldiers1 = game.getPlayerOne().getTrainingSoldiers().size() == 0 && game.getPlayerOne().getSoldiers().size() == 0;
        Boolean noSoldiers2 = game.getPlayerTwo().getTrainingSoldiers().size() == 0 && game.getPlayerTwo().getSoldiers().size() == 0;

        game.getPlayerOne().removeDeadSoldiers();
        game.getPlayerTwo().removeDeadSoldiers();

        if (noSoldiers1 && noSoldiers2) {
            battleStateChangeVariants = BattleStateChangeVariants.BUILDING;
        }

        if (game.getPlayerOne().hasLost()) {
            game.getPlayerOne().setWinningState(WinningState.LOST);
            game.getPlayerTwo().setWinningState(WinningState.WON);
            battleStateChangeVariants = BattleStateChangeVariants.END_GAME;
        }

        if (game.getPlayerTwo().hasLost()) {
            game.getPlayerTwo().setWinningState(WinningState.WON);
            game.getPlayerOne().setWinningState(WinningState.LOST);
            battleStateChangeVariants = BattleStateChangeVariants.END_GAME;
        }

        changeState();
        runnable.run();
    }
}
