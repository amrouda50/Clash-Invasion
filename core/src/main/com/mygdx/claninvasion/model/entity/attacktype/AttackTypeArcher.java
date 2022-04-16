package com.mygdx.claninvasion.model.entity.attacktype;

public class AttackTypeArcher extends AttackTypeTower {
    @Override
    public void attack() {
        super.attack();
        return false;
    }
}
