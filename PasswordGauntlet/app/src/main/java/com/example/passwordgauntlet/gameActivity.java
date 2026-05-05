package com.example.passwordgauntlet;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class gameActivity extends AppCompatActivity {

    EditText passwordEditText;
    Button storyButton;
    TextView levelText;
    TextView timerText;

    int level = 1;
    List<Rules> activeRules;

    ListView listView;
    ArrayAdapter<String> adapter;
    List<String> visibleRules;

    int currentRuleIndex = 0;

    // TIMER
    CountDownTimer timer;
    long timeLeft = 30000; // 30 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);

        if (getIntent().hasExtra("level")) {
            level = getIntent().getIntExtra("level", 1);
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        passwordEditText = findViewById(R.id.input);
        storyButton = findViewById(R.id.storyButton);
        levelText = findViewById(R.id.levelText);
        listView = findViewById(R.id.listView);
        timerText = findViewById(R.id.timerText);

        levelText.setText("Level " + level);
        //Call to get rules based on level
        activeRules = new ArrayList<>();
        getRules();

        visibleRules = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, visibleRules);
        listView.setAdapter(adapter);

        initRules();
        startTimer(); // START TIMER

        passwordEditText.addTextChangedListener(new TextWatcher(){
            @Override
            public void afterTextChanged(Editable s) {
                checkPassword(s.toString());
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        storyButton.setOnClickListener(v -> {

            String input = passwordEditText.getText().toString();
            boolean allPassed = true;

            for (Rules rule : activeRules) {
                if (!rule.checkRule(input)) {
                    allPassed = false;
                    break;
                }
            }

            if (allPassed) {
                Intent intent;
                if (level < 3) {
                    intent = new Intent(gameActivity.this, storyActivity.class);
                    intent.putExtra("level", level);
                }
                else {
                    intent = new Intent(gameActivity.this, activity_win.class);
                }
                startActivity(intent);
                finish();
            } else {
                storyButton.setText("Complete all rules first!");
                storyButton.setBackgroundColor(Color.RED);
            }
        });
    }

    void startTimer() {
        timer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                timerText.setText("Time: " + (millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                timerText.setText("Time: 0");
                loseGame();
            }
        }.start();
    }

    void loseGame() {
        if (timer != null) {
            timer.cancel();
        }

        Intent intent = new Intent(gameActivity.this, activity_loss.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }

    void initRules(){
        currentRuleIndex = 0;
        visibleRules.clear();

        if(!activeRules.isEmpty()){
            visibleRules.add(formatRule(activeRules.get(0), false));
        }

        adapter.notifyDataSetChanged();
    }

    void checkPassword(String input) {

        if (input.isEmpty()) {
            storyButton.setBackgroundColor(Color.RED);
            storyButton.setText("Please try again!");
            return;
        }

        boolean allPassed = true;

        for (int i = 0; i <= currentRuleIndex && i < activeRules.size(); i++) {
            if (!activeRules.get(i).checkRule(input)) {
                allPassed = false;
                break;
            }
        }

        if (allPassed) {
            while (currentRuleIndex < activeRules.size() - 1) {

                Rules nextRule = activeRules.get(currentRuleIndex + 1);

                currentRuleIndex++;

                if (!nextRule.checkRule(input)) {
                    break;
                }
            }
        }

        visibleRules.clear();

        for (int i = 0; i <= currentRuleIndex; i++) {
            Rules rule = activeRules.get(i);
            boolean passed = rule.checkRule(input);
            visibleRules.add(formatRule(rule, passed));
        }

        adapter.notifyDataSetChanged();

        boolean allRulesPassed = true;
        for (Rules rule : activeRules) {
            if (!rule.checkRule(input)) {
                allRulesPassed = false;
                break;
            }
        }

        if (allRulesPassed) {
            storyButton.setBackgroundColor(Color.GREEN);
            storyButton.setText("Password Approved!");
        } else {
            storyButton.setBackgroundColor(Color.RED);
            storyButton.setText("Please try again!");
        }
    }

    String formatRule(Rules rule, boolean passed){
        return (passed ? "✅ " : "❌ ") + rule.getHint();
    }
    void getRules(){
        Random random = new Random();
        if(level == 1){
            activeRules.add(new minLength(random.nextInt(3) + 5));
            activeRules.add(new NumberRule());
            int RNG = random.nextInt(3);
            switch(RNG) {
                case 0:
                    activeRules.add(new ForbiddenWord("guard"));
                    break;
                case 1:
                    activeRules.add(new NoSpaces());
                    break;
                case 2:
                    activeRules.add(new Lowercase());
                    break;
            }
            activeRules.add(new MonthRule());
            activeRules.add(new SpecialCharacter());
            activeRules.add(new NegativeSpaceRule());
            activeRules.add(new AlphabeticalRule());
            activeRules.add(new SumDigits(random.nextInt(5) + 12));

        }
        else if(level == 2){
            activeRules.add(new minLength(random.nextInt(4) + 6));
            activeRules.add(new EndingRule());
            activeRules.add(new CurrentMinute());
            activeRules.add(new LeapYearRule());
            activeRules.add(new DayRule());
            activeRules.add(new RomanNumeralRule());
            activeRules.add(new BatteryRule(this));
            activeRules.add(new SumDigits(random.nextInt(10) + 15));
        }
        else{
            activeRules.add(new minLength(random.nextInt(7) + 2));
            int RNG = random.nextInt(2);
            switch(RNG) {
                case 0:
                    activeRules.add(new StorageRule());
                    break;
                case 1:
                    activeRules.add(new UptimeRule());
                    break;
            }
            activeRules.add(new VolumeRule(this));
            activeRules.add(new LogicRule());
            activeRules.add(new VowelCount());
            activeRules.add(new PrimeNumberRule());
            activeRules.add(new EmojiRule());
            activeRules.add(new SumDigits(random.nextInt(15) + 10));
        }
    }
}