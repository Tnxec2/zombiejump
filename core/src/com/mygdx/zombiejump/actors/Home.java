package com.mygdx.zombiejump.actors;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.zombiejump.base.BaseActor;

/**
 * 
 */
public class Home extends BaseActor {

    private Props[] typs = { 
        new Props(true, false,"home1.png"),
        new Props(false, true, "home2.png"),
        new Props(false, false, "home3.png"),
        new Props(false, false,"home4.png"),
        new Props(false, false, "home5.png"),
        new Props(true,  false, "home6.png"),
        new Props(false, false, "home7.png"),
        new Props(  true,false,"home8.png"),
        new Props(false, false, "home9.png"),
        new Props(false, true,"home10.png"),
        new Props(false, false, "home11.png")
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
        if ( !isHight ) setX( getX() + 30 );
        setBoundaryRectangle();

        setSpeed(200);
    }
   
    @Override
    public void act(float dt) {
        super.act(dt);

        setX(getX() - dt * getSpeed());
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