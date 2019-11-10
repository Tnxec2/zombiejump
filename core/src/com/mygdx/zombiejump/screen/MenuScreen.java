package com.mygdx.zombiejump.screen; 

import javax.swing.text.AttributeSet.FontAttribute;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.mygdx.zombiejump.Zombiejump;
import com.mygdx.zombiejump.base.BaseActor;
import com.mygdx.zombiejump.base.BaseScreen;
import com.mygdx.zombiejump.base.BaseUI;
import com.mygdx.zombiejump.utils.Constants;


/**
 * 
 */
public class MenuScreen extends BaseScreen {

    @Override
    public void initialize() {
        BaseActor wall = new BaseActor(0, 0, mainStage);
        wall.loadTexture("wall.png");
        wall.setSize(Constants.GAME_WINDOW_WIDTH, Constants.GAME_WINDOW_HEIGHT);

        BaseActor title = new BaseActor(0, 0, mainStage);
        title.loadTexture("title.png");
        title.centerAtActor(wall);
        title.moveBy(0, 100);

        TextButton startButton = new TextButton("Jump!", BaseUI.textButtonStyle);

        startButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                Zombiejump.setActiveScreen(new LevelScreen());
                return false;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //
            }
        });

        TextButton quitButton = new TextButton("Quit", BaseUI.textButtonStyle);

        quitButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                return false;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //
            }
        });

        uiTable.add(title).colspan(2).expandY();
        uiTable.row();
        uiTable.add(startButton).expand();
        uiTable.add(quitButton).expand();
    }

    @Override
    public void update(float dt) {
        if (Gdx.input.isKeyPressed(Keys.S))
            Zombiejump.setActiveScreen(new LevelScreen());
    }

    @Override
    public boolean keyDown(int keycode) {
        if ( Gdx.input.isKeyPressed(Keys.ENTER)) 
            Zombiejump.setActiveScreen(new LevelScreen());
        if ( Gdx.input.isKeyPressed(Keys.ESCAPE))
            Gdx.app.exit();
        return false;
    }
    
}