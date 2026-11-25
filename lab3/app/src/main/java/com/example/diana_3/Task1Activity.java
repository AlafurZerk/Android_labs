package com.example.diana_3;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.MaterialToolbar;

/**
 * Задание 1 лабораторной работы №3:
 * Добавить три кнопки, расположив их вертикально (красный, желтый, зеленый).
 * При нажатии выводить текстом цвет кнопки.
 * Реализация обработки нажатий на основе интерфейса View.OnClickListener.
 */
public class Task1Activity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvColorResult;
    private Button btnRed, btnYellow, btnGreen, btnBackToCalc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_task1);

        View rootView = findViewById(R.id.main_task1);
        if (rootView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        initViews();
    }

    private void initViews() {
        tvColorResult = findViewById(R.id.tvColorResult);
        btnRed = findViewById(R.id.btnRed);
        btnYellow = findViewById(R.id.btnYellow);
        btnGreen = findViewById(R.id.btnGreen);
        btnBackToCalc = findViewById(R.id.btnBackToCalc);

        MaterialToolbar toolbar = findViewById(R.id.toolbarTask1);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(v -> finish());
        }

        // Подключаем Activity в качестве единого обработчика нажатий
        btnRed.setOnClickListener(this);
        btnYellow.setOnClickListener(this);
        btnGreen.setOnClickListener(this);
        btnBackToCalc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnRed) {
            tvColorResult.setText("Красный цвет");
            tvColorResult.setTextColor(Color.parseColor("#D32F2F"));
        } else if (id == R.id.btnYellow) {
            tvColorResult.setText("Желтый цвет");
            tvColorResult.setTextColor(Color.parseColor("#F57F17"));
        } else if (id == R.id.btnGreen) {
            tvColorResult.setText("Зеленый цвет");
            tvColorResult.setTextColor(Color.parseColor("#2E7D32"));
        } else if (id == R.id.btnBackToCalc) {
            finish();
        }
    }
}
