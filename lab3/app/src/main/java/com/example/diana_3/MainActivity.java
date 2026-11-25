package com.example.diana_3;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.MaterialToolbar;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Элементы отображения
    private TextView tvExpression;
    private TextView tvResult;
    private MaterialToolbar toolbar;

    // Цифровые кнопки
    private Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;

    // Кнопки математических операций
    private Button btnAdd, btnSubtract, btnMultiply, btnDivide, btnEquals;
    private Button btnSqrt, btnReciprocal, btnPlusMinus, btnDot;

    // Кнопки управления и очистки
    private Button btnClear, btnBackspace;

    // Кнопки памяти
    private Button btnMC, btnMR, btnMS, btnMPlus, btnMMinus;

    // Логическое состояние калькулятора
    private String currentInput = "0";
    private Double firstOperand = null;
    private String pendingOperation = null;
    private boolean isNewOp = true;
    private boolean hasError = false;
    private double memory = 0.0;
    private boolean isMemorySet = false;

    private static final DecimalFormat DECIMAL_FORMAT;

    static {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        symbols.setDecimalSeparator('.');
        DECIMAL_FORMAT = new DecimalFormat("#.########", symbols);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        View rootView = findViewById(R.id.main);
        if (rootView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        initViews();
        setupToolbar();

        if (savedInstanceState != null) {
            currentInput = savedInstanceState.getString("currentInput", "0");
            if (savedInstanceState.containsKey("firstOperand")) {
                firstOperand = savedInstanceState.getDouble("firstOperand");
            }
            pendingOperation = savedInstanceState.getString("pendingOperation", null);
            isNewOp = savedInstanceState.getBoolean("isNewOp", true);
            hasError = savedInstanceState.getBoolean("hasError", false);
            memory = savedInstanceState.getDouble("memory", 0.0);
            isMemorySet = savedInstanceState.getBoolean("isMemorySet", false);

            tvExpression.setText(savedInstanceState.getString("expressionText", ""));
            tvResult.setText(currentInput);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("currentInput", currentInput);
        if (firstOperand != null) {
            outState.putDouble("firstOperand", firstOperand);
        }
        outState.putString("pendingOperation", pendingOperation);
        outState.putBoolean("isNewOp", isNewOp);
        outState.putBoolean("hasError", hasError);
        outState.putDouble("memory", memory);
        outState.putBoolean("isMemorySet", isMemorySet);
        if (tvExpression != null) {
            outState.putString("expressionText", tvExpression.getText().toString());
        }
    }

    private void initViews() {
        tvExpression = findViewById(R.id.tvExpression);
        tvResult = findViewById(R.id.tvResult);
        toolbar = findViewById(R.id.toolbar);

        // Цифровые кнопки
        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);

        // Операции
        btnAdd = findViewById(R.id.btnAdd);
        btnSubtract = findViewById(R.id.btnSubtract);
        btnMultiply = findViewById(R.id.btnMultiply);
        btnDivide = findViewById(R.id.btnDivide);
        btnEquals = findViewById(R.id.btnEquals);
        btnSqrt = findViewById(R.id.btnSqrt);
        btnReciprocal = findViewById(R.id.btnReciprocal);
        btnPlusMinus = findViewById(R.id.btnPlusMinus);
        btnDot = findViewById(R.id.btnDot);

        // Очистка и удаление
        btnClear = findViewById(R.id.btnClear);
        btnBackspace = findViewById(R.id.btnBackspace);

        // Память
        btnMC = findViewById(R.id.btnMC);
        btnMR = findViewById(R.id.btnMR);
        btnMS = findViewById(R.id.btnMS);
        btnMPlus = findViewById(R.id.btnMPlus);
        btnMMinus = findViewById(R.id.btnMMinus);

        // Подключаем Activity как единый обработчик OnClickListener для всех кнопок
        Button[] allButtons = new Button[]{
                btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9,
                btnAdd, btnSubtract, btnMultiply, btnDivide, btnEquals,
                btnSqrt, btnReciprocal, btnPlusMinus, btnDot,
                btnClear, btnBackspace,
                btnMC, btnMR, btnMS, btnMPlus, btnMMinus
        };

        for (Button btn : allButtons) {
            if (btn != null) {
                btn.setOnClickListener(this);
            }
        }
    }

    private void setupToolbar() {
        if (toolbar == null) return;
        toolbar.inflateMenu(R.menu.main_menu);
        toolbar.setOnMenuItemClickListener(this::onToolbarMenuClick);
    }

    private boolean onToolbarMenuClick(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_task1) {
            // Переход к Заданию 1 (Светофор)
            Intent intent = new Intent(this, Task1Activity.class);
            startActivity(intent);
            return true;
        } else if (itemId == R.id.menu_clear) {
            onClearClick();
            return true;
        } else if (itemId == R.id.menu_about) {
            new AlertDialog.Builder(this)
                    .setTitle("О программе")
                    .setMessage("Лабораторная работа №3\nТема: Activity, обработка нажатий на кнопки\nВариант: 9\nПриложение: Калькулятор")
                    .setPositiveButton("OK", null)
                    .show();
            return true;
        }
        return false;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        // Цифры
        if (id == R.id.btn0) onDigitClick("0");
        else if (id == R.id.btn1) onDigitClick("1");
        else if (id == R.id.btn2) onDigitClick("2");
        else if (id == R.id.btn3) onDigitClick("3");
        else if (id == R.id.btn4) onDigitClick("4");
        else if (id == R.id.btn5) onDigitClick("5");
        else if (id == R.id.btn6) onDigitClick("6");
        else if (id == R.id.btn7) onDigitClick("7");
        else if (id == R.id.btn8) onDigitClick("8");
        else if (id == R.id.btn9) onDigitClick("9");

        // Дробная точка
        else if (id == R.id.btnDot) onDotClick();

        // Смена знака (ввод отрицательных чисел)
        else if (id == R.id.btnPlusMinus) onPlusMinusClick();

        // Базовые математические операции
        else if (id == R.id.btnAdd) onOperatorClick("+");
        else if (id == R.id.btnSubtract) onOperatorClick("-");
        else if (id == R.id.btnMultiply) onOperatorClick("*");
        else if (id == R.id.btnDivide) onOperatorClick("/");

        // Вычисление результата
        else if (id == R.id.btnEquals) onEqualsClick();

        // Функции
        else if (id == R.id.btnSqrt) onSqrtClick();
        else if (id == R.id.btnReciprocal) onReciprocalClick();

        // Очистка и удаление
        else if (id == R.id.btnClear) onClearClick();
        else if (id == R.id.btnBackspace) onBackspaceClick();

        // Память
        else if (id == R.id.btnMC) onMemoryClear();
        else if (id == R.id.btnMR) onMemoryRecall();
        else if (id == R.id.btnMS) onMemoryStore();
        else if (id == R.id.btnMPlus) onMemoryAdd();
        else if (id == R.id.btnMMinus) onMemorySubtract();
    }

    private void onDigitClick(String digit) {
        if (hasError) {
            onClearClick();
        }
        if (isNewOp || currentInput.equals("0")) {
            currentInput = digit;
            isNewOp = false;
        } else {
            currentInput += digit;
        }
        tvResult.setText(currentInput);
    }

    private void onDotClick() {
        if (hasError) {
            onClearClick();
        }
        if (isNewOp) {
            currentInput = "0.";
            isNewOp = false;
        } else if (!currentInput.contains(".")) {
            currentInput += ".";
        }
        tvResult.setText(currentInput);
    }

    private void onPlusMinusClick() {
        if (hasError || currentInput.equals("0")) return;
        if (currentInput.startsWith("-")) {
            currentInput = currentInput.substring(1);
        } else {
            currentInput = "-" + currentInput;
        }
        tvResult.setText(currentInput);
    }

    private void onBackspaceClick() {
        if (hasError || isNewOp) {
            currentInput = "0";
            hasError = false;
            isNewOp = false;
        } else if (currentInput.length() > 1) {
            currentInput = currentInput.substring(0, currentInput.length() - 1);
            if (currentInput.equals("-")) {
                currentInput = "0";
            }
        } else {
            currentInput = "0";
        }
        tvResult.setText(currentInput);
    }

    private void onClearClick() {
        currentInput = "0";
        firstOperand = null;
        pendingOperation = null;
        isNewOp = true;
        hasError = false;
        tvExpression.setText("");
        tvResult.setText("0");
    }

    private void onOperatorClick(String op) {
        if (hasError) return;
        try {
            double currentVal = Double.parseDouble(currentInput);
            if (firstOperand != null && pendingOperation != null && !isNewOp) {
                boolean success = calculateIntermediate(currentVal);
                if (!success) return;
            } else {
                firstOperand = currentVal;
            }
            pendingOperation = op;
            isNewOp = true;
            tvExpression.setText(formatNumber(firstOperand) + op);
            tvResult.setText(formatNumber(firstOperand));
        } catch (NumberFormatException e) {
            showError("Недоп.операция");
        }
    }

    private boolean calculateIntermediate(double secondOperand) {
        if (pendingOperation == null || firstOperand == null) return false;
        if (pendingOperation.equals("/") && secondOperand == 0.0) {
            showError(formatNumber(firstOperand) + "/0= " + getString(R.string.error_div_zero));
            return false;
        }

        double result = 0.0;
        switch (pendingOperation) {
            case "+": result = firstOperand + secondOperand; break;
            case "-": result = firstOperand - secondOperand; break;
            case "*": result = firstOperand * secondOperand; break;
            case "/": result = firstOperand / secondOperand; break;
        }

        firstOperand = result;
        currentInput = formatNumber(result);
        return true;
    }

    private void onEqualsClick() {
        if (hasError || firstOperand == null || pendingOperation == null) return;
        try {
            double secondOperand = Double.parseDouble(currentInput);
            String expressionText = formatNumber(firstOperand) + pendingOperation + formatNumber(secondOperand) + "=";

            // Обработка деления на 0
            if (pendingOperation.equals("/") && secondOperand == 0.0) {
                showError(expressionText + " " + getString(R.string.error_div_zero));
                return;
            }

            double result = 0.0;
            switch (pendingOperation) {
                case "+": result = firstOperand + secondOperand; break;
                case "-": result = firstOperand - secondOperand; break;
                case "*": result = firstOperand * secondOperand; break;
                case "/": result = firstOperand / secondOperand; break;
            }

            tvExpression.setText(expressionText);
            currentInput = formatNumber(result);
            tvResult.setText(currentInput);

            firstOperand = null;
            pendingOperation = null;
            isNewOp = true;
        } catch (NumberFormatException e) {
            showError("Недоп.операция");
        }
    }

    private void onSqrtClick() {
        if (hasError) return;
        try {
            double val = Double.parseDouble(currentInput);
            if (val < 0) {
                showError("sqrt(" + formatNumber(val) + ")= " + getString(R.string.error_div_zero));
                return;
            }
            double res = Math.sqrt(val);
            tvExpression.setText("√(" + formatNumber(val) + ")=");
            currentInput = formatNumber(res);
            tvResult.setText(currentInput);
            isNewOp = true;
        } catch (NumberFormatException e) {
            showError("Недоп.операция");
        }
    }

    private void onReciprocalClick() {
        if (hasError) return;
        try {
            double val = Double.parseDouble(currentInput);
            if (val == 0.0) {
                showError("1/0= " + getString(R.string.error_div_zero));
                return;
            }
            double res = 1.0 / val;
            tvExpression.setText("1/(" + formatNumber(val) + ")=");
            currentInput = formatNumber(res);
            tvResult.setText(currentInput);
            isNewOp = true;
        } catch (NumberFormatException e) {
            showError("Недоп.операция");
        }
    }

    private void onMemoryClear() {
        memory = 0.0;
        isMemorySet = false;
        Toast.makeText(this, "Память очищена (MC)", Toast.LENGTH_SHORT).show();
    }

    private void onMemoryRecall() {
        if (isMemorySet) {
            currentInput = formatNumber(memory);
            tvResult.setText(currentInput);
            isNewOp = true;
            Toast.makeText(this, "MR: " + currentInput, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Память пуста", Toast.LENGTH_SHORT).show();
        }
    }

    private void onMemoryStore() {
        try {
            memory = Double.parseDouble(currentInput);
            isMemorySet = true;
            Toast.makeText(this, "Сохранено в память (MS): " + formatNumber(memory), Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException e) {
            showError("Ошибка памяти");
        }
    }

    private void onMemoryAdd() {
        try {
            memory += Double.parseDouble(currentInput);
            isMemorySet = true;
            Toast.makeText(this, "M+: " + formatNumber(memory), Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException e) {
            showError("Ошибка памяти");
        }
    }

    private void onMemorySubtract() {
        try {
            memory -= Double.parseDouble(currentInput);
            isMemorySet = true;
            Toast.makeText(this, "M-: " + formatNumber(memory), Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException e) {
            showError("Ошибка памяти");
        }
    }

    private void showError(String message) {
        hasError = true;
        tvExpression.setText(message);
        tvResult.setText("0");
        firstOperand = null;
        pendingOperation = null;
        isNewOp = true;
    }

    private String formatNumber(double number) {
        if (Double.isInfinite(number) || Double.isNaN(number)) {
            return "Недоп.операция";
        }
        if (number == (long) number) {
            return String.format(Locale.US, "%d", (long) number);
        }
        return DECIMAL_FORMAT.format(number);
    }
}
