package com.neymeha.thegame.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.neymeha.thegame.components.BodyComponent;
import com.neymeha.thegame.components.PlayerComponent;
import com.neymeha.thegame.components.StateComponent;
import com.neymeha.thegame.utils.KeyboardController;

public class PlayerControlSystem extends IteratingSystem {
    ComponentMapper<PlayerComponent> pm;
    ComponentMapper<BodyComponent> bodm;
    ComponentMapper<StateComponent> sm;
    KeyboardController controller;


    @SuppressWarnings("unchecked")
    public PlayerControlSystem(KeyboardController keyCon) {
        super(Family.all(PlayerComponent.class).get());
        controller = keyCon;
        pm = ComponentMapper.getFor(PlayerComponent.class);
        bodm = ComponentMapper.getFor(BodyComponent.class);
        sm = ComponentMapper.getFor(StateComponent.class);
    }
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BodyComponent body = bodm.get(entity);
        StateComponent state = sm.get(entity);

        if(body.body.getLinearVelocity().y > 0){
            state.set(StateComponent.STATE_FALLING);
        }

        if(body.body.getLinearVelocity().y == 0){
            if(state.get() == StateComponent.STATE_FALLING){
                state.set(StateComponent.STATE_NORMAL);
            }
            if(body.body.getLinearVelocity().x != 0){
                state.set(StateComponent.STATE_MOVING);
            }
        }

        if(controller.left){
            body.body.setLinearVelocity(MathUtils.lerp(body.body.getLinearVelocity().x, -5f, 0.2f),body.body.getLinearVelocity().y);
        }
        if(controller.right){
            body.body.setLinearVelocity(MathUtils.lerp(body.body.getLinearVelocity().x, 5f, 0.2f),body.body.getLinearVelocity().y);
        }

        if(!controller.left && ! controller.right){
            body.body.setLinearVelocity(MathUtils.lerp(body.body.getLinearVelocity().x, 0, 0.1f),body.body.getLinearVelocity().y);
        }

        if(controller.up &&
                (state.get() == StateComponent.STATE_NORMAL || state.get() == StateComponent.STATE_MOVING)){
            //b2body.body.applyForceToCenter(0, 3000,true);
            body.body.applyLinearImpulse(0, 75f, body.body.getWorldCenter().x,body.body.getWorldCenter().y, true);
            state.set(StateComponent.STATE_JUMPING);
        }
    }
}
