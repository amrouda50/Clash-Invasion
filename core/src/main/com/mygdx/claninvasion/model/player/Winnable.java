package com.mygdx.claninvasion.model.player;

/*
* The player has won or lost is decided
* */
public interface Winnable {
   WinningState winningState();
   boolean hasWon();
   boolean hasLost();
}
