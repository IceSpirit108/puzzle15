package com.example.puzzle15;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class SettingsActivity extends AppCompatActivity {

    private EditText nameEditText;
    private Button saveButton, backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        nameEditText = findViewById(R.id.edit_name);
        saveButton = findViewById(R.id.button_save);
        backButton = findViewById(R.id.button_back);

        String currentName = Utils.getPlayerName(this);
        nameEditText.setText(currentName);

        saveButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString().trim();
            if (name.isEmpty()) {
                Toast.makeText(this, "Имя не может быть пустым!", Toast.LENGTH_SHORT).show();
                return;
            }
            Utils.savePlayerName(this, name);
            Toast.makeText(this, "Имя сохранено", Toast.LENGTH_SHORT).show();
        });

        backButton.setOnClickListener(v -> finish());
    }
}