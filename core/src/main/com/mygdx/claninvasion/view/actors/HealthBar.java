package com.mygdx.claninvasion.view.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import org.javatuples.Pair;
import org.javatuples.Triplet;


public class HealthBar extends ShapeRenderer {
    private static final int OFFSET = 1;
    private Pair<Float , Float> coordinates;
    private float width;
    private float height;
    private float stamina;
    private Pair<Float, Float> positionOffset;
    private boolean isActive = true;

    public HealthBar(Pair<Float, Float> sizes) {
        setDimensions(sizes);
    }

    public HealthBar() {
        super();
    }

    public void setPositionOffset(Pair<Float, Float> positionOffset) {
        this.positionOffset = positionOffset;
    }

    public void addStamina(float percent) {
        stamina += stamina * percent;
    }

    public void substStamina(float percent) {
        if (stamina == 0) return;
        stamina -= stamina * percent;
        if (stamina == 0) isActive = false;
    }

    private void drawRectangle(Matrix4 camera) {
        begin(ShapeRenderer.ShapeType.Line);
        setColor(Color.BLACK);
        rect(
                coordinates.getValue0() + positionOffset.getValue0(),
                coordinates.getValue1()+ positionOffset.getValue1(),
                width ,
                height
        );
        end();
        begin(ShapeRenderer.ShapeType.Filled);
        setColor(Color.RED);
        rect(
                coordinates.getValue0() + positionOffset.getValue0() + (float)0.5,
                coordinates.getValue1()+ positionOffset.getValue1() + (float)0.5 ,
                stamina,
                height - OFFSET
        );
        setProjectionMatrix(camera);
        end();
    }

    public void rendering(Matrix4 camera) {
        drawRectangle(camera);
    }
    public void setCoordinates(Pair<Float , Float> coordinates){
        this.coordinates = coordinates;
    }

    public void setDimensions(Pair<Float, Float> sizes) {
        width = sizes.getValue0();
        height = sizes.getValue1();
        stamina = sizes.getValue0() - OFFSET;
    }

    public boolean isActive() {
        return isActive;
    }
}
