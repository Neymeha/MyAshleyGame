package com.neymeha.thegame.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class BodyFactory {
    private World world; // зависимость от мира, ведь тела отдельно от него не существуют
    private static BodyFactory thisInstance; // наш инстанс для синглтона
    /*
    Константы для свич оператора и для метода создания Fixture, нужно для того что бы мы спокойной могли создавать
    различные типы фиксчур. Возможно есть смысл их в энумы перевести
    */
    public static final int STEEL = 0;
    public static final int WOOD = 1;
    public static final int RUBBER = 2;
    public static final int STONE = 3;
    private final float DEGTORAD = 0.0174533f; // константа для перевода в радианы

    private BodyFactory(World world) { // приватный конструктор ведь мы делаем синглтон
        this.world = world;
    }

    public static BodyFactory getInstance(World world){ // базовый код под синглтон, не подходит под многопоток
        if(thisInstance == null){
            thisInstance = new BodyFactory(world);
        }
        return thisInstance;
    }

    /*
    Метод для создания типизировных фиксчур
    */
    static public FixtureDef makeFixture(int material, Shape shape){
        FixtureDef fixtureDef = new FixtureDef(); // инициализиурем обект фиксчуры
        fixtureDef.shape = shape; // задаем ей форму введенную в параметр метода

        switch(material){ // через оператор свич согласно введенному параметру устанавливаем свойства фиксчуры
            case STEEL:
                /*
                1)Плотность определяет, насколько массивным является объект.
                2)Коэффициент трения определяет, насколько объект скользит по поверхности.
                3)Коэффициент упругости определяет, насколько объект отскакивает при столкновении.
                */
                fixtureDef.density = 1f;
                fixtureDef.friction = 0.3f;
                fixtureDef.restitution = 0.1f;
                break;
            case WOOD:
                fixtureDef.density = 0.5f;
                fixtureDef.friction = 0.7f;
                fixtureDef.restitution = 0.3f;
                break;
            case RUBBER:
                fixtureDef.density = 1f;
                fixtureDef.friction = 0f;
                fixtureDef.restitution = 1f;
                break;
            case STONE:
                fixtureDef.density = 1f;
                fixtureDef.friction = 0.9f;
                fixtureDef.restitution = 0.01f;
            default:
                fixtureDef.density = 7f;
                fixtureDef.friction = 0.5f;
                fixtureDef.restitution = 0.3f;
        }
        return fixtureDef;
    }

    /*
    Метод для создания физических тел по типу круга
    */
    public Body makeCirclePolyBody(float posx, float posy, float radius, int material, BodyDef.BodyType bodyType, boolean fixedRotation){
        /*
        Для создания тела на нужно задать телу определение, его тип (динамический статический кинетический), позицию,
        радиус в нашем случае, и будет ли оно двигаться вокруг своей оси - катиться
        */
        BodyDef boxBodyDef = new BodyDef(); // инициализация обьекта определяющая основные характеристики тела
        boxBodyDef.type = bodyType; // тип тела
        boxBodyDef.position.x = posx; // местоположение по оси Х
        boxBodyDef.position.y = posy; // местоположение по оси У
        /*
        Когда значение fixedRotation установлено в true, объект будет оставаться неподвижным при воздействии силы
        вращения. Это означает, что объект не будет поворачиваться вокруг своего центра масс, даже если на него будет
        действовать вращающая сила.
        */
        boxBodyDef.fixedRotation = fixedRotation;

        /*
        Создаем тело задавая ему форму(тип, радиус в нашем случае) и с помощью тела создаем фиксчуру использую наш
        кастомный метод передавая в него нужные аргументы
        */
        Body boxBody = world.createBody(boxBodyDef); // создаем тело в мире по заданым параметрам в боди дефинишн
        CircleShape circleShape = new CircleShape(); // создаем форму тела
        circleShape.setRadius(radius /2); // устанавливаем радиус нашей форме тела
        boxBody.createFixture(makeFixture(material,circleShape)); // создаем у тела нашу фиксчуру задавая тип материала тела и его форму
        circleShape.dispose(); // освобождаем память от уже не нужной формы
        return boxBody;// выдаем готовое тело
    }

    // overload метод без fixedRotation по дефолту ставящий false
    public Body makeCirclePolyBody(float posx, float posy, float radius, int material, BodyDef.BodyType bodyType){
        return makeCirclePolyBody( posx,  posy,  radius,  material,  bodyType,  false);
    }

    // overload метод без fixedRotation по дефолту ставящий false и устанавливающий динамический тип тела по дефолту
    public Body makeCirclePolyBody(float posx, float posy, float radius, int material){
        return makeCirclePolyBody( posx,  posy,  radius,  material,  BodyDef.BodyType.DynamicBody,  false);
    }

    /*
    Такой же метод как и для круга, только форма которую мы устанавливаем - box с помощью PolygonShape
    */
    public Body makeBoxPolyBody(float posx, float posy, float width, float height, int material, BodyDef.BodyType bodyType, boolean fixedRotation){
        BodyDef boxBodyDef = new BodyDef();
        boxBodyDef.type = bodyType;
        boxBodyDef.position.x = posx;
        boxBodyDef.position.y = posy;
        boxBodyDef.fixedRotation = fixedRotation;

        Body boxBody = world.createBody(boxBodyDef);
        PolygonShape poly = new PolygonShape();
        poly.setAsBox(width/2, height/2);
        boxBody.createFixture(makeFixture(material,poly));
        poly.dispose();

        return boxBody;
    }

    // overloaded with no fixed rotation and default false
    public Body makeBoxPolyBody(float posx, float posy, float width, float height,int material, BodyDef.BodyType bodyType){
        return makeBoxPolyBody(posx, posy, width, height, material, bodyType, false);
    }

    /*
    Метод для создания физических тел со сложной формой, он принимает массив из обьектов Вектор2, каждый из которых
    содержит две координаты Х и У (если будет массив из 5 обьектов - 5 угольник)
    */
    public Body makePolygonShapeBody(Vector2[] vertices, float posx, float posy, int material, BodyDef.BodyType bodyType){
        // тут все как обычно
        BodyDef boxBodyDef = new BodyDef();
        boxBodyDef.type = bodyType;
        boxBodyDef.position.x = posx;
        boxBodyDef.position.y = posy;
        Body boxBody = world.createBody(boxBodyDef);

        PolygonShape polygon = new PolygonShape();
        polygon.set(vertices); // единственное отличие от остальных методов что мы передаем массив координат в полигон для задания формы
        boxBody.createFixture(makeFixture(material,polygon));
        polygon.dispose();

        return boxBody;
    }

    /*
    Метод для создания конусного сенсора с дальнейшим использованием его для обнаружения столкновений и взаимодействия
    с обьектами в физическом мире
    */
    public void makeConeSensor(Body body, float size){

        FixtureDef fixtureDef = new FixtureDef(); // инициализируем обьект фиксчур дефенишн
        //fixtureDef.isSensor = true; // will add in future

        PolygonShape polygon = new PolygonShape(); // инициализурем наш полигон

        float radius = size; // наш раидус для конуса
        Vector2[] vertices = new Vector2[5]; // создаем массив вершин для конуса
        vertices[0] = new Vector2(0,0); // задаем первую вершину
        for (int i = 2; i < 6; i++) { // вычисляем координаты остальных вершин конуса
            float angle = (float) (i  / 6.0 * 145 * DEGTORAD); // преобразование градусов в радианы
            vertices[i-1] = new Vector2( radius * ((float)Math.cos(angle)), radius * ((float)Math.sin(angle)));
        }
        polygon.set(vertices); // задаем вершины в наш полигон шейп
        fixtureDef.shape = polygon; // устанавливаем форму
        body.createFixture(fixtureDef); // создаем физическое тело с использованием фиксчур дефенишн
        polygon.dispose();
    }

    /*
    Метод который:
    для каждой фикстуры вызывается метод setSensor(true), который устанавливает фикстуру в режим "сенсора".В режиме
    "сенсора" физическая фикстура не взаимодействует с другими объектами, но все еще может обнаруживать столкновения.
    */
    public void makeAllFixturesSensors(Body bod){
        for(Fixture fix :bod.getFixtureList()){
            fix.setSensor(true);
        }
    }
}
