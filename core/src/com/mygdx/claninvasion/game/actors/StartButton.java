package com.mygdx.claninvasion.game.actors;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.util.Random;

public class StartButton extends TextButton {
    private int id;
    public StartButton(String text, Skin skin) {
        super(text, skin);
        this.id = new Random().nextInt();
    }

    public StartButton(String text, Skin skin, String style, int id) {
        super(text, skin, style);
        this.id = id;
    }
}
