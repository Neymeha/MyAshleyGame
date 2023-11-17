package com.neymeha.thegame.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.neymeha.thegame.GameCore;

public class MyContactListener implements ContactListener {
    private GameCore core;

    public MyContactListener(GameCore core) {
        this.core = core;
    }

    @Override
    public void beginContact(Contact contact) {
        System.out.println("Contact"); // регистрируем контакт логируя в консоль
        Fixture fa = contact.getFixtureA(); // присваиваем переменной физическое тело номер один которое в контакте
        Fixture fb = contact.getFixtureB(); // тело номер два в контакте
        System.out.println(fa.getBody().getType()+" has hit "+ fb.getBody().getType()); // логируем какие типы тел всретились в консоль


        if(fa.getBody().getUserData() == "IAMTHESEA"){
            core.isSwimming = true;
            return;
        }else if(fb.getBody().getUserData() == "IAMTHESEA"){
            core.isSwimming = true;
            return;
        }

//        if(fa.getBody().getType() == BodyDef.BodyType.StaticBody){
//            this.shootUpInAir(fa, fb);
//        }else if(fb.getBody().getType() == BodyDef.BodyType.StaticBody){
//            this.shootUpInAir(fb, fa);
//        }else{
//            // neither a nor b are static so do nothing
//        }
    }

    @Override
    public void endContact(Contact contact) {
        // после окончания конктакта с морем убираем флаг
        System.out.println("Contact");
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        if(fa.getBody().getUserData() == "IAMTHESEA"){
            core.isSwimming = false;
            return;
        }else if(fb.getBody().getUserData() == "IAMTHESEA"){
            core.isSwimming = false;
            return;
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    // тестовый метод регистрации столкновений со статичным телом
    private void shootUpInAir(Fixture staticFixture, Fixture otherFixture){
        System.out.println("Adding Force"); // написали текст в консоль
        otherFixture.getBody().applyForceToCenter(new Vector2(-100000,-100000), true); // применили силу, разбудили обьект
    }
}
