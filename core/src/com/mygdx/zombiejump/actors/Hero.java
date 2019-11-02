package com.mygdx.zombiejump.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.zombiejump.base.BaseActor;

/**
 * Hero
 */
public class Hero extends BaseActor {

    public Hero(float x, float y, Stage s) {
        super(x, y, s);
        
        loadAnimationFromSheet("koala.png", 1, 3, 0.3f, true);
        setBoundaryPolygon(8);
    }

    
}