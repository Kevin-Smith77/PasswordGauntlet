package com.example.passwordproject;

public class LengthRule implements MainActivity.PasswordRule {
    private int min;
    public LengthRule(int min){
        this.min=min;
    }
    @Override
    public String getHint(){
        return "Must be at least " + min + " characters.";
    }
    @Override
    public boolean validate(String text){
        return text.length() >= min;
    }
}
