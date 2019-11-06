package com.mygdx.zombiejump.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.zombiejump.base.BaseActor;

/**
 * Zombie
 */
public class Zombie extends BaseActor {

    private String[] textures = { "male-zombie.png", "female-zombie.png"};

    public Zombie(float x, float y, Stage s, int typ) {
        super(x, y, s);

        loadAnimationFromSheet( textures[typ], 2, 5, 0.1f, true);
        setBoundaryPolygon(12);
        setSpeed(220);
    }

    @Override
    public void act(float dt) {
        super.act(dt);

        setX(getX() - dt * getSpeed());
    }
    
}