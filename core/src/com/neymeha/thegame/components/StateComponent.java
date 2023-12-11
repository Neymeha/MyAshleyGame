package com.neymeha.thegame.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class StateComponent implements Component {
    public static final ComponentMapper<StateComponent> Map =
            ComponentMapper.getFor(StateComponent.class);
    /*
    класс который содержит состояния для наших анимаций точнее для дальнейшей их отрисовки
    !!! статичные поля можно перести в конфиг
    */
    public static final int STATE_NORMAL = 0;
    public static final int STATE_JUMPING = 1;
    public static final int STATE_FALLING = 2;
    public static final int STATE_MOVING = 3;
    public static final int STATE_HIT = 4;

    /*
    собвственно state - сомо состояние
    time - аккумулятор для delta time что бы своевременно сменялись кадры в анимации
    и нужна ли нам закальцованность в отрисовки isLooping
    два метода один для установки состояния и соответственно установки времени на 0 (зачем если она и так прописана)
    и второй для возврата состояния
    */
    private int state = 0;
    public float time = 0.0f;
    public boolean isLooping = false;

    public void set(int newState){
        state = newState;
        time = 0.0f;
    }

    public int get(){
        return state;
    }
}
