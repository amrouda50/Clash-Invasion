package com.mygdx.claninvasion.model;

import com.mygdx.claninvasion.model.entity.*;
import com.mygdx.claninvasion.model.gamestate.GameState;

public class Player {
    private Player opponent;
    private Tower towers[];
    private String name;
    private Mineable[] miningFarms;
    private GameState winningState;
    private int wealth;
    private Soldier soldiers[];
    private Castle castle;

    public Player() {

    }

    public Player(Soldier[] soldiers, Castle castle) {
        this.soldiers = soldiers;
        this.castle = castle;
    }

    public Castle getCastle() {
        return castle;
    }

    public void setCastle(Castle castle) {
        this.castle = castle;
    }

    public void reset() {

    }

    public void buildTower()
    {

    }

    public void doMining() {

    }

    public void checkWinningState() {

    }

    public void looseEntity() {

    }

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
