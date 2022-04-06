package com.mygdx.claninvasion.view.screens;

/**
 * A Page manager of the application
 * Extends stack data structure
 * @author andreicristea
 */
public interface GameScreensManager {
    /**
     * Upload new pages inside push
     * @param page - New Page inside manager
     */
    void push(GamePage page);
    /**
     *  Use this method for most cases when there is a need to
     *  fire a last page into the game loop.
     *  Returns last game page (with deletion inside stack)
     *  @return GamePage class of last state
     */
    GamePage pop();
    /**
     * Replaces last page in the stack with one from parameter
     * @param page - replacing page
     */
    GamePage replace(GamePage page);
    /**
     *  Returns last game page (without deletion inside stack)
     *  @return GamePage class of last state
     */
    GamePage get();
    /**
     *  Gets if the stack is empty
     *  @return false - stack not empty, and visa versa
     */
    boolean isEmpty();
}
