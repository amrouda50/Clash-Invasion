package com.mygdx.claninvasion.model.gamestate;

import com.mygdx.claninvasion.model.GameModel;

/**
 * This class is responsible for the common
 * actions in every state of the game
 * ending of the
 * battle state in the game
 * @author andreicristea
 * @author omarashour
 * @author Dinari
 */
public abstract class CommonGameState implements GameState  {
    protected GameModel game;

    /**
     * @param game - game model instance
     */
    public CommonGameState(GameModel game) {
        this.game = game;
    }

    @Override
    public void stopGame() {
        game.setGameEnded(true);
        game.setGameState(new EndGameState(game));
    }

    /**
     * Start a new game
     */
    @Override
    public void startGame() {
        game.setGameEnded(false);
    }

    /**
     * change the phase of game between attack and building
     */
    @Override
    public void changePhase() {
        game.setPhase(GamePhase.BUILDING);
    }

    /**
     * Change the turn of the game
     */
    @Override
    public void changeTurn() {
        game.changeActivePlayer();
    }

    /**
     * Changes the state of the game
     */
    @Override
    public void changeState() {
        game.setGameState(new BuildingState(game));
    }
}
