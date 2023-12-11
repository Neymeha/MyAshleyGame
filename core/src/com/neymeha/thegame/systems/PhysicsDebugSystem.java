package com.neymeha.thegame.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

/*
 Система для отрисовки физики с целью ее отладки
*/
public class PhysicsDebugSystem extends IteratingSystem {
    private Box2DDebugRenderer debugRenderer; // наш дебаг рендер для отображения физ мира на экране
    private World world; // наш мир физ
    private OrthographicCamera camera; // и камера

    public PhysicsDebugSystem(World world, OrthographicCamera camera){
        // никаких компоненты нас не интересуют как и сущности
        super(Family.all().get());
        /*
        не уверен что стоит создавать дебаг рендер тут и хранить его тут, он у меня и в ядре неплохо бы полежал
        инициализируем обьекты нужные для дебаг рендера
        */
        debugRenderer = new Box2DDebugRenderer();
        this.world = world;
        this.camera = camera;
    }

    @Override
    public void update(float deltaTime) {
        /*
        вообще не каноничное написание так как перезаписан метод update, но сами разработики указывают на то что
        логика для обновления системы должна быть помещена в метод !!! updateInterval. однако такое написание особо
        ничего не меняет.
        Мы перезаписываем метод апдейт который есть в классе родителе сначала обращаясь к нему что бы не терять
        родительскую логику а затем добавляем свою а именно рендер нашего физ движка с указанием физ мира и
        матрицы нашей камеры
        */
        super.update(deltaTime);
        debugRenderer.render(world, camera.combined);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        // в данном случае мы не обрабатываем никакие сущности а просто выводим на экран физ мир в методе апдейт
    }
}
