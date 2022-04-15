package com.mygdx.claninvasion.model.entity;

import java.util.concurrent.CompletableFuture;



/**
 * Represents classes that have defence mechanism
 * @author Dinari
 * @version 0.01
 */
public interface Defensible {
    /**
     * @param artificialEntity - attacking entity
     */
    CompletableFuture<Boolean> attack(ArtificialEntity artificialEntity, Fireable fireable);
}
