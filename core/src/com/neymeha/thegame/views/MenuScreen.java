package com.neymeha.thegame.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.neymeha.thegame.GameCore;
import com.neymeha.thegame.MyGame;
import com.neymeha.thegame.huds.MenuScreenHud;
import com.neymeha.thegame.utils.GameConfig;
import com.neymeha.thegame.utils.MyAssetManager;

public class MenuScreen implements Screen {

    private MyGame parent; // основной класс игры из которого мы будем брать batch
    private MenuScreenHud hud; // наш UI класс содержащий Stage и актеров
    /*
    Viewport (видовое окно) в libgdx определяет, как будет масштабироваться и отображаться игровой мир на экране.
    Он определяет размеры и пропорции области экрана, в которой будет отображаться игровой мир.
    Viewport может быть настроен на сохранение соотношения сторон, чтобы избежать искажений при масштабировании.
    Он также может быть настроен на поддержку разных разрешений экрана и устройств.
    */
    private Viewport gameViewport; // Viewport для нашего Screen
    /*
    Camera (камера) в libgdx представляет собой объект, который определяет, какая часть игрового мира будет
    отображаться на экране. Она определяет положение и ориентацию виртуальной камеры в игровом мире. Камера может быть
    настроена на отображение только определенной области мира или на отображение всего мира. Камера также может
    быть перемещена и повернута для создания эффектов движения и визуальных эффектов.
    */
    private OrthographicCamera mainCamera; // камера для нашего Screen
    /*
    OrthographicCamera (ортографическая камера) является одним из типов камер, доступных в libgdx.
    Она представляет собой камеру с ортографической проекцией, что означает, что все объекты на экране отображаются
    без перспективы. Это полезно для создания 2D игр или интерфейсов, где не требуется эффект глубины.
    OrthographicCamera позволяет настроить размеры и положение области мира, которая будет отображаться на экране.
    */

    public MenuScreen(MyGame parent, GameCore core) {
        this.parent = parent; // инициализировали зависимость
        /*
        Далее инициализация наших обьявленный переменных выше
        */
        hud = new MenuScreenHud(parent, core);
        mainCamera = new OrthographicCamera();

        mainCamera.setToOrtho(false, GameConfig.GAME_WIDTH, GameConfig.GAME_HEIGHT); // задаем размер камеры
        mainCamera.position.set(GameConfig.GAME_WIDTH/2f, GameConfig.GAME_HEIGHT/2f, 0); // центруем камеру

        gameViewport = new FitViewport(GameConfig.GAME_WIDTH, GameConfig.GAME_HEIGHT, mainCamera);


    }

    @Override
    public void show() {
        /*
        Поскольку мы храним наши скрины целиком, при переключении экрана нам нужно менять инпут процессор
        */
        hud.setInputProcessor();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1); // отчищаем экран в черный цвет

        hud.drawAndAct(delta); // говорим Stage отрисовать и обрабатывать актеров внутри
    }

    @Override
    public void resize(int width, int height) {
        gameViewport.update(width, height);
        hud.updateViewport(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        hud.dispose();
    }
}
