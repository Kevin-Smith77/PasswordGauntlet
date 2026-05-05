package com.example.passwordproject;

// Rule requiring a specific Unicode character (Fuel Pump ⛽)
public class EmojiRule implements MainActivity.PasswordRule {
    @Override
    public String getHint() {
        return "The password must contain the Unicode for the grinning face emoji" +
                " (case sensitive).";
    }
    public boolean validate(String text) {
        return text.contains("1F600");
    }
}
