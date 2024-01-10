package com.neymeha.thegame.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.neymeha.thegame.GameCore;
import com.neymeha.thegame.MyGame;
import com.neymeha.thegame.utils.GameConfig;

public class GameScreen implements Screen {
    private MyGame parent;
    public Sound ping, boing;
    public static final int BOING_SOUND = 0; // new
    public static final int PING_SOUND = 1; //new

    public GameScreen(MyGame parent) {
        this.parent = parent;
        parent.core.initEngineSystems();
        parent.core.lvlFactory.createPlayer(parent.core.assetManager.getGameAtlas().findRegion("player"),parent.core.camera);
        parent.core.lvlFactory.createFloor(parent.core.assetManager.getGameAtlas().findRegion("player"));
    }

    @Override
    public void show() {
        /// !!! я бы возможно перенес это куда то но пока хз
        parent.core.setInputController(); // добавили контроллер кнопок в инпут для обработки
        parent.core.batch.setProjectionMatrix(parent.core.camera.combined); // говорим использовать матрицу из нашего ядра где все физ тела
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1); // отчистили экран
        parent.core.engine.update(delta);
    }

    @Override
    public void resize(int width, int height) {
        parent.core.gameViewport.update(width,height);
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

    public void playSound(int sound){
        switch(sound){
            case BOING_SOUND:
                boing.play();
                break;
            case PING_SOUND:
                ping.play();
                break;
        }
    }
}
