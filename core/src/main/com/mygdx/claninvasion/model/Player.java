package com.mygdx.claninvasion.model;

import com.mygdx.claninvasion.model.entity.*;
import com.mygdx.claninvasion.model.gamestate.GameState;

import java.util.ArrayList;
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
    private ArrayList<Tower> towers;

    /**
     * Name of the player
     */
    private String name;

    /**
     * All the mines of the player
     */
    private ArrayList<Mineable> miningFarms;

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
    private final ArrayList<Soldier> soldiers;

    /**
     * Castle of the active player
     */
    private final Castle castle;
    private final UUID id;

    public Player() {
        this.id = UUID.randomUUID();
        this.castle = new Castle(this);
        miningFarms = new ArrayList<>();
        soldiers = new ArrayList<>();
        towers = new ArrayList<>();
    }

    public Castle getCastle() {
        return castle;
    }

    public void setOpponent(Player player) {
        opponent = player;
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
    public void addSoldiers()  {
        castle
            .trainSoldiers()
            .thenRun(() -> soldiers.addAll(castle.getSoldiers()))
            .thenRunAsync(() -> System.out.println("New soldiers were successfully added"));

    }

    public UUID getId() {
        return id;
    }
}
