package com.neymeha.thegame.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
    private GameCore core;
    /*
        временный код для текстуры игрока, поскольку мы используем текстурный атлас то сначала нам нужна переменная
        текстурного атласа, в которой мы далее будем искать регион текстур по названию
    */
    public TextureAtlas gameAtlas; // текстурный атлас
    public TextureAtlas.AtlasRegion playerTex; // регион текстур по атласу

    public GameScreen(MyGame parent, GameCore core) {
        this.parent = parent;
        this.core = core;

        this.core.assetManager.queueAddImages();
        this.core.assetManager.manager.finishLoading();
        gameAtlas = this.core.assetManager.manager.get(core.assetManager.gameImages); // ищем текстурный атлас
        playerTex = gameAtlas.findRegion("player"); // ищем регион текстур в атласе
    }

    @Override
    public void show() {
        core.setInputController(); // добавили контроллер кнопок в инпут для обработки
        parent.getBatch().setProjectionMatrix(core.camera.combined); // говорим использовать матрицу из нашего ядра где все физ тела
    }

    @Override
    public void render(float delta) {
        core.logicStep(delta);
        ScreenUtils.clear(0, 0, 0, 1);
        core.debugRender();
        /*
        Говорим отрисовать нашу текстуру игрока задавая текстуру, координаты отрисовки, а так же высоту и ширину текстуры
        И так:
        1)физ тела у нас отрисовываются от координат которые берутся за центр, а спрайты от угла левого нижнего получется
        от сюда не соответствие - поэтому мы отняли по 1 от координат что бы текстура влезла в физ тело
        2)в шоу метод я добавил изменение матрицы - сделано это потому что в данный момент спрайт батч отрисовывал наш мир
        согласно пикселям, а это считай 600х800, а физ мир у нас 24х32 метра, для соответствия одного к другому нужно было
        подогнать пиксельный мир под метровый
        3)из за того что я написал выше теперь наш спрайт рисуется супер огромным, и мы уменьшаем его размер в агрументах
        отрисовки, а то его даже на экране не видно такой он огромный
        */
        parent.getBatch().begin();
        parent.getBatch().draw(playerTex, core.player.getPosition().x-1, core.player.getPosition().y-1,2.5f,2.5f);
        parent.getBatch().end();
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
        playerTex.getTexture().dispose();
    }
}
