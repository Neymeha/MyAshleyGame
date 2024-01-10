package com.neymeha.thegame;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.neymeha.thegame.components.*;
import com.neymeha.thegame.systems.*;
import com.neymeha.thegame.utils.*;
import com.neymeha.thegame.utils.levelgenerator.LevelFactory;

public class GameCore {
    public World world; // ядро игры должно содержать общий мир игры для всех физических обьектов
    public SpriteBatch batch; // batch для всего проекта
    private Box2DDebugRenderer debugRenderer; // ядро должно содержать дебаг что бы мы могли посмотреть на наши физ обьекты
    // !!! может камеру присваивать из рендер системы?
    public OrthographicCamera camera; // без камеры тоже никуда, он отвечает за размер нашего окна в мир так сказать
    private MyKeyboardController controller; // ипут контроллер для реакции на ввод
    public MyAssetManager assetManager; // 	Мой кастомный ассет манаджер
    private BodyFactory bodyFactory; // синглтон для создания тел
    public PooledEngine engine; // движок Ashley для всей нашей ECS системы
    public Viewport gameViewport; // наш вьюпорт для отрисовки в рендере игровых обьектов
    public LevelFactory lvlFactory; // класс использующий генерацию уровня через алгоритм Simplex Noise

    public GameCore(){
        initGame();
    }

    private void initGame(){ // инициализируем все необьходимые обьекты нужных классов для игры
        /*
        Создаем физический мир который будет выполнять симуляцию физики, первым аргументом задали гравитацию, вторым
        разрешили миру не обрабатывать не активные физ тела
        */
        world = new World(new Vector2(0,-10f), true);
        world.setContactListener(new MyContactListener()); // уставили слушателя контактов
        /*
        Создали камеру 32х24, размерность метр на метр
        */
        camera = new OrthographicCamera(GameConfig.FRUSTUM_WIDTH,GameConfig.FRUSTUM_HEIGHT);
        gameViewport = new FitViewport(GameConfig.FRUSTUM_WIDTH,GameConfig.FRUSTUM_HEIGHT, camera);
        /*
        Создали наш дебаг рендер для отрисовки физ мира
        */
        debugRenderer = new Box2DDebugRenderer(true,true,true,true,true,true);

        controller = new MyKeyboardController();

        batch = new SpriteBatch(); // инициализировали батч
        batch.setProjectionMatrix(camera.combined); // установили матрицу согласно камеры

        assetManager = new MyAssetManager();
        /*
        инициализируем наш синглтон для создания тел
        */
        bodyFactory = BodyFactory.getInstance(world);

        engine = new PooledEngine(); // инициализировали движок

        /*
        пришлось перенести загрузку ассетов сюда так как она нужна в лвл фактори
        */
        assetManager.queueAddImages();
        assetManager.finishLoading();
        System.out.println("Loading game images....");
        lvlFactory = new LevelFactory(engine, assetManager.getGameAtlas().findRegion("player"));
    }

    // !!! не уверен что тут ему место а где нибудь в гейм скрин
//    private void initAssetManagerStuffNeeded(){ // работа с ассет менеджером
//        // говорим что загружать
//        assetManager.queueAddSounds();
//        // загружаем и ждем окончания
//        assetManager.manager.finishLoading();
//        // присваиваем переменным два звука загруженных
//        ping = assetManager.manager.get(assetManager.pingSound, Sound.class);
//        boing = assetManager.manager.get(assetManager.boingSound, Sound.class);
//    }

    public void initEngineSystems(){ // добавляем системы в движок
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new RenderingSystem(batch, camera, gameViewport));
        engine.addSystem(new PhysicsSystem(world));
        engine.addSystem(new PhysicsDebugSystem(world, camera));
        engine.addSystem(new CollisionSystem());
        engine.addSystem(new PlayerControlSystem(controller));
        engine.addSystem(new LevelGenerationSystem(lvlFactory));
        engine.addSystem(new TrackingSystem());
    }

//    public void initEntities(){ // добавляем игровые обьекты в мир
//        createPlayer();
//        createPlatform(2,2);
//        createPlatform(2,7);
//        createPlatform(7,2);
//        createPlatform(7,7);
//        createFloor();
//    }


