package com.example.passwordproject;

import java.util.Calendar;
import java.util.Locale;

public class DayRule implements MainActivity.PasswordRule{
    @Override
    public String getHint() {
        return "Must contain the name of the current day.";
    }

    @Override
    public boolean validate(String text) {
        String day = Calendar.getInstance().getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        return text.toLowerCase().contains(day.toLowerCase());
    }
}
