package com.mygdx.zombiejump.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.zombiejump.Zombiejump;
import com.mygdx.zombiejump.base.BaseActor;
import com.mygdx.zombiejump.base.BaseScreen;
import com.mygdx.zombiejump.base.BaseUI;
import com.mygdx.zombiejump.utils.AudioUtils;
import com.mygdx.zombiejump.utils.Constants;


/**
 * 
 */
public class MenuScreen extends BaseScreen {

    Label toggleMusic, toggleSound;

    @Override
    public void initialize() {
        BaseActor wall = new BaseActor(0, 0, mainStage);
        wall.loadTexture(Constants.TEXTURE_MENU_SCREEN_BACKGROUND);
        wall.setSize(Constants.GAME_WINDOW_WIDTH, Constants.GAME_WINDOW_HEIGHT);

        BaseActor title = new BaseActor(0, 0, mainStage);
        title.loadTexture(Constants.TEXTURE_MENU_SCREEN_TITLE);
        title.centerAtActor(wall);
        title.moveBy(0, 100);

        toggleMusic = new Label( Zombiejump.myBundle.get( AudioUtils.getInstance().getMusicRegionName() ) , BaseUI.labelStyle);
        toggleMusic.setColor(Constants.UI_TEXT_COLOR_DEFAULT);

        toggleMusic.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                AudioUtils.getInstance().toggleMusic();
                toggleMusic.setText(Zombiejump.myBundle.get( AudioUtils.getInstance().getMusicRegionName() ) );
                return false;
            }
        });

        toggleSound = new Label( Zombiejump.myBundle.get( AudioUtils.getInstance().getSoundRegionName() ) , BaseUI.labelStyle);
        toggleSound.setColor(Constants.UI_TEXT_COLOR_DEFAULT);

        toggleSound.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                AudioUtils.getInstance().toggleSound();
                toggleSound.setText(Zombiejump.myBundle.get( AudioUtils.getInstance().getSoundRegionName() ) );
                return false;
            }
        });

        TextButton startButton = new TextButton( Zombiejump.myBundle.format("startJump"), BaseUI.textButtonStyle);

        startButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                newGame();
                return false;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //
            }
        });

        TextButton quitButton = new TextButton(Zombiejump.myBundle.format("quit"), BaseUI.textButtonStyle);

        quitButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                quitGame();
                return false;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //
            }
        });

        uiTable.add(toggleMusic).expandX();
        uiTable.add(toggleSound);
        uiTable.row();

        uiTable.add(title).colspan(2).expandY();
        uiTable.row();

        Label tutorialLeft = new Label(Zombiejump.myBundle.format("tutorial1"), BaseUI.labelStyle);
        tutorialLeft.setColor(Constants.UI_TEXT_COLOR_DEFAULT);
        Label tutorialRight = new Label(Zombiejump.myBundle.format("tutorial2"), BaseUI.labelStyle);
        tutorialRight.setColor(Constants.UI_TEXT_COLOR_DEFAULT);

        uiTable.add(tutorialLeft).colspan(2);
        uiTable.row();
        uiTable.add(tutorialRight).colspan(2);

        uiTable.row();
        uiTable.add(startButton).expand();
        uiTable.add(quitButton).expand();


        Preferences prefs = Gdx.app.getPreferences(Constants.PREFS_NAME);
        int coinsHighScore = prefs.getInteger(Constants.PREFS_NAME_COINS_HIGHSCORE, 0);
        if ( coinsHighScore != 0) {
            Label highScoreLaben = new Label(Zombiejump.myBundle.format("highScore", coinsHighScore), BaseUI.labelStyle);
            highScoreLaben.setColor(Constants.UI_TEXT_COLOR_DEFAULT);
            uiTable.row();
            uiTable.add(highScoreLaben).colspan(2);
        }
    }

    @Override
    public void update(float dt) {
        if (Gdx.input.isKeyPressed(Keys.S))
            newGame();
    }

    @Override
    public boolean keyDown(int keycode) {
        if ( Gdx.input.isKeyPressed(Keys.ENTER)) 
            newGame();
        if ( Gdx.input.isKeyPressed(Keys.ESCAPE))
            quitGame();
        return false;
    }

    private void newGame() {
        Zombiejump.setActiveScreen(new LevelScreen());
    }

    private void quitGame() {
        Gdx.app.exit();
    }
    
}