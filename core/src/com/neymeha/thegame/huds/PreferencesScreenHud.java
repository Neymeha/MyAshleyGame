package com.neymeha.thegame.huds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.neymeha.thegame.MyGame;
import com.neymeha.thegame.utils.GameConfig;


// класс по тиму MenuScreenHud с некоторыми дополнительными UI элементами
public class PreferencesScreenHud {
    private MyGame parent;
    private Stage stage;

    public PreferencesScreenHud(MyGame parent) {
        this.parent = parent;
        /*
        C фит вьюпорт проблема с заполняемостью заднего фона
        со скрин вью портом обьекты на стейдже не скейляться
        оставил пока fit а там посмотрим
        */
        Viewport gameViewport = new FitViewport(GameConfig.GAME_WIDTH, GameConfig.GAME_HEIGHT, new OrthographicCamera()); // можно поменять на стретч тогда кнопки будут растягивать с окном а не менять размер

        stage = new Stage(gameViewport, parent.core.batch);

        Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        setupUI(skin);

        Gdx.input.setInputProcessor(stage);
    }

    private void setupUI(Skin skin){
        /*
        Инициализация обьектов:
            таблица
            подвижный слайдер для настройки громскости музыки
            повторяем для звука
            Чек бокс для музыки
            Кнопка возврата на основное меню
            5 лейблов для всех нужд
        */

        Table table = new Table();

        final Slider volumeMusicSlider = new Slider( 0f, 1f, 0.1f,false, skin );
        final Slider volumeSoundSlider = new Slider( 0f, 1f, 0.1f,false, skin );
        final CheckBox musicCheckbox = new CheckBox(null, skin); // инициализируем
        final CheckBox soundCheckbox = new CheckBox(null, skin); // инициализируем
        final TextButton backButton = new TextButton("Back", skin, "small"); // small - это дополнительный стиль на кнопке

        Label titleLabel = new Label( "Preferences", skin );
        Label volumeMusicLabel = new Label( "Music Volume", skin );
        Label volumeSoundLabel = new Label( "Sound Volume", skin );
        Label musicOnOffLabel = new Label( "Music On/Off", skin );
        Label soundOnOffLabel = new Label( "Sound On/Off", skin );

        /*
        Настраиваем наши элементы
        */
        table.setFillParent(true);
//        table.setDebug(true); // убираем за ненадобностью

        volumeMusicSlider.setValue( GameConfig.getMusicVolume() ); // задали значение по дефолту
        volumeSoundSlider.setValue( GameConfig.getSoundVolume() );
        musicCheckbox.setChecked( GameConfig.isMusicEnabled() ); // загружаем значения
        soundCheckbox.setChecked( GameConfig.isSoundEnabled() ); // загружаем значения

        /*
        Добавляем слушателей для наших актеров
        */
        volumeMusicSlider.addListener( new EventListener() { // добавляем слушателя что бы при изменении показателя происходило действие
            @Override
            public boolean handle(Event event) {
                GameConfig.setMusicVolume( volumeMusicSlider.getValue() ); // а именно записываем громкость музыки
                return false;
            }
        });

        volumeSoundSlider.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                GameConfig.setSoundVolume( volumeSoundSlider.getValue() );
                return false;
            }
        });

        musicCheckbox.addListener( new EventListener() { // добавляем слушателя
            @Override
            public boolean handle(Event event) {
                boolean enabled = musicCheckbox.isChecked(); // записываем значение чек бокса (стоит ли галочка)
                GameConfig.setMusicEnabled( enabled ); // и сохраняем его в настройки
                return false;
            }
        });

        soundCheckbox.addListener( new EventListener() { // добавляем слушателя
            @Override
            public boolean handle(Event event) {
                boolean enabled = soundCheckbox.isChecked(); // записываем значение чек бокса (стоит ли галочка)
                GameConfig.setSoundEnabled( enabled ); // и сохраняем его в настройки
                return false;
            }
        });

        backButton.addListener(new ChangeListener() { // добавили слушателя
            @Override
            public void changed(ChangeEvent event, Actor actor) { // при изменении состояния кнопки выполняем смену экрана
                parent.changeScreen(MyGame.MENU);
            }
        });

        TextureAtlas atlas = parent.core.assetManager.getLoadingAtlas();
        TextureAtlas.AtlasRegion background = atlas.findRegion("flamebackground"); // фон
        table.setBackground(new TiledDrawable(background)); // установили фон для таблицы
        /*
        Добавляем все элементы в таблицу
        */
        table.add(titleLabel).colspan(2); // colspan(2) центрируем надпись по середине между двух столбцов
        table.row().pad(10,0,0,10);
        table.add(volumeMusicLabel).left();
        table.add(volumeMusicSlider);
        table.row().pad(10,0,0,10);
        table.add(musicOnOffLabel).left();
        table.add(musicCheckbox);
        table.row().pad(10,0,0,10);
        table.add(volumeSoundLabel).left();
        table.add(volumeSoundSlider);
        table.row().pad(10,0,0,10);
        table.add(soundOnOffLabel).left();
        table.add(soundCheckbox);
        table.row().pad(10,0,0,10);
        table.add(backButton).colspan(2);

        stage.addActor(table); // и наконец добавили все в стейдж
    }

    public void actAndDraw(float delta){
        this.stage.getViewport().apply(true);
        this.stage.act(delta);
        this.stage.draw();
    }

    public void dispose(){
        stage.dispose();
    }

    public void updateViewport(int width, int height){
        this.stage.getViewport().update(width,height);
    }
    public void setInputProcessor(){
        Gdx.input.setInputProcessor(stage);
    }
}
