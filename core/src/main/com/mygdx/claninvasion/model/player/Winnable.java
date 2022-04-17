package com.mygdx.claninvasion.model.player;

public interface Winnable {
   WinningState winningState();
   boolean hasWon();
   boolean hasLost();
}
