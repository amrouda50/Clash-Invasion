package com.mygdx.claninvasion.view.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import org.javatuples.Pair;



public class HealthBar extends ShapeRenderer {
    private Pair<Float , Float> coordinates;
    public void DrawRectangle(Matrix4 camera){
        begin(ShapeRenderer.ShapeType.Line);
        setColor(Color.BLACK);
        rect(coordinates.getValue0()-26 ,coordinates.getValue1()+40 , 15 , 5);
        end();
        begin(ShapeRenderer.ShapeType.Filled);
        setColor(Color.RED);
        rect(coordinates.getValue0()-26 + (float)0.5 , coordinates.getValue1()+40 + (float)0.5 , 14, 4);
        setProjectionMatrix(camera);
        end();
    }

    public  void rendering(Matrix4 camera){
            DrawRectangle(camera);
    }
    public void setCoordinates(Pair<Float , Float> coordinates){
        this.coordinates = coordinates;
    }
}
