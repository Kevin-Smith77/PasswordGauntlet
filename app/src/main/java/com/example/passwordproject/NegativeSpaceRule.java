package com.example.passwordproject;

public class NegativeSpaceRule implements MainActivity.PasswordRule{
    @Override
    public String getHint() {
        return "Cannot contain any letters from the word 'ESCAPE'.";
    }

    @Override
    public boolean validate(String text) {
        String forbidden = "escape";
        for(char c : text.toLowerCase().toCharArray()){
            if(forbidden.indexOf(c) != -1){
                return false;
            }
        }
        return true;
    }
}
