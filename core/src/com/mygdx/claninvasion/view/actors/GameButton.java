package com.mygdx.claninvasion.view.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Composition of the button with project specific implementation
 * @author andreicristea
 * @author omarashour
 * @version 0.1
 * @see com.badlogic.gdx.scenes.scene2d.ui.Button
 */
public class GameButton {
    private final TextButton.TextButtonStyle buttonStyle;
    private BitmapFont font  = new BitmapFont(Gdx.files.internal("skin/skin/default.fnt"));
    private Skin skin;
    private final TextButton button;

    /**
     * @param skin - resource for ui widgets
     * @see Skin
     * @param text - button text
     */
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

    /**
     * Used to add new Runnable listeners to the button
     * @param runnable - Lambda function or class with run method
     */
    public void addClickListener(final Runnable runnable) {
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                runnable.run();
            }
        });
    }

    /**
     * Change font method
     * @param font - Bitmap font instance#
     * @see BitmapFont
     */
    public void setFont(BitmapFont font) {
        this.font = font;
    }

    /**
     * Change default skin
     * @param skin - new skin
     * @see Skin
     */
    public void setSkin(Skin skin) {
        this.skin = skin;
    }

    /**
     * Button getter
     * @return button
     */
    public TextButton getButton() {
        return button;
    }
}
