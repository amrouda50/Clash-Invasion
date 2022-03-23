package com.mygdx.claninvasion.view.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GameButton {
    private final TextButton.TextButtonStyle buttonStyle;
    private BitmapFont font  = new BitmapFont(Gdx.files.internal("skin/skin/default.fnt"));
    private Skin skin;
    private final TextButton button;

    public GameButton(Skin skin, String text) {
        buttonStyle = new TextButton.TextButtonStyle();
        this.skin = skin;
        buttonStyleInit();
        button = new TextButton(text, buttonStyle);
    }

    private void buttonStyleInit() {
        buttonStyle.up = skin.getDrawable("default-window");
        buttonStyle.down = skin.getDrawable("default-window");
        buttonStyle.pressedOffsetX = 1;
        buttonStyle.pressedOffsetY = -1;
        buttonStyle.font = font;
    }

    public void addClickListener(final Runnable runnable) {
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                runnable.run();
            }
        });
    }

    public void setFont(BitmapFont font) {
        this.font = font;
    }

    public void setSkin(Skin skin) {
        this.skin = skin;
    }

    public TextButton getButton() {
        return button;
    }
}
