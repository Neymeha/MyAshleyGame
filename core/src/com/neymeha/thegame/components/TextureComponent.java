package com.neymeha.thegame.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureComponent implements Component {
    public static final ComponentMapper<TextureComponent> Map =
            ComponentMapper.getFor(TextureComponent.class);
    public TextureRegion region = null;
}
