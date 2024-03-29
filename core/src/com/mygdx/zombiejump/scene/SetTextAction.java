package com.mygdx.zombiejump.scene;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.zombiejump.base.BaseDialogBox;

/**
 *  Designed for use in concert with 
 *      the DialogBox, SceneSegment, and Scene classes.
 */
public class SetTextAction extends Action
{
    protected String textToDisplay;
    
    public SetTextAction(String s)
    {  
        textToDisplay = s;
    }
    
    public boolean act(float dt)
    {
        BaseDialogBox db = (BaseDialogBox)target;
        db.setText( textToDisplay );
        return true; // action completed
    }
}