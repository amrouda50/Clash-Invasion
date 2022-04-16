package com.mygdx.claninvasion.model.entity.attacktype;

/**
 * Specifies the type of entity/soldier attack
 */
public interface AttackType {
    /**
     * Should represent the way of attacking of certain entity
     * @return
     */
    boolean attack();
    void decreaseHealth();

}
