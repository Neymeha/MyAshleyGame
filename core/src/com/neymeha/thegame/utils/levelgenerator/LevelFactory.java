package com.neymeha.thegame.utils.levelgenerator;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.neymeha.thegame.GameCore;
import com.neymeha.thegame.components.*;
import com.neymeha.thegame.utils.BodyFactory;
import com.neymeha.thegame.utils.DFUtils;
import com.neymeha.thegame.utils.MyContactListener;

public class LevelFactory {
    private BodyFactory bodyFactory;
    public World world;
    private PooledEngine engine;
    private SimplexNoise sim;
    public int currentLevel = 0;
    private TextureRegion floorTex;

    public LevelFactory(PooledEngine en, TextureRegion floorTexture){
        engine = en;
        floorTex = floorTexture;
        world = new World(new Vector2(0,-10f), true);
        world.setContactListener(new MyContactListener());
        bodyFactory = BodyFactory.getInstance(world);
        // create a new SimplexNoise (size,roughness,seed)
        sim = new SimplexNoise(512, 0.85f, 1);
    }


    /** Creates a pair of platforms per level up to yLevel
     * @param ylevel
     */
    public void generateLevel(int ylevel){
        /*
        изменение генерации с целью добавления пружин на определенные платформы а так же доп код для генерации врагов
        на платформах. Не совсем понимаю что мы тут высчитываем
        !!! СТОИТ РАЗОБРАТЬСЯ
        */
        while(ylevel > currentLevel){
            // get noise      sim.getNoise(xpos,ypos,zpos) 3D noise
            float noise1 = (float)sim.getNoise(1, currentLevel, 0);		// platform 1 should exist?
            float noise2 = (float)sim.getNoise(1, currentLevel, 100);	    // if plat 1 exists where on x axis
            float noise3 = (float)sim.getNoise(1, currentLevel, 200);	    // platform 2 exists?
            float noise4 = (float)sim.getNoise(1, currentLevel, 300);	    // if 2 exists where on x axis ?
            float noise5 = (float)sim.getNoise(1, currentLevel ,1400);	// should spring exist on p1?
            float noise6 = (float)sim.getNoise(1, currentLevel ,2500);	// should spring exists on p2?
            float noise7 = (float)sim.getNoise(1, currentLevel, 2700);	// should enemy exist?
            float noise8 = (float)sim.getNoise(1, currentLevel, 3000);	// platform 1 or 2?
            /*
            !!!! с использованием алгоритмов тоже есть проблема, есть место где платформ вообще нет почему то, так что
            !!!! невозможно пройти дальше
            */
            if(noise1 > 0.2f){
                createPlatform(noise2 * 25 +2 ,currentLevel * 2);
                if(noise5 > 0.5f){
                    // add bouncy platform
                    createBouncyPlatform(noise2 * 25 +2,currentLevel * 2);
                }
            }
            if(noise3 > 0.2f){
                createPlatform(noise4 * 25 +2, currentLevel * 2);
                if(noise6 > 0.4f){
                    // add bouncy platform
                    createBouncyPlatform(noise4 * 25 +2,currentLevel * 2);
                    /*
                    !!! Что то не так, код явно работает но платформы я не видел. возможно потому что моя камера ограничена
                    !!! а платформы создаются вне ее видимости тоже. Надо будет потом подумать проверить
                    */
                }
            }
            currentLevel++;
        }
    }


    public Entity createBouncyPlatform(float x, float y){
        Entity entity = engine.createEntity();
        // create body component
        BodyComponent b2dbody = engine.createComponent(BodyComponent.class);
        b2dbody.body = bodyFactory.makeBoxPolyBody(x, y, .5f, 0.5f, BodyFactory.STONE, BodyType.StaticBody);
        //make it a sensor so not to impede movement
        bodyFactory.makeAllFixturesSensors(b2dbody.body);

        // give it a texture..todo get another texture and anim for springy action
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.region = floorTex;

        TypeComponent type = engine.createComponent(TypeComponent.class);
        type.type = TypeComponent.SPRING;

        b2dbody.body.setUserData(entity);
        entity.add(b2dbody);
        entity.add(texture);
        entity.add(type);
        engine.addEntity(entity);

        return entity;
    }

    public void createPlatform(float x, float y){
        Entity entity = engine.createEntity();
        BodyComponent b2dbody = engine.createComponent(BodyComponent.class);
        b2dbody.body = bodyFactory.makeBoxPolyBody(x, y, 1.5f, 0.2f, BodyFactory.STONE, BodyType.StaticBody);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.region = floorTex;
        TypeComponent type = engine.createComponent(TypeComponent.class);
        type.type = TypeComponent.SCENERY;
        b2dbody.body.setUserData(entity);
        entity.add(b2dbody);
        entity.add(texture);
        entity.add(type);
        engine.addEntity(entity);

    }

