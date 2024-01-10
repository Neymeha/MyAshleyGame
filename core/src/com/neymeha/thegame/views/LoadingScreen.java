package com.neymeha.thegame.views;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.neymeha.thegame.MyGame;
import com.neymeha.thegame.huds.LoadingScreenHud;

public class LoadingScreen implements Screen {

    private MyGame parent;
    /*
     Перенесено в hud класс
    */
//    public TextureAtlas loadingAtlas; // текстурный атлас
//    public TextureAtlas.AtlasRegion title; // регион текстур по атласу
//    public TextureAtlas.AtlasRegion dash; // регион текстур по атласу
//    public Animation flameAnimation;

    public final int IMAGE = 0;		// загрузка текстур
    public final int FONT = 1;		// загрузка шрифтов
    public final int PARTY = 2;		// загрузка частичных эфектов
    public final int SOUND = 3;		// загрузка звуков
    public final int MUSIC = 4;		// загрузка музыки
    private int currentLoadingStage = 0; // счетчик прогресс бара
    public float countDown = 5f; // таймер 5 секунд ожидания перед сменой экрана
    /*
    Переехал в hud класс
    */
//    private Stage stage;
    private LoadingScreenHud hud;

    public LoadingScreen(MyGame parent) {
        this.parent = parent;
        // ставим в очередь на загрузку ресурсы и загружаем
        parent.core.assetManager.queueAddLoadingImages();
        System.out.println("Loading loading images....");
        parent.core.assetManager.queueAddSkin();
        System.out.println("Loading skin....");
        parent.core.assetManager.queueAddMusic();
        System.out.println("Loading music....");
        parent.core.assetManager.finishLoading();
        System.out.println("Finished");
        hud = new LoadingScreenHud(parent);
//        /*
//        Функция смешивания определяет, как цвета пикселей объектов будут смешиваться при отрисовке на экране.
//        */
//        parent.getBatch().setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
    }

    @Override
    public void show() {
        /*
        все перенесено в LoadingBarPart что бы стейдж отрисовывал
        */
//        stateTime = 0f;
//        // загружаем текстуры загрузочные и ждем окончания загрузки
//        parent.core.assetManager.queueAddLoadingImages();
//        parent.core.assetManager.manager.finishLoading();
//
//        // достаем атлас и из атласа нужные регионы текстур
//        loadingAtlas = parent.core.assetManager.manager.get("images/loading.atlas");
//        title = loadingAtlas.findRegion("staying-alight-logo");
//        dash = loadingAtlas.findRegion("loading-dash");
//        /*
//        инициализируем анимацию, первый параметр устанавливает продолжительность кадра, второй регион текстур в атласе
//        который будет использован, и третий мод для запуска в нашем случае - закольцевать
//        */
//        flameAnimation = new Animation(0.07f, loadingAtlas.findRegions("flames/flames"), Animation.PlayMode.LOOP);
//
//        // ставим в очередь загрузку гейм текстур но не начинаем загрузку
//        parent.core.assetManager.queueAddImages();
//        System.out.println("Loading Images....");
    }

    /*
    Поскольку рендер метод сработает после шоу и конструктора, значит после инициализации всех наших ресурсов нужных
    мы и переключаемся на следующий экран
    */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1); // отчистили экран
        /*
        Вернули батчу прошлые настройки
        */
//        parent.getBatch().setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
//        stateTime += delta; // аккумулируем время смены частоты кадров для смены анимации согласно частоте
//        /*
//        получаем текущий кадр для отрисовки прогресс бара для текущего момента времени согласно stateTime
//        */
//        TextureRegion currentFrame = (TextureRegion) flameAnimation.getKeyFrame(stateTime, true);
//
//        // работа с батчем
//        parent.getBatch().begin();
//        drawLoadingBar(currentLoadingStage*2, currentFrame, parent.getBatch());
//        parent.getBatch().draw(title, 135, 250);
//        parent.getBatch().end();

        /*
        С помощью кода ниже мы обновляем лоадинг бар но так же можно это сделать и по другому
        Для отображения полосы прогресса в libgdx при загрузке ресурсов можно использовать различные подходы.
        Один из способов - это использование метода manager.update() для получения значения прогресса загрузки и
        отображения полосы прогресса на основе этого значения.
        Да и сам прогресс бар можно делать с помощью стилей и отдельного класса в либе, если что спрашивай чат гпт
        */
        // проверяем закончил ли менеджер с прошлой загрузкой
        if (parent.core.assetManager.update()) { // метод который возвращет тру если в очереди нет загрузок незаконченных
            currentLoadingStage+= 1; // идем дальше оп свичу с помощью переменной а так же исп ее в лоадинг баре
            hud.addLoadingBarStage(currentLoadingStage, hud.getStage().getRoot().<Table>findActor("loadingTable"));
            switch(currentLoadingStage){
                case FONT:
                    System.out.println("Loading Fonts....");
                    parent.core.assetManager.queueAddFonts();
                    break;
                case PARTY:
                    System.out.println("Loading Particle Effects....");
                    parent.core.assetManager.queueAddParticleEffects();
                    break;
                case SOUND:
                    System.out.println("Loading Sounds....");
                    parent.core.assetManager.queueAddSounds();
                    break;
                case MUSIC:
                    System.out.println("Loading fonts....");
                    parent.core.assetManager.queueAddMusic();
                    break;
                case 5:
                    System.out.println("Finished"); // закончили
                    break;
            }
            /*
            несколько замысловато работает код -
            поскольку перед этим мы увеличивает лоадинг стейдж на 1, когда он становится больше 5 мы отнимаем от нашего
            счетчика для задержки на загрузочном экране время смены кадра, что бы в итоге прошло 5 секунд(прикольный способ)
            затем возвращаем лоадинг стейдж 5ку что бы он не увеличивался чрезмерно а зафиксировался на 5
            и проверяем когда время счетчика уйдет в - мы сменим экран
            */
            if (currentLoadingStage >5){
                countDown -= delta;
                currentLoadingStage = 5;
                if(countDown < 0){
                    parent.changeScreen(MyGame.MENU);
                }
            }
            hud.actAndDraw(delta);
        }
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
    /*
     Отрисовкой актеров будет заниматься hud класс с наличием stage
    */
//    /*
//    stage - это уровень текущей загрузки, currentFrame - текущий кадр анимации
//    смысл такой - чем больше наш стейдж тем больше раз будет отрисована текстурка с прогресс баром
//    ну и с каждой итерацией будет смещение этого дела за счет чего и будет достигнута собственно наша отрисовка
//    и между всем этим еще и будет текстурка dash
//    */
//    private void drawLoadingBar(int stage, TextureRegion currentFrame, SpriteBatch batch){
//        for(int i = 0; i < stage;i++){
//            batch.draw(currentFrame, 50 + (i * 50), 150, 50, 50);
//            batch.draw(dash, 35 + (i * 50), 140, 80, 80);
//        }
//    }

    /*
    загрузка ассетов для лоадинга перенесена в hud класс для удобства
    */
//    private void loadAssets() {
//        // load loading images and wait until finished
//        parent.core.assetManager.queueAddLoadingImages();
//        parent.core.assetManager.manager.finishLoading();
//
//        // get images used to display loading progress
//        atlas = parent.core.assetManager.manager.get("images/loading.atlas");
//        title = atlas.findRegion("staying-alight-logo");
//        dash = atlas.findRegion("loading-dash");
//        flameAnimation = new Animation(0.07f, atlas.findRegions("flames/flames"), Animation.PlayMode.LOOP);
//    }
}
