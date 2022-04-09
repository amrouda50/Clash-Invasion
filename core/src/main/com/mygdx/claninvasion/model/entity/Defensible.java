package com.mygdx.claninvasion.model.entity;

/**
 * Represents classes that have defence mechanism
 * @author Dinari
 * @version 0.01
 */
public interface Defensible {
    /**
     * @param artificialEntity - attacking entity
     */
    void defend(ArtificialEntity artificialEntity);
}
