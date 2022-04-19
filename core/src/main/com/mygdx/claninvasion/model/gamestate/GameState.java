package com.mygdx.claninvasion.model.gamestate;

/**
 * Game State machine
 * @version 0.01
 */
public interface GameState {
    /**
     * A stop game functionality, should disable the view game part
     */
    void stopGame();

    /**
     * Start a new game
     */
    void startGame();

    /**
     * There are phases of the game lifecycle
     * which can be changed
     */
    void changePhase();

    /**
     * Turns will be shifted between players
     */
    void changeTurn();

    /**
     * State of the game changer
     */
    void changeState();

    void initState();

    GameState getState();

    void updateState(float delta, Runnable runnable);
    void endGame(Runnable runnable);

    boolean isInteractive();
}
