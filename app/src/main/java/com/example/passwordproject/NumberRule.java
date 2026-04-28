package com.example.passwordproject;

public class NumberRule implements MainActivity.PasswordRule {
    @Override
    public String getHint(){
        return "Must contain at least one digit.";
    }
    @Override
    public boolean validate(String text){
        return text.matches(".*\\d.*");
    }
}
