package com.mygdx.claninvasion.model.gamestate;

public class StartGameState implements GameState {

    private boolean startGame;

    public boolean isStartGame() {
        return startGame;
    }

    public void setStartGame(boolean startGame) {
        this.startGame = startGame;
    }
}
