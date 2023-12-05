package com.neymeha.thegame.huds.actors;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/*
 Класс который заменит наш загрузочный бар на стейдже, а точнее один элемент загрузочного бара
*/
public class LoadingBarPart extends Actor {
    private TextureAtlas.AtlasRegion image; // текстура наша кусочка лоадинг бара
    private Animation flameAnimation; // анимация огня на загрузочном баре
    private float stateTime; // переменная для фиксации времени изменения анимации
    private TextureRegion currentFrame; // текущий кадр анимации
    public LoadingBarPart(TextureAtlas.AtlasRegion ar, Animation an) {
        super();
        image = ar; // картинка нашего элемента лоадинг бара
        flameAnimation = an; // анимация огня под нашей картинкой для нашего элемента лоадинг бара
        stateTime = 0f; // переменнай прошедшего времени для смены кадров в анимации
        /*
        устанавливаем высоту и ширину для нашего стейджа так как он будет отрисовывать без этого он не будет понимать
        что и как отрисовать
        установили невидимость что бы загрузка не отображалась полностью на старте
        */
        this.setWidth(30);
        this.setHeight(25);
        this.setVisible(false);
    }

    /*
    перезаписываем метод дро для нужной нам отрисовки анимации и картинки
    */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        /*
        отрисовываем текстуру котору нужно отрисовать по заданным координатам в конструкторе с заданной шириной и высотой
        устанавливаем смешивание цветов 1 - исходный цвет 2 - с которым смешиваем
        отрисовываем текущий кадр анимации с заданными параметрами
        и возвращаем режим смешивания в исходный режим
        */
        batch.draw(image, getX(),getY(), 30, 30);
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
        batch.draw(currentFrame, getX()-5,getY(), 40, 40);
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

    }

    /*
    Перезаписываем метод для отрисовки в стейдж что бы обьект текущего класса получал при вызове акт текущий кадр анимации
    */
    @Override
    public void act(float delta) {
        super.act(delta); // сначала заходим в основной метод что бы вся нужная логика отработала а потом вносим наши изменения ниже
        stateTime += delta; // аккумулируем время для ортрисовки анимации
        currentFrame = (TextureRegion) flameAnimation.getKeyFrame(stateTime, true); // получаем текущий кадр анимации
    }
}
