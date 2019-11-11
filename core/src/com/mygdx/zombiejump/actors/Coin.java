package com.mygdx.zombiejump.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.zombiejump.base.BaseActor;
import com.mygdx.zombiejump.utils.Constants;

public class Coin extends BaseActor
{
    public Coin(float x, float y, Stage s)
    {
        super(x,y,s);
        loadAnimationFromSheet(Constants.TEXTURE_COIN, 1, 8, 0.1f, true);
        
        setSpeed(Constants.SCROLL_SPEED_GROUND);
        setMotionAngle(180);
    }
    
    public void act(float dt)
    {
        super.act(dt);
        applyPhsysic(dt);
        
        // remove after moving past left edge of screen
        if ( getX() + getWidth() < 0 )
            remove();
    }
    
}