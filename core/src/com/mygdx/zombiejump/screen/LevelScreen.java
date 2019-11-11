package com.mygdx.zombiejump.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.zombiejump.Zombiejump;
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
import com.mygdx.zombiejump.utils.Constants;

/**
 * LevelScreen
 */
public class LevelScreen extends BaseScreen {

    private Sky sky1, sky2;

    private Hero hero;

    private float zombieSpawnTimer;
    private float zombieSpawnInterval = 1f;

    
    private Home lastHome;
    private int spawnWidthHome;
    
    private Label zombieLabel, nachladeLabel, healthLabel, coinLabel;
    private BaseDialogBox dialogBox;
    
    private boolean gameOver;
    
    private Music music;
    private Sound jump, hit, shotgun, dryfire, reload, sparkle;
    private float audioVolume;
    
    private Vector2 offset;
    
    private Shot shot;

    private boolean reloadShot;
    private float shotTime;

    private float starTimer;
  
    
    @Override
    public void initialize() {

        Zombiejump.zombieCount = 0;
        Zombiejump.health = 3;
        Zombiejump.coinsCount = 0;

        sky1 = new Sky(0, 0, mainStage);
        sky2 = new Sky(sky1.getWidth(), 0, mainStage);

        lastHome = new Home(Constants.HERO_STARTX - 100, 0, mainStage, 0);
        hero = new Hero(Constants.HERO_STARTX, lastHome.getHeight(), mainStage);
        shot = new Shot(0, 0, mainStage);
        shotTime = Constants.SHOT_RELOAD_INTERVAL;

        BaseActor zombieIcon = new BaseActor(0,0,uiStage);
        zombieIcon.loadTexture(Constants.TEXTURE_ICON_ZOMBIE);
        BaseActor healthIcon = new BaseActor(0,0,uiStage);
        healthIcon.loadTexture(Constants.TEXTURE_ICON_HEALTH);
        BaseActor coinIcon = new BaseActor(0,0,uiStage);
        coinIcon.loadTexture(Constants.TEXTURE_ICON_COIN);

        zombieLabel = new Label(" x " + Zombiejump.zombieCount, BaseUI.labelStyle);
        zombieLabel.setColor(Constants.UI_TEXT_COLOR_ZOMBIE);

        healthLabel = new Label(" x " + Zombiejump.health, BaseUI.labelStyle);
        healthLabel.setColor(Constants.UI_TEXT_COLOR_HEALTH);

        coinLabel  = new Label(" x " + Zombiejump.coinsCount,  BaseUI.labelStyle);
        coinLabel.setColor(Constants.UI_TEXT_COLOR_COIN);

        dialogBox = new BaseDialogBox(0,0, uiStage);
        dialogBox.setBackgroundColor( Color.TAN );
        dialogBox.setFontColor( Color.BROWN );
        dialogBox.setDialogSize(Constants.GAME_WINDOW_WIDTH / 4 * 3, 100); // width = three-fourths of game width
        dialogBox.setFontScale(0.80f);
        dialogBox.alignCenter();
        dialogBox.setVisible(false);

        spawnHome();
        spawnWidthHome = MathUtils.random(100, 170);

        nachladeLabel = new Label(Zombiejump.myBundle.format("reloadIn", 0), BaseUI.labelStyle);
        nachladeLabel.setColor(Constants.UI_TEXT_COLOR_DEFAULT);
        reloadShot = false;

        BaseActor.setWorldBounds(Constants.GAME_WINDOW_WIDTH, Constants.GAME_WINDOW_HEIGHT);

        gameOver = false;

        music = Gdx.audio.newMusic(Gdx.files.internal("Shanghai_Action.mp3"));
        jump = Gdx.audio.newSound(Gdx.files.internal("jump.wav"));
        hit = Gdx.audio.newSound(Gdx.files.internal("hit.wav"));
        shotgun = Gdx.audio.newSound(Gdx.files.internal("shotgun.wav"));
        dryfire = Gdx.audio.newSound(Gdx.files.internal("dryfire.wav"));
        reload = Gdx.audio.newSound(Gdx.files.internal("reload.wav"));
        sparkle = Gdx.audio.newSound(Gdx.files.internal("sparkle.mp3"));

        audioVolume = -0.50f;
        music.setLooping(true);
        music.setVolume(audioVolume);
        music.play();

        uiTable.pad(20);
        uiTable.add(zombieIcon);
        uiTable.add(zombieLabel);
        uiTable.add().expandX();
        uiTable.add(coinIcon);
        uiTable.add(coinLabel);
        uiTable.add().expandX();
        uiTable.add(healthIcon);
        uiTable.add(healthLabel);
        uiTable.row();
        uiTable.add(nachladeLabel).colspan(6).top().left();
        uiTable.add().expand();
        uiTable.row();
        uiTable.add(dialogBox).colspan(6);
        
    }

