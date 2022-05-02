package com.mygdx.claninvasion.view.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
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
    private final BitmapFont font;
    private Skin skin;
    private final TextButton button;
    private String drawableName = "Asset 4";

    /**
     * @param skin - resource for ui widgets
     * @see Skin
     * @param text - button text
     */
    public GameButton(Skin skin, String text, BitmapFont font, String drawableName) {
        this.font = font;
        buttonStyle = new TextButton.TextButtonStyle();
        this.skin = skin;
        if (drawableName != null) {
            this.drawableName = drawableName;
        }
        buttonStyleInit();
        button = new TextButton(text, buttonStyle);
        button.getStyle().fontColor = Color.WHITE;
    }

    private void buttonStyleInit() {
        buttonStyle.up = skin.getDrawable(drawableName);
        buttonStyle.down = skin.getDrawable(drawableName);
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
