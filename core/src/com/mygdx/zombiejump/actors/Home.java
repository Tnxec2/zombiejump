package com.mygdx.zombiejump.actors;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.zombiejump.base.BaseActor;
import com.mygdx.zombiejump.utils.Constants;

/**
 * 
 */
public class Home extends BaseActor {

    private Props[] typs = { 
        new Props( true, false, "zombierun-grafitti-1.png")
    };

    public boolean isLong;
    public boolean isHight;

    public Home(float x, float y, Stage s ) {
        super(x, y, s);

        setHome(x, MathUtils.random(typs.length-1));
    }

    public Home(float x, float y, Stage s, int typ ) {
        super(x, y, s);
        
        setHome(x, typ);
    }

    private  void setHome(float x, int typ) {
        loadTexture(typs[typ].fileName);
        isLong = typs[typ].isLong;
        isHight = typs[typ].isHeight;
        
        //if ( !isHight ) setX( getX() + 40 );

        setY( - MathUtils.random( 0, getHeight() / 2 ) );

        setBoundaryRectangle();

        setSpeed(Constants.SCROLL_SPEED_GROUND);


    }

    @Override
    public void act(float dt) {
        super.act(dt);

        setX(getX() - dt * getSpeed());

        if (getX() + getWidth() <= 0) {
            remove();
        }
    }

    class Props {
        boolean isLong;
        boolean isHeight;
        String fileName;

        public Props(boolean isLong, boolean isHeight, String fileName) {
            this.isLong = isLong;
            this.isHeight = isHeight;
            this.fileName = fileName;
        }
        
    }
}