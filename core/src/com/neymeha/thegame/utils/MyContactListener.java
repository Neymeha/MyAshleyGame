package com.neymeha.thegame.utils;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.neymeha.thegame.GameCore;
import com.neymeha.thegame.components.CollisionComponent;

public class MyContactListener implements ContactListener {
    private GameCore core;

    public MyContactListener() {
    }

    /*
    регистрируем контакт
    */
    @Override
    public void beginContact(Contact contact) {
        System.out.println("Contact");
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        System.out.println(fa.getBody().getType()+" has hit "+ fb.getBody().getType());

        if(fa.getBody().getUserData() instanceof Entity){
            Entity ent = (Entity) fa.getBody().getUserData();
            entityCollision(ent,fb);
            return;
        }else if(fb.getBody().getUserData() instanceof Entity){
            Entity ent = (Entity) fb.getBody().getUserData();
            entityCollision(ent,fa);
            return;
        }
    }

    /*
    метод с кодом для регистрации контакта и изменения нужных нам компонентов
    */
    private void entityCollision(Entity ent, Fixture fb) {
        if(fb.getBody().getUserData() instanceof Entity){
            Entity colEnt = (Entity) fb.getBody().getUserData();

            CollisionComponent col = ent.getComponent(CollisionComponent.class);
            CollisionComponent colb = colEnt.getComponent(CollisionComponent.class);

            if(col != null){
                col.collisionEntity = colEnt;
            }else if(colb != null){
                colb.collisionEntity = ent;
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        System.out.println("Contact end");
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
