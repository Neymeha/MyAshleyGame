package com.neymeha.thegame.views;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.neymeha.thegame.MyGame;
import com.neymeha.thegame.huds.MenuScreenHud;
import com.neymeha.thegame.huds.PreferencesScreenHud;

public class PreferencesScreen implements Screen {
    private MyGame parent;
    private PreferencesScreenHud hud;
    private Viewport gameViewport;
    private OrthographicCamera mainCamera;

    public PreferencesScreen(MyGame parent) {
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
