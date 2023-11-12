package com.neymeha.thegame.huds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.neymeha.thegame.MyGame;
import com.neymeha.thegame.utils.GameConfig;

public class MenuScreenHud {
    private MyGame parent; // для переключения экранов
    private Stage stage; // для размещения актеров


    public MenuScreenHud(MyGame parent) {
        this.parent = parent; // установили зависимость
        /*
        Инициализируем вьюпорт
        */
        Viewport gameViewport = new FitViewport(GameConfig.GAME_WIDTH, GameConfig.GAME_HEIGHT, new OrthographicCamera()); // можно поменять на стретч тогда кнопки будут растягивать с окном а не менять размер

        /*
        Инициализируем stage, добавлем в него тут же созданный вьюпорт и основной батч
        */
        stage = new Stage(gameViewport, parent.getBatch());

        /*
        Добавляем skin для нашего проекта в частности для кнопок
        */
        Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        /*
        Вся остальная доп настройка наших актеров для нашего стейдж
        */
        setupUI(skin);
    }

    private void setupUI(Skin skin) {
        Table table = new Table(); // создаем нашу таблицу для актеров
        /*
        Инициализируем кнопки с помощь скина добавляя им текст
        */
        ImageTextButton gameBtn = new ImageTextButton("Game", skin);
        ImageTextButton preferencesBtn = new ImageTextButton("Preferences", skin);
        ImageTextButton progressBtn = new ImageTextButton("Progress", skin);
        ImageTextButton exitBtn = new ImageTextButton("Exit", skin);

        /*
        setFillParent(true) - используется для установки размеров и позиции таблицы
        таким образом, чтобы она заполнила всю доступную область родительского
        контейнера. Добавляем дебаг временно что бы наблюдать границы кнопок и нашей таблицы.
        */
        table.setFillParent(true);
        table.setDebug(true);

        // добавляем слушателей каждой кнопке на случай изменения, с последующей сменой экрана
        gameBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(MyGame.GAME);
            }
        });
        progressBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });
        preferencesBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(MyGame.PREFERENCES);
            }
        });
        exitBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        /*
        Добавляем в таблицу актеров и дорбавляем заполнение по Х что бы все кнопки были одинакового размера,
        роу - переход к следующей ячейке таблицы вниз, так же добавляем отступы
        */
        table.add(gameBtn).fillX();
        table.row().pad(10, 0, 10, 0);
        table.add(preferencesBtn).fillX();
        table.row();
        table.add(progressBtn).fillX();
        table.row().pad(10, 0, 0, 0);
        table.add(exitBtn).fillX(); // заполнили кнопку по горизонтали она единственная выделяется

        stage.addActor(table); // добавляем таблицу с элементами на Stage для дальнейшей обработки
    }

    public void drawAndAct(float delta){
        /*
        ProjectionMatrix - это матрица проекции, которая определяет, как объекты в вашей игре будут отображаться
        на экране. В libgdx она используется для определения перспективы и преобразования координат объектов в
        мировых координатах в координаты экрана.
        */
        // Применение настроек вьюпорта к текущему состоянию экрана
        this.stage.getViewport().apply(true); // поскольку мы можем использовать не один вьюпорт нам нужно применить вьюпорт для стейдж перед выполнением команд
        this.stage.draw(); // отрисовка актеров
        this.stage.act(delta); // и обработка инпута
    }

    public void dispose(){
        stage.dispose(); // освобождаем из памяти наш стейдж
    }

    public void updateViewport(int width, int height){
        this.stage.getViewport().update(width,height); // обновляем наш вьюпорт в случае изменений
    }

    public void setInputProcessor(){
        Gdx.input.setInputProcessor(stage);
    }
}
