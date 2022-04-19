package com.mygdx.claninvasion.model.gamestate;

import com.mygdx.claninvasion.model.GameModel;
import com.mygdx.claninvasion.model.player.Player;
import com.mygdx.claninvasion.model.player.WinningState;

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

    public BattleState(GameModel game) {
        super(game);
        changePhase();
        initializePlayerMove(game.getPlayerOne());
        initializePlayerMove(game.getPlayerTwo());
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
            });
        }
        if (tempThread != null) {
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

    @Override
    public boolean isInteractive() {
        return false;
    }

    @Override
    public void updateState(float delta, Runnable runnable) {
        Boolean noSoldiers1 = game.getPlayerOne().getSoldiers().size() == 0;
        Boolean noSoldiers2 = game.getPlayerTwo().getSoldiers().size() == 0;
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
