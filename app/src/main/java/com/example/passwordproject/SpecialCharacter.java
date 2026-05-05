package com.example.passwordproject;

// Rule requiring at least one special character
public class SpecialCharacter implements MainActivity.PasswordRule {
    @Override
    public String getHint() {
        return "The password must contain at least one special character (e.g., !, @, #).";
    }

    @Override
    public boolean validate(String text) {
        // Regex looks for anything that isn't a letter or digit
        return text.matches(".*[^a-zA-Z0-9 ].*");
    }
}