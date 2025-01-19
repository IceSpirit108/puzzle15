package com.example.puzzle15;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;


public class RecordsActivity extends AppCompatActivity {

    private TextView recordsTextView;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        recordsTextView = findViewById(R.id.text_records);
        backButton = findViewById(R.id.button_back);

        backButton.setOnClickListener(v -> finish());

        showRecords();
    }

    private void showRecords() {
        int[] moves = Utils.getRecordMoves(this);
        int[] times = Utils.getRecordTimes(this);
        String[] names = Utils.getRecordNames(this);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            if (times[i] == Integer.MAX_VALUE && moves[i] == Integer.MAX_VALUE) {
                sb.append((i + 1) + ". ---\n");
            } else {
                int m = times[i] / 60;
                int s = times[i] % 60;
                sb.append(String.format(Locale.getDefault(), "%d. Имя: %s | Время: %02d:%02d | Ходы: %d\n",
                        i + 1, names[i], m, s, moves[i]));
            }
        }

        recordsTextView.setText(sb.toString());
    }
}