package com.neymeha.thegame.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.Body;
import com.neymeha.thegame.components.BodyComponent;
import com.neymeha.thegame.components.WallComponent;

public class WallSystem extends IteratingSystem{
    private Entity player;
    private ComponentMapper<BodyComponent> bm = ComponentMapper.getFor(BodyComponent.class);

    @SuppressWarnings("unchecked")
    public WallSystem(Entity player) {
        super(Family.all(WallComponent.class).get());
        this.player = player;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        // get current y level of player entity
        float currentyLevel = player.getComponent(BodyComponent.class).body.getPosition().y;
        // get the body component of the wall we're updating
        Body bod = bm.get(entity).body;
        //set the walls y position to match the player
        bod.setTransform(bod.getPosition().x, currentyLevel, bod.getAngle());
    }
}