package com.mygdx.claninvasion.model.gamestate;

public class BattleState implements GameState{

    private boolean battlePhase;

    public boolean isBattlePhase() {
        return battlePhase;
    }

    public void setBattlePhase(boolean battlePhase) {
        this.battlePhase = battlePhase;
    }
}
