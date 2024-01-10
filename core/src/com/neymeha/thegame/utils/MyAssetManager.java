package com.neymeha.thegame.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class MyAssetManager {
    /*
    Собственно сам ассет менеджер
    */
    private final AssetManager manager = new AssetManager();
    /*
    Линки на мои текстуры
    */
    // Textures
    public final String gameImages = "images/game.atlas";
    public final String loadingImages = "images/loading.atlas";
    // звуки
    public final String boingSound = "sounds/boing.wav";
    public final String pingSound = "sounds/ping.wav";
    // Music
    public final String playingSong = "music/Rolemusic_-_pl4y1ng.mp3";
    // Skin
    public final String skin = "skin/glassy-ui.json";
    public final String skinAtlas = "skin/glassy-ui.atlas";

    /*
    Метод в котором мы говорим менджеру какие именно ресурсы мы хотим загрузить.
    */
    public void queueAddImages(){
        manager.load(gameImages, TextureAtlas.class);
    }

    public void queueAddLoadingImages(){
        manager.load(loadingImages, TextureAtlas.class);
    }

    public void queueAddSounds(){
        manager.load(boingSound, Sound.class);
        manager.load(pingSound, Sound.class);
    }

    public void queueAddMusic(){
        manager.load(playingSong, Music.class);
    }

    public void queueAddSkin(){
        SkinLoader.SkinParameter params = new SkinLoader.SkinParameter(skinAtlas);
        manager.load(skin, Skin.class, params);
    }

    public void queueAddFonts(){
    }

    public void queueAddParticleEffects(){
    }

    public void finishLoading(){
        manager.finishLoading();
    }

    public TextureAtlas getGameAtlas(){
        return manager.get(gameImages); // ищем текстурный атлас
    }

    public TextureAtlas getLoadingAtlas(){
        return manager.get(loadingImages);
    }

    public Music getGameMusic(){
        return manager.get(playingSong);
    }

    public Skin getGameSkin(){
        return manager.get(skin);
    }

    public boolean update(){
        return manager.update();
    }

    public void dispose(){
        manager.dispose();
    }
}
