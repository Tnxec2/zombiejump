package com.mygdx.zombiejump.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.zombiejump.Zombiejump;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "ZJ";
		config.width = 800;
		config.height = 600;
		new LwjglApplication(new Zombiejump(), config);
	}
}
