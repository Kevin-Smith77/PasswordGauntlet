package com.example.passwordproject;

import android.content.Context;
import android.provider.Settings;

public class BrightnessRule implements MainActivity.PasswordRule{
    private Context context;
    public BrightnessRule(Context c){
        this.context = c;
    }

    @Override
    public String getHint() {
        return "Must include your current screen brightness level (0-255).";
    }

    @Override
    public boolean validate(String text) {
        try{
            int b = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
            return text.contains(String.valueOf(b));
        } catch (Exception e) {
            return false;
        }

    }
}
