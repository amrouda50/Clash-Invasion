package com.mygdx.claninvasion.model.entity;

public class Soldier extends ArtificialEntity {


    public void attackCastle(Castle c) {

    }

    public void step() {

    }

    public void train() {

    }

    @Override
    public void heal() {
        super.heal();
    }

    @Override
    public void damage(int amount) {
        super.damage(amount);
    }


    public void healSoldier() {
        health++;
    }
}
