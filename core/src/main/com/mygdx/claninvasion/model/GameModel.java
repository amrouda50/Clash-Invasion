package com.mygdx.claninvasion.model;

import com.mygdx.claninvasion.model.gamestate.GamePhase;
import com.mygdx.claninvasion.model.gamestate.GameState;
import com.mygdx.claninvasion.model.gamestate.StartGameState;
import com.mygdx.claninvasion.model.map.WorldMap;

/**
 * This class is responsible for handling
 * the working of the model package
 * @author andreicristea
 * @author omarashour
 * @author Dinari
 */

public class GameModel {
    /**
     * Players of the game
     */
    private Player playerOne;
    private Player playerTwo;

    /**
     * The player to whom current changes
     * will be made
     */
    private Player activePlayer;
    /**
     * This is for navigating through the map
     */
    private final WorldMap worldMap;

    /**
     * Goes through the phase of the game
     * i.e attack or build
     */
    private GamePhase phase;

    /**
     * In case the game ends
     */
    private boolean gameEnded;

    /**
     * To check if game is paused
     */
    private boolean gamePause;

    /**
     * This goes through the states of the game
     */
    private GameState gameState;

    public GameModel() {
        this.phase = GamePhase.BUILDING;
        this.gameEnded = true;
        this.gamePause = false;
        playerOne = new Player();
        playerTwo = new Player();

        playerOne.setOpponent(playerTwo);
        playerTwo.setOpponent(playerOne);

        activePlayer = playerOne;

        gameState = new StartGameState(this);
        worldMap = new WorldMap();

//        playerOne.addSoldiers();
    }


    /**
     * Responsible for stopping the game
     */
    public void stopGame() {
        gameState.stopGame();
    }

    /**
     * Responsible for starting the game
     */
    public void startGame() {
        gameState.startGame();
    }

    /**
     * changes the phase of the game
     */
    public void changePhase() {
        gameState.changePhase();
    }

    /**
     * Changes the turn of the player
     */
    public void changeTurn() {
        gameState.changeTurn();
    }


    public Player getActivePlayer() {
        return activePlayer;
    }

    /**
     * Changes the active player
     */
    public void changeActivePlayer() {
        if (activePlayer.getId().equals(playerOne.getId())) {
            activePlayer = playerTwo;
        } else {
            activePlayer = playerOne;
        }
    }

    public GamePhase getPhase() {
        return phase;
    }

    public void setPhase(GamePhase phase) {
        this.phase = phase;
    }

    public boolean isGameEnded() {
        return gameEnded;
    }

    public void setGameEnded(boolean gameEnded) {
        this.gameEnded = gameEnded;
    }

    public boolean isGamePause() {
        return gamePause;
    }

    public void setGamePause(boolean gamePause) {
        this.gamePause = gamePause;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * @return - map getter
     * @see WorldMap
     */
    public WorldMap getWorldMap() {
        return worldMap;
    }
}
