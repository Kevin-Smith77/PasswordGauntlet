package com.example.passwordgauntlet;
import java.util.Random;
import java.util.Calendar;

abstract class Rules {
    abstract String getHint();
    abstract Boolean checkRule(String input);
}
class minLength extends Rules {
    int length;
    public String getHint(){
        return "Password must be at least " + length + " characters long";
    }
    minLength() {
        Random rand = new Random();
        length = rand.nextInt(4) + 5;
    }
    Boolean checkRule(String input) {
        return input.length() >= length;
    }
}
class NumberRule extends Rules {
    public String getHint(){
        return "Must contain at least one digit.";
    }
    @Override
    public Boolean checkRule(String text){
        return text.matches(".*\\d.*");
    }
}
class CurrentMinute extends Rules {
    @Override
    public String getHint() {
        return "The password must contain the current minute of the hour. e.g. 45 in 2:45";
    }

    @Override
    public Boolean checkRule(String text) {
        int minute = Calendar.getInstance().get(Calendar.MINUTE);
        String minuteString = String.valueOf(minute);
        return text.contains(minuteString);
    }
}
class ForbiddenWord extends Rules {
    private String forbidden;

    public ForbiddenWord(String forbidden) {
        this.forbidden = forbidden;
    }

    @Override
    public String getHint() {
        return "Your password cannot contain the word: " + forbidden;
    }

    @Override
    public Boolean checkRule(String text) {
        return !text.toLowerCase().contains(forbidden.toLowerCase());
    }
}
class RomanNumeralRule extends Rules {

    @Override
    public String getHint() {
        return "The password must contain the Roman numeral equivalent of 35.";
    }

    @Override
    public Boolean checkRule(String text) {
        return text.toUpperCase().contains("XXXV");
    }
}
class SumDigits extends Rules {
    private int targetSum;

    public SumDigits(int targetSum){
        this.targetSum = targetSum;
    }
    @Override
    public String getHint() {
        return "The sum in your digits must add up to " + targetSum + ".";
    }

    @Override
    public Boolean checkRule(String text) {
        int sum = 0;
        for(char c : text.toCharArray()){
            if(Character.isDigit(c)){
                sum += Character.getNumericValue(c);
            }
        }

        return sum == targetSum;
    }
}

