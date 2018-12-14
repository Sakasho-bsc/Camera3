package com.example.sakasho1.camera3;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SePlayer {
    private SoundPool soundPool;
    private int se;

    public SePlayer(Context context) {
        this.soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        this.se = soundPool.load(context, R.raw.se_maoudamashii_system48, 1);}


    public void playSe() {
        soundPool.play(se,1.0f,1.0f,1,0,1.0f);
    }

}



