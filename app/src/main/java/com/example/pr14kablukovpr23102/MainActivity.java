package com.example.pr14kablukovpr23102;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Объявление переменных
    private EditText etDisplay;
    private Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    private Button btnAdd, btnSubtract, btnMultiply, btnDivide;
    private Button btnEquals, btnClear, btnBackspace, btnDecimal;

    private String currentNumber = "";
    private String firstNumber = "";
    private String operation = "";
    private boolean isNewOperation = true;
    private boolean hasDecimal = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etDisplay = findViewById(R.id.etDisplay);

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

        btnAdd = findViewById(R.id.btnAdd);
        btnSubtract = findViewById(R.id.btnSubtract);
        btnMultiply = findViewById(R.id.btnMultiply);
        btnDivide = findViewById(R.id.btnDivide);

        btnEquals = findViewById(R.id.btnEquals);
        btnClear = findViewById(R.id.btnClear);
        btnBackspace = findViewById(R.id.btnBackspace);
        btnDecimal = findViewById(R.id.btnDecimal);

        setClickListeners();
    }

    private void setClickListeners() {
        // Установка слушателей для цифровых кнопок
        btn0.setOnClickListener(v -> appendNumber("0"));
        btn1.setOnClickListener(v -> appendNumber("1"));
        btn2.setOnClickListener(v -> appendNumber("2"));
        btn3.setOnClickListener(v -> appendNumber("3"));
        btn4.setOnClickListener(v -> appendNumber("4"));
        btn5.setOnClickListener(v -> appendNumber("5"));
        btn6.setOnClickListener(v -> appendNumber("6"));
        btn7.setOnClickListener(v -> appendNumber("7"));
        btn8.setOnClickListener(v -> appendNumber("8"));
        btn9.setOnClickListener(v -> appendNumber("9"));

        // Установка слушателей для операций
        btnAdd.setOnClickListener(v -> setOperation("+"));
        btnSubtract.setOnClickListener(v -> setOperation("-"));
        btnMultiply.setOnClickListener(v -> setOperation("×"));
        btnDivide.setOnClickListener(v -> setOperation("÷"));

        // Установка слушателей для специальных кнопок
        btnEquals.setOnClickListener(v -> calculateResult());
        btnClear.setOnClickListener(v -> clearAll());
        btnBackspace.setOnClickListener(v -> backspace());
        btnDecimal.setOnClickListener(v -> appendDecimal());
    }

    private void appendNumber(String number) {
        if (isNewOperation) {
            currentNumber = number;
            isNewOperation = false;
        } else {
            currentNumber += number;
        }
        updateDisplay(currentNumber);
    }

    private void appendDecimal() {
        if (!hasDecimal) {
            if (currentNumber.isEmpty() || isNewOperation) {
                currentNumber = "0.";
                isNewOperation = false;
            } else {
                currentNumber += ".";
            }
            hasDecimal = true;
            updateDisplay(currentNumber);
        }
    }

    private void setOperation(String op) {
        if (!currentNumber.isEmpty()) {
            if (!firstNumber.isEmpty() && !operation.isEmpty() && !isNewOperation) {
                calculateResult();
            }
            firstNumber = currentNumber;
            operation = op;
            isNewOperation = true;
            hasDecimal = false;
        }
    }

    private void calculateResult() {
        if (!firstNumber.isEmpty() && !currentNumber.isEmpty() && !operation.isEmpty()) {
            try {
                double num1 = Double.parseDouble(firstNumber);
                double num2 = Double.parseDouble(currentNumber);
                double result = 0;

                switch (operation) {
                    case "+":
                        result = num1 + num2;
                        break;
                    case "-":
                        result = num1 - num2;
                        break;
                    case "×":
                        result = num1 * num2;
                        break;
                    case "÷":
                        if (num2 == 0) {
                            Toast.makeText(this, "Ошибка: деление на ноль!", Toast.LENGTH_SHORT).show();
                            clearAll();
                            return;
                        }
                        result = num1 / num2;
                        break;
                }

                if (result == (long) result) {
                    currentNumber = String.valueOf((long) result);
                } else {
                    currentNumber = String.format("%.10f", result).replaceAll("0*$", "").replaceAll("\\.$", "");
                }

                updateDisplay(currentNumber);
                firstNumber = "";
                operation = "";
                isNewOperation = true;
                hasDecimal = currentNumber.contains(".");

            } catch (Exception e) {
                Toast.makeText(this, "Ошибка вычисления", Toast.LENGTH_SHORT).show();
                clearAll();
            }
        }
    }

    private void clearAll() {
        currentNumber = "";
        firstNumber = "";
        operation = "";
        isNewOperation = true;
        hasDecimal = false;
        updateDisplay("0");
    }

    private void backspace() {
        if (!currentNumber.isEmpty()) {
            if (currentNumber.charAt(currentNumber.length() - 1) == '.') {
                hasDecimal = false;
            }

            currentNumber = currentNumber.substring(0, currentNumber.length() - 1);

            if (currentNumber.isEmpty()) {
                updateDisplay("0");
                isNewOperation = true;
            } else {
                updateDisplay(currentNumber);
            }
        }
    }

    private void updateDisplay(String text) {
        etDisplay.setText(text);
    }
}