package com.example.passwordproject;

public class PrimeNumberRule implements MainActivity.PasswordRule{
    @Override
    public String getHint() {
        return "Must contain a prime number under 20.";
    }

    @Override
    public boolean validate(String text) {
        String[] primes = {"2", "3","5","7","11","13","17","19"};
        for(String p : primes){
            if(text.contains(p)){
                return true;
            }
        }
        return false;
    }
}
