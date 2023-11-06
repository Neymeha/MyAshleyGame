package com.neymeha.thegame.views;

import com.badlogic.gdx.Screen;
import com.neymeha.thegame.MyGame;

public class EndScreen implements Screen{
    /*
    Нам нужна ссылка в каждом из Screen на наш основной класс игры, для того что бы они могли общаться,
    для смены экрана
    */
    private MyGame parent;

    public EndScreen(MyGame parent) {
        this.parent = parent;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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
