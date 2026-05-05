package com.example.passwordproject;

import android.os.SystemClock;

public class UptimeRule implements MainActivity.PasswordRule {
    @Override
    public String getHint() {
        return "The password must contain the number of minutes the device has been awake.";
    }

    @Override
    public boolean validate(String text) {
        // Gets milliseconds since boot, converts to minutes
        long minutes = (SystemClock.elapsedRealtime() / 1000) / 60;
        return text.contains(String.valueOf(minutes));
    }
}
