package com.example.puzzle15;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    private Button startButton, recordsButton, settingsButton, exitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.initRecordsIfNeeded(this);
        String playerName = Utils.getPlayerName(this);
        if (playerName.isEmpty()) {

            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.button_start);
        recordsButton = findViewById(R.id.button_records);
        settingsButton = findViewById(R.id.button_settings);
        exitButton = findViewById(R.id.button_exit);

        startButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BoardSizeActivity.class);
            startActivity(intent);
        });
        recordsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RecordsActivity.class);
            startActivity(intent);
        });
        settingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
        exitButton.setOnClickListener(v -> {
            finish();
        });
    }
}