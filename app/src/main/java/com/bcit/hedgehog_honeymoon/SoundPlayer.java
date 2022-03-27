package com.bcit.hedgehog_honeymoon;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundPlayer {

    private static SoundPool soundPool;
    private static int hitSound;

    public SoundPlayer(Context context) {
        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        hitSound = soundPool.load(context, R.raw.can_pop, 1);
    }

    public void playHitSound() {
        soundPool.play(hitSound, 2.0f, 2.0f, 1, 0, 0.8f);
    }
}
