package com.mygdx.claninvasion.game.screens;

public interface GameScreensManager {
    void push(GamePage page);
    GamePage pop();
    GamePage replace(GamePage page);
    GamePage get();
    boolean isEmpty();
}
