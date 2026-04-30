package com.example.passwordgauntlet;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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

public class gameActivity extends AppCompatActivity {

    EditText passwordEditText;
    Button storyButton;
    TextView levelText;

    int level = 1;
    List<Rules> activeRules;

    ListView listView;
    ArrayAdapter<String> adapter;
    List<String> visibleRules;

    int currentRuleIndex = 0;

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

        levelText.setText("Level " + level);

        activeRules = new ArrayList<>();
        activeRules.add(new minLength());
        activeRules.add(new NumberRule());
        activeRules.add(new ForbiddenWord("guard"));
        activeRules.add(new CurrentMinute());
        activeRules.add(new RomanNumeralRule());
        activeRules.add(new SumDigits(15));

        visibleRules = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, visibleRules);
        listView.setAdapter(adapter);

        initRules();

        passwordEditText.addTextChangedListener(new TextWatcher(){
            @Override
            public void afterTextChanged(Editable s) {
                checkPassword(s.toString());
            }

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

    void initRules(){
        currentRuleIndex = 0;
        visibleRules.clear();

        if(!activeRules.isEmpty()){
            visibleRules.add(formatRule(activeRules.get(0), false));
        }

        adapter.notifyDataSetChanged();
    }

    void checkPassword(String input) {

        List<String> newVisibleRules = new ArrayList<>();

        boolean allPassed = true;

        for (int i = 0; i <= currentRuleIndex && i < activeRules.size(); i++) {

            Rules rule = activeRules.get(i);
            boolean passed = rule.checkRule(input);

            if (!passed) {
                allPassed = false;
            }

            newVisibleRules.add(formatRule(rule, passed));
        }

        visibleRules.clear();
        visibleRules.addAll(newVisibleRules);
        adapter.notifyDataSetChanged();

        if (input.isEmpty()) {
            storyButton.setBackgroundColor(Color.RED);
            storyButton.setText("Please try again!");
            return;
        }

        if (allPassed) {

            if (currentRuleIndex < activeRules.size() - 1) {
                currentRuleIndex++;

                visibleRules.add(formatRule(activeRules.get(currentRuleIndex), false));
                adapter.notifyDataSetChanged();

                return;
            }

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
}