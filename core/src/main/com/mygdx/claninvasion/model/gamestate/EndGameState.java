package com.mygdx.claninvasion.model.gamestate;

import com.mygdx.claninvasion.model.GameModel;
import com.mygdx.claninvasion.model.player.Player;

/**
 * This class is responsible for beginning
 * of the end state of the game. This is used
 * when game ends.
 * @author andreicristea
 * @author omarashour
 * @author Dinari
 * @author Dinari
 */
public class EndGameState extends CommonGameState {

    /**
     * @param game - game model instance
     */
    public EndGameState(GameModel game) {
        super(game);
        this.game.setGameEnded(true);
        System.out.println("Game ended");
    }

    /**
     * Changes the state of the game
     */
    @Override
    public void changeState() {
        this.game.setGameState(new StartGameState(game));
    }

    @Override
    public void startGame() {
        super.startGame();
        changeState();
    }

    @Override
    public void endGame(Runnable runnable) {
        runnable.run();
        super.endGame(runnable);
        changeState();
    }

    public Player getWinnerPlayer() {
        if (game.getPlayerOne().hasWon()) {
            return game.getPlayerOne();
        }
        return game.getPlayerTwo();
    }
}
