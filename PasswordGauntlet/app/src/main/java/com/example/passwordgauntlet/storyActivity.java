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
        setContentView(R.layout.activity_story1);

        level = getIntent().getIntExtra("level", 1);

        backButton = findViewById(R.id.startButton);

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(storyActivity.this, gameActivity.class);
            intent.putExtra("level", level + 1);
            startActivity(intent);
            finish();
        });
    }
}