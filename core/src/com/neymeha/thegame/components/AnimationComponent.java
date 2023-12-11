package com.neymeha.thegame.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.IntMap;

public class AnimationComponent implements Component {
    public static final ComponentMapper<AnimationComponent> Map =
            ComponentMapper.getFor(AnimationComponent.class);
    /*
    используем мапу у которой ключи названия анимации а значения собственно сама анимация
    у разных обьектов может быть не одна анимация ведь
    */
    public IntMap<Animation> animations = new IntMap<Animation>();
}
