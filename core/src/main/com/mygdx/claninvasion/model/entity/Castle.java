package com.mygdx.claninvasion.model.entity;

/**
 * Castle entity
 * TODO: Logic part is missing
 */
public class Castle extends ArtificialEntity {
    /**
     * @see ArtificialEntity
     * @param amount - amount of injury
     */
    @Override
    public void damage(int amount) {
        this.health.set(this.health.get() - amount);
    }

    /**
     * Heal attacked soldier
     * @param soldier - soldier
     * TODO Implement logic
     */
    public void healSoldier(Soldier soldier) {}

    /**
     * Damage attacked soldier
     * @param soldier - soldier
     * TODO Implement logic
     */
    public void damageSoldier(Soldier soldier) {}
}
