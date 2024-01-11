package com.neymeha.thegame.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.Body;
import com.neymeha.thegame.components.BodyComponent;
import com.neymeha.thegame.components.WaterFloorComponent;

public class WaterFloorSystem extends IteratingSystem {
    private Entity player;
    private ComponentMapper<BodyComponent> bm = ComponentMapper.getFor(BodyComponent.class);

    public WaterFloorSystem(Entity player) {
        super(Family.all(WaterFloorComponent.class).get());
        this.player = player;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        // get current y level of player entity
        float currentyLevel = player.getComponent(BodyComponent.class).body.getPosition().y;
        // get the body component of the wall we're updating
        Body bod = bm.get(entity).body;

        float speed = (currentyLevel / 300);
        /*
        !!! слишком быстро движется вода надо подкорректировать
        */
        speed = speed>1?1:speed;

        bod.setTransform(bod.getPosition().x, bod.getPosition().y+speed, bod.getAngle());
    }

}
