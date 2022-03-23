package com.mygdx.claninvasion.model;

import com.mygdx.claninvasion.model.gamestate.GamePhase;
import com.mygdx.claninvasion.model.gamestate.GameState;
import com.mygdx.claninvasion.model.gamestate.StartGameState;
import com.mygdx.claninvasion.model.map.Map;

public class GameModel {
    private Player playerOne;
    private Player playerTwo;
    private Player activePlayer;
    private Map map;
    private GamePhase phase;
    private boolean gameEnded;
    private boolean gamePause;
    private GameState gameState;

    public GameModel() {
        this.phase = GamePhase.BUILDING;
        this.gameEnded = true;
        this.gamePause = false;
        playerOne = new Player();
        playerTwo = new Player();
        gameState = new StartGameState(this);
    }

    public void stopGame(){
        gameState.stopGame();
    }
    
    public void startGame(){
        gameState.startGame();
    }

    public void changePhase(){
        gameState.changePhase();
    }

    public void changeTurn(){
        gameState.changeTurn();
    }

    public Player getActivePlayer() {
        return activePlayer;
    }


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
}
