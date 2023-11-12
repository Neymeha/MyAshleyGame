package com.neymeha.thegame;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.neymeha.thegame.utils.BodyFactory;
import com.neymeha.thegame.utils.GameConfig;

public class GameCore {
    public World world; // ядро игры должно содержать общий мир игры для всех физических обьектов
    private Box2DDebugRenderer debugRenderer; // ядро должно содержать дебаг что бы мы могли посмотреть на наши физ обьекты
    private OrthographicCamera camera; // без камеры тоже никуда, он отвечает за размер нашего окна в мир так сказать
    private Body bodyd; // тело для тестового пола
    private Body bodys; // тело для тестового обьекта
    private Body bodyk; // тело для тестового движущегося обьекта

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
        /*
        Создали наши обьекты тестовые в мире
        */
        createFloor();
        createObject();
        createMovingObject();

        /*
        инициализируем наш синглтон для создания тел
        */
        BodyFactory bodyFactory = BodyFactory.getInstance(world);

        // тестовый шар 1
        bodyFactory.makeCirclePolyBody(1, 1, 2, BodyFactory.RUBBER, BodyDef.BodyType.DynamicBody,false);

        // 2
        bodyFactory.makeCirclePolyBody(4, 1, 2, BodyFactory.STEEL, BodyDef.BodyType.DynamicBody,false);

        // 3
        bodyFactory.makeCirclePolyBody(-4, 1, 2, BodyFactory.STONE, BodyDef.BodyType.DynamicBody,false);

    }

    private void createFloor() {

        // create a new body definition (type and location)
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0, -10);

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
}
