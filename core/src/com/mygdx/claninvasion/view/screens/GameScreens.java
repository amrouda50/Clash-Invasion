package com.mygdx.claninvasion.view.screens;

import java.util.Stack;

/**
 * Implementation of GameScreenManager interface
 * @author andreicristea
 * @author omarashour
 * @version 0.1
 * @see GameScreensManager
 */
public class GameScreens implements GameScreensManager {
    Stack<GamePage> pages;

    /**
     * Initializes manager stack
     */
    public GameScreens() {
        pages = new Stack<>();
    }

    /**
     * See GameScreensManager interface
     * Pushes new state
     */
    @Override
    public void push(GamePage page) {
        pages.push(page);
    }

    /**
     * See GameScreensManager interface
     * Removes and returns last item in stack
     */
    @Override
    public GamePage pop() {
        return pages.pop();
    }


    /**
     * See GameScreensManager interface
     * Replaces last item with the one passed
     * in the arguments
     */
    @Override
    public GamePage replace(GamePage page) {
        GamePage last = pop();
        push(page);
        return last;
    }

    /**
     * See GameScreensManager interface
     * Gets last state page without removing it
     */
    @Override
    public GamePage get() {
        return pages.lastElement();
    }

    /**
     * See GameScreensManager interface
     * Checks if the stack is empty or not
     */
    @Override
    public boolean isEmpty() {
        return (long) pages.size() == 0;
    }
}
