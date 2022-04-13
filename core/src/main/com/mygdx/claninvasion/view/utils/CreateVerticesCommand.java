package com.mygdx.claninvasion.view.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Quintet;

import static com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell.*;
import static com.badlogic.gdx.graphics.g2d.Batch.*;


public class CreateVerticesCommand {
    private static final Map<Integer, VerticesCommand> CREATORS;
    public static final int NUM_VERTICES = 20;

    private static void flipX(float[] vertices, boolean flip) {
        if (!flip) {
            return;
        }
        float temp = vertices[U1];
        vertices[U1] = vertices[U3];
        vertices[U3] = temp;
        temp = vertices[U2];
        vertices[U2] = vertices[U4];
        vertices[U4] = temp;
    }

    private static void flipY(float[] vertices, boolean flip) {
        if (!flip) {
            return;
        }
        float temp = vertices[V1];
        vertices[V1] = vertices[V3];
        vertices[V3] = temp;
        temp = vertices[V2];
        vertices[V2] = vertices[V4];
        vertices[V4] = temp;
    }

    private static void createVertices(
            float[] vertices,
            Quartet<Float, Float, Float, Float> coord,
            float color,
            Quintet<Integer, Integer, Integer, Integer, Integer> positions) {
        vertices[positions.getValue0()] = coord.getValue0();
        vertices[positions.getValue1()] = coord.getValue1();
        vertices[positions.getValue2()] = color;
        vertices[positions.getValue3()] = coord.getValue2();
        vertices[positions.getValue4()] = coord.getValue3();
    }

    private static void createVertices1(float[] vertices, Quartet<Float, Float, Float, Float> coord, float color) {
        createVertices(vertices, coord, color, new Quintet<>(X1, Y1, C1, U1, V1));
    }

    private static void createVertices2(float[] vertices, Quartet<Float, Float, Float, Float> coord, float color) {
        createVertices(vertices, coord, color, new Quintet<>(X2, Y2, C2, U2, V2));
    }

    private static void createVertices3(float[] vertices, Quartet<Float, Float, Float, Float> coord, float color) {
        createVertices(vertices, coord, color, new Quintet<>(X3, Y3, C3, U3, V3));
    }

    private static void createVertices4(float[] vertices, Quartet<Float, Float, Float, Float> coord, float color) {
        createVertices(vertices, coord, color, new Quintet<>(X4, Y4, C4, U4, V4));
    }


    private static float[] defaultVerticesCreator(
            Quartet<Float, Float, Float, Float> xy,
            Quartet<Float, Float, Float, Float> uv,
            float color
    ) {
        float[] vertices = new float[NUM_VERTICES];
        createVertices1(
                vertices,
                new Quartet<>(xy.getValue0(), xy.getValue1(), uv.getValue0(), uv.getValue1()),
                color
        );

        createVertices2(
                vertices,
                new Quartet<>(xy.getValue0(), xy.getValue3(), uv.getValue0(), uv.getValue3()),
                color
        );

        createVertices3(
                vertices,
                new Quartet<>(xy.getValue2(), xy.getValue3(), uv.getValue2(), uv.getValue3()),
                color
        );

        createVertices4(
                vertices,
                new Quartet<>(xy.getValue2(), xy.getValue1(), uv.getValue2(), uv.getValue1()),
                color
        );

        return vertices;
    }

    static {
        final Map<Integer, VerticesCommand> creators = new HashMap<>();

        creators.put(ROTATE_0, (flips, xy, uv, color) -> {
            float[] vertices = defaultVerticesCreator(xy, uv, color);
            flipX(vertices, flips.getValue0());
            flipY(vertices, flips.getValue1());
            return vertices;
        });

        creators.put(ROTATE_90, (flips, xy, uv, color) -> {
            float[] vertices = defaultVerticesCreator(xy, uv, color);
            flipX(vertices, flips.getValue0());
            flipY(vertices, flips.getValue1());
            float tempV = vertices[V1];
            vertices[V1] = vertices[V2];
            vertices[V2] = vertices[V3];
            vertices[V3] = vertices[V4];
            vertices[V4] = tempV;

            float tempU = vertices[U1];
            vertices[U1] = vertices[U2];
            vertices[U2] = vertices[U3];
            vertices[U3] = vertices[U4];
            vertices[U4] = tempU;

            return vertices;
        });

        creators.put(ROTATE_180, (flips, xy, uv, color) -> {
            float[] vertices = defaultVerticesCreator(xy, uv, color);
            flipX(vertices, flips.getValue0());
            flipY(vertices, flips.getValue1());

            float tempU = vertices[U1];
            vertices[U1] = vertices[U3];
            vertices[U3] = tempU;
            tempU = vertices[U2];
            vertices[U2] = vertices[U4];
            vertices[U4] = tempU;

            float tempV = vertices[V1];
            vertices[V1] = vertices[V3];
            vertices[V3] = tempV;
            tempV = vertices[V2];
            vertices[V2] = vertices[V4];
            vertices[V4] = tempV;

            return vertices;
        });

        creators.put(ROTATE_270, (flips, xy, uv, color) -> {
            float[] vertices = defaultVerticesCreator(xy, uv, color);
            flipX(vertices, flips.getValue0());
            flipY(vertices, flips.getValue1());

            float tempV = vertices[V1];
            vertices[V1] = vertices[V4];
            vertices[V4] = vertices[V3];
            vertices[V3] = vertices[V2];
            vertices[V2] = tempV;

            float tempU = vertices[U1];
            vertices[U1] = vertices[U4];
            vertices[U4] = vertices[U3];
            vertices[U3] = vertices[U2];
            vertices[U2] = tempU;

            return vertices;
        });

        CREATORS = Collections.unmodifiableMap(creators);
    }

    private final Pair<Boolean, Boolean> flips;
    private final Quartet<Float, Float, Float, Float> xy;
    private final Quartet<Float, Float, Float, Float> uv;
    private final float color;

    CreateVerticesCommand(
            Pair<Boolean, Boolean> flips,
            Quartet<Float, Float, Float, Float> xy,
            Quartet<Float, Float, Float, Float> uv,
            float color
    ) {
        this.flips = flips;
        this.xy = xy;
        this.uv = uv;
        this.color = color;
    }

    /** @return 20 elements float array of vertices
     * for every corner of isometric region
     * @see VerticesCommand
     */
    public float[] createVertices(int rotation) {
        VerticesCommand command = CREATORS.get(rotation);

        if (command == null) {
            throw new IllegalArgumentException("Invalid rotation type. Expected 0, 1, 2, 3, got " + rotation);
        }

        return command.createVertices(flips, xy, uv, color);
    }
}
