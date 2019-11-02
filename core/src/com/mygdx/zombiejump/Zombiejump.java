package com.mygdx.zombiejump;

import com.mygdx.zombiejump.base.BaseGame;
import com.mygdx.zombiejump.screen.LevelScreen;

public class Zombiejump extends BaseGame {

    @Override
    public void create() {
        super.create();
        
        setActiveScreen( new LevelScreen() );
    }

}