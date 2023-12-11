package com.neymeha.thegame.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.neymeha.thegame.components.AnimationComponent;
import com.neymeha.thegame.components.StateComponent;
import com.neymeha.thegame.components.TextureComponent;

/*
Система для анимации
находим все энтити в которых есть 3 компонента в констуркторе
переписываем код обработки сущность
*/
public class AnimationSystem extends IteratingSystem {
    /*
    Мапперы которые содержат быстрый доступ к компонентам заданного типа у определенного etity
    */
    ComponentMapper<TextureComponent> tm;
    ComponentMapper<AnimationComponent> am;
    ComponentMapper<StateComponent> sm;

    @SuppressWarnings("unchecked")
    public AnimationSystem(){
        /*
        передаем в родительский конструктор обьект класса Family который определяет какие компоненты должны
        присутствовать у сущностей что бы они были обработаны системой, значит система будет обрабатывать обьекты
        которые имеют сразу все эти сущности
        */
        super(Family.all(TextureComponent.class,
                AnimationComponent.class,
                StateComponent.class).get());

        tm = ComponentMapper.getFor(TextureComponent.class);
        am = ComponentMapper.getFor(AnimationComponent.class);
        sm = ComponentMapper.getFor(StateComponent.class);
    }

    /*
    В первых двух строках кода мы получаем компоненты AnimationComponent и StateComponent для заданной сущности в методе.
    Компонент AnimationComponent содержит анимации, а компонент StateComponent содержит текущее состояние сущности(etity).

    Далее, мы проверяем, содержит ли AnimationComponent анимацию для текущего состояния сущности. Если да, то мы
    получаем компонент TextureComponent для сущности и устанавливаем его регион текстуры на основе текущего кадра
    анимации.

    Затем мы увеличиваем время состояния сущности на значение deltaTime, которое представляет время, прошедшее с
    предыдущего обновления.
    */
    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        AnimationComponent ani = am.get(entity);
        StateComponent state = sm.get(entity);

        if(ani.animations.containsKey(state.get())){
            TextureComponent tex = tm.get(entity);
            tex.region = (TextureRegion) ani.animations.get(state.get()).getKeyFrame(state.time, state.isLooping);
        }

        state.time += deltaTime;
    }
}
