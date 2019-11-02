package com.mygdx.zombiejump.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.zombiejump.base.BaseActor;

/**
 * Sky
 */
public class Sky extends BaseActor {

    public Sky(float x, float y, Stage s) {
        super(x, y, s);
        loadTexture("sky.png");

        setSpeed(100);
        setMotionAngle(180);

    }

    @Override
    public void act(float dt) {
        super.act(dt);

        applyPhsysic(dt);
    }

    

}