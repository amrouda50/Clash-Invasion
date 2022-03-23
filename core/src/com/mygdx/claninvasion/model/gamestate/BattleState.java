package com.mygdx.claninvasion.model.gamestate;

import com.mygdx.claninvasion.model.GameModel;

public class BattleState extends  CommonGameState {
    public BattleState(GameModel game) {
        super(game);
    }

    @Override
    public void changeState() {
        this.game.setGameState(new EndGameState(game));
    }
}
