package com.mygdx.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class Audio {

    public static final Sound dropSound = Gdx.audio.newSound(Gdx.files.internal("Audio/WaterSound.wav"));;
    public static final Music rainMusic = Gdx.audio.newMusic(Gdx.files.internal("Audio/RainMusic.mp3"));
    public static final Sound clickButton = Gdx.audio.newSound(Gdx.files.internal("Audio/click.wav"));

}
