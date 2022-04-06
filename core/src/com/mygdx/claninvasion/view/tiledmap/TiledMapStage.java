package com.mygdx.claninvasion.view.tiledmap;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

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

    @Override
    public void addActor(Actor actor) {
        super.addActor(actor);
        if (actor instanceof TiledMapActor) {
            createActorForLayer((TiledMapActor) actor);
        }
    }

    private void createActorForLayer(TiledMapActor actor) {
        actor.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                EventListener eventListener = new TiledMapClickListener(actor);
                actor.addListener(eventListener);
                super.clicked(event, x, y);
            }
        });
    }

    @Override
    public void act(float delta) {
//        screenActor.setSize(getHeight(), getWidth());
//        screenActor.setX(this.getViewport().getScreenX());
//        screenActor.setY(this.getViewport().getScreenY());
//        screenActor.act(delta);
        super.act(delta);
    }


}
