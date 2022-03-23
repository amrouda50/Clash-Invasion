package com.mygdx.claninvasion.view.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class LabeledTextField {
    private final Label label;
    private final Label.LabelStyle labelStyle;
    private final Skin skin;
    private final TextField textField;
    private final TextField.TextFieldStyle fieldStyle;
    private final BitmapFont font  = new BitmapFont(Gdx.files.internal("skin/skin/default.fnt"));

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

    public void applyToTheTable(Table table) {
        table.add(getLabel()).pad(2);
        table.row();
        table.add(getTextField());
    }

    public Label getLabel() {
        return label;
    }

    public TextField getTextField() {
        return textField;
    }
}
