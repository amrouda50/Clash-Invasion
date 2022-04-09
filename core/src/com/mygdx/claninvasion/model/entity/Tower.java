package com.mygdx.claninvasion.model.entity;

public class Tower extends ArtificialEntity implements Defensible {
    @Override
    public void damage(int amount) {
        super.damage(amount);
    }

    @Override
    public void heal() {
        super.heal();
    }


    @Override
    public void defend(ArtificialEntity ae) {

    }
}
