package com.neymeha.thegame.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class PlayerComponent implements Component {
    public static final ComponentMapper<PlayerComponent> Map =
            ComponentMapper.getFor(PlayerComponent.class);
    /*
    Камера для передвижения камеры за игроком
    логические флаги для системы столкновений
    */
    public OrthographicCamera cam;
    public boolean onSpring;
    public boolean isDead;
    public boolean onPlatform;
}
