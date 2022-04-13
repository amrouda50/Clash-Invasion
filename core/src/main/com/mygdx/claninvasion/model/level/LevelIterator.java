package com.mygdx.claninvasion.model.level;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

/**
 * This class iterates through the levels of tower,
 * soldiers and mining
 * @author andreicristea
 * @author omarashour
 * @author Dinari
 * @version 0.1
 */
public class LevelIterator<Level> implements Iterator<Level> {
    private final ArrayList<Level> levels;
    private int currentLevelNumber = 0;

    public LevelIterator(ArrayList<Level> levels) {
        this.levels = levels;
        if (this.levels.size() == 0) {
            throw new IllegalArgumentException("Please provide a collection with at least one level in it.");
        }
    }

    @Override
    public boolean hasNext() {
        return currentLevelNumber >= levels.size();
    }

    public Level current() {
        return levels.get(currentLevelNumber);
    }

    @Override
    public Level next() {
        return levels.get(currentLevelNumber++);
    }

    @Override
    public void remove() {
        levels.remove(currentLevelNumber);
    }

    @Override
    public void forEachRemaining(Consumer<? super Level> action) {
        while (hasNext()) {
            action.accept(next());
        }
    }

    public void reset() {
        currentLevelNumber = 0;
    }
}
