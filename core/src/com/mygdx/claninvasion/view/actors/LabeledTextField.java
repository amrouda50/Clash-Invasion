package com.mygdx.claninvasion.view.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

/**
 * Composition of the label and text-field libgdx api's
 * @author andreicristea
 * @author omarashour
 * @version 0.1
 * @see Label
 * @see TextField
 */
public class LabeledTextField {
    private final Label label;
    private final Label.LabelStyle labelStyle;
    private final Skin skin;
    private final TextField textField;
    private final TextField.TextFieldStyle fieldStyle;
    private final BitmapFont font  = new BitmapFont(Gdx.files.internal("skin/skin/default.fnt"));

    /**
     * @param skin - resource for ui widgets
     * @see Skin
     * @param placeholder - placeholder value
     */
    public LabeledTextField(Skin skin, String placeholder) {
        labelStyle = new Label.LabelStyle();
        fieldStyle = new TextField.TextFieldStyle();
        this.skin = skin;
        textFieldStyleInit();
        labelStyleInit();
        textField = new TextField("", fieldStyle);
        label = new Label(placeholder, labelStyle);
    }


    private void textFieldStyleInit() {
        fieldStyle.cursor = skin.getDrawable("cursor");
        fieldStyle.background = skin.getDrawable("textfield");
        fieldStyle.font = font;
        fieldStyle.fontColor = new Color(255, 255, 255, 1);
    }

    private void labelStyleInit() {
        labelStyle.font = font;
        labelStyle.fontColor = new Color(0, 0, 0, 1);
    }

    /**
     * Apply current fields to the Table
     * @param table - class which handles positioning of the elements of the screen
     * @see Table
     */
    public void applyToTheTable(Table table) {
        table.add(getLabel()).pad(2);
        table.row();
        table.add(getTextField());
    }

    /**
     * Label getter
     * @return - label
     */
    public Label getLabel() {
        return label;
    }

    /**
     * TextField getter
     * @return - text field
     */
    public TextField getTextField() {
        return textField;
    }
}
