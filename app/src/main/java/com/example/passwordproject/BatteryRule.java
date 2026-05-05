package com.example.passwordproject;

import android.content.Context;
import android.os.BatteryManager;

public class BatteryRule implements MainActivity.PasswordRule{
    private Context context;

    public BatteryRule(Context context){
        this.context = context;
    }

    @Override
    public String getHint() {
        return "Your password must include your current battery percentage.";
    }

    @Override
    public boolean validate(String text) {
        BatteryManager bm = (BatteryManager) context.getSystemService(Context.BATTERY_SERVICE);
        if (bm != null) {
            int percentage = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
            // Check if percentage is valid (usually 0-100)
            if (percentage >= 0 && percentage <= 100) {
                return text.contains(String.valueOf(percentage));
            }
        }
        return false;
    }
}
