package com.neymeha.thegame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.neymeha.thegame.utils.MyAssetManager;
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
		UPD так в гайде сделано, но я сомневаюсь что есть смысл хранить наши скрины по ссылкам, пусть лучше они уходят
		в сбор мусора как по мне. Но это не точно, наверное зависит от используемой памяти
	*/
	private LoadingScreen loadingScreen;
	private PreferencesScreen preferencesScreen;
	private MenuScreen menuScreen;
	private GameScreen gameScreen;
	private EndScreen endScreen;

	public GameCore core;

	/*
	Далее статичные константы для метода смены экрана
	*/
	public final static int MENU = 0;
	public final static int PREFERENCES = 1;
	public final static int GAME = 2;
	public final static int ENDGAME = 3;

	public Music playingSong;

	/*
	Единственный обязательно переопределяемый метод при наследовании класса Game, в нем мы инициируем
	загрузочный экран
	*/
	@Override
	public void create() {
		/*
		сначала инициализируем ядро
		затем лоадинг скрин так ему понадобится наше ядро
		и переключаемся на лоадинг скрин в котором пойдет загрузка наших ассетов
		*/

		core = new GameCore();
		loadingScreen = new LoadingScreen(this);
		setScreen(loadingScreen);
		playingSong = core.assetManager.getGameMusic();
		// закольцовываем музыку и запускаем
		playingSong.setLooping(true);
		playingSong.play();
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
			case GAME:
				if (gameScreen == null) gameScreen = new GameScreen(this);
				this.setScreen(gameScreen);
				break;
			case ENDGAME:
				if (endScreen == null) endScreen = new EndScreen(this);
				this.setScreen(endScreen);
				break;
		}
	}

	/*
	Переопределили dispose что бы освобождать наш батч из памяти.
	*/
	@Override
	public void dispose() {
		super.dispose();
		playingSong.dispose();
		core.dispose();
	}
}