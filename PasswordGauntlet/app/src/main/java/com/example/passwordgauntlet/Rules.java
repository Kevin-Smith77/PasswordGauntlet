package com.example.passwordgauntlet;
import java.util.Random;

abstract class Rules {
    String ruleText;
    abstract Boolean checkRule(String input);
}
class minLength extends Rules {
    int length;
    String ruleText;
    minLength() {
        Random rand = new Random();
        length = rand.nextInt(4) + 5;
        ruleText = "Password must be at least " + length + " characters long";
    }
    Boolean checkRule(String input) {
        return input.length() >= length;
    }
}

