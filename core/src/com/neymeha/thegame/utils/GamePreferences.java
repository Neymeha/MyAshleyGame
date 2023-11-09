package com.neymeha.thegame.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;


/*
Класс хранилище для настроек приложения
*/
public class GamePreferences {
    /*
    Ниже наши ключи для хранимых значений
    */
    private static final String PREF_MUSIC_VOLUME = "volume";
    private static final String PREF_MUSIC_ENABLED = "music.enabled";
    private static final String PREF_SOUND_ENABLED = "sound.enabled";
    private static final String PREF_SOUND_VOL = "sound";
    private static final String PREFS_NAME = "b2dtut"; // по факту это название для нашей мапы в которой будут храниться наши ключь значения


    protected Preferences getPrefs() { // получаем нашу мапу
        return Gdx.app.getPreferences(PREFS_NAME);
    }

    public boolean isMusicEnabled(){
        return getPrefs().getBoolean(PREF_MUSIC_ENABLED, true);
    }

    public boolean isSoundEffectsEnabled(){
        return getPrefs().getBoolean(PREF_SOUND_ENABLED, true);
    }

    public float getSoundVolume(){
        return getPrefs().getFloat(PREF_SOUND_VOL, 0.5f);
    }

    public float getMusicVolume() {
        return getPrefs().getFloat(PREF_MUSIC_VOLUME, 0.5f); // достаем из мапы значение, если его нет достаем дефолтное
    }

    public void setMusicEnabled(boolean musicEnabled){
        getPrefs().putBoolean(PREF_MUSIC_ENABLED, musicEnabled);
        getPrefs().flush();
    }

    public void setSoundEffectsEnabled(boolean soundEnabled){
        getPrefs().putBoolean(PREF_SOUND_ENABLED, soundEnabled);
        getPrefs().flush();
    }

    public void setSoundVolume(float volume){
        getPrefs().putFloat(PREF_SOUND_VOL, volume);
        getPrefs().flush();
    }

    public void setMusicVolume(float volume) {
        getPrefs().putFloat(PREF_MUSIC_VOLUME, volume); // кладем в нашу мапу с настройками установленный нами volume
        getPrefs().flush(); // сохраняем значение на диск
    }
}
