package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView numberField;
    TextView tvMemory;
    double memoryResult = 0;
    int positionOperation = 0;

    public static final String MEMORY_PLUS = "MEMORY_PLUS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvMemory = findViewById(R.id.tvMemory);
        numberField = findViewById(R.id.numberField);
        numberField.setText("0");
    }

    //fvkgjgjhgjhgjkgjhgjhgjhxbvcbxcvbx
    public void operClick(View view) {
        String s = "";
        String numberFieldText = numberField.getText().toString();
        //проверка делим ли на ноль и не нажат ли escape
        if (view.getId() != R.id.mc && numberFieldText.equals("cannot be divided by zero")) {
            Log.d("myLog", "было деление на ноль, нельзя ввести знак операции.");
            return;
        }
        ArrayList<String> list;

        switch (view.getId()) {

            //обработка нажатия плюса
            case R.id.plus:
                list = processingOperation("+", numberFieldText, positionOperation);
                numberField.setText(list.get(0));
                positionOperation = Integer.parseInt(list.get(1));
                break;

            //обработка нажатия минуса
            case R.id.minus:
                list = processingOperation("-", numberFieldText, positionOperation);
                numberField.setText(list.get(0));
                positionOperation = Integer.parseInt(list.get(1));
                break;
            //обработка нажатия умножить
            case R.id.umn:
                list = processingOperation("*", numberFieldText, positionOperation);
                numberField.setText(list.get(0));
                positionOperation = Integer.parseInt(list.get(1));
                break;
            //обработка нажатия делить
            case R.id.divide:
                list = processingOperation("/", numberFieldText, positionOperation);
                numberField.setText(list.get(0));
                positionOperation = Integer.parseInt(list.get(1));
                break;

            //обработка нажатия esc
            case R.id.escape:
                numberField.setText("0");
                positionOperation = 0;
                break;

            case R.id.perecent:
                numberField.setText(onClickPercent(positionOperation, numberFieldText));

                break;

            //обработка нажатия знака числа
            case R.id.btnMinusPlus:
                list = minPlus(numberFieldText, positionOperation);
                numberField.setText(list.get(0));
                positionOperation = Integer.parseInt(list.get(1));
                break;

            case R.id.rav:
                numberField.setText(validationNumber(result(numberFieldText, positionOperation)));
                positionOperation = 0;

                break;

            case R.id.btnMplus:
                s = validationNumber(onClickMemory(numberFieldText, positionOperation, memoryResult, MEMORY_PLUS));

                memoryResult = Double.parseDouble(s);
                tvMemory.setText(s);
                numberField.setText(s);

                positionOperation =0;


                break;

            case R.id.btnMminus:
                s = validationNumber(onClickMemory(numberFieldText, positionOperation, memoryResult, "MEMORY_MINUS"));

                memoryResult = Double.parseDouble(s);
                tvMemory.setText(s);
                numberField.setText(s);

                positionOperation =0;
                break;

            case R.id.mc:
                tvMemory.setText("");
                memoryResult = 0;
                break;

            case R.id.btnM:
                numberField.setText(onClickMr(numberFieldText, positionOperation, tvMemory.getText().toString()));
                break;

            case R.id.del:
                if (positionOperation == numberFieldText.length()) {
                    positionOperation = 0;
                }
                if (numberFieldText.length() == 1) {
                    numberField.setText("0");
                }else if (!numberFieldText.equals("")) {
                    numberField.setText(numberFieldText.substring(0, numberFieldText.length() - 1));

                }
                break;

            case R.id.dot:
                numberField.setText(onClickDot(numberFieldText, positionOperation));
                break;


        }


        //обрабатывем напжатия цыфер
        Button button = (Button) view;
        String textButton = button.getText().toString();

        if (textButton.equals("0") || textButton.equals("1") || textButton.equals("2")
                || textButton.equals("3") || textButton.equals("4") || textButton.equals("5")
                || textButton.equals("6") || textButton.equals("7") || textButton.equals("8")
                || textButton.equals("9")) {

            numberField.setText(onClickNumbers(numberFieldText, textButton, positionOperation));

        }


    }


    public static String onClickMr (String numberFieldText, int positionOperation, String tvMemory) {
        if (tvMemory.equals("")) {
            numberFieldText = "0";
        }
        //введено только 1 число
        else if (positionOperation == 0) {
            numberFieldText = tvMemory;
        }
        //введено 1 число и знак операции
        else if (positionOperation == numberFieldText.length()) {
            numberFieldText += tvMemory;
        }
        //введено 2 числа
        else if (positionOperation != 0 && positionOperation < numberFieldText.length()) {
            String s = numberFieldText.substring(positionOperation + 1, numberFieldText.length());
            s = tvMemory;
            numberFieldText = s;

        }

        return numberFieldText;
    }


    public static String onClickMemory (String numberFieldText, int positionOperation, double memoryResult, String singMemory) {
        BigDecimal num1 = null;

        String res = result(numberFieldText, positionOperation);
        num1 = new BigDecimal(res);

        BigDecimal num2 = new BigDecimal(String.valueOf(memoryResult));

        if (singMemory.equals(MEMORY_PLUS)) {
            num1 = num1.add(num2);
        }

        if (singMemory.equals("MEMORY_MINUS")) {
            num1 = num2.subtract(num1);
            //Log.d("myLog", "MEMORY_MINUS");

        }

        // Log.d("myLog", "num1 " + num1);


        return num1.toString();

    }





    public static String onClickPercent (int positionOperation, String numberFieldText) {
        BigDecimal i = null;
        BigDecimal num1;
        BigDecimal num2 = new BigDecimal("100");
        BigDecimal num3;


        if (positionOperation == 0) {
            num1 = new BigDecimal("1");
            num3 = new BigDecimal(numberFieldText);
            num1 = num1.divide(num2);
            num1 = num1.multiply(num3);
            numberFieldText = validationNumber(num1.toString());
            // Log.d("myLog", "введено только 1 число, нужно взять " + numberFieldText + " процентов от 1");

        }else if (positionOperation == numberFieldText.length()) {
            num1 = new BigDecimal(numberFieldText.substring(1, positionOperation - 1));
            num3 = new BigDecimal("0");
            num1 = num1.divide(num2);
            num1 = num1.multiply(num3);
            numberFieldText += validationNumber(num1.toString());

            //Log.d("myLog", "введено 1 число  и знак, нужно взять " + numberFieldText.substring(0, numberFieldText.length() - 1) + " процентов от 1");


        } else if (positionOperation != 0 && positionOperation < numberFieldText.length()) {
            //    Log.d("myLog", "введено 1 число, знак и 2 число. Нужно взять " + numberFieldText.substring(0, positionOperation - 1) + " процентов от " + numberFieldText.substring(positionOperation, numberFieldText.length()));
            num1 = new BigDecimal(numberFieldText.substring(0, positionOperation - 1));
            num3 = new BigDecimal(numberFieldText.substring(positionOperation, numberFieldText.length()));
//            Log.d("myLog", "n1 " + num1 + " n3 " + num3);
            num1 = num1.divide(num2);
            num1 = num1.multiply(num3);
            numberFieldText = validationNumber(num1.toString());
        }

        return numberFieldText;


    }





    public static String onClickDot (String numberFieldText, int positionOperation) {
        int positionDot;

        //обрабатываем 1 число
        if (positionOperation == 0) {
            positionDot = numberFieldText.indexOf(".");
            if (positionDot == -1 && !numberFieldText.equals("")) {
                // numberField.append(".");
                numberFieldText += ".";
            }
            //обрабатываем 2 число
        } else {
            String secondNumber = numberFieldText.substring(positionOperation, numberFieldText.length());
            positionDot = secondNumber.indexOf(".");
            if (positionDot == -1 && !secondNumber.equals("")) {
                //numberField.append(".");
                numberFieldText += ".";
            }
        }

        return numberFieldText;
    }




    public static String onClickNumbers (String numberFieldText, String textButton, int positionOperation) {
        //проверка делим ли на ноль
        if (numberFieldText.equals("cannot be divided by zero")) {
            //  Log.d("myLog", "было деление на ноль, нельзя ввести знак операции.");
            return "cannot be divided by zero";
        }

        //валидация 0 в первом числе
        if (positionOperation == 0) {
            //когда 0 стоит первым
            if (numberFieldText.length() == 1 && numberFieldText.equals("0")) {
//                numberField.setText(textButton);
                numberFieldText = textButton;
                //когда первым стоит -0
            } else if (numberFieldText.length() == 2 && numberFieldText.equals("-0")) {
//                numberField.setText("-" + textButton);
                numberFieldText = "-" + textButton;
                //когда все хорошо
            } else {
                // numberField.append(textButton);
                numberFieldText += textButton;
            }

            //валидация 0 в втором числе
        } else {
            String secondNumber = numberFieldText.substring(positionOperation, numberFieldText.length());
            //когда 0 стоит первым
            if (secondNumber.length() == 1 && secondNumber.equals("0")  ) {
                //numberField.setText(numberFieldText.substring(0, numberFieldText.length() - 1));
                //numberField.append(textButton);
                numberFieldText = numberFieldText.substring(0, numberFieldText.length() - 1) + textButton;

                //когда первым стоит -0
            } else if (secondNumber.length() == 2 && secondNumber.equals("-0")) {
                // Log.d("myLog", "-0 ");
                // numberField.setText(numberFieldText.substring(0, positionOperation) + "-" + textButton);
                numberFieldText = numberFieldText.substring(0, positionOperation) + "-" + textButton;
                //когда все хорошо
            } else {
                // numberField.append(textButton);
                numberFieldText += textButton;
            }
        }
        return numberFieldText;

    }



    //обработка минуса перед числом
    public static ArrayList<String> minPlus (String numberFieldText, int positionOperation) {
        //numberField.setText( "-" + numberField.getText().toString());
        //меняем знак первого числа до знака операции включительно
        if (positionOperation == 0 || positionOperation == numberFieldText.length()) {
            if (!numberFieldText.substring(0, 1).equals("-")) {
                // numberField.setText("-" + numberFieldText);
                numberFieldText = "-" + numberFieldText;
                if (positionOperation != 0) {
                    positionOperation++;
                }
                //   Log.d("myLog", "1 число устанавливаемт знак минус ");

            } else {
                // numberField.setText(numberFieldText.substring(1, numberFieldText.length()));
                numberFieldText = numberFieldText.substring(1, numberFieldText.length());

                if (positionOperation != 0) {
                    positionOperation--;
                }
                //  Log.d("myLog", "1 число устанавливаемт знак плюс ");

            }
            //меняем знак второго числа после знака операции
        } else {
            String secondNumber = numberFieldText.substring(positionOperation, numberFieldText.length());
            // Log.d("myLog", "secondNumber: " + secondNumber);

            if (!secondNumber.substring(0, 1).equals("-")) {
                //numberField.setText(numberFieldText.substring(0, positionOperation) + "-" + secondNumber);
                numberFieldText = numberFieldText.substring(0, positionOperation) + "-" + secondNumber;

                //   Log.d("myLog", "2 число устанавливаемт знак минус ");

            } else {
                //numberField.setText(numberFieldText.substring(0, positionOperation) + secondNumber.substring(1, secondNumber.length()));
                numberFieldText = numberFieldText.substring(0, positionOperation) + secondNumber.substring(1, secondNumber.length());

                //   Log.d("myLog", "2 число устанавливаемт знак плюс ");
            }
        }
        ArrayList<String> list = new ArrayList<>();
        list.add(numberFieldText);
        list.add(String.valueOf(positionOperation));
        return list;

    }




    public static   ArrayList<String> processingOperation(String typeOperation, String numberFieldText, int positionOperation) {

        //String numberFieldText = numberField.getText().toString();

        //знак операции нажимается повторно (нету второго числа)
        if (numberFieldText.length() == positionOperation ) {
            //Log.d("myLog", "знак в подряд");
//            numberField.setText(numberFieldText.substring(0, numberFieldText.length() - 1));
//            numberField.append(typeOperation);

            numberFieldText = numberFieldText.substring(0, numberFieldText.length() - 1) + typeOperation;


//            positionOperation = numberField.getText().toString().length();
            positionOperation = numberFieldText.length();


            //знак операции нажимается повторно (есть второе число)
        } else if (positionOperation != 0) {
            //  Log.d("myLog", "нужно делать действие чисел  и добавить знак");
            String secondNumber = numberFieldText.substring(positionOperation, numberFieldText.length());
            secondNumber = validationNumber(secondNumber);
            numberFieldText = numberFieldText.substring(0, positionOperation) + secondNumber;
            //String res = result(numberFieldText);
            String res = result(numberFieldText, positionOperation);
            if (!res.equals("cannot be divided by zero")) {
                res = validationNumber(res) + typeOperation;
            }
            //numberField.setText(res);
            //positionOperation = numberField.getText().toString().length();

            numberFieldText = res;
            positionOperation = numberFieldText.length();

            //знак операции не был введен значит введено только первое число
        } else {
//            numberField.setText(validationNumber(numberFieldText));
//            numberField.append(typeOperation);
//            positionOperation = numberField.getText().toString().length();

            numberFieldText = validationNumber(numberFieldText) + typeOperation;
            positionOperation = numberFieldText.length();

            //  Log.d("myLog", "первый ввод знака операции");

        }

        //  Log.d("myLog", "position operation: " + String.valueOf(positionOperation));


        ArrayList<String> list = new ArrayList<>();
        list.add(numberFieldText);
        list.add(String.valueOf(positionOperation));
        return  list;
    }




    //убирает в конце числа лишние нули и точку
    public static String validationNumber (String num) {

        String lastSymbol = num.substring(num.length() - 1, num.length());

        //удаляет лишние нули в конце числа при условии что есть точка
        if (num.indexOf(".") != -1) {

            int i = 0;
            while (true) {
                if (lastSymbol.equals("0")) {
                    num = num.substring(0, num.length() - 1);
                    lastSymbol = num.substring(num.length() - 1, num.length());
                    i++;

                } else {
//                    Log.d("myLog", "удалили " + i + " нулей в конце числа ");
                    break;
                }
            }
        }

        //удаляет точку в конце числа
        if (lastSymbol.equals(".")) {
            num = num.substring(0, num.length() - 1);
//            Log.d("myLog", "удалили точку в конце числа ");

        }

        return num;
    }



    //передает результат операции между 2 числами
    public static String result(String numberFieldText, int positionOperation) {

        String firstNumber="0";
        String secondNumber="0";
        String typeOperation;

        //введено 1 число
        if (positionOperation == 0) {
            return numberFieldText;
        }

        firstNumber = numberFieldText.substring(0, positionOperation - 1);
        typeOperation = numberFieldText.substring(positionOperation - 1, positionOperation);


        //введено 1 число и знак операции
        if (positionOperation == numberFieldText.length()) {
            secondNumber = firstNumber;
        }
        //введено 2 числа
        else if (positionOperation != 0 && positionOperation < numberFieldText.length()) {
            secondNumber = numberFieldText.substring(positionOperation, numberFieldText.length());

        }

        BigDecimal num1 = new BigDecimal(firstNumber);
        BigDecimal num2 = new BigDecimal(secondNumber);

        //операция прибавления
        if (typeOperation.equals("+")) {
            num2 = num2.add(num1);
//            Log.d("myLog", "сумма: " + num2);
            //операция отнимания
        } else if (typeOperation.equals("-")) {
            num2 = num1.subtract(num2);
            //  Log.d("myLog", "разница: " + num2);
            //операция умножения
        } else if (typeOperation.equals("*")) {
            num2 = num1.multiply(num2);
            //    Log.d("myLog", "множенное: " + num2);
        }
        //операция деления
        else if (typeOperation.equals("/")) {
            if (secondNumber.equals("0")) {
                return "cannot be divided by zero";
            }
            num2 = num1.divide(num2, 10, BigDecimal.ROUND_DOWN);
            //  Log.d("myLog", "поделенное: " + num2);

        }

        return String.valueOf(num2);
    }







}