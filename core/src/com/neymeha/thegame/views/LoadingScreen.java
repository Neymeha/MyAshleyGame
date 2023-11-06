package com.neymeha.thegame.views;

import com.badlogic.gdx.Screen;
import com.neymeha.thegame.MyGame;
import com.neymeha.thegame.entitys.BackgroundEntity;

public class LoadingScreen implements Screen {

    private MyGame parent;

    public LoadingScreen(MyGame parent) {
        this.parent = parent;
    }

    @Override
    public void show() {
        new BackgroundEntity().createEntity();
    }

    /*
    Поскольку рендер метод сработает после шоу и конструктора, значит после инициализации всех наших ресурсов нужных
    мы и переключаемся на следующий экран
    */
    @Override
    public void render(float delta) {
        parent.changeScreen(MyGame.MENU);
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
