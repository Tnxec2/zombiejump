package com.mygdx.zombiejump.actors;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.zombiejump.base.BaseActor;
import com.mygdx.zombiejump.utils.AudioUtils;
import com.mygdx.zombiejump.utils.Constants;

/**
 * Hero
 */
public class Hero extends BaseActor {

    private float gravity;
    private float jumpSpeed;
    private boolean jumping;
    private boolean canJump;

    private Sound jumpSound;

    public Hero(float x, float y, Stage s) {
        super(x, y, s);
        
        loadAnimationFromSheet(Constants.TEXTURE_HERO_RUNNING, 1, 9, 0.1f, true);
        setBoundaryPolygon(12);

        setAcceleration(100);
        gravity = Constants.HERO_GRAVITY;
        jumpSpeed = Constants.HERO_SPEED_JUMP;
        jumping = false;
        canJump = false;
        setMaxSpeed(Constants.HERO_SPEED_MAX);

        jumpSound = AudioUtils.getInstance().getJumpSound();
    }

	public boolean jump() {
        if ( !jumping && canJump ) {
            velocityVec.y = jumpSpeed;
            jumping = true;
            AudioUtils.getInstance().playSound(jumpSound);
            return true;
        } else {
            return false;
        }
	}

	@Override
	public void act(float dt) {
        super.act(dt);
        
        velocityVec.y = velocityVec.y - gravity * dt;
        if ( !isJumping() && getX() < Constants.HERO_STARTX )  {
            setX(getX() + dt * Constants.HERO_SPEED_WALK);
        }
        if (getX() > Constants.HERO_STARTX)
            setX(Constants.HERO_STARTX);

        applyPhsysic(dt);
	}

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public boolean isCanJump() {
        return canJump;
    }

    public void setCanJump(boolean canJump) {
        this.canJump = canJump;
    }

    
    
}