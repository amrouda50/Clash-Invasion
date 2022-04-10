package com.mygdx.claninvasion.model.adapters;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;
import org.javatuples.Pair;

import static com.badlogic.gdx.math.Matrix3.M00;
import static com.badlogic.gdx.math.Matrix3.M01;

/**
 * Special adapter which handles dirty tricks with
 * vector transformations
 * @author andreicristea
 */
public class IsometricToOrthogonalAdapt {
    private final Vector2 isometricPoint;

    public IsometricToOrthogonalAdapt(Vector2 point) {
        isometricPoint = point;
    }

    public IsometricToOrthogonalAdapt(Pair<Float, Float> point) {
        isometricPoint = new Vector2(point.getValue0(), point.getValue1());
    }

    /**
     * @return - orthogonal point which is derived from isometric one
     */
    public Vector2 getPoint() {
        // isometric matrix, needed for matrix multiplications
        // idea brought from https://www.youtube.com/watch?v=0fZXlxtMbC0
        Matrix3 isoMatrix = new Matrix3(new float[]{1f, 1f, 0f, -0.5f, 0.5f, 0, 0, 0, 1});

        Matrix3 pointMatrix = new Matrix3(new float[]{
                isometricPoint.x, 0, 0,
                isometricPoint.y, 1, 0,
                0, 0, 1
        });

        isoMatrix.inv();
        float[] result = pointMatrix.mul(isoMatrix).getValues();
        return new Vector2(result[M00], result[M01]);
    }
}
