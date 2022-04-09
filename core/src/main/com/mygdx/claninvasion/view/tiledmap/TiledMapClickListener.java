package com.mygdx.claninvasion.view.tiledmap;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class TiledMapClickListener extends ClickListener {
    private final TiledMapActor actor;

    public TiledMapClickListener(TiledMapActor actor) {
        this.actor = actor;
    }

    @Override
    public void clicked (InputEvent event, float x, float y)  {
//        System.out.printf("Clicked on the coordinates: %f %f", );
        System.out.printf("Actor coordinates %f %f", actor.getX(), actor.getY());
        actor.getCell().setTile(null);
        System.out.println(actor.getName()  + " has been clicked.");
    }
}