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
            // если у энтити с которым столкнулись есть
            if(type != null){
                switch(type.type){
                    // в операторе свич согласно определенному типу энтити выполняем кусок кода
                    // для примера просто сообщение в консоль
                    case TypeComponent.ENEMY:
                        System.out.println("player hit enemy");
                        break;
                    case TypeComponent.SCENERY:
                        System.out.println("player hit scenery");
                        break;
                    case TypeComponent.OTHER:
                        System.out.println("player hit other");
                        break; // фактически и не нужная строка
                }
                cc.collisionEntity = null; // делаем энтити пригодным для удаления GC не совсем понятно зачем ибо скоп закончился
            }
        }

    }
}
