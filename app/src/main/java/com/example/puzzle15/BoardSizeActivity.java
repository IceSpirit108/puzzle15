package com.example.puzzle15;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class BoardSizeActivity extends AppCompatActivity {

    private Button btn3x3, btn4x4, btn5x5, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_size);

        btn3x3 = findViewById(R.id.button_3x3);
        btn4x4 = findViewById(R.id.button_4x4);
        btn5x5 = findViewById(R.id.button_5x5);
        btnBack = findViewById(R.id.button_back);

        btn3x3.setOnClickListener(v -> startGame(3));
        btn4x4.setOnClickListener(v -> startGame(4));
        btn5x5.setOnClickListener(v -> startGame(5));

        btnBack.setOnClickListener(v -> finish());
    }

    private void startGame(int size) {
        Intent intent = new Intent(BoardSizeActivity.this, GameActivity.class);
        intent.putExtra("board_size", size);
        startActivity(intent);
    }
}