//    public void createAndAddPlayerEtity(){
//        /*
//        создаем энтити игрока и все нужные для него компоненты с помощью engine
//        */
//        Entity entity = engine.createEntity();
//        BodyComponent b2dbody = engine.createComponent(BodyComponent.class);
//        TransformComponent position = engine.createComponent(TransformComponent.class);
//        TextureComponent texture = engine.createComponent(TextureComponent.class);
//        PlayerComponent player = engine.createComponent(PlayerComponent.class);
//        CollisionComponent colComp = engine.createComponent(CollisionComponent.class);
//        TypeComponent type = engine.createComponent(TypeComponent.class);
//        StateComponent stateCom = engine.createComponent(StateComponent.class);
//
//        /*
//        создаем информацию для добавления ее к компонентам
//        */
//        b2dbody.body = bodyFactory.makeCirclePolyBody(10,10,1, BodyFactory.STONE, BodyDef.BodyType.DynamicBody,true);
//        position.position.set(10,10,0); // определяем позицию нашего игрока и очередь на отрисовку координатой Z
//        texture.region = assetManager.getGameAtlas().findRegion("player");
//        type.type = TypeComponent.PLAYER;
//        stateCom.set(StateComponent.STATE_NORMAL);
//        b2dbody.body.setUserData(entity);
//
//        /*
//        добавляем компоненты нашему энтити
//        */
//        entity.add(b2dbody);
//        entity.add(position);
//        entity.add(texture);
//        entity.add(player);
//        entity.add(colComp);
//        entity.add(type);
//        entity.add(stateCom);
//
//        // add the entity to the engine
//        engine.addEntity(entity);
//    }
//
//    public void createAndAddPlatformEntity(float x, float y){
//        Entity entity = engine.createEntity();
//        BodyComponent b2dbody = engine.createComponent(BodyComponent.class);
//        b2dbody.body = bodyFactory.makeBoxPolyBody(x, y, 3, 0.2f, BodyFactory.STONE, BodyDef.BodyType.StaticBody);
//        TextureComponent texture = engine.createComponent(TextureComponent.class);
//        texture.region = assetManager.getGameAtlas().findRegion("player");
//        TypeComponent type = engine.createComponent(TypeComponent.class);
//        type.type = TypeComponent.SCENERY;
//        b2dbody.body.setUserData(entity);
//
//        entity.add(b2dbody);
//        entity.add(texture);
//        entity.add(type);
//
//        engine.addEntity(entity);
//
//    }
//
//    public void createAndAddFloorEtity(){
//        Entity entity = engine.createEntity();
//        BodyComponent b2dbody = engine.createComponent(BodyComponent.class);
//        b2dbody.body = bodyFactory.makeBoxPolyBody(0, 0, 100, 0.2f, BodyFactory.STONE, BodyDef.BodyType.StaticBody);
//        TextureComponent texture = engine.createComponent(TextureComponent.class);
//        texture.region = assetManager.getGameAtlas().findRegion("player");
//        TypeComponent type = engine.createComponent(TypeComponent.class);
//        type.type = TypeComponent.SCENERY;
//
//        b2dbody.body.setUserData(entity);
//
//        entity.add(b2dbody);
//        entity.add(texture);
//        entity.add(type);
//
//        engine.addEntity(entity);
//    }

//    public void logicStep(float delta){ // обновляем наш мир что бы он не был статичен
//        /*
//        если игрок в воде применяет к нему выталкивающую силу
//        */
////        if(isSwimming){
////            player.applyForceToCenter(0, 40f, true);
////        }
//        /*
//        Добавляем реакции на наши флаги из контролера что бы управлять нашим игроком
//        */
//        if(controller.left){
//            player.applyForceToCenter(-10, 0,true);
//        }else if(controller.right){
//            player.applyForceToCenter(10, 0,true);
//        }else if(controller.up){
//            player.applyForceToCenter(0, 10,true);
//        }else if(controller.down){
//            player.applyForceToCenter(0, -10,true);
//        }
//        /*
//        check if mouse1 is down (player click) then if true check if point intersects
//        */
//        if(controller.isMouse1Down && controller.pointIntersectsBody(player,controller.mouseLocation,camera)){
//            System.out.println("Player was clicked");
//        }
//        /*
//        delta - это время, прошедшее с момента последнего обновления мира. Оно обычно представляет собой разницу во
//        времени между двумя кадрами игры. Значение delta обычно передается в метод world.step() из основного цикла игры.
//
//        Первый аргумент 3 - это количество итераций, которые будут выполнены для обновления физического мира.
//        Чем больше итераций, тем более точное и плавное будет обновление физики, но это также может повлечь за собой
//        увеличение нагрузки на процессор. Значение 3 является стандартным значением и может быть изменено в зависимости
//        от требований вашей игры.
//
//        Второй аргумент 3 - это количество под-итераций, которые будут выполнены для обновления физического мира.
//        Под-итерации используются для более точного расчета столкновений и реакций объектов в физическом мире.
//        Как и в случае с количеством итераций, более высокое значение может улучшить точность физического моделирования,
//        но может повлечь за собой увеличение нагрузки на процессор.
//        */
//        world.step(delta , 3, 3);
//    }

//    public void debugRender(){ // метод для отрисовки дебаг рендера
//        debugRenderer.render(world, camera.combined);
//    }

    public void setInputController() {
        Gdx.input.setInputProcessor(controller);
    }
    public void dispose(){
        batch.dispose();
        assetManager.dispose();
    }

}
