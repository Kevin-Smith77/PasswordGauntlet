package com.example.passwordproject;

import java.text.BreakIterator;
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

    private TextView promptTextView;
    private EditText passwordEditText;
    private Button actionButton;
    private List<PasswordRule> activeRules;
    private TextView unsatisfiedTextView;
    private TextView satisfiedTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        promptTextView = findViewById(R.id.promptTextView);
        passwordEditText = findViewById(R.id.passwordEditText);
        unsatisfiedTextView = findViewById(R.id.unsatisfiedTextView);
        satisfiedTextView = findViewById(R.id.satisfiedTextView);
        actionButton = findViewById(R.id.actionButton);

        //initializing the arraylist of rules
        activeRules = new ArrayList<>();
        activeRules.add(new LengthRule(5));
        activeRules.add(new NumberRule());
        activeRules.add(new SumDigits(4));
        activeRules.add(new ForbiddenWord("the"));

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

    // Transitions to Level 2 (The Prison Complex)
    private void goToLevelTwo() {
        activeRules.clear();
        activeRules.add(new LengthRule(10)); // Higher security
        activeRules.add(new SumDigits(25));    // New logic puzzle
        activeRules.add(new ForbiddenWord("Guard"));
        activeRules.add(new CurrentMinute());
        activeRules.add(new RomanNumeralRule());
        updatePrompts();
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

        if (unsatisfied.isEmpty() && !input.isEmpty()) { //self-explanatory, if all rules passed and input is not empty
            actionButton.setBackgroundColor(Color.GREEN);
            actionButton.setText("Password Approved!");
        } else {
            //this handles the "red" state if it's wrong or empty
            actionButton.setBackgroundColor(Color.RED);
            actionButton.setText("Please try again!");
            actionButton.setOnClickListener(null);
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

    private void updatePrompts(){
        StringBuilder sb = new StringBuilder("Current Requirements:\n"); //combines multiple strings
        //into one
        for(PasswordRule rule : activeRules){
            //grabbing the unique hint from each specific rule class
            sb.append("- ").append(rule.getHint()).append("\n");
        }
        promptTextView.setText(sb.toString()); //setting combined string to the TextView
    }

    public interface PasswordRule{
        String getHint();
        boolean validate(String text);
    }


}



