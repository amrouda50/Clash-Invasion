package com.mygdx.claninvasion.model.gamestate;

import com.mygdx.claninvasion.model.GameModel;

public abstract class CommonGameState implements GameState  {
    protected GameModel game;

    public CommonGameState(GameModel game) {
        this.game = game;
    }

    @Override
    public void stopGame() {
        game.setGameEnded(true);
        game.setGameState(new EndGameState(game));
    }

    @Override
    public void startGame() {
        game.setGameEnded(false);
    }

    @Override
    public void changePhase() {
        game.setPhase(GamePhase.BUILDING);
    }

    @Override
    public void changeTurn() {
        game.changeActivePlayer();
    }

    @Override
    public void changeState() {
        game.setGameState(new BuildingState(game));
    }
}
