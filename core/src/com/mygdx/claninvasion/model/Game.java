package com.mygdx.claninvasion.model;

import com.mygdx.claninvasion.model.gamestate.*;
import com.mygdx.claninvasion.model.map.Map;

import static com.mygdx.claninvasion.model.gamestate.GameStateSymbols.BUILDING;
import static com.mygdx.claninvasion.model.gamestate.GameStateSymbols.ATTACK;

public class Game{
    
    private Player playerOne;
    private Player playerTwo;
    private Map map;

    private GameStateSymbols currentGameState;
    private StartGameState gameStart;
    private EndGameState gameEnded;

    public Game() {
        gameStart.setStartGame(true);
        gameEnded.setGameEnded(false);
        currentGameState = BUILDING;
    }

    public void stopGame(){
    
    };
    
    public void startGame(){
    
    };
    
    public void changePhase(){
    
    };
    
    public void changeTurn(){
    
    };
}
