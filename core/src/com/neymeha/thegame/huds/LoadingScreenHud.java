package com.neymeha.thegame.huds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.neymeha.thegame.MyGame;
import com.neymeha.thegame.huds.actors.LoadingBarPart;
import com.neymeha.thegame.utils.GameConfig;

/*
 hud класс содержащий стейдж и инициализацию его и всего что с ним связано
*/
public class LoadingScreenHud {
    private Stage stage; // для размещения актеров
    private MyGame parent; // для связи с кором а именно ассет менеджером

    public LoadingScreenHud(MyGame parent) {
        this.parent = parent; // зависимость от основного класса игры
        /*
        C фит вьюпорт проблема с заполняемостью заднего фона
        со скрин вью портом обьекты на стейдже не скейляться
        оставил пока fit а там посмотрим
        */
        Viewport gameViewport = new FitViewport(GameConfig.GAME_WIDTH, GameConfig.GAME_HEIGHT, new OrthographicCamera()); // можно поменять на стретч тогда кнопки будут растягивать с окном а не менять размер

        stage = new Stage(gameViewport, parent.getBatch()); // инициализируем стейдж

        setupUI(); // настраиваем весь интерфейс

        Gdx.input.setInputProcessor(stage); // устанавливаем импут процессор
    }

    public void setupUI(){
        // ставим в очередь на загрузку ресурсы и загружаем
        parent.core.assetManager.queueAddLoadingImages();
        parent.core.assetManager.manager.finishLoading();

        /*
        Достаем все нужные нам текстуры и анимации для дальнейшей работы с ними
        */
        TextureAtlas atlas = parent.core.assetManager.manager.get(parent.core.assetManager.loadingImages); // достали весь атлас загрузочный
        TextureAtlas.AtlasRegion title = atlas.findRegion("staying-alight-logo"); // доставли основной логотип с названием
        TextureAtlas.AtlasRegion dash = atlas.findRegion("loading-dash"); // достали элемент лоадинг бара
        Animation flameAnimation = new Animation(0.07f, atlas.findRegions("flames/flames"), Animation.PlayMode.LOOP); // достали анимацию целиком
        TextureAtlas.AtlasRegion background = atlas.findRegion("flamebackground"); // фон
        TextureAtlas.AtlasRegion copyright = atlas.findRegion("copyright"); // права

        Image titleImage = new Image(title); // создаем актера Image с нашим логотипом титульным

        /*
        Создаем основную таблицу и вкладываем в нее вторую таблицу с множеством элементов
        Таким образом, создание таблицы внутри другой таблицы позволяет организовать компоненты интерфейса в удобном
        формате и управлять их расположением. Это может быть полезно, например, для создания сложных макетов с
        различными элементами интерфейса, такими как заголовки, кнопки, изображения и т.д.
        */
        Table table = new Table();
        table.setName("mainTable");
        table.setFillParent(true);
        table.setDebug(false);

        Table loadingTable = new Table(); // вложенная
        loadingTable.setName("loadingTable");

        /*
        Повторяющийся код создания 10 невидимых на старте элементов лоадинг бара в фор луп переложил
        */
        for (int i=1; i<11; i++){
            LoadingBarPart part = new LoadingBarPart(dash,flameAnimation);
            part.setName(""+i);
            loadingTable.add(part);
        }
//        loadingTable.add(new LoadingBarPart(dash,flameAnimation)); // наши элементы лоадинг бара которые по дефолту
//        loadingTable.add(new LoadingBarPart(dash,flameAnimation)); // невидимые и которые буду становиться видимыми
//        loadingTable.add(new LoadingBarPart(dash,flameAnimation)); // по прогрессу таблицы
//        loadingTable.add(new LoadingBarPart(dash,flameAnimation));
//        loadingTable.add(new LoadingBarPart(dash,flameAnimation));
//        loadingTable.add(new LoadingBarPart(dash,flameAnimation));
//        loadingTable.add(new LoadingBarPart(dash,flameAnimation));
//        loadingTable.add(new LoadingBarPart(dash,flameAnimation));
//        loadingTable.add(new LoadingBarPart(dash,flameAnimation));
//        loadingTable.add(new LoadingBarPart(dash,flameAnimation));
        /*
        поскольку основная таблица fillparent что значит заполняет собой весь экран то мы можем бэкграунд установить
        на таблицу и этого будет достаточно наша картинка заполнит весь фон
        */
        table.setBackground(new TiledDrawable(background));

        table.add(titleImage).align(Align.center).pad(10, 0, 0, 0).colspan(10);
        table.row(); // move to next row
        table.add(loadingTable).width(400);
        table.row();
        table.add(new Image(copyright)).align(Align.center).pad(200, 0, 0, 0).colspan(10);

        stage.addActor(table);
    }

    public void addLoadingBarStage(int currentLoadingStage, Table loadingTable){
        if(currentLoadingStage <= 5){
            /*
             я решил не хранить актеров в hud классе так что они все в стейдж код переделал вариант выше и в screen
            */
            loadingTable.getCells().get((currentLoadingStage-1)*2).getActor().setVisible(true);  // new
            loadingTable.getCells().get((currentLoadingStage-1)*2+1).getActor().setVisible(true);
        }
    }

    public void actAndDraw(float delta){ // cтандартный метод что бы не доставать наш стейдж для отрисовки и акта а так же применяет вьюпорт
        this.stage.getViewport().apply(true);
        this.stage.act(delta);
        this.stage.draw();
    }

    public void dispose(){ // стандартный метод для диспоуз
        stage.dispose();
    }

    public void updateViewport(int width, int height){ // стандартный метод для апдейта нашего вьюпорта у стейдж
        this.stage.getViewport().update(width,height);
    }
    public void setInputProcessor(){ // стандартный метод для установки инпут процессора из вне
        Gdx.input.setInputProcessor(stage);
    }

    public Stage getStage() {
        return stage;
    }
}
