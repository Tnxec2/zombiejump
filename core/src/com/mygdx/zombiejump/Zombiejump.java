package com.mygdx.zombiejump;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.mygdx.zombiejump.base.BaseGame;
import com.mygdx.zombiejump.screen.MenuScreen;

public class Zombiejump extends BaseGame {

    public static int health;
	public static int coinsCount;
    public static int zombieCount;
    
    @Override
    public void create() {
        super.create();
        
        setActiveScreen( new MenuScreen() );
    }

}