package com.mygdx.claninvasion.model.entity;

public class Castle extends ArtificialEntity implements SoldierCreatable{

    public void heal(Soldier soldier) {
        soldier.healSoldier();
    }

    @Override
    public void damage(int amount) {
        Soldier s = new Soldier();
        s.damage(1);
    }

    public boolean trainSoldier(Soldier s) {
        return false;
    }
}
