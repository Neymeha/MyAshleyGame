package com.neymeha.thegame.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.neymeha.thegame.components.TextureComponent;
import com.neymeha.thegame.components.TransformComponent;
import com.neymeha.thegame.utils.ZComparator;

import java.util.Comparator;

import static com.neymeha.thegame.utils.GameConfig.*;

public class RenderingSystem extends SortedIteratingSystem {
    /*
    PPM в GameConfig
    FRUSTUM_WIDTH
    FRUSTUM_HEIGHT
    PIXELS_TO_METRES
    тоже поехали в GameConfig
    */
//    static final float PPM = 32.0f;
//    static final float FRUSTUM_WIDTH = Gdx.graphics.getWidth()/PPM;
//    static final float FRUSTUM_HEIGHT = Gdx.graphics.getHeight()/PPM;
//    public static final float PIXELS_TO_METRES = 1.0f / PPM;

    private static Vector2 meterDimensions = new Vector2();
    private static Vector2 pixelDimensions = new Vector2();
    /*
    Статический метод для получения размера экрана в метрах
    */
    public static Vector2 getScreenSizeInMeters(){
        meterDimensions.set(Gdx.graphics.getWidth()*PIXELS_TO_METRES,
                Gdx.graphics.getHeight()*PIXELS_TO_METRES);
        return meterDimensions;
    }

    /*
    Статический метод для получения размера экрана в пикселях
    */
    public static Vector2 getScreenSizeInPixesl(){
        pixelDimensions.set(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        return pixelDimensions;
    }

    /*
    Конвертор пикселей в метры
    */
    public static float PixelsToMeters(float pixelValue){
        return pixelValue * PIXELS_TO_METRES;
    }

    private SpriteBatch batch; // ссылка для хранения нашего батча который мы передаем в конструкторе
    private Array<Entity> renderQueue; // массив позволяющий сортировать картинки позволяющий отрисовать их одну над другой
    private Comparator<Entity> comparator; // компоратор для сортировки картинок основанный на Z элементе трасформ компонента
    private OrthographicCamera cam; // ссылка на камеру

    /*
    компонент мапперы для наших компонентов
!!!!    но не уверен что они нужны так как я мапперы оставил в каждом компоненте как статичное поле
    */
    private ComponentMapper<TextureComponent> textureM;
    private ComponentMapper<TransformComponent> transformM;

    @SuppressWarnings("unchecked")
    public RenderingSystem(SpriteBatch batch) {
        /*
        Отправляем в родительский коструктор Family из компонентов которые мы будем итерировать в данной системе
        вторым параметром задаем компортатор который будет сортировать наши сущности в системе для обработки

        Таким образом мы передаем в систему все Entity которые обладают обоими компонентами и эти сущности будут отсо
        ртированы ZComporator'ом

        Family.all(TransformComponent.class, TextureComponent.class).get() - это вызов метода all() класса Family,
        который принимает в качестве аргументов классы компонентов. В данном случае, мы указываем, что сущность должна
        иметь как компонент TransformComponent, так и компонент TextureComponent. Метод get() возвращает объект Family,
        который представляет собой группу компонентов, которые должны присутствовать у сущности, чтобы она была
        обработана этой системой.
        */
        super(Family.all(TransformComponent.class, TextureComponent.class).get(), new ZComparator());

        /*
        Создаем наши компонент мапперы
    !!!!    что возможно лишние из за того что компоненты уже имеют такое статичное поле
        */
        textureM = ComponentMapper.getFor(TextureComponent.class);
        transformM = ComponentMapper.getFor(TransformComponent.class);

        // инициализируем массив который будет нашей отсортированной очередью
        renderQueue = new Array<Entity>();

        this.batch = batch;  // присваиваем батч

        // настраиваем камеру так что бы она подходила под размеры экрана
        cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        cam.position.set(FRUSTUM_WIDTH / 2f, FRUSTUM_HEIGHT / 2f, 0);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        // сортировка очереди на основе Z индекса
        renderQueue.sort(comparator);

        // update camera and sprite batch
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        batch.enableBlending();
        batch.begin();

        // loop through each entity in our render queue
        for (Entity entity : renderQueue) {
            TextureComponent tex = textureM.get(entity);
            TransformComponent t = transformM.get(entity);

            if (tex.region == null || t.isHidden) {
                continue; // переходим к следующей сущности если у текстуры регион пуст и если трансформ спрятан
            }


            float width = tex.region.getRegionWidth();
            float height = tex.region.getRegionHeight();

            float originX = width/2f;
            float originY = height/2f;

            /*
            Отрисовываем текстуру tex.region
            не совсем понимаю почему от координат мы отнимаем половину высоты и ширины
            далее задаем центр текстуры получается?
            высоту и ширину
            масштаб по Х и по Y который перевели в метры
            и задали угол поворота
            */
            batch.draw(tex.region,
                    t.position.x - originX, t.position.y - originY,
                    originX, originY,
                    width, height,
                    PixelsToMeters(t.scale.x), PixelsToMeters(t.scale.y),
                    t.rotation);
        }

        batch.end();
        renderQueue.clear();
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }

    // convenience method to get camera
    public OrthographicCamera getCamera() {
        return cam;
    }
}

