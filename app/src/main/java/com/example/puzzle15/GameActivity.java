package com.example.puzzle15;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.Locale;
import android.view.View;


public class GameActivity extends AppCompatActivity {

    private TextView movesTextView, timerTextView;
    private Button resetButton, backButton;
    private GridLayout gridLayout;

    private PuzzleGame puzzleGame;
    private int movesCount = 0;
    private int boardSize = 4; // default
    private Handler timerHandler = new Handler();
    private long startTime = 0;
    private boolean gameRunning = false;
    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            if (gameRunning) {
                long millis = System.currentTimeMillis() - startTime;
                int seconds = (int) (millis / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                timerTextView.setText(String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds));
                timerHandler.postDelayed(this, 500);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        movesTextView = findViewById(R.id.text_moves);
        timerTextView = findViewById(R.id.text_timer);
        resetButton = findViewById(R.id.button_reset);
        backButton = findViewById(R.id.button_back);
        gridLayout = findViewById(R.id.grid_layout);

        boardSize = getIntent().getIntExtra("board_size", 4);
        startNewGame();

        resetButton.setOnClickListener(v -> startNewGame());
        backButton.setOnClickListener(v -> finish());
    }

    private void startNewGame() {
        puzzleGame = new PuzzleGame(boardSize);
        movesCount = 0;
        updateMovesText();
        setupGrid();
        startTimer();
    }

    private void updateMovesText() {
        movesTextView.setText("Ходов: " + movesCount);
    }

    private void setupGrid() {
        gridLayout.removeAllViews();
        gridLayout.setColumnCount(boardSize);
        gridLayout.setRowCount(boardSize);

        int tileSize = getResources().getDisplayMetrics().widthPixels / (boardSize + 1);
        int[] board = puzzleGame.getBoard();

        for (int i = 0; i < board.length; i++) {
            Button tile = new Button(this);
            if (board[i] == 0) {
                tile.setText("");
            } else {
                tile.setText(String.valueOf(board[i]));
            }

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = tileSize;
            params.height = tileSize;
            tile.setLayoutParams(params);

            final int index = i;
            tile.setOnClickListener(v -> {
                if (puzzleGame.moveTile(index)) {
                    movesCount++;
                    updateMovesText();
                    refreshGrid();
                    if (puzzleGame.isSolved()) {
                        gameWon();
                    }
                }
            });

            gridLayout.addView(tile);
        }
    }

    private void refreshGrid() {
        int[] board = puzzleGame.getBoard();
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            Button tile = (Button) gridLayout.getChildAt(i);
            if (board[i] == 0) {
                tile.setText("");
            } else {
                tile.setText(String.valueOf(board[i]));
            }
        }
    }

    private void startTimer() {
        gameRunning = false;
        timerHandler.removeCallbacks(timerRunnable);
        startTime = System.currentTimeMillis();
        gameRunning = true;
        timerHandler.post(timerRunnable);
    }

    private void stopTimer() {
        gameRunning = false;
        timerHandler.removeCallbacks(timerRunnable);
    }

    private void gameWon() {
        stopTimer();

        String timeStr = timerTextView.getText().toString();
        int minutes = Integer.parseInt(timeStr.substring(0, 2));
        int seconds = Integer.parseInt(timeStr.substring(3, 5));
        int totalSeconds = minutes * 60 + seconds;


        Utils.addNewRecord(this, movesCount, totalSeconds);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Поздравляем, вы выиграли!");
        builder.setMessage("Хотите начать новую игру?");
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startNewGame();
            }
        });
        builder.setNegativeButton("Нет", (dialog, which) -> {

        });
        builder.show();
    }
}