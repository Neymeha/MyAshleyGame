package com.neymeha.thegame.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class PlayerComponent implements Component {
    public static final ComponentMapper<PlayerComponent> Map =
            ComponentMapper.getFor(PlayerComponent.class);
    // класс пустой потому что это компонент флажок
    public OrthographicCamera cam;
}
