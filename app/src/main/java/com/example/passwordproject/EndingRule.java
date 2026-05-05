package com.example.passwordproject;

public class EndingRule implements MainActivity.PasswordRule{
    @Override
    public String getHint() {
        return "Must end with a punctuation mark.";
    }

    @Override
    public boolean validate(String text) {
        return text.endsWith(".") || text.endsWith("?") || text.endsWith("!");
    }
}
