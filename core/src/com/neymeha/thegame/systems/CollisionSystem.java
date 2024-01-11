package com.neymeha.thegame.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.neymeha.thegame.components.CollisionComponent;
import com.neymeha.thegame.components.PlayerComponent;
import com.neymeha.thegame.components.TypeComponent;

public class CollisionSystem extends IteratingSystem {
    /*
    мапперы которые будут содержать быстрый доступ к заданным компонентам у конкретной сущности
    */
    ComponentMapper<CollisionComponent> cm;
    ComponentMapper<PlayerComponent> pm;

    @SuppressWarnings("unchecked")
    public CollisionSystem() {
        // нас интересует только столкновение с игроком значит нужные сущности к обработки должны иметь
        // и компонент столкновений и компонент игрока
        super(Family.all(CollisionComponent.class, PlayerComponent.class).get());

        /*
        инициализируем мапперы для наших компонентов для быстрого доступа к ним из конкретных энтити
        */
        cm = ComponentMapper.getFor(CollisionComponent.class);
        pm = ComponentMapper.getFor(PlayerComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        // нашли компонент столкновения у игрока
        CollisionComponent cc = cm.get(entity);

        Entity collidedEntity = cc.collisionEntity; // нашли энтити с которым столкнулся игрок
        // далее проверки идут если энтити со столкновением существует продолжаем
        if(collidedEntity != null){
            TypeComponent type = collidedEntity.getComponent(TypeComponent.class);
            // если у энтити с которым столкнулись есть определенный TYPE
            if(type != null){
                switch(type.type){
                    case TypeComponent.ENEMY:
                        //do player hit enemy thing
                        System.out.println("player hit enemy");
                        PlayerComponent pl = pm.get(entity);
                        pl.isDead = true;
                        int score = (int) pl.cam.position.y;
                        System.out.println("Score = "+ score);
                        pm.get(entity).isDead = true;
                        break;
                    case TypeComponent.SCENERY:
                        //do player hit scenery thing
                        pm.get(entity).onPlatform = true;
                        System.out.println("player hit scenery");
                        break;
                    case TypeComponent.SPRING:
                        //do player hit other thing
                        pm.get(entity).onSpring = true;
                        System.out.println("player hit spring: bounce up");
                        break;
                    case TypeComponent.OTHER:
                        //do player hit other thing
                        System.out.println("player hit other");
                        break;
                    default:
                        System.out.println("No matching type found");
                }
                cc.collisionEntity = null; // делаем пригодным для удаление GC
            }else{
                System.out.println("type == null");
            }
        }

    }
}
