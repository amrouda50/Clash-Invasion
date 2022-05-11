package com.mygdx.claninvasion.view.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import org.javatuples.Pair;


public class HealthBar extends ShapeRenderer {
    private static final int OFFSET = 1;
    private Pair<Float , Float> coordinates;
    private float width;
    private float height;
    private float stamina;
    private Pair<Float, Float> positionOffset;
    private boolean isActive = true;
    private final Color healthColor;
    private Color strokeColor = Color.BLACK;
    private float initStamina;

    public HealthBar(Color color) {
        healthColor = color;
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

    public void setStamina(float percent) {
        if (this.stamina == 0) return;
        this.stamina = initStamina * (percent / 100f);
        if (this.stamina == 0) isActive = false;
    }

    private void drawRectangle(Matrix4 camera) {
        begin(ShapeRenderer.ShapeType.Line);
        setColor(strokeColor);
        rect(
                coordinates.getValue0() + positionOffset.getValue0(),
                coordinates.getValue1() + positionOffset.getValue1(),
                width ,
                height
        );
        end();
        begin(ShapeRenderer.ShapeType.Filled);
        setColor(healthColor);
        rect(
                coordinates.getValue0() + positionOffset.getValue0() + (float)0.5,
                coordinates.getValue1() + positionOffset.getValue1() + (float)0.5 ,
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

    public Pair<Float , Float> getCoordinates() {
        return coordinates;
    }

    public void setDimensions(Pair<Float, Float> sizes) {
        width = sizes.getValue0();
        height = sizes.getValue1();
        stamina = sizes.getValue0() - OFFSET;
        initStamina = stamina;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setStrokeColor(Color color) {
        strokeColor = color;
    }
}
