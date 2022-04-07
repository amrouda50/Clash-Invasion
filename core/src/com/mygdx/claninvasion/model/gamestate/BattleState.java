package com.mygdx.claninvasion.model.gamestate;

import com.mygdx.claninvasion.model.GameModel;

/**
 * This class is responsible for beginning and
 * ending of the
 * battle state in the game
 * @author andreicristea
 * @author omarashour
 * @author Dinari
 */
public class BattleState extends  CommonGameState {
    public BattleState(GameModel game) {
        super(game);
    }

    @Override
    public void changeState() {
        this.game.setGameState(new EndGameState(game));
    }
}
