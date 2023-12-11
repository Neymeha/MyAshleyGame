package com.neymeha.thegame.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/*
компоненты это классы хранящие информацию определенного типа
текущий компонент будет хранить позицию
*/
public class TransformComponent implements Component {
    /*
    строка ниже позволяет нам иметь быстрый доступ к компонентам данного типа, с помощью Map мы сможем как быстро получить
    компонент у определенной entity так и быстро изменить
    TransformComponent transformComponent = TransformComponent.Map.get(entity);
    TransformComponent.Map.set(entity, newTransformComponent);
    */
    public static final ComponentMapper<TransformComponent> Map =
            ComponentMapper.getFor(TransformComponent.class);
    /*
    Данные по позиции
    */
    public final Vector3 position = new Vector3();
    public final Vector2 scale = new Vector2(1.0f, 1.0f);
    public float rotation = 0.0f;
    public boolean isHidden = false;

}
