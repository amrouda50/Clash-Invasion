package com.mygdx.claninvasion.view.utils;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.claninvasion.model.map.WorldCell;
import com.mygdx.claninvasion.model.map.WorldMap;
import org.javatuples.Pair;

public interface Placer {
    void placeRender(WorldMap map);
    void place(TextureRegion region, Pair<Integer, Integer> position, WorldCell cell);
}
