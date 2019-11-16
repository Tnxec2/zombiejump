package com.mygdx.zombiejump.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.zombiejump.MyGame;
import com.mygdx.zombiejump.actors.Coin;
import com.mygdx.zombiejump.actors.Hero;
import com.mygdx.zombiejump.actors.Home;
import com.mygdx.zombiejump.actors.Shot;
import com.mygdx.zombiejump.actors.Sky;
import com.mygdx.zombiejump.actors.Zombie;
import com.mygdx.zombiejump.base.BaseActor;
import com.mygdx.zombiejump.base.BaseDialogBox;
import com.mygdx.zombiejump.base.BaseGame;
import com.mygdx.zombiejump.base.BaseScreen;
import com.mygdx.zombiejump.base.BaseUI;
import com.mygdx.zombiejump.utils.AudioUtils;
import com.mygdx.zombiejump.utils.Constants;

/**
 * LevelScreen
 */
public class LevelScreen extends BaseScreen {

    private Sky sky1, sky2;

    private Hero hero;

    private float zombieSpawnTimer;
    private float zombieSpawnInterval;

    
    private Home lastHome;
    private int spawnWidthHome;
    
    private Label zombieLabel, bulletLabel, healthLabel, coinLabel, scoreLabel;
    private BaseDialogBox dialogBox;
    
    private boolean gameOver;

    private Vector2 offset;
    
    private Shot shot;

    private boolean reloadShot;
    private float shotTime;

    private float coinTimer;

    private boolean touchfeld;
  
    
    public LevelScreen(MyGame game) {
        super(game);
	}

	@Override
    public void initialize() {
        touchfeld = false;

        game.zombieCount = 0;
        game.health = Constants.HERO_MAX_HEALTH;
        game.coinsCount = 0;
        game.scoreCount = 0;

        gameOver = false;
        shotTime = Constants.SHOT_RELOAD_INTERVAL;
        reloadShot = false;

        setupActors();

        spawnWidthHome = MathUtils.random(0, 100);
        spawnHome();

        BaseActor.setWorldBounds(Constants.GAME_WINDOW_WIDTH, Constants.GAME_WINDOW_HEIGHT);

        setupUI();

        AudioUtils.getInstance().playMusic();
    }

    private void setupActors() {
        sky1 = new Sky(0, 0, mainStage);
        sky2 = new Sky(sky1.getWidth(), 0, mainStage);

        lastHome = new Home(Constants.HERO_STARTX - 100, 0, mainStage, 0);
        hero = new Hero(Constants.HERO_STARTX, lastHome.getHeight(), mainStage);
        shot = new Shot(0, 0, mainStage);
    }

    private   void setupUI() {
        BaseActor zombieIcon = new BaseActor(0,0,uiStage);
        zombieIcon.loadTexture(Constants.TEXTURE_ICON_ZOMBIE);
        BaseActor healthIcon = new BaseActor(0,0,uiStage);
        healthIcon.loadTexture(Constants.TEXTURE_ICON_HEALTH);
        BaseActor coinIcon = new BaseActor(0,0,uiStage);
        coinIcon.loadTexture(Constants.TEXTURE_ICON_COIN);
        BaseActor scoreIcon = new BaseActor(0,0,uiStage);
        scoreIcon.loadTexture(Constants.TEXTURE_ICON_SCORE);
        BaseActor bulletIcon = new BaseActor(0,0,uiStage);
        bulletIcon.loadTexture(Constants.TEXTURE_ICON_BULLET);

        zombieLabel = new Label(" x " + game.zombieCount, BaseUI.labelStyle);
        zombieLabel.setColor(Constants.UI_TEXT_COLOR_ZOMBIE);

        healthLabel = new Label("" + game.health + " x " , BaseUI.labelStyle);
        healthLabel.setColor(Constants.UI_TEXT_COLOR_HEALTH);

        coinLabel  = new Label(" x " + game.coinsCount,  BaseUI.labelStyle);
        coinLabel.setColor(Constants.UI_TEXT_COLOR_COIN);

        scoreLabel  = new Label("" + game.scoreCount + " x ",  BaseUI.labelStyle);
        scoreLabel.setColor(Constants.UI_TEXT_COLOR_DEFAULT);

        scoreLabel  = new Label("" + game.scoreCount + " x ",  BaseUI.labelStyle);
        scoreLabel.setColor(Constants.UI_TEXT_COLOR_SCORE);

        /*
        dialogBox = new BaseDialogBox(0,0, uiStage);
        dialogBox.setBackgroundColor( Color.TAN );
        dialogBox.setFontColor( Color.BROWN );
        dialogBox.setDialogSize(Constants.GAME_WINDOW_WIDTH / 4 * 3, 100); // width = three-fourths of game width
        dialogBox.setFontScale(0.80f);
        dialogBox.alignCenter();
        dialogBox.setVisible(false);
        */

        bulletLabel = new Label( " x ", BaseUI.labelStyle);
        bulletLabel.setColor(Color.RED);

        if(Gdx.app.getType() == ApplicationType.iOS || Gdx.app.getType() == ApplicationType.Android) {
            //Do awesome stuff for iOS here
            setupTouchfield();
        }

        uiTable.pad(20);
        uiTable.add(zombieIcon).right();
        uiTable.add(zombieLabel).left();
        uiTable.add().expandX();
        uiTable.add(coinIcon);
        uiTable.add(coinLabel);
        uiTable.add().expandX();
        uiTable.add(healthLabel).right().expandX();
        uiTable.add(healthIcon).left();
        uiTable.row();
        uiTable.add(bulletIcon).right();
        uiTable.add(bulletLabel).left();
        uiTable.add(scoreLabel).right().colspan(5).expandX();
        uiTable.add(scoreIcon).left();
        uiTable.row();
        uiTable.add().colspan(8).expand();

    }

