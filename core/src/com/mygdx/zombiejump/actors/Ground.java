package com.mygdx.zombiejump.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.zombiejump.base.BaseActor;

/**
 * Ground
 */
public class Ground extends BaseActor {

    public Ground(float x, float y, Stage s) {
        super(x, y, s);
        
        loadTexture("ground.png");
        setBoundaryRectangle();

        setSpeed(50);
        setMotionAngle(180);
    }
   
    @Override
    public void act(float dt) {
        super.act(dt);

        applyPhsysic(dt);
    }
}