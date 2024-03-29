package com.mygdx.zombiejump.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.zombiejump.MyGame;
import com.mygdx.zombiejump.base.BaseActor;
import com.mygdx.zombiejump.base.BaseScreen;
import com.mygdx.zombiejump.base.BaseUI;
import com.mygdx.zombiejump.utils.Constants;

/**
 * 
 */
public class GameOverScreen extends BaseScreen
{
    
    public GameOverScreen(MyGame game) {
        super(game);
	}

	@Override
    public void initialize()
    {
        BaseActor wall = new BaseActor(0, 0, mainStage);
        wall.loadTexture(Constants.TEXTURE_GAMEOVER_SCREEN_BACKGROUND);
        wall.setSize(Constants.GAME_WINDOW_WIDTH, Constants.GAME_WINDOW_HEIGHT);

        BaseActor title = new BaseActor(0, 0, mainStage);
        title.loadTexture(Constants.TEXTURE_GAMEOVER_SCREEN_TITLE);
        title.centerAtActor(wall);
        title.moveBy(0, 100);

        BaseActor zombieIcon = new BaseActor(0, 0, uiStage);
        zombieIcon.loadTexture(Constants.TEXTURE_ICON_ZOMBIE);

        BaseActor coinIcon = new BaseActor(0, 0, uiStage);
        coinIcon.loadTexture(Constants.TEXTURE_ICON_COIN);

        BaseActor scoreIcon = new BaseActor(0, 0, uiStage);
        scoreIcon.loadTexture(Constants.TEXTURE_ICON_SCORE);

        Label zombieLabel = new Label(" x " + game.zombieCount, BaseUI.labelStyle);
        zombieLabel.setColor(Constants.UI_TEXT_COLOR_ZOMBIE);

        Label coinLabel = new Label(" x " + game.coinsCount, BaseUI.labelStyle);
        coinLabel.setColor(Constants.UI_TEXT_COLOR_COIN);

        Label scoreLabel = new Label(" x " + MathUtils.ceil(game.scoreCount), BaseUI.labelStyle);
        scoreLabel.setColor(Constants.UI_TEXT_COLOR_SCORE);
        
        Label highScoreLabel = new Label(game.myBundle.format("newHighScore"), BaseUI.labelStyle);
        highScoreLabel.setColor(Constants.UI_TEXT_COLOR_DEFAULT);
        highScoreLabel.setVisible(false);

        Label message = new Label(game.myBundle.format("touchToRestart"), BaseUI.labelStyle);
        message.setColor(Constants.UI_TEXT_COLOR_DEFAULT);

        uiTable.pad(20);
        uiTable.add(title).colspan(2).expandY();
        uiTable.row();
        uiTable.add(zombieIcon).right();
        uiTable.add(zombieLabel).left();
        uiTable.row();
        uiTable.add(coinIcon).right();
        uiTable.add(coinLabel).left();
        uiTable.row();
        uiTable.add(scoreIcon).right();
        uiTable.add(scoreLabel).left();
        uiTable.row();
        uiTable.add(highScoreLabel).colspan(2);
        uiTable.row();
        uiTable.add(message).colspan(2).expandY();

        Preferences prefs = Gdx.app.getPreferences(Constants.PREFS_NAME);
        int coinsHighScore = prefs.getInteger(Constants.PREFS_NAME_COINS_HIGHSCORE, 0);
        float scoreHighScore = prefs.getFloat(Constants.PREFS_NAME_SCORE_HIGHSCORE, 0);

        if ( game.coinsCount > coinsHighScore )
        {
            prefs.putInteger(Constants.PREFS_NAME_COINS_HIGHSCORE, game.coinsCount);
            prefs.flush();
            //highScoreLabel.setVisible(true);
        }
        if ( game.scoreCount > scoreHighScore )
        {
            prefs.putFloat(Constants.PREFS_NAME_SCORE_HIGHSCORE,  game.scoreCount);
            prefs.flush();
            highScoreLabel.setVisible(true);
        }
    }

    @Override
    public boolean keyDown(int keycode)
    {
        if (Gdx.input.isKeyPressed(Keys.ESCAPE))
            Gdx.app.exit();
        else
            newGame();
        return false;
    }

    @Override
    public void update(float delta) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        
        newGame();

        return super.touchDown(screenX, screenY, pointer, button);
    }

    private void newGame() {
        MyGame.setActiveScreen(new MenuScreen(game));
    }
    
}