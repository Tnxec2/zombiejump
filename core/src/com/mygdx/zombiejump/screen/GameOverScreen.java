package com.mygdx.zombiejump.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.zombiejump.Zombiejump;
import com.mygdx.zombiejump.base.BaseActor;
import com.mygdx.zombiejump.base.BaseScreen;
import com.mygdx.zombiejump.base.BaseUI;
import com.mygdx.zombiejump.utils.Constants;

/**
 * 
 */
public class GameOverScreen extends BaseScreen {

    static Preferences prefs = Gdx.app.getPreferences("ZombieJumpPrefs");
    
    @Override
    public void initialize() {
        BaseActor wall = new BaseActor(0, 0, mainStage);
        wall.loadTexture("wall.png");
        wall.setSize(Constants.GAME_WINDOW_WIDTH, Constants.GAME_WINDOW_HEIGHT);

        BaseActor title = new BaseActor(0, 0, mainStage);
        title.loadTexture("title.png");
        title.centerAtActor(wall);
        title.moveBy(0, 100);

        BaseActor zombieIcon = new BaseActor(0, 0, uiStage);
        zombieIcon.loadTexture("zombie-icon.png");

        BaseActor coinIcon = new BaseActor(0, 0, uiStage);
        coinIcon.loadTexture("coin-icon.png");

        Label zombieLabel = new Label(" x " + Zombiejump.zombieCount, BaseUI.labelStyle);
        zombieLabel.setColor(Color.CYAN);

        Label coinLabel = new Label(" x " + Zombiejump.coinsCount, BaseUI.labelStyle);
        coinLabel.setColor(Color.GOLD);

        Label message = new Label(" Touch to restart", BaseUI.labelStyle);
        message.setColor(Color.GOLD);

        uiTable.add(title).colspan(2).expandY();
        uiTable.row();
        uiTable.add(zombieIcon);
        uiTable.add(zombieLabel);
        uiTable.row();
        uiTable.add(coinIcon);
        uiTable.add(coinLabel);
        uiTable.row();
        uiTable.add(message).colspan(2).expandY();

        int coinsHighScore = prefs.getInteger("coinsHighScore", 0);

        if ( Zombiejump.coinsCount > coinsHighScore ) {
            coinsHighScore = Zombiejump.coinsCount;
            prefs.putInteger("coinsHighScore", Zombiejump.coinsCount);
            coinLabel.setText(coinLabel.getText() + " !!! New High Score" );
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        if (Gdx.input.isKeyPressed(Keys.ESCAPE))
            Gdx.app.exit();
        else
            Zombiejump.setActiveScreen(new MenuScreen());
        return false;
    }

    @Override
    public void update(float delta) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        
        Zombiejump.setActiveScreen(new MenuScreen());

        return super.touchDown(screenX, screenY, pointer, button);
    }
    
}