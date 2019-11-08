package com.mygdx.zombiejump.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.zombiejump.base.BaseActor;

/**
 * Hero
 */
public class Hero extends BaseActor {

    private float gravity;
    private float jumpSpeed;
    private boolean jumping;

    public Hero(float x, float y, Stage s) {
        super(x, y, s);
        
        loadAnimationFromSheet("angryhero.png", 1, 9, 0.1f, true);
        setBoundaryPolygon(12);

        setAcceleration(100);
        gravity = 700;
        jumpSpeed = 400;
        jumping = false;
        setMaxSpeed(700);
    }

	public void jump() {
        
        velocityVec.y = jumpSpeed;
        jumping = true;
	}

	@Override
	public void act(float dt) {
        super.act(dt);
        
        velocityVec.y = velocityVec.y - gravity * dt;

        applyPhsysic(dt);
	}

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    
    
}