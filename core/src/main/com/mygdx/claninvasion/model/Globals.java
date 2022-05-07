package com.mygdx.claninvasion.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Global application constants
 * @version 0.01
 */
public class Globals {
    /**
     * Initial virtual width of the screen
     */
    public static final int V_WIDTH = 480;

    /**
     * Initial virtual height of the screen
     */
    public static final int V_HEIGHT = 420;
    /**
     * Change to true if you want to debug main screen page
     */
    public static final boolean DEBUG = false;
//    public static final boolean DEBUG = false;

    public static final String ATLAS_WINDOW = "window";
    public static final String ATLAS_BUTTON_PRIMARY = "button-primary";
    public static final String ATLAS_BUTTON_SECONDARY = "button-secondary";
    public static final String ATLAS_BIG_BUTTON_PRIMARY = "big-button-primary";
    public static final String ATLAS_BIG_BUTTON_SECONDARY = "big-button-secondary";
    public static final String ATLAS_HEADER_BLUE = "header-blue";
    public static final String ATLAS_HEADER_RED = "header-red";
    public static final String ATLAS_WINDOW_SMALL = "window-small";
    public static final String ATLAS_WINDOW_BIG = "window-big";
    public static final String ATLAS_INPUT = "input";

    public static final String PATH_FONT = "skin/custom-skin/ORIOND(1).fnt";
    public static final String PATH_ATLAS = "skin/custom-skin/skin.atlas";

    public static final String COLOR_BLACK_BLUE = "#090919";
    public static final String COLOR_ROSE = "#E06367";

    public static TextureAtlas DEFAULT_ATLAS = new TextureAtlas(PATH_ATLAS);
    public static Texture APP_LOGO_TEXTURE = new Texture(Gdx.files.internal("splash/clashmenulogo.png"));
    public static Texture APP_BACKGROUND_TEXTURE = new Texture(Gdx.files.internal("splash/background.jpg"));
    public static Texture LOADING_TEXTURE = new Texture(Gdx.files.internal("LoadingscreenAnimation/Loading-Screen-icon0.png"));
    public static Texture HEART_TEXTURE = new Texture(Gdx.files.internal("raw/heart-stroked.png"));
    public static Texture GOLD_TEXTURE = new Texture(Gdx.files.internal("raw/gold.png"));
    public static Texture STAR_TEXTURE = new Texture(Gdx.files.internal("raw/star.png"));
    public static Texture BACK_TEXTURE = new Texture(Gdx.files.internal("raw/button-back.png"));
    public static Texture SOLDIER_TEXTURE = new Texture(Gdx.files.internal("Soliders/barbarian.png"));
    public static Texture GOLDMINE_TEXTURE = new Texture(Gdx.files.internal("Buidlings/goldmine.png"));
    public static Texture TOWER_TEXTURE = new Texture(Gdx.files.internal("BuildingBlocks/tower.png"));


    private Globals() {
    }
}
