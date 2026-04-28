package com.example.passwordproject;

public class ForbiddenWord implements MainActivity.PasswordRule {
    private String forbidden;

    public ForbiddenWord(String forbidden) {
        this.forbidden = forbidden;
    }

    @Override
    public String getHint() {
        return "Your password cannot contain the word: " + forbidden;
    }

    @Override
    public boolean validate(String text) {
        return !text.toLowerCase().contains(forbidden.toLowerCase());
    }
}
