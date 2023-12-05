package com.neymeha.thegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.neymeha.thegame.utils.*;

public class GameCore {
    public World world; // ядро игры должно содержать общий мир игры для всех физических обьектов
    private Box2DDebugRenderer debugRenderer; // ядро должно содержать дебаг что бы мы могли посмотреть на наши физ обьекты
    public OrthographicCamera camera; // без камеры тоже никуда, он отвечает за размер нашего окна в мир так сказать
    private MyKeyboardController controller; // ипут контроллер для реакции на ввод
    /*
	Мой кастомный ассет манаджер
	*/
    public MyAssetManager assetManager;
    /*
    код ниже не представляет ценности для ядра, и создан для тестов
    */
    private Body bodyd; // тело для тестового статичного пола
    private Body bodys; // тело для тестового динамичного обьекта
    private Body bodyk; // тело для тестового кинематичного обьекта
    public Body player;
    public Sound ping, boing;
    public boolean isSwimming = false; // флаг на нахождение в воде
    public static final int BOING_SOUND = 0; // new
    public static final int PING_SOUND = 1; //new

    public GameCore(){
        /*
        Создаем физический мир который будет выполнять симуляцию физики, первым аргументом задали гравитацию, вторым
        разрешили миру не обрабатывать не активные физ тела
        */
        world = new World(new Vector2(0,-10f), true);
        /*
        Создали камеру 32х24, размерность метр на метр
        */
        camera = new OrthographicCamera(GameConfig.GAME_WIDTH/GameConfig.PPM,GameConfig.GAME_HEIGHT/GameConfig.PPM);
        /*
        Создали наш дебаг рендер для отрисовки физ мира
        */
        debugRenderer = new Box2DDebugRenderer(true,true,true,true,true,true);

        controller = new MyKeyboardController();

        assetManager = new MyAssetManager();

        world.setContactListener(new MyContactListener(this));
        /*
        Создали наши обьекты тестовые в мире
        */
        createFloor();
//        createObject();
//        createMovingObject();
        // говорим что загружать
        assetManager.queueAddSounds();
        // загружаем и ждем окончания
        assetManager.manager.finishLoading();
        // присваиваем переменным два звука загруженных
        ping = assetManager.manager.get(assetManager.pingSound, Sound.class);
        boing = assetManager.manager.get(assetManager.boingSound, Sound.class);
        /*
        инициализируем наш синглтон для создания тел
        */
        BodyFactory bodyFactory = BodyFactory.getInstance(world);
        // создаем игрока
        player = bodyFactory.makeBoxPolyBody(1, -3, 2, 2, BodyFactory.RUBBER, BodyDef.BodyType.DynamicBody,false);
        // создаем воду
        Body water =  bodyFactory.makeBoxPolyBody(1, -8, 40, 10, BodyFactory.RUBBER, BodyDef.BodyType.StaticBody,false);
        water.setUserData("IAMTHESEA");
        // делаем все фиксчуры воды сенсерами, тогда сквозь воду можно пройти и она фиксирует столкновение
        bodyFactory.makeAllFixturesSensors(water);
    }

    private void createFloor() {
        // create a new body definition (type and location)
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0, -11);

        // add it to the world
        bodyd = world.createBody(bodyDef);

        // set the shape (here we use a box 50 meters wide, 1 meter tall )
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(50, 1);

        // create the physical object in our body)
        // without this our body would just be data in the world
        bodyd.createFixture(shape, 0.0f);

        // we no longer use the shape object here so dispose of it.
        shape.dispose();
    }

    private void createObject(){

        //create a new body definition (type and location)
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0,0);


        // add it to the world
        bodys = world.createBody(bodyDef);

        // set the shape (here we use a box 50 meters wide, 1 meter tall )
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1,1);

        // set the properties of the object ( shape, weight, restitution(bouncyness)
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        // create the physical object in our body)
        // without this our body would just be data in the world
        bodys.createFixture(shape, 0.0f);

        // we no longer use the shape object here so dispose of it.
        shape.dispose();
    }

    private void createMovingObject(){

        //create a new body definition (type and location)
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(0,-12);


        // add it to the world
        bodyk = world.createBody(bodyDef);

        // set the shape (here we use a box 50 meters wide, 1 meter tall )
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1,1);

        // set the properties of the object ( shape, weight, restitution(bouncyness)
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        // create the physical object in our body)
        // without this our body would just be data in the world
        bodyk.createFixture(shape, 0.0f);

        // we no longer use the shape object here so dispose of it.
        shape.dispose();

        bodyk.setLinearVelocity(0, 0.75f);
    }

    public void logicStep(float delta){ // обновляем наш мир что бы он не был статичен
        /*
        если игрок в воде применяет к нему выталкивающую силу
        */
        if(isSwimming){
            player.applyForceToCenter(0, 40f, true);
        }
        /*
        Добавляем реакции на наши флаги из контролера что бы управлять нашим игроком
        */
        if(controller.left){
            player.applyForceToCenter(-10, 0,true);
        }else if(controller.right){
            player.applyForceToCenter(10, 0,true);
        }else if(controller.up){
            player.applyForceToCenter(0, 10,true);
        }else if(controller.down){
            player.applyForceToCenter(0, -10,true);
        }
        /*
        check if mouse1 is down (player click) then if true check if point intersects
        */
        if(controller.isMouse1Down && controller.pointIntersectsBody(player,controller.mouseLocation,camera)){
            System.out.println("Player was clicked");
        }
        /*
        delta - это время, прошедшее с момента последнего обновления мира. Оно обычно представляет собой разницу во
        времени между двумя кадрами игры. Значение delta обычно передается в метод world.step() из основного цикла игры.

        Первый аргумент 3 - это количество итераций, которые будут выполнены для обновления физического мира.
        Чем больше итераций, тем более точное и плавное будет обновление физики, но это также может повлечь за собой
        увеличение нагрузки на процессор. Значение 3 является стандартным значением и может быть изменено в зависимости
        от требований вашей игры.

        Второй аргумент 3 - это количество под-итераций, которые будут выполнены для обновления физического мира.
        Под-итерации используются для более точного расчета столкновений и реакций объектов в физическом мире.
        Как и в случае с количеством итераций, более высокое значение может улучшить точность физического моделирования,
        но может повлечь за собой увеличение нагрузки на процессор.
        */
        world.step(delta , 3, 3);
    }

    public void debugRender(){ // метод для отрисовки дебаг рендера
        debugRenderer.render(world, camera.combined);
    }

    public void setInputController() {
        Gdx.input.setInputProcessor(controller);
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
