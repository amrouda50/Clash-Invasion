package com.mygdx.claninvasion.model.gamestate;

import com.mygdx.claninvasion.model.GameModel;

public class EndGameState extends CommonGameState {
    public EndGameState(GameModel game) {
        super(game);
        this.game.setGameEnded(true);
    }

    @Override
    public void changeState() {
        this.game.setGameState(new StartGameState(game));
    }
}