    @Override
    public void update(float delta) {

        if (gameOver)
            return;

        if (hero.isOutOfWorld() && !gameOver)
            heroOutofWorld();

        coinTimer += delta;
        if (coinTimer > Constants.COIN_SPAWN_INTERVAL)
        {
            new Coin( Constants.GAME_WINDOW_WIDTH, 
                lastHome.getHeight() + lastHome.getY() + MathUtils.random(20,150), 
                mainStage );
            coinTimer = 0;
        }

        for (BaseActor coinActor : BaseActor.getList(mainStage, Coin.class.getName() ) ) 
        {
            if (hero.overlaps(coinActor))
            {
                AudioUtils.getInstance().playSparkleSound();
                coinActor.remove();
                game.coinsCount++;
                coinLabel.setText(" x " + game.coinsCount );
            }
        }

        for (BaseActor zombieActor : BaseActor.getList(mainStage, Zombie.class.getName()))
        {
            if ( shot.isVisible() && zombieActor.overlaps(shot))
            {
                zombieActor.remove();
                shot.hide();
                game.zombieCount++;
                continue;
            }

            if (zombieActor.overlaps(hero))
            {
                zombieHit(zombieActor);
            }

        }

        if (zombieSpawnTimer >= zombieSpawnInterval)
        {
            spawnZombie();
        }
        zombieSpawnTimer += delta;

        hero.setCanJump(false);
        for (BaseActor homeActor : BaseActor.getList(mainStage, Home.class.getName()))
        {

            offset = hero.preventOverlap(homeActor);
            if (offset != null)
            {
                if (Math.abs(offset.y) > Math.abs(offset.x))
                {
                    if (hero.isJumping())
                    {
                        hero.setJumping(false);
                    }

                }
            }
            if ((hero.getX() + hero.getWidth()) > homeActor.getX()
                    && (hero.getX() + hero.getWidth() * 0.25) < (homeActor.getX() + homeActor.getWidth()))
            {
                hero.setCanJump(true);
                //System.out.println("Can Jump: " + hero.isCanJump() );
            }
        }

        if (Constants.GAME_WINDOW_WIDTH - (lastHome.getX() + lastHome.getWidth()) > spawnWidthHome)
            spawnHome();

        zombieLabel.setText(" x " + game.zombieCount);

        if ( shotTime < Constants.SHOT_RELOAD_INTERVAL ) shotTime += delta;

        if ( !gameOver )
        {
            if (shotTime < Constants.SHOT_RELOAD_INTERVAL )
            {
                bulletLabel.setText(" x " + MathUtils.round( Constants.SHOT_RELOAD_INTERVAL - shotTime));
                bulletLabel.setColor(Constants.UI_TEXT_COLOR_BULLET_FALSE);
            } else
            {
                if ( ! reloadShot )
                {
                    reloadShot = true;
                    bulletLabel.setText("OK");
                    bulletLabel.setColor(Constants.UI_TEXT_COLOR_BULLET_OK);
                    AudioUtils.getInstance().playReloadSound();
                }
            }
        }

        game.scoreCount += delta * Constants.SCROLL_SPEED_GROUND / 50;

        scoreLabel.setText( MathUtils.ceil(game.scoreCount) + " x ");

    }

