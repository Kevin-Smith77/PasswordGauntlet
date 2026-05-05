package com.example.passwordproject;

import android.content.Context;
import android.media.AudioManager;

public class VolumeRule implements MainActivity.PasswordRule{
    private Context context;
    public VolumeRule(Context c){
        this.context=c;
    }

    @Override
    public String getHint() {
        return "Must include your current volume level.";
    }

    @Override
    public boolean validate(String text) {
        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int vol = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        return text.contains(String.valueOf(vol));
    }
}
