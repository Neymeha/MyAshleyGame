package com.neymeha.thegame.huds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.neymeha.thegame.MyGame;
import com.neymeha.thegame.utils.GameConfig;

public class MenuScreenHud {
    /*
    Зависимость от основного класса игры так как нам понадобиться взаимодействие с ним для переключения экрана
    при изменении состояния кнопки
    */
    private MyGame parent;
    /*
    Нам нужен так же Stage как наш процессор ввода что бы в него попадали нажатия на кнопку от инпута с дальнейшей
    обработкой
    */
    private Stage stage;
    /*
    Нам так же нужен вьюпорт что бы адекватно размещать все наши обьекты на экране из нашего Stage
    */
    private Viewport gameViewport;
    /*
    Наши кнопки с дополнительным функционалом анимаций для нажатия и др
    */
    private ImageButton startGameBtn, infoGameBtn, progressGameBtn, quitGameBtn;
    /*

    */
    private Table table;

    public MenuScreenHud(MyGame parent) {
        this.parent = parent; // установили зависимость
        /*
        Инициализируем классы
        */
        gameViewport = new FitViewport(GameConfig.GAME_WIDTH, GameConfig.GAME_HEIGHT, new OrthographicCamera()); // можно поменять на стретч тогда кнопки будут растягивать с окном а не менять размер
        stage = new Stage(gameViewport, parent.getBatch());

        Gdx.input.setInputProcessor(stage); // установили наш процессор обработки ввода пользователя
        /*
        Вся остальная доп настройка наших актеров для нашего стейдж
        */
        setupTableAndButtons();
    }

    private void setupTableAndButtons() {
        // добавляем image на кнопку без нажатия и с нажатием, при этом функционал смены текстуры после нажатия уже вшит в функционал ImageButton
        startGameBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("Buttons/Menu/Play_Unpressed.png"))),
                new SpriteDrawable(new Sprite(new Texture("Buttons/Menu/Play_Pressed.png"))));
        progressGameBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("Buttons/Menu/Stats_Unpressed.png"))),
                new SpriteDrawable(new Sprite(new Texture("Buttons/Menu/Stats_Pressed.png"))));
        infoGameBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("Buttons/Menu/Info_Unpressed.png"))),
                new SpriteDrawable(new Sprite(new Texture("Buttons/Menu/Info_Pressed.png"))));
        quitGameBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("Buttons/Menu/Cross_Unpressed.png"))),
                new SpriteDrawable(new Sprite(new Texture("Buttons/Menu/Cross_Pressed.png"))));

        setupActionsForButtons(); // добавляем действия на нажатие кнопки

        /*
        setFillParent(true) - используется для установки размеров и позиции таблицы
        таким образом, чтобы она заполнила всю доступную область родительского
        контейнера. Добавляем дебаг временно что бы наблюдать границы кнопок и нашей таблицы.
        */
        table = new Table();
        table.setFillParent(true);
        table.setDebug(true);

        /*
        Изменяем размер кнопок ибо они гигансткие, так же добавляем небольшие отступы, так же добавляем их в наш table
        что бы потом они так же попали в наш stage
        */
        table.add(startGameBtn).size(GameConfig.GAME_WIDTH/2f, GameConfig.GAME_HEIGHT/4f-GameConfig.GAME_HEIGHT/10f);
        table.row().pad(10, 0, 10, 0);
        table.add(infoGameBtn).size(GameConfig.GAME_WIDTH/2f, GameConfig.GAME_HEIGHT/4f-GameConfig.GAME_HEIGHT/10f);
        table.row();
        table.add(progressGameBtn).size(GameConfig.GAME_WIDTH/2f, GameConfig.GAME_HEIGHT/4f-GameConfig.GAME_HEIGHT/10f);
        table.row().pad(10, 0, 0, 0);
        table.add(quitGameBtn).size(GameConfig.GAME_WIDTH/2f, GameConfig.GAME_HEIGHT/4f-GameConfig.GAME_HEIGHT/10f);

        stage.addActor(table); // добавляем таблицу с элементами на Stage для дальнейшей обработки
    }

    private void setupActionsForButtons() {
        // добавляем слушателей каждой кнопке на случай изменения, с последующей сменой экрана
        startGameBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
//                parent.changeScreen(MyGame.MAIN);
            }
        });
        progressGameBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });
        infoGameBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });
        quitGameBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
    }

    public void drawAndAct(float delta){
        /*
        ProjectionMatrix - это матрица проекции, которая определяет, как объекты в вашей игре будут отображаться
        на экране. В libgdx она используется для определения перспективы и преобразования координат объектов в
        мировых координатах в координаты экрана.
        */
        gameViewport.apply(true); // вместо строки ниже, и все заработало корректно
//        parent.getBatch().setProjectionMatrix(this.getStage().getCamera().combined); // пришлось убрать эту строку, из за нее кнопки растягивались при изменении экрана а этого быть не должно было
        this.getStage().draw(); // отрисовка актеров
        this.getStage().act(delta); // и обработка инпута
    }

    /*
    Геттер для нашего Стейдж так как нам нужно будет его отрисовывать на нужном нам экране.
    */
    public Stage getStage() {
        return stage;
    }
}