    @Override
    public void update(float delta) {

        if (gameOver)
            return;

        if (hero.isOutOfWorld() && !gameOver) {
            heroOutofWorld();
        }

        starTimer += delta;
        if (starTimer > Constants.COIN_SPAWN_INTERVAL)
        {
            new Coin( Constants.GAME_WINDOW_WIDTH, 
                lastHome.getHeight() + lastHome.getY() + MathUtils.random(20,150), 
                mainStage );
            starTimer = 0;
        }

        for (BaseActor coinActor : BaseActor.getList(mainStage, Coin.class.getName() ) ) 
        {
            if (hero.overlaps(coinActor))
            {
                sparkle.play(audioVolume);
                coinActor.remove();
                Zombiejump.coinsCount++;
                coinLabel.setText(" x " + Zombiejump.coinsCount );
            }
        }

        for (BaseActor zombieActor : BaseActor.getList(mainStage, Zombie.class.getName())) {
            if ( shot.isVisible() && zombieActor.overlaps(shot)) {
                zombieActor.remove();
                shot.hide();;
                Zombiejump.zombieCount++;
                continue;
            }

            if (zombieActor.overlaps(hero)) {
                zombieHit(zombieActor);
            }

        }

        if (zombieSpawnTimer >= zombieSpawnInterval) {
            spawnZombie();
        }
        zombieSpawnTimer += delta;

        hero.setCanJump(false);
        for (BaseActor homeActor : BaseActor.getList(mainStage, Home.class.getName())) {

            offset = hero.preventOverlap(homeActor);
            if (offset != null) {
                if (Math.abs(offset.y) > Math.abs(offset.x)) {
                    if (hero.isJumping()) {
                        hero.setJumping(false);
                    }

                }
            }
            if ((hero.getX() + hero.getWidth()) > homeActor.getX()
                    && (hero.getX() + hero.getWidth() * 0.25) < (homeActor.getX() + homeActor.getWidth())) {
                hero.setCanJump(true);
            }
        }

        if (Constants.GAME_WINDOW_WIDTH - (lastHome.getX() + lastHome.getWidth()) > spawnWidthHome)
            spawnHome();

        zombieLabel.setText(" x " + Zombiejump.zombieCount);

        if ( shotTime < Constants.SHOT_RELOAD_INTERVAL ) shotTime += delta;

        if ( !gameOver ) {
            if (shotTime < Constants.SHOT_RELOAD_INTERVAL ) {
                nachladeLabel.setText(Zombiejump.myBundle.format("reloadIn", MathUtils.round( Constants.SHOT_RELOAD_INTERVAL - shotTime)) );
            } else {
                if ( ! reloadShot ) {
                    reloadShot = true;
                    nachladeLabel.setText(Zombiejump.myBundle.format("readyToShot"));
                    reload.play(audioVolume);
                }
            }
        } 

    }

    private void heroOutofWorld() {
            gameOver();
    }

    private void zombieHit(BaseActor zombie) {
        zombie.remove();
        Zombiejump.health--;
        healthLabel.setText(" x " + Zombiejump.health);
        shot.hide();
        hit.play(audioVolume);

        if ( Zombiejump.health <= 0 ) {
            gameOver();
        }
    }


    private void spawnZombie() {
        if (lastHome.isLong) {
            if (lastHome.getX() + lastHome.getWidth() > Constants.GAME_WINDOW_WIDTH) {

                new Zombie(
                    lastHome.getX() + lastHome.getWidth() - 64,
                    lastHome.getHeight() + lastHome.getY(),
                    mainStage);
                zombieSpawnTimer = 0;
                zombieSpawnInterval = MathUtils.random(3f) + 1f;
            }
        }
    }

    private void spawnHome() {
        // System.out.print( "Ende: " + ( lastHome.getX() + lastHome.getWidth() ) +
        // ", Breite: " + spawnWidthHome + ", Anfang: ");
        lastHome = new Home(lastHome.getX() + lastHome.getWidth() + spawnWidthHome, 0, mainStage);
        // System.out.println( lastHome.getX() );
        spawnWidthHome = MathUtils.random(100, 180);
    }

    private void gameOver() {
        gameOver = true;
        music.dispose();
        sky1.setSpeed(0);
        sky2.setSpeed(0);
        hero.remove();
        shot.remove();
        nachladeLabel.remove();
        BaseActor gameOverMessage = new BaseActor(0, 0, uiStage);
        gameOverMessage.loadTexture(Constants.TEXTURE_GAME_OVER);
        gameOverMessage.centerAtPosition(Constants.GAME_WINDOW_WIDTH / 2, 
                                        Constants.GAME_WINDOW_HEIGHT / 2);
        gameOverMessage.setOpacity(0);
        gameOverMessage.addAction(Actions.after(Actions.fadeIn(1)));
        // instrumental.stop();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        if (!gameOver) {
            jump.play(audioVolume);
            hero.jump();
        } else if (gameOver) {
            newGame();
        }

        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public void hide() {
        super.hide();

        music.dispose();
        jump.dispose();
        hit.dispose();
        shotgun.dispose();
        dryfire.dispose();
        reload.dispose();
        sparkle.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Keys.CONTROL_LEFT:
                if (!gameOver) doShoot();
                break;
            case Keys.SPACE:
                if (!gameOver) hero.jump();
                else newGame();
                break;
            default:
                break;
        }
        return super.keyDown(keycode);
    }

    private void newGame() {
        BaseGame.setActiveScreen(new GameOverScreen());
    }

    private void doShoot() {

        if ( shotTime < Constants.SHOT_RELOAD_INTERVAL || shot.isVisible() ) {
            if ( shotTime < Constants.SHOT_RELOAD_INTERVAL ) {
                dryfire.play(audioVolume);
            }
            return;
        }
        shotTime = 0;
        shot.setX(hero.getX() + hero.getWidth());
        shot.setY(hero.getY() + hero.getHeight() / 2 );
        shot.show();
        shotgun.play(audioVolume);
        reloadShot = false;
    }
    
}