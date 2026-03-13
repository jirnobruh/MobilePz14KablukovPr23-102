package com.example.pr14kablukovpr23102;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText numDisplay;
    private Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    private Button btnAdd, btnSubtract, btnMultiply, btnDivide;
    private Button btnEquals, btnClear, btnBackspace, btnDecimal, btnPlusMinus;

    private String currentNumber = "";
    private String firstNumber = "";
    private String operation = "";
    private boolean isNewOperation = true;
    private boolean hasDecimal = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numDisplay = findViewById(R.id.numDisplay);

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
        btnPlusMinus = findViewById(R.id.btnPlusMinus);

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
        btnPlusMinus.setOnClickListener(v -> toggleSign());
    }

    private void appendNumber(String number) {
        if (isNewOperation || currentNumber.equals("0")) {
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
                double num1 = Double.parseDouble(firstNumber.replace(",", "."));
                double num2 = Double.parseDouble(currentNumber.replace(",", "."));
                double result = 0;

                switch (operation) {
                    case "+": result = num1 + num2; break;
                    case "-": result = num1 - num2; break;
                    case "×": result = num1 * num2; break;
                    case "÷":
                        if (num2 == 0) {
                            Toast.makeText(this, "Ошибка: деление на ноль!", Toast.LENGTH_SHORT).show();
                            clearAll();
                            updateDisplay("Ошибка деления на ноль!");
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

    private void toggleSign() {
        if (currentNumber.isEmpty() || currentNumber.equals("0")) return;

        if (currentNumber.startsWith("-")) {
            currentNumber = currentNumber.substring(1);
        } else {
            currentNumber = "-" + currentNumber;
        }
        updateDisplay(currentNumber);
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

            if (currentNumber.isEmpty() || currentNumber.equals("-")) {
                currentNumber = "0"; // Вместо пустой строки ставим "0"
                isNewOperation = true; // Указываем, что следующее нажатие заменит этот 0
                hasDecimal = false;
            }
            updateDisplay(currentNumber);
        }
    }

    private void updateDisplay(String text) {
        numDisplay.setText(text);
    }
}