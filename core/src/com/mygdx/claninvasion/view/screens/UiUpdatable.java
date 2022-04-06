package com.mygdx.claninvasion.view.screens;

/**
 * Represents classes which are updated with some delta timeframe
 * @author andreicristea
 */
public interface UiUpdatable {
    /**
     * @param delta - time
     */
    default void update(float delta) {}
}