    public void createFloor(TextureRegion tex){
        Entity entity = engine.createEntity();
        BodyComponent b2dbody = engine.createComponent(BodyComponent.class);
        b2dbody.body = bodyFactory.makeBoxPolyBody(0, 0, 100, 0.2f, BodyFactory.STONE, BodyType.StaticBody);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.region = tex;
        TypeComponent type = engine.createComponent(TypeComponent.class);
        type.type = TypeComponent.SCENERY;

        b2dbody.body.setUserData(entity);

        entity.add(b2dbody);
        entity.add(texture);
        entity.add(type);

        engine.addEntity(entity);
    }

    public Entity createPlayer(TextureRegion tex, OrthographicCamera cam){

        Entity entity = engine.createEntity();
        BodyComponent b2dbody = engine.createComponent(BodyComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        PlayerComponent player = engine.createComponent(PlayerComponent.class);
        CollisionComponent colComp = engine.createComponent(CollisionComponent.class);
        TypeComponent type = engine.createComponent(TypeComponent.class);
        StateComponent stateCom = engine.createComponent(StateComponent.class);


        player.cam = cam;
        b2dbody.body = bodyFactory.makeCirclePolyBody(10,1,1, BodyFactory.STONE, BodyType.DynamicBody,true);
        // set object position (x,y,z) z used to define draw order 0 first drawn
        position.position.set(10,1,0);
        texture.region = tex;
        type.type = TypeComponent.PLAYER;
        stateCom.set(StateComponent.STATE_NORMAL);
        b2dbody.body.setUserData(entity);

        entity.add(b2dbody);
        entity.add(position);
        entity.add(texture);
        entity.add(player);
        entity.add(colComp);
        entity.add(type);
        entity.add(stateCom);

        engine.addEntity(entity);
        return entity;
    }

    /**
     * Creates the water entity that steadily moves upwards towards player
     * @return
     */
    /*
    !!!! застряет в верхнем слое воды и движется на ней, надо разобраться
    */
    public Entity createWaterFloor(TextureRegion tex){
        Entity entity = engine.createEntity();
        BodyComponent b2dbody = engine.createComponent(BodyComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        TypeComponent type = engine.createComponent(TypeComponent.class);
        WaterFloorComponent waterFloor = engine.createComponent(WaterFloorComponent.class);

        type.type = TypeComponent.ENEMY;
        texture.region = tex;
        b2dbody.body = bodyFactory.makeBoxPolyBody(20,-15,40,10, BodyFactory.STONE, BodyType.KinematicBody,true);
        position.position.set(20,-15,0);
        entity.add(b2dbody);
        entity.add(position);
        entity.add(texture);
        entity.add(type);
        entity.add(waterFloor);

        b2dbody.body.setUserData(entity);

        engine.addEntity(entity);

        return entity;
    }

    public void createWalls(TextureRegion tex){

        for(int i = 0; i < 2; i++){
            System.out.println("Making wall "+i);
            Entity entity = engine.createEntity();
            BodyComponent b2dbody = engine.createComponent(BodyComponent.class);
            TransformComponent position = engine.createComponent(TransformComponent.class);
            TextureComponent texture = engine.createComponent(TextureComponent.class);
            TypeComponent type = engine.createComponent(TypeComponent.class);
            WallComponent wallComp = engine.createComponent(WallComponent.class);

            //make wall
            b2dbody.body = b2dbody.body = bodyFactory.makeBoxPolyBody(0+(i*40),30,1,60, BodyFactory.STONE, BodyType.KinematicBody,true);
            position.position.set(0+(i*40), 30, 0);
            texture.region = tex;
            type.type = TypeComponent.SCENERY;

            entity.add(b2dbody);
            entity.add(position);
            entity.add(texture);
            entity.add(type);
            entity.add(wallComp);
            b2dbody.body.setUserData(entity);

            engine.addEntity(entity);
        }
    }

    public void customCreateFloor(int floorWidth, int floorHeight, String floorColorCode){
        TextureRegion floorRegion = DFUtils.makeTextureRegion(floorWidth, floorHeight, floorColorCode);
        createFloor(floorRegion);
    }

    public void customCreateWaterFloor(int waterFloorWidth, int waterFloorHeight, String waterFloorColorCode){
        TextureRegion waterFloorRegion = DFUtils.makeTextureRegion(waterFloorWidth, waterFloorHeight, waterFloorColorCode);
        createWaterFloor(waterFloorRegion);

    }

    public void customCreateWall(int floorWidth, int floorHeight, String floorColorCode){
        TextureRegion floorRegion = DFUtils.makeTextureRegion(floorWidth, floorHeight, floorColorCode);
        createWalls(floorRegion);
    }
}
