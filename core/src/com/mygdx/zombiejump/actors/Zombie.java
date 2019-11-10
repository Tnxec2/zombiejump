package com.mygdx.zombiejump.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.zombiejump.base.BaseActor;
import com.mygdx.zombiejump.utils.Constants;

/**
 * Zombie
 */
public class Zombie extends BaseActor {

    

    public Zombie(float x, float y, Stage s, int typ) {
        super(x, y, s);

        loadAnimationFromSheet( Constants.ZOMBIE_TEXTURES_FILENAME[typ], 2, 5, 0.1f, true);
        setBoundaryPolygon(12);
        setSpeed(Constants.SCROLL_SPEED_ZOMBIE);
        setMotionAngle(180);
    }

    @Override
    public void act(float dt) {
        super.act(dt);

        applyPhsysic(dt);
    }
    
}