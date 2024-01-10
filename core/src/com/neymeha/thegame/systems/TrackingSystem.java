package com.neymeha.thegame.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.neymeha.thegame.components.PlayerComponent;
import com.neymeha.thegame.components.TransformComponent;

/*
Просто тест самодельной системы изменяющей координаты камеры в зависимости от положения игрока
по Y координате
*/
public class TrackingSystem extends IteratingSystem {
    ComponentMapper <TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);
    ComponentMapper <PlayerComponent> pm = ComponentMapper.getFor(PlayerComponent.class);
    Entity entity;
    public TrackingSystem() {
        super(Family.all(PlayerComponent.class, TransformComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        this.entity = entity;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        PlayerComponent pc = pm.get(entity);
        TransformComponent tc = tm.get(entity);
        pc.cam.position.y = tc.position.y;
    }
}
