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
        game.setPhase(GamePhase.BUILDING);
    }

    /**
     * change the phase of game between attack and building
     */
    @Override
    public void changePhase() {
        game.setPhase(GamePhase.ATTACK);
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

    @Override
    public void initState() {}

    @Override
    public GameState getState() {
        return this;
    }

    @Override
    public void updateState(float delta, Runnable runnable) {
        runnable.run();
    }

    @Override
    public boolean isInteractive() {
        return true;
    }

    @Override
    public void endGame(Runnable runnable) {}
}
