package com.mygdx.claninvasion.view.screens;

import java.util.EmptyStackException;
import java.util.NoSuchElementException;
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
     * @throws EmptyStackException  if this stack is empty.
     */
    @Override
    public GamePage pop() throws EmptyStackException {
        return pages.pop();
    }


    /**
     * See GameScreensManager interface
     * Replaces last item with the one passed
     * in the arguments
     * @throws EmptyStackException  if this stack is empty.
     */
    @Override
    public GamePage replace(GamePage page) throws EmptyStackException {
        GamePage last = pop();
        push(page);
        return last;
    }

    /**
     * See GameScreensManager interface
     * Gets last state page without removing it
     * @throws  NoSuchElementException if the element does not exist
     */
    @Override
    public GamePage get() throws NoSuchElementException {
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
