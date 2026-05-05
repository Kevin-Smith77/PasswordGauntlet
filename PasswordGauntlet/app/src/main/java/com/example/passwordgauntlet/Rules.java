package com.example.passwordgauntlet;
import java.util.Random;
import java.util.Calendar;
import android.content.Context;
import android.os.BatteryManager;
import android.os.Environment;
import android.os.StatFs;
import android.os.SystemClock;
import android.provider.Settings;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.media.AudioManager;


abstract class Rules {
    abstract String getHint();
    abstract Boolean checkRule(String input);
}
class minLength extends Rules {
    int length;
    public String getHint(){
        return "Password must be at least " + length + " characters long";
    }
    minLength(int length) {
        this.length = length;
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
class AlphabeticalRule extends Rules {
    @Override
    public String getHint() { return "Must contain 3 letters in alphabetical order (e.g., 'abc')."; }
    @Override
    public Boolean checkRule(String text) {
        for (int i = 0; i < text.length() - 2; i++) {
            char a = text.toLowerCase().charAt(i);
            char b = text.toLowerCase().charAt(i+1);
            char c = text.toLowerCase().charAt(i+2);
            if (Character.isLetter(a) && b == a + 1 && c == b + 1) return true;
        }
        return false;
    }
}
class BatteryRule extends Rules{
    private Context context;

    public BatteryRule(Context context){
        this.context = context;
    }

    @Override
    public String getHint() {
        return "Your password must include your current battery percentage.";
    }

    @Override
    public Boolean checkRule(String text) {
        BatteryManager bm = (BatteryManager) context.getSystemService(Context.BATTERY_SERVICE);
        if (bm != null) {
            int percentage = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
            // Check if percentage is valid (usually 0-100)
            if (percentage >= 0 && percentage <= 100) {
                return text.contains(String.valueOf(percentage));
            }
        }
        return false;
    }
}
class BrightnessRule extends Rules{
    private Context context;
    public BrightnessRule(Context c){
        this.context = c;
    }

    @Override
    public String getHint() {
        return "Must include your current screen brightness level (0-255).";
    }

    @Override
    public Boolean checkRule(String text) {
        try{
            int b = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
            return text.contains(String.valueOf(b));
        } catch (Exception e) {
            return false;
        }

    }
}
class DayRule extends Rules{
    @Override
    public String getHint() {
        return "Must contain the name of the current day.";
    }

    @Override
    public Boolean checkRule(String text) {
        String day = Calendar.getInstance().getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        return text.toLowerCase().contains(day.toLowerCase());
    }
}

class EmojiRule extends Rules {
    @Override
    public String getHint() {
        return "The password must contain the Unicode for the grinning face emoji" +
                " (case sensitive).";
    }
    public Boolean checkRule(String text) {
        return text.contains("1F600");
    }
}
class EndingRule extends Rules{
    @Override
    public String getHint() {
        return "Must end with a punctuation mark.";
    }

    @Override
    public Boolean checkRule(String text) {
        return text.endsWith(".") || text.endsWith("?") || text.endsWith("!") || text.endsWith(";") || text.endsWith(":");
    }
}
class LeapYearRule extends Rules{

    @Override
    public String getHint() {
        return "Your password must contain a leap year.";
    }

    @Override
    public Boolean checkRule(String text) {
        Pattern p = Pattern.compile("\\d{4}");
        Matcher m = p.matcher(text);
        while(m.find()){
            int year = Integer.parseInt(m.group());
            if((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)){
                return true;
            }
        }
        return false;
    }
}
// Rule requiring a specific name
class LogicRule extends Rules {

    @Override
    public String getHint() {
        return "The password must include the name of" +
                " a Wheaton College computer science professor.";
    }

    @Override
    public Boolean checkRule(String text) {
        String[] professors = {"Gagne","Gousie","Tong",
                "Leblanc","Almeder"};
        for(String oneProfessor : professors){
            if(text.toLowerCase().contains(oneProfessor.toLowerCase())){
                return true;
            }

        }
        return false;
    }
}
class Lowercase extends Rules{
    @Override
    public String getHint() {
        return "Must contain at least one lowercase letter.";
    }

    @Override
    public Boolean checkRule(String text) {
        return !text.equals(text.toUpperCase()) && text.matches(".*[a-z].*");
    }
}
class MonthRule extends Rules{

    @Override
    public String getHint() {
        return "The password must include a month of the year.";
    }

    @Override
    public Boolean checkRule(String text) {
        String[] months = {"January", "February","March","April","May",
                "June", "July", "August", "September","October","November","December"};
        for(String month : months){
            if(text.toLowerCase().contains(month.toLowerCase())){
                return true;
            }
        }
        return false;
    }
}
class NegativeSpaceRule extends Rules{
    @Override
    public String getHint() {
        return "Cannot contain any letters from the word 'ESCAPE'.";
    }

    @Override
    public Boolean checkRule(String text) {
        String forbidden = "escape";
        for(char c : text.toLowerCase().toCharArray()){
            if(forbidden.indexOf(c) != -1){
                return false;
            }
        }
        return true;
    }
}
class NoSpaces extends Rules{
    @Override
    public String getHint() {
        return "The password must not contain any spaces.";
    }

    @Override
    public Boolean checkRule(String text) {
        return !text.contains(" ");
    }
}
class PrimeNumberRule extends Rules{
    @Override
    public String getHint() {
        return "Must contain a prime number under 20.";
    }

    @Override
    public Boolean checkRule(String text) {
        String[] primes = {"2", "3","5","7","11","13","17","19"};
        for(String p : primes){
            if(text.contains(p)){
                return true;
            }
        }
        return false;
    }
}
class SpecialCharacter extends Rules {
    @Override
    public String getHint() {
        return "The password must contain at least one special character (e.g., !, @, #).";
    }

    @Override
    public Boolean checkRule(String text) {
        // Regex looks for anything that isn't a letter or digit
        return text.matches(".*[^a-zA-Z0-9 ].*");
    }
}
class StorageRule extends Rules {
    @Override
    public String getHint() { return "Must include the first digit of your remaining GB of storage."; }
    @Override
    public Boolean checkRule(String text) {
        StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
        long bytesAvailable = stat.getBlockSizeLong() * stat.getAvailableBlocksLong();
        long gbAvailable = bytesAvailable / (1024 * 1024 * 1024);
        String firstDigit = String.valueOf(String.valueOf(gbAvailable).charAt(0));
        return text.contains(firstDigit);
    }
}
class UptimeRule extends Rules {
    @Override
    public String getHint() {
        return "The password must contain the number of minutes the device has been awake." +
                "(find the milliseconds by going to Settings > About Phone > Status > Uptime)";
    }

    @Override
    public Boolean checkRule(String text) {
        // 1. Get milliseconds
        long millis = SystemClock.elapsedRealtime();

        // 2. Convert to minutes: (ms / 1000) / 60
        long minutes = (millis / 1000) / 60;

        // 3. Check if the password contains this number
        return text.contains(String.valueOf(minutes));
    }
}
class VolumeRule extends Rules{
    private Context context;
    public VolumeRule(Context c){
        this.context=c;
    }

    @Override
    public String getHint() {
        return "Must include your current volume level.";
    }

    @Override
    public Boolean checkRule(String text) {
        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int vol = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        return text.contains(String.valueOf(vol));
    }
}
class VowelCount extends Rules{
    @Override
    public String getHint() {
        return "Must not contain at least two vowels.";
    }

    @Override
    public Boolean checkRule(String text) {
        int count = 0;
        for(char c : text.toLowerCase().toCharArray()){
            if("aeiou".indexOf(c) != -1)
                count ++;
        }
        return count >= 2;
    }
}


