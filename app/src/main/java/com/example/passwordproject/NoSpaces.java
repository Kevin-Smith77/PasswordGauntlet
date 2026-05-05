package com.example.passwordproject;

public class NoSpaces implements MainActivity.PasswordRule{
    @Override
    public String getHint() {
        return "The password must not contain any spaces.";
    }

    @Override
    public boolean validate(String text) {
        return !text.contains(" ");
    }
}
