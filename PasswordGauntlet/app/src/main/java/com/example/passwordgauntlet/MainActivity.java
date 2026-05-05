package com.example.passwordgauntlet;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.enterButton);

        startButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, gameActivity.class);
            startActivity(intent);
        });
    }
}