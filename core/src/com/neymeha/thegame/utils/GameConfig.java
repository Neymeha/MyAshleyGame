package com.neymeha.thegame.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class GameConfig {
    public static final int GAME_WIDTH = 600;
    public static final int GAME_HEIGHT = 800;
    public static final int PPM = 25; // пиксели на каждый метр - нужно для преобразования физического мира относительно нашего экрана
    /*
    Ниже наши ключи для хранимых значений в Preferences
    */
    private static final String PREF_MUSIC_VOLUME = "volume";
    private static final String PREF_MUSIC_ENABLED = "music.enabled";
    private static final String PREF_SOUND_ENABLED = "sound.enabled";
    private static final String PREF_SOUND_VOL = "sound";
    private static final String PREFS_NAME = "b2dtut"; // по факту это название для нашей мапы в которой будут храниться наши ключь значения

    protected static Preferences getPreferences() { // получаем нашу мапу
        return Gdx.app.getPreferences(PREFS_NAME);
    }

    public static boolean isMusicEnabled(){
        return getPreferences().getBoolean(PREF_MUSIC_ENABLED, true);
    }

    public static boolean isSoundEnabled(){
        return getPreferences().getBoolean(PREF_SOUND_ENABLED, true);
    }

    public static float getSoundVolume(){
        return getPreferences().getFloat(PREF_SOUND_VOL, 0.5f);
    }

    public static float getMusicVolume() {
        return getPreferences().getFloat(PREF_MUSIC_VOLUME, 0.5f); // достаем из мапы значение, если его нет достаем дефолтное
    }

    public static void setMusicEnabled(boolean musicEnabled){
        getPreferences().putBoolean(PREF_MUSIC_ENABLED, musicEnabled);
        getPreferences().flush();
    }

    public static void setSoundEnabled(boolean soundEnabled){
        getPreferences().putBoolean(PREF_SOUND_ENABLED, soundEnabled);
        getPreferences().flush();
    }

    public static void setSoundVolume(float volume){
        getPreferences().putFloat(PREF_SOUND_VOL, volume);
        getPreferences().flush();
    }

    public static void setMusicVolume(float volume) {
        getPreferences().putFloat(PREF_MUSIC_VOLUME, volume); // кладем в нашу мапу с настройками установленный нами volume
        getPreferences().flush(); // сохраняем значение на диск
    }
}
