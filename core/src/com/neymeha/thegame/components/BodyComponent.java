package com.neymeha.thegame.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.physics.box2d.Body;

public class BodyComponent implements Component {
    public static final ComponentMapper<BodyComponent> Map =
            ComponentMapper.getFor(BodyComponent.class);
    public Body body;
}
