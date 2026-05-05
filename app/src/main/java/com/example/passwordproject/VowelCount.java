package com.example.passwordproject;

public class VowelCount implements MainActivity.PasswordRule{
    @Override
    public String getHint() {
        return "Must not contain at least two vowels.";
    }

    @Override
    public boolean validate(String text) {
        int count = 0;
        for(char c : text.toLowerCase().toCharArray()){
            if("aeiou".indexOf(c) != -1)
                count ++;
        }
        return count >= 2;
    }
}
