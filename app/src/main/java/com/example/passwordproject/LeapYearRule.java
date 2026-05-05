package com.example.passwordproject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LeapYearRule implements MainActivity.PasswordRule{

    @Override
    public String getHint() {
        return "Your password must contain a leap year.";
    }

    @Override
    public boolean validate(String text) {
        Pattern p = Pattern.compile("\\d{4}");
        Matcher m = p.matcher(text);
        while(m.find()){
            int year = Integer.parseInt(m.group());
            if((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)){
                return true;
            }
        }
        return false;
    }
}
