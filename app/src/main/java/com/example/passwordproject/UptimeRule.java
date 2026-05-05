package com.example.passwordproject;

import android.os.SystemClock;

public class UptimeRule implements MainActivity.PasswordRule {
    @Override
    public String getHint() {
        return "The password must contain the number of minutes the device has been awake." +
                "(find the milliseconds by going to Settings > About Phone > Status > Uptime)";
    }

    @Override
    public boolean validate(String text) {
        // 1. Get milliseconds
        long millis = SystemClock.elapsedRealtime();

        // 2. Convert to minutes: (ms / 1000) / 60
        long minutes = (millis / 1000) / 60;

        // 3. Check if the password contains this number
        return text.contains(String.valueOf(minutes));
    }
}
