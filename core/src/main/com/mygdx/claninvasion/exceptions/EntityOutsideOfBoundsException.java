package com.mygdx.claninvasion.exceptions;

import org.javatuples.Pair;

public class EntityOutsideOfBoundsException extends IndexOutOfBoundsException  {
    public EntityOutsideOfBoundsException(Pair<Integer, Integer> position) {
        super("Position " + position.toString() + " is not of bounds for the current map. Entity should be between 0,0 and maxsize, maxsize");
    }
}
