package com.example.passwordgauntlet;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class gameActivity extends AppCompatActivity {

    EditText input;
    String inputText;
    Button Submit;
    Button storyButton;
    TextView levelText;

    int level = 1;

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

        input = findViewById(R.id.input);
        Submit = findViewById(R.id.Submit);
        storyButton = findViewById(R.id.storyButton);
        levelText = findViewById(R.id.levelText);

        // Display level
        levelText.setText("Level " + level);

        Rules[] rules = { new minLength() };

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean valid = true;
                inputText = input.getText().toString();

                for (Rules rule : rules) {
                    if (!rule.checkRule(inputText)) {
                        valid = false;
                    }
                }

                if (valid) {
                    Submit.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#55FF55")));
                } else {
                    Submit.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF5555")));
                }
            }
        });

        storyButton.setOnClickListener(v -> {
            Intent intent = new Intent(gameActivity.this, storyActivity.class);
            intent.putExtra("level", level);
            startActivity(intent);
            finish();
        });
    }
}