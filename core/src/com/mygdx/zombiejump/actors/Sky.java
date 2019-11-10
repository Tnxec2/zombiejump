package com.mygdx.zombiejump.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.zombiejump.base.BaseActor;
import com.mygdx.zombiejump.utils.Constants;

/**
 * Sky
 */
public class Sky extends BaseActor {

    public Sky(float x, float y, Stage s) {
        super(x, y, s);
        loadTexture(Constants.SKY_TEXTURE_FILENAME);

        setSpeed(Constants.SCROLL_SPEED_SKY);
        setMotionAngle(180);
    }

    @Override
    public void act(float dt) {
        super.act(dt);

        applyPhsysic(dt);
    }
   

}