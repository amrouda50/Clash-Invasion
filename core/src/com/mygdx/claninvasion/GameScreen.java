package com.mygdx.claninvasion;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen extends ScreenAdapter {
    public static final int WIDTH = 1920 / 3;
    public static final int HEIGHT = 1080 / 3;

    private SpriteBatch batch;
    private OrthographicCamera orthographicCamera;
    private Renderer renderer;


    public GameScreen(SpriteBatch batch) {
        this.batch = batch;
    }

    @Override
    public void show() {
        super.show();
        orthographicCamera = new OrthographicCamera(WIDTH, HEIGHT);
        orthographicCamera.position.set((float) (WIDTH / 2.0),  (float)(HEIGHT / 2.0), 10);


        renderer = new Renderer();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(orthographicCamera.combined);


        orthographicCamera.update();
        batch.begin();

        renderer.drawGrass(batch);

        batch.end();
    }

    @Override
    public void dispose() {

    }
}
