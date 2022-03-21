package com.mygdx.claninvasion.model.entity;

import com.mygdx.claninvasion.model.level.LevelIterator;

public class ArtificialEntity extends Entity {

    private LevelIterator level;

    public LevelIterator getLevel() {
        return level;
    }

    public void setLevel(LevelIterator level) {
        this.level = level;
    }

    public void heal() {

    }

    public void damage(int amount) {

    }
}
