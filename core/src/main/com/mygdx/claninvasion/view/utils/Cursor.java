package com.mygdx.claninvasion.view.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.mygdx.claninvasion.model.Globals;

public class Cursor {
    public static void changeToDisabled() {
        Pixmap pm = new Pixmap(Gdx.files.internal(Globals.DISABLE_CURSOR));
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, 0, 0));
    }

    public static void changeToDefault() {
        Gdx.graphics.setSystemCursor(
                com.badlogic.gdx.graphics.Cursor.SystemCursor.Arrow
        );
    }
}
