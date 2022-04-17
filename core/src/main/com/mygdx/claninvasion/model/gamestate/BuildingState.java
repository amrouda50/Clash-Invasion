package com.mygdx.claninvasion.model.gamestate;

import com.mygdx.claninvasion.model.GameModel;

/**
 * This class is responsible for beginning and
 * ending of the
 * building state in the game. It comes before
 * the battle state.
 * @author andreicristea
 * @author omarashour
 * @author Dinari
 */
public class BuildingState extends CommonGameState{
    public BuildingState(GameModel game) {
        super(game);
    }

    /**
     * Changes to the next state
     */
    @Override
    public void changeState() {
        this.game.setGameState(new BattleState(game));
    }

    /**
     * Changes to the next phase
     */
    @Override
    public void changePhase() {
        if(this.game.getPhase() == GamePhase.BUILDING) {
            this.game.setPhase(GamePhase.ATTACK);
        }
    }
}
