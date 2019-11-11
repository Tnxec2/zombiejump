package com.mygdx.zombiejump.utils;

import com.badlogic.gdx.graphics.Color;

/**
 * Constants
 */
public class Constants {
    public static final String GAME_TITLE = "Zombie Jump";
    public static final int GAME_WINDOW_WIDTH = 800;
    public static final int GAME_WINDOW_HEIGHT = 480;
    
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
    
    public static final String PREFS_NAME = "ZombieJumpPrefs";
    public static final String PREFS_NAME_COINS_HIGHSCORE = "coinsHighScore";
    
    /*
    *   Texturen
    */
    public static final String TEXTURE_MENU_SCREEN_BACKGROUND = "wall.png";
    public static final String TEXTURE_MENU_SCREEN_TITLE = "title.png";
    
    public static final String TEXTURE_GAMEOVER_SCREEN_BACKGROUND = "wall.png";
    public static final String TEXTURE_GAMEOVER_SCREEN_TITLE = "title.png";
    
    public static final String TEXTURE_ICON_ZOMBIE = "zombie-icon.png";
    public static final String TEXTURE_ICON_HEALTH = "heart-icon.png";
    public static final String TEXTURE_ICON_COIN = "coin-icon.png";
    
    public static final String TEXTURE_COIN = "coin.png";
    public static final String TEXTURE_SHOT = "shot.png";

    public static final String TEXTURE_GAME_OVER = "gameover.png";

    public static final String TEXTURE_SKY = "sky2.png";
    public static final String TEXTURE_HERO_RUNNING = "angryhero.png";

    public static final String[] ZOMBIE_TEXTURES_FILENAME = { "male-zombie.png", "female-zombie.png"};

    
    /*
    * UI Section
    */
    public static final String UI_TFT_FONT = "SpriteGraffiti-Regular.ttf";
    //public static final String UI_TFT_FONT = "OpenSans.ttf";
    //public static final String UI_TFT_FONT = "softsugarplain.ttf";
    public static final String UI_BUTTON_TEXTURE = "button.png";
    public static final Color UI_TEXT_COLOR_DEFAULT = Color.GOLD;
    public static final Color UI_TEXT_COLOR_ZOMBIE = Color.CYAN;
    public static final Color UI_TEXT_COLOR_COIN = Color.GOLD;
    public static final Color UI_TEXT_COLOR_HEALTH = Color.RED;
    
    /*
    *   Audio
    */
    public static final String AUDIO_GAME_MUSIK = "audio/Shanghai_Action.ogg";
	public static final String AUDIO_JUMPING_SOUND = "audio/jump.wav";
	public static final String AUDIO_HIT_SOUND = "audio/hit.wav";
	public static final String AUDIO_SHOTGUN_SOUND = "audio/shotgun.wav";
	public static final String AUDIO_DRYFIRE_SOUND = "audio/dryfire.wav";
	public static final String AUDIO_RELOAD_SOUND = "audio/reload.wav";
    public static final String AUDIO_SPARKLE_SOUND = "audio/sparkle.ogg";
    
    public static final String SOUND_ON_REGION_NAME = "sound_on";
    public static final String SOUND_OFF_REGION_NAME = "sound_off";
    public static final String MUSIC_ON_REGION_NAME = "music_on";
    public static final String MUSIC_OFF_REGION_NAME = "music_off";

}