package com.example.passwordproject;

import java.util.List;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView levelHeader;
    private EditText passwordEditText;
    private Button actionButton;
    private List<PasswordRule> activeRules;
    private TextView unsatisfiedTextView;
    private TextView satisfiedTextView;
    private int currentLevel = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        levelHeader = findViewById(R.id.levelHeader);
        passwordEditText = findViewById(R.id.passwordEditText);
        unsatisfiedTextView = findViewById(R.id.unsatisfiedTextView);
        satisfiedTextView = findViewById(R.id.satisfiedTextView);
        actionButton = findViewById(R.id.actionButton);

        actionButton.setOnClickListener(v -> {
            if(actionButton.getText().toString().contains("Proceed to next level")){
                advanceLevel();
            }
        });
        //initializing the arraylist of rules
        activeRules = new ArrayList<>();
        activeRules.add(new LengthRule(5));
        activeRules.add(new NumberRule());
        activeRules.add(new SumDigits(4));
        activeRules.add(new ForbiddenWord("the"));
        activeRules.add(new SpecialCharacter());
        activeRules.add(new Lowercase());
        activeRules.add(new NoSpaces());
        activeRules.add(new VowelCount());

        checkPassword(""); //initializes the screen with the first set of hints

        passwordEditText.addTextChangedListener(new TextWatcher(){
            @Override
            public void afterTextChanged(Editable s) { //Editable is basically like a String, but can be changed
                checkPassword(s.toString()); //sending the current text
            }

            //attaches a listener to check every time a key is pressed
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
    }




    private void advanceLevel(){
        currentLevel++;
        activeRules.clear();
        passwordEditText.setText("");

        if(currentLevel == 2){
            levelHeader.setText("LEVEL 2");
            activeRules.add(new LengthRule(10));
            activeRules.add(new MonthRule());
            activeRules.add(new RomanNumeralRule());
            activeRules.add(new LogicRule());
            activeRules.add(new NegativeSpaceRule());
            activeRules.add(new AlphabeticalRule());
            activeRules.add(new PalindromeRule());
            activeRules.add(new PrimeNumberRule());
        } else if(currentLevel == 3){
            levelHeader.setText("LEVEL 3");
            activeRules.add(new BatteryRule(this));
            activeRules.add(new LeapYearRule());
            activeRules.add(new EmojiRule());
            activeRules.add(new UptimeRule());
            activeRules.add(new DayRule());
            activeRules.add(new BrightnessRule(this ));
            activeRules.add(new StorageRule());
            activeRules.add(new VolumeRule(this ));
        } else {
            levelHeader.setText("YOU ESCAPED!");
            actionButton.setEnabled(false);
            actionButton.setText("CONGRATULATIONS!");
            actionButton.setBackgroundColor(Color.GREEN);
            activeRules.clear();
        }
        checkPassword("");
    }

    private void checkPassword(String input) {
        List<PasswordRule> unsatisfied = new ArrayList<>();
        List<PasswordRule> satisfied = new ArrayList<>();


        for (PasswordRule rule : activeRules) {  //loops through each rule in the list
            if (!rule.validate(input)) {
                unsatisfied.add(rule);
            }else{
                satisfied.add(rule);
            }
        }

        displaySortedRules(unsatisfied, satisfied);

        if (unsatisfied.isEmpty() && !input.isEmpty() && currentLevel < 4) { //self-explanatory, if all rules passed and input is not empty
            actionButton.setBackgroundColor(Color.BLUE);
            actionButton.setText("Proceed to next level");
        } else if (currentLevel < 4) {
            //this handles the "red" state if it's wrong or empty
            actionButton.setBackgroundColor(Color.RED);
            actionButton.setText("Level " + currentLevel + " in progress....");
        }
    }

    private void displaySortedRules(List<PasswordRule> unsatisfied, List<PasswordRule> satisfied){
        StringBuilder unSb = new StringBuilder("NEEDS ATTENTION:\n");
        for(PasswordRule r : unsatisfied){
            unSb.append("❌ ").append(r.getHint()).append("\n");
        }

        unsatisfiedTextView.setText(unSb.toString());

        StringBuilder satSb = new StringBuilder("COMPLETED:\n");
        for(PasswordRule r : satisfied){
            satSb.append("✅ ").append(r.getHint()).append("\n");
        }
        satisfiedTextView.setText(satSb.toString());
    }

    public interface PasswordRule{
        String getHint();
        boolean validate(String text);
    }


}



