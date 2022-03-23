package com.mygdx.claninvasion.view.screens;

import java.util.Stack;

public class GameScreens implements GameScreensManager {
    Stack<GamePage> pages;

    public GameScreens() {
        pages = new Stack<>();
    }

    @Override
    public void push(GamePage page) {
        pages.push(page);
    }

    @Override
    public GamePage pop() {
        return pages.pop();
    }

    @Override
    public GamePage replace(GamePage page) {
        GamePage last = pop();
        push(page);
        return last;
    }

    @Override
    public GamePage get() {
        return pages.lastElement();
    }

    @Override
    public boolean isEmpty() {
        return (long) pages.size() == 0;
    }
}