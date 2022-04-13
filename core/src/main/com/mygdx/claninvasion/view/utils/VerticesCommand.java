package com.mygdx.claninvasion.view.utils;

import org.javatuples.Pair;
import org.javatuples.Quartet;

public interface VerticesCommand {
    /** @return 20 elements float array of vertices
     * for every corner of isometric region
     */
    float[] createVertices(
            Pair<Boolean, Boolean> flips,
            Quartet<Float, Float, Float, Float> xy,
            Quartet<Float, Float, Float, Float> uv, float color
    );
}
