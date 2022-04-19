package com.mygdx.claninvasion.model.gamestate;

import com.mygdx.claninvasion.model.GameModel;

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
}
