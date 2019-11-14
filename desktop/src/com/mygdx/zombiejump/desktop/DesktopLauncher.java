package com.mygdx.zombiejump.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.zombiejump.MyGame;
import com.mygdx.zombiejump.utils.Constants;

public class DesktopLauncher
{
	public static void main (String[] arg)
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = Constants.GAME_TITLE;
		config.width = Constants.GAME_WINDOW_WIDTH;
		config.height = Constants.GAME_WINDOW_HEIGHT;
		new LwjglApplication(new MyGame(), config);
	}
}
