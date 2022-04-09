package com.mygdx.claninvasion.view.tiledmap;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Tiled map stage with actors
 */
public class TiledMapStage extends Stage {
    public Actor screenActor;

    public TiledMapStage() {
        screenActor = new Actor();
        screenActor.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
            }
        });
        screenActor.setSize(getHeight(), getWidth());
        screenActor.setX(this.getViewport().getScreenX());
        screenActor.setY(this.getViewport().getScreenY());
        super.addActor(screenActor);
    }
}
