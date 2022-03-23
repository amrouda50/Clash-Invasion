package com.mygdx.claninvasion.view.screens;

public interface GameScreensManager {
    void push(GamePage page);
    GamePage pop();
    GamePage replace(GamePage page);
    GamePage get();
    boolean isEmpty();
}
