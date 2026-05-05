package com.example.passwordproject;

public class AlphabeticalRule implements MainActivity.PasswordRule {
    @Override
    public String getHint() { return "Must contain 3 letters in alphabetical order (e.g., 'abc')."; }
    @Override
    public boolean validate(String text) {
        for (int i = 0; i < text.length() - 2; i++) {
            char a = text.toLowerCase().charAt(i);
            char b = text.toLowerCase().charAt(i+1);
            char c = text.toLowerCase().charAt(i+2);
            if (Character.isLetter(a) && b == a + 1 && c == b + 1) return true;
        }
        return false;
    }
}