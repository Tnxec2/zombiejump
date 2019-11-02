package com.mygdx.zombiejump.screen;

import com.badlogic.gdx.utils.Array;
import com.mygdx.zombiejump.actors.Ground;
import com.mygdx.zombiejump.actors.Hero;
import com.mygdx.zombiejump.actors.Sky;
import com.mygdx.zombiejump.base.BaseScreen;

/**
 * LevelScreen
 */
public class LevelScreen extends BaseScreen {

    private Sky sky1, sky2;
    private Ground ground1, ground2;
    Hero hero;

    @Override
    public void initialize() {

        sky1 =  new Sky(0, 0, mainStage);
        sky2 =  new Sky(sky1.getWidth(), 0, mainStage);

        ground1 =  new Ground(0, 0, mainStage);
        ground2 =  new Ground(ground1.getWidth(), 0, mainStage);
    }

    @Override
    public void update(float delta) {
        
        if ( sky1.getX() + sky1.getWidth() < 0 )
            sky1.setX(sky2.getX() + sky2.getWidth() );
        if ( sky2.getX() + sky2.getWidth() < 0 )
            sky2.setX(sky1.getX() + sky1.getWidth() );  

        if ( ground1.getX() + ground1.getWidth() < 0 )
            ground1.setX(ground2.getX() + ground2.getWidth() );
        if ( ground2.getX() + ground2.getWidth() < 0 )
            sky2.setX(ground1.getX() + ground1.getWidth() );
    }

    
}