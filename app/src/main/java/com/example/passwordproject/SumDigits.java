package com.example.passwordproject;

public class SumDigits implements MainActivity.PasswordRule {
    private int targetSum;

    public SumDigits(int targetSum){
        this.targetSum = targetSum;
    }
    @Override
    public String getHint() {
        return "The sum in your digits must add up to " + targetSum + ".";
    }

    @Override
    public boolean validate(String text) {
        int sum = 0;
        for(char c : text.toCharArray()){
            if(Character.isDigit(c)){
                sum += Character.getNumericValue(c);
            }
        }

        return sum == targetSum;
    }
}
