package com.mygdx.claninvasion.model;

import com.mygdx.claninvasion.model.entity.*;
import com.mygdx.claninvasion.model.gamestate.GameState;

import java.util.UUID;

/**
 * This class is responsible for handling
 * the workings of player
 * @author andreicristea
 * @author omarashour
 * @author Dinari
 * TODO: Logic part is missing
 */

public class Player {
    /**
     * Opponent of the active player
     */
    private Player opponent;

    /**
     * All the towers of the player
     */
    private Tower[] towers;

    /**
     * Name of the player
     */
    private String name;

    /**
     * All the mines of the player
     */
    private Mineable[] miningFarms;

    /**
     * Status of player in the game
     */
    private GameState winningState;

    /**
     * Amount of gold of the player
     */
    private int wealth;

    /**
     * All the soldiers of the player
     */
    private Soldier[] soldiers;

    /**
     * Castle of the active player
     */
    private Castle castle;
    private UUID id;

    public Player() {
        this.id = UUID.randomUUID();
    }

    public Player(Soldier[] soldiers, Castle castle) {
        this.soldiers = soldiers;
        this.castle = castle;
        this.id = UUID.randomUUID();
    }

    public Castle getCastle() {
        return castle;
    }

    public void setCastle(Castle castle) {
        this.castle = castle;
    }

    /**
     * This method resets all the resources for the player
     */
    public void reset() {

    }

    /**
     * This method starts building towers for the active player
     */
    public void buildTower()
    {

    }

    /**
     * This method starts the mining for the active player
     */
    public void doMining() {

    }

    /**
     * This checks if the player has won
     */
    public void checkWinningState() {

    }

    /**
     * This checks if the player has lost
     */
    public void looseEntity() {

    }

    /**
     * This will add more soldiers
     * to player's army
     */
    public void addSoldiers() {

    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    public void setTowers(Tower[] towers) {
        this.towers = towers;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMiningFarms(Mineable[] miningFarms) {
        this.miningFarms = miningFarms;
    }

    public void setWinningState(GameState winningState) {
        this.winningState = winningState;
    }

    public UUID getId() {
        return id;
    }

    public void setWealth(int wealth) {
        this.wealth = wealth;
    }

    public void setSoldiers(Soldier[] soldiers) {
        this.soldiers = soldiers;
    }

    public Player getOpponent() {
        return opponent;
    }

    public Tower[] getTowers() {
        return towers;
    }

    public String getName() {
        return name;
    }

    public Mineable[] getMiningFarms() {
        return miningFarms;
    }

    public GameState getWinningState() {
        return winningState;
    }

    public int getWealth() {
        return wealth;
    }

    public Soldier[] getSoldiers() {
        return soldiers;
    }
}
