package com.example.passwordproject;

import java.util.Calendar;

public class CurrentMinute implements MainActivity.PasswordRule {
    @Override
    public String getHint() {
        return "The password must contain the current minute of the hour. e.g. 45 in 2:45";
    }

    @Override
    public boolean validate(String text) {
        int minute = Calendar.getInstance().get(Calendar.MINUTE);
        String minuteString = String.valueOf(minute);
        return text.contains(minuteString);
    }
}
