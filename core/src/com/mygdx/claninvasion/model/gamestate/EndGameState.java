package com.mygdx.claninvasion.model.gamestate;

public class EndGameState implements GameState{

    private boolean gameEnded;

    public boolean isGameEnded() {
        return gameEnded;
    }

    public void setGameEnded(boolean gameEnded) {
        this.gameEnded = gameEnded;
    }
}
