package com.example.passwordgauntlet;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LoseActivity extends AppCompatActivity {

    Button retryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lose);

        retryButton = findViewById(R.id.retryButton);

        retryButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoseActivity.this, gameActivity.class);
            startActivity(intent);
            finish();
        });
    }
}