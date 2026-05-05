package com.example.passwordgauntlet;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class storyActivity extends AppCompatActivity {

    Button backButton;
    int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        level = getIntent().getIntExtra("level", 1);
        if (level == 1) {
            setContentView(R.layout.activity_story);
        }
        else if (level == 2) {
            setContentView(R.layout.activity_story2);
        }
        else if (level == 3) {
            setContentView(R.layout.activity_story3);
        }
        else if (level == 4) {
            setContentView(R.layout.activity_story4);
        }

        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(storyActivity.this, gameActivity.class);
            intent.putExtra("level", level + 1);
            startActivity(intent);
            finish();
        });
    }
}