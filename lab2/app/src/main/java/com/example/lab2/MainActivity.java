package com.example.lab2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        loadLayout(R.layout.activity_main);
    }

    private void loadLayout(int layoutId) {
        setContentView(layoutId);

        if (layoutId == R.layout.activity_main) {
            setTitle(getString(R.string.title_variant8));
        }

        View rootView = findViewById(android.R.id.content);
        if (rootView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        setupInteractions(layoutId);
    }

    private void setupInteractions(int layoutId) {
        if (layoutId == R.layout.activity_main) {
            int[] buttonIds = new int[]{
                    R.id.btn_h1, R.id.btn_h2, R.id.btn_h3, R.id.btn_h4, R.id.btn_h5,
                    R.id.btn_h6, R.id.btn_h7, R.id.btn_h8, R.id.btn_h9, R.id.btn_h10,
                    R.id.btn_h11, R.id.btn_h12, R.id.btn_h13, R.id.btn_h14, R.id.btn_h15,
                    R.id.btn_h16, R.id.btn_h17
            };

            for (int i = 0; i < buttonIds.length; i++) {
                Button btn = findViewById(buttonIds[i]);
                if (btn != null) {
                    final int index = i + 1;
                    btn.setOnClickListener(v -> Toast.makeText(this, "Кнопка " + index + " буквы Н (долгое нажатие для меню заданий)", Toast.LENGTH_SHORT).show());
                    btn.setOnLongClickListener(v -> {
                        showTaskSelectionDialog();
                        return true;
                    });
                }
            }
        } else {
            View task1View = findViewById(R.id.main_task1);
            if (task1View != null) {
                task1View.setOnClickListener(v -> showTaskSelectionDialog());
            }
            View task2View = findViewById(R.id.main_task2);
            if (task2View != null) {
                task2View.setOnClickListener(v -> showTaskSelectionDialog());
            }
            View linearHView = findViewById(R.id.main_linear_h);
            if (linearHView != null) {
                linearHView.setOnClickListener(v -> showTaskSelectionDialog());
            }
        }
    }

    private void showTaskSelectionDialog() {
        String[] options = {
                "Вариант 8: Буква Н (GridLayout)",
                "Вариант 8: Буква Н (LinearLayout)",
                "Задание 1: Рисунок 2.9 (светофор)",
                "Задание 2: Рисунок 2.13 (композиция)"
        };

        new AlertDialog.Builder(this)
                .setTitle("Выберите экран лабораторной работы:")
                .setItems(options, (dialog, which) -> {
                    switch (which) {
                        case 0:
                            loadLayout(R.layout.activity_main);
                            break;
                        case 1:
                            loadLayout(R.layout.activity_variant8_linear);
                            break;
                        case 2:
                            loadLayout(R.layout.activity_task1);
                            break;
                        case 3:
                            loadLayout(R.layout.activity_task2);
                            break;
                    }
                })
                .setNegativeButton("Отмена", null)
                .show();
    }
}
