package com.neymeha.thegame.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.neymeha.thegame.components.BodyComponent;
import com.neymeha.thegame.components.PlayerComponent;
import com.neymeha.thegame.components.StateComponent;
import com.neymeha.thegame.utils.MyKeyboardController;

public class PlayerControlSystem extends IteratingSystem {
    /*
    мапперы для быстрого доступа к заданным компонентам у конкретной сущности
    */
    ComponentMapper<PlayerComponent> pm;
    ComponentMapper<BodyComponent> bodm;
    ComponentMapper<StateComponent> sm;
    MyKeyboardController controller; // наш кастомный инпут контролер


    @SuppressWarnings("unchecked")
    public PlayerControlSystem(MyKeyboardController keyCon) {
        super(Family.all(PlayerComponent.class).get()); // управлять мы ходим только сущностями с компонентом игрока
        controller = keyCon;
        pm = ComponentMapper.getFor(PlayerComponent.class);
        bodm = ComponentMapper.getFor(BodyComponent.class);
        sm = ComponentMapper.getFor(StateComponent.class);
    }
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        /*
         Получили физ компонент и состояния у нашей сущности
        */
        BodyComponent body = bodm.get(entity);
        StateComponent state = sm.get(entity);
        PlayerComponent player = pm.get(entity);
        /*
        Далее 3 условия для того что менять состояния в котором находиться наш игрок
        */
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

        /*
         далее проверки на условия для дальнейшего изменения скорости тела
          мы изменяем линейную скорость тела, используя функцию MathUtils.lerp, чтобы плавно изменить скорость до
          значения -5f/5f по оси x c шагом 0.2f
        */
        if(controller.left){
            body.body.setLinearVelocity(MathUtils.lerp(body.body.getLinearVelocity().x, -5f, 0.2f),body.body.getLinearVelocity().y);
        }
        if(controller.right){
            body.body.setLinearVelocity(MathUtils.lerp(body.body.getLinearVelocity().x, 5f, 0.2f),body.body.getLinearVelocity().y);
        }

        /*
         если по оси X мы не движемся а значит в лево и вправо не нажали то возвражаем скорость по иксу до 0 с шагом 0.1f
        */
        if(!controller.left && ! controller.right){
            body.body.setLinearVelocity(MathUtils.lerp(body.body.getLinearVelocity().x, 0, 0.1f),body.body.getLinearVelocity().y);
        }

        /*
         если нажата кнопка вверх + состояние или нормальное или движение то мы применяем к телу импульс по оси Y,
         и устанавлием состояние на прыжок
        */
        if(controller.up &&
                (state.get() == StateComponent.STATE_NORMAL || state.get() == StateComponent.STATE_MOVING)){
            //b2body.body.applyForceToCenter(0, 3000,true);
            body.body.applyLinearImpulse(0, 75f, body.body.getWorldCenter().x,body.body.getWorldCenter().y, true);
            state.set(StateComponent.STATE_JUMPING);
        }

        // make player jump very high
        if(player.onSpring){
            body.body.applyLinearImpulse(0, 175f, body.body.getWorldCenter().x,body.body.getWorldCenter().y, true);
            state.set(StateComponent.STATE_JUMPING);
            player.onSpring = false; // что бы не прыгал постоянно
        }

    }
}
