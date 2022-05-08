package com.mygdx.claninvasion.view.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.claninvasion.model.Globals;

public class ButtonWithIcon extends Table {
    protected Texture icon;
    protected Skin skin;
    protected Label.LabelStyle labelStyle;
    protected Label label;
    /**
     * @param skin         - resource for ui widgets
     * @param text         - button text
     * @see Skin
     */
    public ButtonWithIcon(Skin skin, String text, BitmapFont font, Texture iconTexture, String sub) {
        icon = iconTexture;
        this.skin = skin;

        labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        background(skin.getDrawable(Globals.ATLAS_BIG_BUTTON_PRIMARY));

        label = new Label(text, labelStyle);
        add(label);
        if (iconTexture != null && sub != null) {
            Table table = new Table();
            Image image = new Image(iconTexture);
            table.add(image);
            Label.LabelStyle labelStyle = new Label.LabelStyle();
            labelStyle.font = font;
            Label label = new Label(sub, labelStyle);
            label.setFontScale(0.8f);
            table.row();
            table.add(label);
            add(table).padLeft(15);
        }
    }

    /**
     * Used to add new Runnable listeners to the button
     * @param runnable - Lambda function or class with run method
     */
    public void addClickListener(final Runnable runnable) {
        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                runnable.run();
            }
        });
    }
}
