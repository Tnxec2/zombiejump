package com.mygdx.zombiejump.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioUtils
{
    
    private static AudioUtils ourInstance = new AudioUtils();
    private static Music music;
    private static Sound jumpSound;
    private static Sound hitSound;
    private static Sound shotgunSound;
    private static Sound dryfireSound;
    private static Sound reloadSound;
    private static Sound collectcoinSound;

    private static final String MUSIC_ON_PREFERENCE = "music_on";
    private static final String SOUND_ON_PREFERENCE = "sound_on";

    private AudioUtils() { }

    public static AudioUtils getInstance()
    {
        return ourInstance;
    }

    public Music getMusic()
    {
        return music;
    }

    private Preferences getPreferences()
    {
        return Gdx.app.getPreferences(Constants.PREFS_NAME);
    }

    public void init()
    {
        music = Gdx.audio.newMusic(Gdx.files.internal(Constants.AUDIO_GAME_MUSIK));
        music.setLooping(true);
        jumpSound = createSound(Constants.AUDIO_JUMPING_SOUND);
        hitSound = createSound(Constants.AUDIO_HIT_SOUND);
        shotgunSound = createSound(Constants.AUDIO_SHOTGUN_SOUND);
        dryfireSound = createSound(Constants.AUDIO_DRYFIRE_SOUND);
        reloadSound = createSound(Constants.AUDIO_RELOAD_SOUND);
        collectcoinSound = createSound(Constants.AUDIO_COLLECTCOIN_SOUND);
    }

    public Sound createSound(String soundFileName)
    {
        return Gdx.audio.newSound(Gdx.files.internal(soundFileName));
    }

    public void playMusic()
    {
        boolean musicOn = getPreferences().getBoolean(MUSIC_ON_PREFERENCE, true);
        if (musicOn) {
            music.play();
        }
    }

    public void playSound(Sound sound)
    {
        boolean soundOn = getPreferences().getBoolean(SOUND_ON_PREFERENCE, true);
        if (soundOn) {
            sound.play();
        }
    }

    public void toggleMusic()
    {
        saveBoolean(MUSIC_ON_PREFERENCE, !getPreferences().getBoolean(MUSIC_ON_PREFERENCE, true));
    }

    public void toggleSound()
    {
        saveBoolean(SOUND_ON_PREFERENCE, !getPreferences().getBoolean(SOUND_ON_PREFERENCE, true));
    }

    private void saveBoolean(String key, boolean value)
    {
        Preferences preferences = getPreferences();
        preferences.putBoolean(key, value);
        preferences.flush();
    }

    public static void dispose()
    {
        music.dispose();
        jumpSound.dispose();
        hitSound.dispose();
        shotgunSound.dispose();
        dryfireSound.dispose();
        reloadSound.dispose();
        collectcoinSound.dispose();
    }

    public void stopMusic()
    {
        music.stop();
    }

    public void pauseMusic()
    {
        music.pause();
    }

    public String getSoundRegionName()
    {
        boolean soundOn = getPreferences().getBoolean(SOUND_ON_PREFERENCE, true);
        return soundOn ? Constants.SOUND_ON_REGION_NAME : Constants.SOUND_OFF_REGION_NAME;
    }

    public String getMusicRegionName()
    {
        boolean musicOn = getPreferences().getBoolean(MUSIC_ON_PREFERENCE, true);
        return musicOn ? Constants.MUSIC_ON_REGION_NAME : Constants.MUSIC_OFF_REGION_NAME;
    }

    public void playJumpSound()
    {
        playSound(jumpSound);
    }

    public void playHitSound()
    {
        playSound(hitSound);
    }

    public void playShotgunSound()
    {
        playSound(shotgunSound);
    }

    public void playDryfireSound()
    {
        playSound(dryfireSound);
    }

    public void playReloadSound()
    {
        playSound(reloadSound);
    }

    public void playSparkleSound()
    {
        playSound(collectcoinSound);
    }
}