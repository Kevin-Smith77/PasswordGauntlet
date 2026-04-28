package com.example.passwordproject;

public class RomanNumeralRule implements MainActivity.PasswordRule {

    @Override
    public String getHint() {
        return "The password must contain the Roman numeral equivalent of 35.";
    }

    @Override
    public boolean validate(String text) {
        return text.toUpperCase().contains("XXXV");
    }
}
