package com.example.passwordproject;

public class MonthRule implements MainActivity.PasswordRule{

    @Override
    public String getHint() {
        return "The password must include a month of the year.";
    }

    @Override
    public boolean validate(String text) {
        String[] months = {"January", "February","March","April","May",
        "June", "July", "August", "September","October","November","December"};
        for(String month : months){
            if(text.toLowerCase().contains(month.toLowerCase())){
                return true;
            }
        }
        return false;
    }
}
