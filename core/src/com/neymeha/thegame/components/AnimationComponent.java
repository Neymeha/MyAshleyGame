package com.neymeha.thegame.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.IntMap;

public class AnimationComponent implements Component {
    public static final ComponentMapper<AnimationComponent> Map =
            ComponentMapper.getFor(AnimationComponent.class);
    public IntMap<Animation> animations = new IntMap<Animation>();
}
