package com.example.passwordproject;

public class Lowercase implements MainActivity.PasswordRule{
    @Override
    public String getHint() {
        return "Must contain at least one lowercase letter.";
    }

    @Override
    public boolean validate(String text) {
        return !text.equals(text.toUpperCase()) && text.matches(".*[a-z].*");
    }
}
