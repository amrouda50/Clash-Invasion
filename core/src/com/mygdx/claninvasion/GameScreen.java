package com.mygdx.claninvasion;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import org.w3c.dom.Text;
import com.badlogic.gdx.graphics.GL20;


public class GameScreen extends ScreenAdapter {
    public static final int WIDTH = 320*4;
    public static final int HEIGHT = 180*4;
    private  SpriteBatch batch;
    private Texture img;
    private OrthographicCamera camera;

    public GameScreen(SpriteBatch batch , Texture img){
        this.img = img;
        this.batch = batch;
    }

    @Override
    public void show(){
        //camera = new OrthographicCamera(WIDTH , HEIGHT);
        //camera.position.set( WIDTH/2 , HEIGHT/2 , 10);

    }
    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
     //   camera.update();
        batch.begin();
        batch.draw(img, 0,0 , 50, 50);
        batch.end();
    }
    @Override
    public void dispose(){
    }

}
