package com.neymeha.thegame.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.neymeha.thegame.GameCore;
import com.neymeha.thegame.MyGame;

public class GameScreen implements Screen {
    private MyGame parent;
    private GameCore core;

    public GameScreen(MyGame parent) {
        this.parent = parent;
        core = new GameCore();
    }

    @Override
    public void show() {
        core.setInputController(); // добавили контроллер кнопок в инпут для обработки
    }

    @Override
    public void render(float delta) {
        core.logicStep(delta);
        ScreenUtils.clear(0, 0, 0, 1);
        core.debugRender();
    }

    @Override
    public void resize(int width, int height) {

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

    }
}
