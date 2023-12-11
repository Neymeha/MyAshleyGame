package com.neymeha.thegame.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class TypeComponent implements Component {
    public static final ComponentMapper<TypeComponent> Map =
            ComponentMapper.getFor(TypeComponent.class);
    /*
    компонент которое содержит тип энтити
    !!! статичные поля можно и перенести в конфиг
    */
    public static final int PLAYER = 0;
    public static final int ENEMY = 1;
    public static final int SCENERY = 3;
    public static final int OTHER = 4;

    public int type = OTHER;
}
