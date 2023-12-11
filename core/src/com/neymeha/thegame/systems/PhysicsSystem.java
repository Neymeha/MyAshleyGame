package com.neymeha.thegame.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.neymeha.thegame.components.BodyComponent;
import com.neymeha.thegame.components.TransformComponent;

import static com.neymeha.thegame.utils.GameConfig.MAX_STEP_TIME;

/*
 класс для обработки физики
*/
public class PhysicsSystem extends IteratingSystem {
    /*
    перенесено в гейм конфиг
    */
//    private static final float MAX_STEP_TIME = 1/45f;
    private static float accumulator = 0f;
    private World world; // наш мир в котором происходит взаимодействие
    private Array<Entity> bodiesQueue; // наша очередь из тел в этом мире

    /*
     мапперы для быстрого доступа к указанным компонентам из заданной энтити
    */
    private ComponentMapper<BodyComponent> bm = ComponentMapper.getFor(BodyComponent.class);
    private ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);

    @SuppressWarnings("unchecked")
    public PhysicsSystem(World world) {
        // только энтити с наличием компонентов тел и передвижения/положения
        super(Family.all(BodyComponent.class, TransformComponent.class).get());
        // инициализируем нужные нам обьекты
        this.world = world;
        this.bodiesQueue = new Array<Entity>();
    }

    /*
    код для реальной обработки данных изменения в позициях и телах
    */
    @Override
    public void update(float deltaTime) {
        /*
        вообще не каноничное написание так как перезаписан метод update, но сами разработики указывают на то что
        логика для обновления системы должна быть помещена в метод !!! updateInterval. однако такое написание особо
        ничего не меняет.
        */
        super.update(deltaTime);
        /*
        Затем переменная frameTime вычисляется как минимальное значение между deltaTime и 0.25f. Это ограничивает
        время кадра, чтобы избежать слишком больших значений и сглаживания движения.
        */
        float frameTime = Math.min(deltaTime, 0.25f);
        accumulator += frameTime; // аккумулируем время обновления
        /*
        далее проверка если аккумулятор больше 1/45 Зачем я не понял но если частота обновления очень высокая то ок
        а если слишком медленная то мы установили определенный порог значений
        */
        if(accumulator >= MAX_STEP_TIME) {
            /*
             выполняем физическое обновления мира с шагом по времени заданым, с заданной точностью, и количесвом итераций
            */
            world.step(MAX_STEP_TIME, 6, 2);
            accumulator -= MAX_STEP_TIME;

            /*
            очередь из наш энтити/сущностей
            в которой мы достаем у сущности у которой будем обновлять значения трансформ компонент и боди компонент
            далее ново созданной переменной Вектор 2 присваиваем позицию физического тела
            а дальше нашему трасформ компоненту меняем позицию по XY согласно нашего физического тела
            это нужно для адекватного обновления анимации/текстур
            Также вращение физического тела преобразуется в градусы и присваивается tfm.rotation.
            */
            for (Entity entity : bodiesQueue) {
                TransformComponent tfm = tm.get(entity);
                BodyComponent bodyComp = bm.get(entity);
                Vector2 position = bodyComp.body.getPosition();
                tfm.position.x = position.x;
                tfm.position.y = position.y;
                tfm.rotation = bodyComp.body.getAngle() * MathUtils.radiansToDegrees;
            }
        }
        /*
        После завершения цикла, очередь bodiesQueue очищается с помощью bodiesQueue.clear(). Это гарантирует, что
        сущности, которые были обновлены, больше не будут обрабатываться в следующем обновлении.
        */
        bodiesQueue.clear();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        bodiesQueue.add(entity);
    }
}
