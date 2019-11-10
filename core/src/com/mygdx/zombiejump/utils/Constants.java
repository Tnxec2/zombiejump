package com.mygdx.zombiejump.utils;

/**
 * Constants
 */
public class Constants {
    public static final String GAME_TITLE = "Zombie Jump";
    public static final int GAME_WINDOW_WIDTH = 800;
    public static final int GAME_WINDOW_HEIGHT = 600;
    
    public static final String SKY_TEXTURE_FILENAME = "sky2.png";
    public static final String RUNNING_TEXTURE_FILENAME = "angryhero.png";

    public static final String[] ZOMBIE_TEXTURES_FILENAME = { "male-zombie.png", "female-zombie.png"};

    public static final int HERO_STARTX = 200;
    public static final int HERO_GRAVITY = 700;
    public static final int HERO_SPEED_JUMP = 400;
    public static final int HERO_SPEED_MAX = 700;
    public static final int HERO_SPEED_WALK = 50;

    public static final int SCROLL_SPEED_SKY = 30;
    public static final int SCROLL_SPEED_GROUND = 200;
    public static final int SCROLL_SPEED_ZOMBIE = 230;

    public static final float SHOT_RELOAD_INTERVAL = 4f;
    public static final float COIN_SPAWN_INTERVAL = 4f;
    
    /*
    * UI Section
    */
    //public static final String UI_TFT_FONT = "OpenSans.ttf";
    public static final String UI_TFT_FONT = "softsugarplain.ttf";
    public static final String UI_BUTTON_TEXTURE = "button.png";
}