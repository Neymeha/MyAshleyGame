package com.neymeha.thegame.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;

public class CollisionComponent implements Component {
    public static final ComponentMapper<CollisionComponent> Map =
            ComponentMapper.getFor(CollisionComponent.class);
    public Entity collisionEntity;
}
