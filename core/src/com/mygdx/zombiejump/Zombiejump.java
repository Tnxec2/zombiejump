package com.mygdx.zombiejump;

import java.util.Locale;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.I18NBundle;
import com.mygdx.zombiejump.base.BaseGame;
import com.mygdx.zombiejump.screen.MenuScreen;

public class Zombiejump extends BaseGame {

    public static int health;
	public static int coinsCount;
    public static int zombieCount;
    public static I18NBundle myBundle;

    @Override
    public void create() {
        super.create();
        
        FileHandle baseFileHandle = Gdx.files.internal("i18n/lang");
        //Locale locale = new Locale("de", "DE");
        myBundle = I18NBundle.createBundle(baseFileHandle, Locale.getDefault());

        setActiveScreen( new MenuScreen() );
    }

}