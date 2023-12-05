package com.neymeha.thegame.views;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.neymeha.thegame.MyGame;
import com.neymeha.thegame.huds.PreferencesScreenHud;
import com.neymeha.thegame.utils.GameConfig;

public class PreferencesScreen implements Screen {
    private MyGame parent;
    private PreferencesScreenHud hud;
    private Viewport gameViewport;
    private OrthographicCamera mainCamera;

    public PreferencesScreen(MyGame parent) {
        this.parent = parent;

        hud = new PreferencesScreenHud(parent);
        mainCamera = new OrthographicCamera();

        mainCamera.setToOrtho(false, GameConfig.GAME_WIDTH, GameConfig.GAME_HEIGHT);
        mainCamera.position.set(GameConfig.GAME_WIDTH/2f, GameConfig.GAME_HEIGHT/2f, 0);

        gameViewport = new FitViewport(GameConfig.GAME_WIDTH, GameConfig.GAME_HEIGHT, mainCamera);
    }

    @Override
    public void show() {
        hud.setInputProcessor();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        hud.actAndDraw(delta);
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