    private void heroOutofWorld()
    {
            gameOver();
    }

    private void zombieHit(BaseActor zombie)
    {
        zombie.remove();
        game.health--;
        healthLabel.setText(game.health + " x ");
        shot.hide();
        AudioUtils.getInstance().playHitSound();

        if ( game.health <= 0 )
        {
            gameOver();
        }
    }


    private void spawnZombie()
    {
        if (lastHome.isLong)
        {
            if (lastHome.getX() + lastHome.getWidth() > Constants.GAME_WINDOW_WIDTH)
            {
                new Zombie(
                    lastHome.getX() + lastHome.getWidth() - 64,
                    lastHome.getHeight() + lastHome.getY(),
                    mainStage);
                zombieSpawnTimer = 0;
                zombieSpawnInterval = MathUtils.random(3f) + 1f;
            }
        }
    }

    private void spawnHome()
    {
        // System.out.print( "Ende: " + ( lastHome.getX() + lastHome.getWidth() ) +
        // ", Breite: " + spawnWidthHome + ", Anfang: ");
        lastHome = new Home(lastHome.getX() + lastHome.getWidth() + spawnWidthHome, 0, mainStage);
        // System.out.println( lastHome.getX() );
        spawnWidthHome = MathUtils.random(100, 180);
    }

    private void gameOver()
    {
        gameOver = true;
        AudioUtils.getInstance().stopMusic();
        sky1.setSpeed(0);
        sky2.setSpeed(0);
        hero.remove();
        shot.remove();
        bulletLabel.remove();
        BaseActor gameOverMessage = new BaseActor(0, 0, uiStage);
        gameOverMessage.loadTexture(Constants.TEXTURE_GAME_OVER);
        gameOverMessage.centerAtPosition(Constants.GAME_WINDOW_WIDTH / 2, 
                                        Constants.GAME_WINDOW_HEIGHT / 2);
        gameOverMessage.setOpacity(0);
        gameOverMessage.addAction(Actions.after(Actions.fadeIn(1)));
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        
        if (gameOver)
        {
            newGame();
        } else {
            if ( ! touchfeld ) doJump();
        }

        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public void hide()
    {
        super.hide();

        AudioUtils.getInstance().stopMusic();
    }

    @Override
    public boolean keyDown(int keycode)
    {
        switch (keycode)
        {
            case Keys.CONTROL_LEFT:
                if (!gameOver) doShot();
                break;
            case Keys.SPACE:
                if (!gameOver) doJump();
                else newGame();
                break;
            default:
                break;
        }
        return super.keyDown(keycode);
    }

    private void newGame()
    {
        BaseGame.setActiveScreen(new GameOverScreen(game));
    }

    private void doJump()
    {
        hero.jump();
    }

    private void doShot()
    {
        if ( gameOver ) return;

        if ( shotTime < Constants.SHOT_RELOAD_INTERVAL || shot.isVisible() )
        {
            if ( shotTime < Constants.SHOT_RELOAD_INTERVAL )
            {
                AudioUtils.getInstance().playDryfireSound();
            }
            return;
        }
        shotTime = 0;
        shot.setX(hero.getX() + hero.getWidth());
        shot.setY(hero.getY() + hero.getHeight() / 2 );
        shot.show();
        AudioUtils.getInstance().playShotgunSound();
        reloadShot = false;
    }


    private void setupTouchfield() {
        touchfeld = true;
        Texture touchTexture = new Texture(Gdx.files.internal("touchpoint.png"));
        Texture touchTexturePressed = new Texture(Gdx.files.internal("touchpoint-pressed.png"));
        ImageButton jumpButton = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(touchTexture)),
                new TextureRegionDrawable(new TextureRegion(touchTexturePressed))
        );
        jumpButton.setPosition(10, 10);
        uiStage.addActor(jumpButton);

        jumpButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                doJump();
                return false;
            }
        });

        ImageButton shotButton = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(touchTexture)),
                new TextureRegionDrawable(new TextureRegion(touchTexturePressed))
        );
        shotButton.setPosition(Constants.GAME_WINDOW_WIDTH-shotButton.getWidth()-10 , 10);
        uiStage.addActor(shotButton);

        shotButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                doShot();
                return false;
            }
        });
    }
}