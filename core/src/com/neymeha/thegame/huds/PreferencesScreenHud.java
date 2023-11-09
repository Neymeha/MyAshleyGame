package com.neymeha.thegame.huds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.neymeha.thegame.MyGame;
import com.neymeha.thegame.utils.GameConfig;

public class PreferencesScreenHud {
    private MyGame parent;
    private Stage stage;
    private Viewport gameViewport;
    private Table table;
    private Skin skin;

    public PreferencesScreenHud(MyGame parent) {
        this.parent = parent;

        gameViewport = new FitViewport(GameConfig.GAME_WIDTH, GameConfig.GAME_HEIGHT, new OrthographicCamera()); // можно поменять на стретч тогда кнопки будут растягивать с окном а не менять размер

        stage = new Stage(gameViewport, parent.getBatch());

        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        table = new Table();

        Gdx.input.setInputProcessor(stage); // установили наш процессор обработки ввода пользователя

    }
}
