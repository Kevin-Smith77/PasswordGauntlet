package com.example.passwordgauntlet;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.ArrayList;
import java.util.List;

public class gameActivity extends AppCompatActivity {

    EditText passwordEditText;
    String inputText;
    Button Submit;
    Button storyButton;
    TextView levelText;

    int level = 1;
    List<Rules> activeRules;
    TextView promptTextView;
    TextView unsatisfiedTextView;
    TextView satisfiedTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);

        // Receive level from story screen
        if (getIntent().hasExtra("level")) {
            level = getIntent().getIntExtra("level", 1);
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        passwordEditText = findViewById(R.id.input);
        Submit = findViewById(R.id.Submit);
        storyButton = findViewById(R.id.storyButton);
        levelText = findViewById(R.id.levelText);
        //promptTextView = findViewById(R.id.promptTextView);

        // Display level
        levelText.setText("Level " + level);

        activeRules = new ArrayList<>();

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


        storyButton.setOnClickListener(v -> {
            Intent intent = new Intent(gameActivity.this, storyActivity.class);
            intent.putExtra("level", level);
            startActivity(intent);
            finish();
        });
    }
    void checkPassword(String input) {
        List<Rules> unsatisfied = new ArrayList<>();
        List<Rules> satisfied = new ArrayList<>();


        for (Rules rule : activeRules) {  //loops through each rule in the list
            if (!rule.checkRule(input)) {
                unsatisfied.add(rule);
            }else{
                satisfied.add(rule);
            }
        }

        //displaySortedRules(unsatisfied, satisfied);

        if (unsatisfied.isEmpty() && !input.isEmpty()) { //self-explanatory, if all rules passed and input is not empty
            storyButton.setBackgroundColor(Color.GREEN);
            storyButton.setText("Password Approved!");
        } else {
            //this handles the "red" state if it's wrong or empty
            storyButton.setBackgroundColor(Color.RED);
            storyButton.setText("Please try again!");
            storyButton.setOnClickListener(null);
        }
    }
    void updatePrompts(){
        StringBuilder sb = new StringBuilder("Current Requirements:\n"); //combines multiple strings
        //into one
        for(Rules rule : activeRules){
            //grabbing the unique hint from each specific rule class
            sb.append("- ").append(rule.getHint()).append("\n");
        }
        promptTextView.setText(sb.toString()); //setting combined string to the TextView
    }
    void displaySortedRules(List<Rules> unsatisfied, List<Rules> satisfied){
        StringBuilder unSb = new StringBuilder("NEEDS ATTENTION:\n");
        for(Rules r : unsatisfied){
            unSb.append("❌ ").append(r.getHint()).append("\n");
        }

        unsatisfiedTextView.setText(unSb.toString());

        StringBuilder satSb = new StringBuilder("COMPLETED:\n");
        for(Rules r : satisfied){
            satSb.append("✅ ").append(r.getHint()).append("\n");
        }
        satisfiedTextView.setText(satSb.toString());
    }
    /*void goToLevelTwo() {
        activeRules.clear();
        activeRules.add(new LengthRule(10)); // Higher security
        activeRules.add(new SumDigits(25));    // New logic puzzle
        activeRules.add(new ForbiddenWord("Guard"));
        activeRules.add(new CurrentMinute());
        activeRules.add(new RomanNumeralRule());
        updatePrompts();
    }*/
}