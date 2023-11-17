package com.neymeha.thegame.utils;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;

/*
Класс пока без надобности но пусть полежит тут
*/
public class KeyboardController implements InputProcessor {

        public boolean left,right,up,down; // флаги реакции на нажатые нами стандартные кнопки, вверх вниз влево вправо
        /*
        флаги для мыши ниже
        */
        public boolean isMouse1Down, isMouse2Down,isMouse3Down;
        public boolean isDragged;
        public Vector2 mouseLocation = new Vector2();
        /*
        Метод регистрирующий нажатие кнопки и меняющий флаги в случае нажатия вверх вниз влево вправо
        */
        @Override
        public boolean keyDown(int keycode) {
                boolean keyProcessed = false;
                switch (keycode) // switch code base on the variable keycode
                {
                        case Input.Keys.LEFT:  	// if keycode is the same as Keys.LEFT a.k.a 21
                                left = true;	// do this
                                keyProcessed = true;// we have reacted to a keypress
                                break;	// we have reacted to a keypress so stop checking for more
                        case Input.Keys.RIGHT: 	// if keycode is the same as Keys.LEFT a.k.a 22
                                right = true;	// do this
                                keyProcessed = true;// we have reacted to a keypress
                                break;	// we have reacted to a keypress so stop checking for more
                        case Input.Keys.UP: 	// if keycode is the same as Keys.LEFT a.k.a 19
                                up = true;		// do this
                                keyProcessed = true;// we have reacted to a keypress
                                break;	// we have reacted to a keypress so stop checking for more
                        case Input.Keys.DOWN: 	// if keycode is the same as Keys.LEFT a.k.a 20
                                down = true;	// do this
                                keyProcessed = true;// we have reacted to a keypress
                }
                return keyProcessed;	// no keys pressed that we care about so return false
        }
        @Override
        public boolean keyUp(int keycode) {
                boolean keyProcessed = false;
                switch (keycode) // switch code base on the variable keycode
                {
                        case Input.Keys.LEFT:  	// if keycode is the same as Keys.LEFT a.k.a 21
                                left = false;	// do this
                                keyProcessed = true;	// we have reacted to a keypress
                                break;
                        case Input.Keys.RIGHT: 	// if keycode is the same as Keys.LEFT a.k.a 22
                                right = false;	// do this
                                keyProcessed = true;	// we have reacted to a keypress
                                break;
                        case Input.Keys.UP: 		// if keycode is the same as Keys.LEFT a.k.a 19
                                up = false;		// do this
                                keyProcessed = true;	// we have reacted to a keypress
                                break;
                        case Input.Keys.DOWN: 	// if keycode is the same as Keys.LEFT a.k.a 20
                                down = false;	// do this
                                keyProcessed = true;	// we have reacted to a keypress
                }
                return keyProcessed;	//  return our peyProcessed flag
        }
        @Override
        public boolean keyTyped(char character) {
                return false;
        }
        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if(button == 0){
                        isMouse1Down = true;
                }else if(button == 1){
                        isMouse2Down = true;
                }else if(button == 2){
                        isMouse3Down = true;
                }
                mouseLocation.x = screenX;
                mouseLocation.y = screenY;
                return false;
        }
        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                isDragged = false;
                //System.out.println(button);
                if(button == 0){
                        isMouse1Down = false;
                }else if(button == 1){
                        isMouse2Down = false;
                }else if(button == 2){
                        isMouse3Down = false;
                }
                mouseLocation.x = screenX;
                mouseLocation.y = screenY;
                return false;
        }

        @Override
        public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
                return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
                isDragged = true;
                mouseLocation.x = screenX;
                mouseLocation.y = screenY;
                return false;
        }
        @Override
        public boolean mouseMoved(int screenX, int screenY) {
                mouseLocation.x = screenX;
                mouseLocation.y = screenY;
                return false;
        }

        @Override
        public boolean scrolled(float amountX, float amountY) {
                return false;
        }


        /*
         * Checks if point is in first fixture
         * Does not check all fixtures.....yet
         *
         * @param body the Box2D body to check
         * @param mouseLocation the point on the screen
         * @return true if click is inside body
         */
        public boolean pointIntersectsBody(Body body, Vector2 mouseLocation, OrthographicCamera camera){
                Vector3 mousePos = new Vector3(mouseLocation,0); //convert mouseLocation to 3D position
                camera.unproject(mousePos); // convert from screen potition to world position
                if(body.getFixtureList().first().testPoint(mousePos.x, mousePos.y)){
                        return true;
                }
                return false;
        }

}
