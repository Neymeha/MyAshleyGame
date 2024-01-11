package com.neymeha.thegame.views;

import com.badlogic.ashley.core.Entity;
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
import com.neymeha.thegame.utils.DFUtils;
import com.neymeha.thegame.utils.GameConfig;

public class GameScreen implements Screen {
    private MyGame parent;
    public Sound ping, boing;
    public static final int BOING_SOUND = 0; // new
    public static final int PING_SOUND = 1; //new

    public GameScreen(MyGame parent) {
        this.parent = parent;
        // записываем в переменную плеера что бы передать его в запуск системы а именно в систему движения воды
        // не совсем нравится мне решение но пока пусть будет так
        Entity entity = parent.core.lvlFactory.createPlayer(parent.core.assetManager.getGameAtlas().findRegion("player"),parent.core.camera);
//        parent.core.lvlFactory.createFloor(parent.core.assetManager.getGameAtlas().findRegion("player")); // устарело
        /*
        Ниже создаем обьекты игрового мира пол, водяной уровень, стены, поправка на ППМ обязательно ведь все обьекты физические
        создаем с помощью ютил класса не подгружая и не создавая текстуры.
        */
        parent.core.lvlFactory.customCreateFloor(40*GameConfig.PPM,1*GameConfig.PPM,"11331180");

        parent.core.lvlFactory.customCreateWaterFloor(40*GameConfig.PPM,10*GameConfig.PPM,"11113380");
        parent.core.lvlFactory.customCreateWall(1*GameConfig.PPM,60*GameConfig.PPM,"222222FF");

        parent.core.initEngineSystems(entity);
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
