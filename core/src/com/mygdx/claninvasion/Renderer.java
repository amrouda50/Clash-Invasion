package com.mygdx.claninvasion;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Renderer  {
    public static final int TILE_WIDTH = 40;
    public static final int TILE_HEIGHT = 40;
    private Texture grass;

    public Renderer() {
        grass = new Texture("tile-square.png");
    }

    public void drawGrass(SpriteBatch batch) {
        for (int row = 4; row >= 0; row -= 1) {
            for (int col = 4; col >= 0; col -= 1) {
                float x = (col - row) * (TILE_WIDTH / 2.f) ;
                float y = (col + row) * (TILE_HEIGHT / 2.f);

                batch.draw(grass, x, y);
            }
        }
    }
}
