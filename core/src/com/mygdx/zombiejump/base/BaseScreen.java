package com.mygdx.zombiejump.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.mygdx.zombiejump.MyGame;
import com.mygdx.zombiejump.utils.Constants;

public abstract class BaseScreen implements Screen, InputProcessor {

    protected MyGame game;

    protected Stage mainStage;
    protected Stage uiStage;
    protected Table uiTable;

    private Rectangle screenLeftSide;
    private Rectangle screenRightSide;

    public BaseScreen(MyGame game) {
        this.game = game;

        mainStage = new Stage(new ScalingViewport(Scaling.stretch, Constants.GAME_WINDOW_WIDTH, Constants.GAME_WINDOW_HEIGHT,
                new OrthographicCamera(Constants.GAME_WINDOW_WIDTH, Constants.GAME_WINDOW_HEIGHT)));
        uiStage = new Stage(new ScalingViewport(Scaling.stretch, Constants.GAME_WINDOW_WIDTH, Constants.GAME_WINDOW_HEIGHT,
                new OrthographicCamera(Constants.GAME_WINDOW_WIDTH, Constants.GAME_WINDOW_HEIGHT)));

        uiTable = new Table();
        uiTable.setFillParent(true);
        uiStage.addActor(uiTable);
        setUpTouchControlAreas();
        
        initialize();
    }

    public abstract void initialize();

    public abstract void update(float delta);

    // Gameloop:
    // (1) process input (discrete handled by listener; continuous in update)
    // (2) update game logic
    // (3) render the graphics
    @Override
    public void render(float delta) {
        uiStage.act(delta);
        mainStage.act(delta);

        update(delta);

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mainStage.draw();
        uiStage.draw();
    }

    // method required by screen interface
    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
    
        /**
     *  Called when this becomes the active screen in a Game.
     *  Set up InputMultiplexer here, in case screen is reactivated at a later time.
     */
    @Override
    public void show() {
        // Stage objects and screen class itself should by added 
        // to the game's InputMultiplexer when this screen is displayed
        InputMultiplexer im = (InputMultiplexer) Gdx.input.getInputProcessor();

        System.out.println("2." + im);

        im.addProcessor(this);
        im.addProcessor(uiStage);
        im.addProcessor(mainStage);
        
    }

    /**
     *  Called when this is no longer the active screen in a Game.
     *  Screen class and Stages no longer process input.
     *  Other InputProcessors must be removed manually.
     */
    @Override
    public void hide() {
        // when a new screen is set, this class and all stages
        //  should be removed from InputMultiplexer
        InputMultiplexer im = (InputMultiplexer) Gdx.input.getInputProcessor();
        im.removeProcessor(this);
        im.removeProcessor(uiStage);
        im.removeProcessor(mainStage);
    }
    
    @Override
    public void dispose() {
    }
    
    // methods required by InputProzessor interface

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    private void setUpTouchControlAreas() {
        screenLeftSide = new Rectangle(0, 0, mainStage.getCamera().viewportWidth / 2,
        mainStage.getCamera().viewportHeight);
        screenRightSide = new Rectangle(mainStage.getCamera().viewportWidth / 2, 0,
        mainStage.getCamera().viewportWidth / 2, mainStage.getCamera().viewportHeight);
    }

    public boolean rightSideTouched(float x, float y) {
        return screenRightSide.contains(x, y);
    }

    public boolean leftSideTouched(float x, float y) {
        return screenLeftSide.contains(x, y);
    }
}
