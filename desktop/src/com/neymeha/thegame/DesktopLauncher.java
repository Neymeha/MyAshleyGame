package com.neymeha.thegame;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.neymeha.thegame.utils.GameConfig;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(GameConfig.GAME_WIDTH, GameConfig.GAME_HEIGHT);
		config.setForegroundFPS(60);
		config.setTitle("MyGame");
		new Lwjgl3Application(new MyGame(), config);
	}
}
