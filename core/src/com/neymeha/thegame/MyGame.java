package com.neymeha.thegame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.neymeha.thegame.views.*;

/*
	Game class класс нужный нам для переключения между различными Screen, он схож с функционалом с дефолтным
	ApplicationListener, но делегирует функционал текущему установленному Screen
	Screen сами по себе не удаляются так что на них нужно вызывать dispose при замене!

	это так же наш основной класс игры
*/
public class MyGame extends Game {
	/*
	Далее переменные наших экранов, мы их будем хранить в основном классе игры
	*/
	private LoadingScreen loadingScreen;
	private PreferencesScreen preferencesScreen;
	private MenuScreen menuScreen;
	private MainScreen mainScreen;
	private EndScreen endScreen;

	/*
	Единственный на все экраны батч который мы будем использовать
	*/
	private SpriteBatch batch;

	/*
	Далее статичные константы для метода смены экрана
	*/
	public final static int MENU = 0;
	public final static int PREFERENCES = 1;
	public final static int MAIN = 2;
	public final static int ENDGAME = 3;

	/*
	Единственный обязательно переопределяемый метод при наследовании класса Game, в нем мы инициируем
	загрузочный экран
	*/
	@Override
	public void create() {
		batch = new SpriteBatch();
		loadingScreen = new LoadingScreen(this);
		setScreen(loadingScreen);
	}

	/*
	Метод для смены экрана в котором мы при надобности создаем экран если он у нас не был создан ранее и
	храним его в созданных выше переменных.
	*/
	public void changeScreen(int screen) {
		switch (screen) {
			case MENU:
				if (menuScreen == null) menuScreen = new MenuScreen(this);
				this.setScreen(menuScreen);
				break;
			case PREFERENCES:
				if (preferencesScreen == null) preferencesScreen = new PreferencesScreen(this);
				this.setScreen(preferencesScreen);
				break;
			case MAIN:
				if (mainScreen == null) mainScreen = new MainScreen(this);
				this.setScreen(mainScreen);
				break;
			case ENDGAME:
				if (endScreen == null) endScreen = new EndScreen(this);
				this.setScreen(endScreen);
				break;
		}
	}

	/*
	Геттер для нашего батча что бы его переиспользовать
	*/
	public SpriteBatch getBatch() {
		return batch;
	}

	/*
	Переопределили dispose что бы освобождать наш батч из памяти.
	*/
	@Override
	public void dispose() {
		super.dispose();
		batch.dispose();
	}
}