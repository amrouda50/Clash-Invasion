package com.mygdx.claninvasion.model.gamestate;

import com.mygdx.claninvasion.model.GameModel;

public class BuildingState extends CommonGameState{
    public BuildingState(GameModel game) {
        super(game);
    }

    @Override
    public void changeState() {
        this.game.setGameState(new BattleState(game));
    }

    @Override
    public void changePhase() {
        this.game.setPhase(GamePhase.ATTACK);
    }
}
