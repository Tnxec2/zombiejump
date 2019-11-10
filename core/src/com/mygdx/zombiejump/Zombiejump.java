package com.mygdx.zombiejump;

import com.mygdx.zombiejump.base.BaseGame;
import com.mygdx.zombiejump.screen.MenuScreen;

public class Zombiejump extends BaseGame {

    public static int health;
	public static int coins;
	public static int zombieCount;

    @Override
    public void create() {
        super.create();
        
        setActiveScreen( new MenuScreen() );
    }

}