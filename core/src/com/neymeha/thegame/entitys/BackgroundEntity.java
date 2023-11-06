package com.neymeha.thegame.entitys;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.neymeha.thegame.components.TextureComponent;
import com.neymeha.thegame.components.TransformComponent;


// пока что я абсолютно не понимаю что это и с чем это есть
public class BackgroundEntity {
    private PooledEngine engine;

    public BackgroundEntity() {
        engine = new PooledEngine();
    }

    public void createEntity() {
        // Создание сущности
        Entity entity = engine.createEntity();

        // Добавление компонента к сущности
        Component component = new TextureComponent();
        entity.add(component);
        entity.add(new TransformComponent());

        // Добавление сущности в движок
        engine.addEntity(entity);
    }

    public void update(float deltaTime) {
        // Обновление движка
        engine.update(deltaTime);
    }
}
