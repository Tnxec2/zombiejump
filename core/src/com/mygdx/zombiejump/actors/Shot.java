
package com.mygdx.zombiejump.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.zombiejump.base.BaseActor;

/**
 * Shoot
 */
public class Shot extends BaseActor {

    public Shot(float x, float y, Stage s) {
        super(x, y, s);

        loadTexture("shot.png");

        setSpeed(400);
        setMotionAngle(0);
        setVisible(false);
    }

    @Override
    public void act(float dt) {
        super.act(dt);

        if (isVisible()) applyPhsysic(dt);

        if ( isOutOfWorld() ) setVisible(false);
    }

    public void  hide() {
        this.setVisible(false); 
    }

    public void show() {
        this.setVisible(true);
    }
}