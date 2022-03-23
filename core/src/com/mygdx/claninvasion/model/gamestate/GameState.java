package com.mygdx.claninvasion.model.gamestate;

public interface GameState {
    void stopGame();
    void startGame();
    void changePhase();
    void changeTurn();
    void changeState();
}
