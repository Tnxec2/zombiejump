package com.mygdx.zombiejump.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Array;
import com.mygdx.zombiejump.actors.Ground;
import com.mygdx.zombiejump.actors.Hero;
import com.mygdx.zombiejump.actors.Home;
import com.mygdx.zombiejump.actors.Sky;
import com.mygdx.zombiejump.actors.Zombie;
import com.mygdx.zombiejump.base.BaseActor;
import com.mygdx.zombiejump.base.BaseGame;
import com.mygdx.zombiejump.base.BaseScreen;
import com.mygdx.zombiejump.base.BaseUI;

/**
 * LevelScreen
 */
public class LevelScreen extends BaseScreen {

    private Sky sky1, sky2;

    private Hero hero;

    private float zombieSpawnTimer;
    private float zombieSpawnTIme;

    private static final int heroX = 200;

    private Home lastHome;
    private int spawnWidthHome;

    private Label zombieLabel;

    private boolean gameOver;

    private Music music;
    private Sound jump, hit;
    private float audioVolume;

    @Override
    public void initialize() {

        sky1 = new Sky(0, 0, mainStage);
        sky2 = new Sky(sky1.getWidth(), 0, mainStage);

        lastHome = new Home(100, 0, mainStage, 0);
        hero = new Hero(heroX, lastHome.getHeight(), mainStage);

        spawnHome();
        spawnWidthHome = 200;

        zombieLabel = new Label("Zombies passt:", BaseUI.labelStyle);
        zombieLabel.setColor(Color.CYAN);

        BaseActor.setWorldBounds(800, 600);

        gameOver = false;

        music = Gdx.audio.newMusic(Gdx.files.internal("Shanghai_Action.mp3"));
        jump = Gdx.audio.newSound(Gdx.files.internal("jump.wav"));

        audioVolume = -1.00f;
        music.setLooping(true);
        music.setVolume(audioVolume);
        music.play();
    }

    @Override
    public void update(float delta) {

        if (gameOver)
            return;

        if (hero.isOutOfWorld() && !gameOver) {
            gameOver();
        }

        if (sky1.getX() + sky1.getWidth() <= 0)
            sky1.setX(sky2.getX() + sky2.getWidth());
        if (sky2.getX() + sky2.getWidth() <= 0)
            sky2.setX(sky1.getX() + sky1.getWidth());

        if (hero.getX() > heroX)
            hero.setX(heroX);

        for (BaseActor zombieActor : BaseActor.getList(mainStage, Zombie.class.getName())) {
            if (zombieActor.overlaps(hero)) {
                gameOver();
            }

            if (zombieActor.getX() + zombieActor.getWidth() <= 0)
                zombieActor.remove();
        }

        if (zombieSpawnTimer >= zombieSpawnTIme) {
            spawnZombie();
        }

        zombieSpawnTimer += delta;

        Vector2 offset;
        for (BaseActor homeActor : BaseActor.getList(mainStage, Home.class.getName())) {
            offset = hero.preventOverlap(homeActor);
            if (offset != null) {
                if (Math.abs(offset.y) > Math.abs(offset.x) && hero.isJumping()) {
                    hero.setJumping(false);
                    hero.setX(heroX);
                }
            }

            if (homeActor.getX() + homeActor.getWidth() <= 0) {
                homeActor.remove();
            }
        }

        if (800 - (lastHome.getX() + lastHome.getWidth()) > spawnWidthHome)
            spawnHome();

    }

    private void spawnZombie() {
        if (lastHome.isLong) {
            if (lastHome.getX() + lastHome.getWidth() > 800) {
                int rand = MathUtils.random(1);
                new Zombie(lastHome.getX() + lastHome.getWidth() - 64, lastHome.getHeight(), mainStage, rand);
                zombieSpawnTimer = 0;
                zombieSpawnTIme = MathUtils.random(3f) + 1f;
            }
        }
    }

    private void spawnHome() {
        System.out.print( "Ende: " + ( lastHome.getX() + lastHome.getWidth() ) + ", Breite: " + spawnWidthHome + ", Anfang: ");
        lastHome = new Home(lastHome.getX() + lastHome.getWidth() + spawnWidthHome, 0, mainStage);
        System.out.println( lastHome.getX() );
        spawnWidthHome = MathUtils.random(50, 100);
    }

    private void gameOver() {
        gameOver = true;
        music.dispose();
        sky1.setSpeed(0);
        sky2.setSpeed(0);
        hero.remove();
        BaseActor gameOverMessage = new BaseActor(0, 0, uiStage);
        gameOverMessage.loadTexture("gameover.png");
        gameOverMessage.centerAtPosition(400, 300);
        gameOverMessage.setOpacity(0);
        gameOverMessage.addAction(Actions.after(Actions.fadeIn(1)));
        // instrumental.stop();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        if (!gameOver && !hero.isJumping()) {
            jump.play();
            hero.jump();
        } else if ( gameOver ) {
            BaseGame.setActiveScreen(new LevelScreen());
        }

        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public void hide() {
        super.hide();
        music.dispose();
    }


    
}